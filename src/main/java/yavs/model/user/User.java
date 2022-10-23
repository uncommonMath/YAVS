package yavs.model.user;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import yavs.auth.roles.Role;
import yavs.auth.roles.Status;
import yavs.model.lobby.Lobby;
import yavs.model.lobby.Room;

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


//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_room",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "room_id")}
//    )
//    private List<Room> rooms;
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_lobby",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "lobby_id")}
//    )
//    private List<Lobby> lobbies;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

//    public UserDTO toUserDTO() {
//        return new UserDTO(id, username, email);
//    }

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
}