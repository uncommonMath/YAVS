package yavs.model.user;

import lombok.*;
import org.hibernate.Hibernate;
import yavs.model.lobby.Lobby;
import yavs.model.lobby.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_room",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private List<Room> rooms;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_lobby",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "lobby_id")}
    )
    private List<Lobby> lobbies;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}