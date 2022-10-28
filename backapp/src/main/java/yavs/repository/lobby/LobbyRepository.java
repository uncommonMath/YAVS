package yavs.repository.lobby;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yavs.model.lobby.Lobby;

@Repository
public interface LobbyRepository extends CrudRepository<Lobby, Long> {
}
