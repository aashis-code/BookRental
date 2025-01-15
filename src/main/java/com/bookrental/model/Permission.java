package com.bookrental.model;

import com.bookrental.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="permission", uniqueConstraints = @UniqueConstraint(name="uk_permission_name", columnNames = {"name"}))
public class Permission extends Auditable {

    @Id
    @SequenceGenerator(name = "permission_seq_gen", sequenceName = "permission_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq_gen")
    Integer id;


    String name;

    String description;
}
