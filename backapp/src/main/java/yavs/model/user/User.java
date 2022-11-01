package yavs.model.user;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import yavs.auth.roles.Role;
import yavs.auth.roles.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    private String token;

    @ElementCollection
    @CollectionTable(
            name = "mutedUsers",
            joinColumns = {@JoinColumn(name = "requesterUser_id")}
    )
    @Column(name = "mutedUser_id")
    private List<Long> blacklist;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public UserDetails toUserDetails() {
        return new org.springframework.security.core.userdetails.User(
                email,
                password,
                true,
                true,
                true,
                true,
                role.getAuthorities()
        );
    }

    public void addToBlacklist(User mutedUser) {
        blacklist.add(mutedUser.getId());
    }
}