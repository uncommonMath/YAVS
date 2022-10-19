package yavs.service;

import org.springframework.stereotype.Service;
import yavs.repository.lobby.LobbyRepository;

@Service
public class LobbyService {
    private final LobbyRepository repo;

    public LobbyService(LobbyRepository repo) {
        this.repo = repo;
    }
}
