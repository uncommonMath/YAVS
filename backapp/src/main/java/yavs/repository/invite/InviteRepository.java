package yavs.repository.invite;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import yavs.model.invite.Invite;
import yavs.model.lobby.Lobby;

import java.util.UUID;

@Repository
public interface InviteRepository extends CrudRepository<Invite, UUID> {

    void deleteByLobby(Lobby lobby);

    Invite getByLobby(Lobby lobby);
}
