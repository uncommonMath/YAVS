package yavs.auth.roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(Set.of(Permission.WRITE, Permission.READ)),
    USER(Set.of(Permission.READ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionAsText()))
                .collect(Collectors.toSet());
    }
}
