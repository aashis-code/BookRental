package com.bookrental.controller;

import com.bookrental.dto.RefreshTokenDto;
import com.bookrental.serviceimpl.oauth.GoogleSignIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.helper.ResponseObject;
import com.bookrental.security.JwtService;
import com.bookrental.security.LoginMemberDto;
import com.bookrental.security.LoginResponse;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for managing Authentication related activities.")
public class AuthenticationController extends BaseController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final GoogleSignIn googleSignIn;

    //    Log In user
    @Operation(
            summary = "Get Token",
            description = "Retrieve a list of all users with optional filtering by status.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully Login.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginMemberDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request.")
            }
    )
    @PostMapping("/login")
    public ResponseObject authenticate(@RequestBody LoginMemberDto loginMemberDto) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginMemberDto.getEmail(), loginMemberDto.getPassword()));

        if (authenticate.isAuthenticated()) {
            String jwtToken = jwtService.generateToken((UserDetails) authenticate.getPrincipal());
            LoginResponse loginResponse = LoginResponse.builder().token(jwtToken)
                    .expiresIn(jwtService.getExpirationTime()).build();
            return new ResponseObject(true, "", loginResponse);
        } else {
            throw new ResourceNotFoundException("Enter Valid user details.", null);
        }

    }

@PostMapping("/get-access-token")
public ResponseObject getAccessToken(@RequestBody RefreshTokenDto refreshTokenDto){
    String refreshToken = refreshTokenDto.getRefreshToken();
    if(!jwtService.validateToken(refreshToken)){
        return getFailureResponse("Invalid Refresh Token", null);
    }
    return getSuccessResponse(null, null);
}

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        System.out.println(code);
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse("Token received.", Map.of("token",this.googleSignIn.handleGoogleSignInAndReturnToken(code))));
    }

}
