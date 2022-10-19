package yavs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yavs.model.lobby.Room;
import yavs.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController implements IController<Room, Long> {
    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @Override
//    @PostMapping
    public ResponseEntity<Room> create(Room entity, String token) {
        return null;
    }

    @Override
//    @PostMapping
    public ResponseEntity<Room> update(Room entity, String token) {
        return null;
    }

    @Override
//    @GetMapping
    public ResponseEntity<Room> getById(Long id) {
        return null;
    }

    @Override
//    @DeleteMapping
    public ResponseEntity<Room> deleteById(Long id) {
        return null;
    }
}
