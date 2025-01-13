package com.bookrental.model;

import com.bookrental.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "password_reset_token", uniqueConstraints = { @UniqueConstraint(name="uk_password_reset_token_token", columnNames = { "token"})})
public class PasswordResetToken extends Auditable {

    @Id
    @SequenceGenerator(name = "password_reset_token_seq_gen", sequenceName = "password_reset_token_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_seq_gen")
    private Integer id;
    private String token;
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_password_reset_token_member"))
    private Member member;

    @PrePersist
    public void setExpiryDate() {
        this.expiryDate = LocalDateTime.now().plusMinutes(15);
    }
}
