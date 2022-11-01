package yavs.model.lobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import yavs.model.user.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@RequiredArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_room",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> participants;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "mutedUsers_room",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> mutedUsers;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }
}
