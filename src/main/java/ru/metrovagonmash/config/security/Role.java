package ru.metrovagonmash.config.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.metrovagonmash.config.security.Permission.*;

public enum Role {
    ADMIN(Sets.newHashSet(Permission.ADMIN_CREATE,ADMIN_READ,ADMIN_UPDATE,ADMIN_DELETE)),
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
}
