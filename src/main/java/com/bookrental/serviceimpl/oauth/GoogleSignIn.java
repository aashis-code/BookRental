package com.bookrental.serviceimpl.oauth;

import com.bookrental.model.Member;
import com.bookrental.repository.MemberRepo;
import com.bookrental.security.JwtService;
import com.bookrental.security.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GoogleSignIn {

    @Value(value = "${spring.security.oauth2.client.registration.google.clientId}")
    private String CLIENT_ID;

    @Value(value = "${spring.security.oauth2.client.registration.google.clientSecret}")
    private String CLIENT_SECRET;

    @Value(value = "${security.jwt.refresh-token-expiration-time}")
    private long jwtRefereshTokenExpiration;


    private final RestTemplate restTemplate;

    private final MemberDetailsService memberDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final MemberRepo memberRepo;

    Logger logger = LoggerFactory.getLogger(GoogleSignIn.class);

    private static String TOKEN_END_POINT = "https://oauth2.googleapis.com/token";


    public String handleGoogleSignInAndReturnToken(String authCode) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", authCode);
            params.add("client_id", CLIENT_ID);
            params.add("client_secret", CLIENT_SECRET);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = this.restTemplate.postForEntity(TOKEN_END_POINT, request, Map.class);

            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");
                UserDetails userDetails = null;

                try {
                    userDetails = this.memberDetailsService.loadUserByUsername(email);
                } catch (Exception e) {
                    Member member = new Member();
                    member.setEmail(email);
                    member.setName(email);
                    member.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    this.memberRepo.save(member);
                }
                return this.jwtService.generateToken(userDetails, jwtRefereshTokenExpiration);
            }

        } catch (Exception e) {
            logger.info("Exception occur : " + e.getMessage());

        }
        return null;
    }

}
