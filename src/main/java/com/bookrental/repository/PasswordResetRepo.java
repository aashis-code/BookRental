package com.bookrental.repository;

import com.bookrental.model.PasswordResetToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepo extends GenericRepo<PasswordResetToken, Integer>{

    Optional<PasswordResetToken> findByToken(String token);
}
