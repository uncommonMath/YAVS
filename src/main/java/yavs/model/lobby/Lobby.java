package yavs.model.lobby;

import lombok.*;
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

    @OneToMany(mappedBy = "lobby")
    private List<Room> rooms;

    @ManyToMany(mappedBy = "lobbies")
    private List<User> participants;

}
