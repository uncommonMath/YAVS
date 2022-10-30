package yavs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yavs.model.lobby.Lobby;
import yavs.service.LobbyService;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/lobby")
public class LobbyController {
    private final LobbyService service;

    public LobbyController(LobbyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Lobby> create(@RequestBody Lobby entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Lobby> update(@RequestBody Lobby entity) {
        return new ResponseEntity<>(service.update(entity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lobby> getById(@PathVariable Long id) {
        var lobby = service.getById(id);
        if (lobby != null)
            return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
        else
            throw new EntityNotFoundException("Entity with id=" + id + "not found!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (service.getById(id) != null) {
            service.delete(id);
            return ResponseEntity.ok("Successfully deleted.");
        }
        else
            throw new EntityNotFoundException("Entity with id=" + id + "not found!");
    }
}