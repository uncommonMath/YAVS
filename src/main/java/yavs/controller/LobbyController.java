package yavs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yavs.model.lobby.Lobby;
import yavs.service.LobbyService;

@RestController
@RequestMapping("/lobby")
public class LobbyController implements IController<Lobby, Long> {
    private final LobbyService service;

    public LobbyController(LobbyService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Lobby> create(Lobby entity, String token) {
        return null;
    }

    @Override
    public ResponseEntity<Lobby> update(Lobby entity, String token) {
        return null;
    }

    @Override
    public ResponseEntity<Lobby> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Lobby> deleteById(Long id) {
        return null;
    }
}
