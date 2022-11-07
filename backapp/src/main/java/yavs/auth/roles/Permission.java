package yavs.auth.roles;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Permission {
    USER("user"),
    LOBBY_ADMIN("lobby_admin"),
    SYSTEM_ADMIN("system_admin");

    private final String permission;

    public String getPermissionAsText() {
        return permission;
    }
}
