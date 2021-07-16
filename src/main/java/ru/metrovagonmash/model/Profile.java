
package ru.metrovagonmash.model;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.metrovagonmash.config.security.Permission;
import ru.metrovagonmash.config.security.Role;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profile")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

/*    @Override
    public Long getId() {
        return id;
    }*/

   /* enum Role {
        ADMIN(Sets.newHashSet(ADMIN_CREATE,ADMIN_READ,ADMIN_UPDATE,ADMIN_DELETE)),
        EMPLOYEE(Sets.newHashSet(USER_UPDATE,USER_READ, USER_CREATE, USER_UPDATE));
        public final Set<Permission> permissions;

        Role(Set<Permission> permissions) {
            this.permissions = permissions;
        }

        public Set<Permission> getPermissions() {
            return permissions;
        }

        public Set<SimpleGrantedAuthority> getAuthorities() {
            return getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .collect(Collectors.toSet());
        }
    }*/

}

