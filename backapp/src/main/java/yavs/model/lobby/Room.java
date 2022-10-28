package yavs.model.lobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import yavs.model.user.User;

import javax.persistence.*;
import java.util.List;

//import org.hibernate.Hibernate;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@RequiredArgsConstructor
public class Room {
    @Id
    @Column(name = "room_id")
    private Long id;

    @ManyToMany
    private List<User> participants;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private Lobby lobby;

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
