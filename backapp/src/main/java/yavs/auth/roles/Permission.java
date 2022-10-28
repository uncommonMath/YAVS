package yavs.auth.roles;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write");

    private final String permission;

    public String getPermissionAsText() {
        return permission;
    }
}
