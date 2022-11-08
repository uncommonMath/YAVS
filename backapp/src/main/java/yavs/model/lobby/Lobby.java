package yavs.model.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yavs.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lobbies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lobby_id")
    private Long id;

    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "room_lobby",
            joinColumns = {@JoinColumn(name = "lobby_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private List<Room> rooms;

    @ManyToMany
    @JoinTable(
            name = "user_lobby",
            joinColumns = {@JoinColumn(name = "lobby_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> participants;

    @ManyToMany
    @JoinTable(
            name = "enabled_notifications_users",
            joinColumns = {@JoinColumn(name = "lobby_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> enabledNotificationsUsers;
}
