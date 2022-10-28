package yavs.service;

import org.springframework.stereotype.Service;
import yavs.model.lobby.Lobby;
import yavs.repository.lobby.LobbyRepository;

@Service
public class LobbyService implements IBasedService<Lobby, Long> {
    private final LobbyRepository repo;

    public LobbyService(LobbyRepository repo) {
        this.repo = repo;
    }

    @Override
    public Lobby save(Lobby obj) {
        return null;
    }

    @Override
    public Lobby update(Lobby obj) {
        return null;
    }

    @Override
    public Lobby getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
