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
@Table(name = "role_permission_mapping", uniqueConstraints = {@UniqueConstraint(name = "uk_role_id", columnNames = {"role"}),
                                                              @UniqueConstraint(name = "uk_permission_id", columnNames = {"permission"})})
public class RolePermissionMapping extends Auditable {

    @Id
    @SequenceGenerator(name = "role_permission_seq_gen", sequenceName = "role_permission_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_permission_seq_gen")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_role_permission_role"))
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_role_permission_permission"))
    private Permission permission;
}
