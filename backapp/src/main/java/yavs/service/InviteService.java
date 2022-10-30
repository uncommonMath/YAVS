package yavs.service;

import org.springframework.stereotype.Service;
import yavs.model.invite.Invite;
import yavs.model.lobby.Lobby;
import yavs.repository.invite.InviteRepository;

import java.util.UUID;

@Service
public class InviteService implements IBasedService<Invite, UUID> {
    private final InviteRepository repo;

    public InviteService(InviteRepository repo) {
        this.repo = repo;
    }

    @Override
    public Invite save(Invite invite) {
        return repo.save(invite);
    }

    @Override
    public Invite update(Invite invite) {
        return null;
    }

    public Invite revoke(Lobby lobby) {
        repo.deleteByLobby(lobby);
        return repo.save(new Invite(lobby));
    }

    @Override
    public Invite getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    public Invite getByLobby(Lobby lobby) {
        return repo.getByLobby(lobby);
    }
}
