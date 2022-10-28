package yavs.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import yavs.model.lobby.Room;
import yavs.service.RoomService;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    private static final Room DEFAULT_ROOM = new Room();

    public RoomController(RoomService service) {
        this.roomService = service;
    }

    @GetMapping("default")
    public ResponseEntity<Room> getDefaultRoom() {
        return ResponseEntity.ok(DEFAULT_ROOM);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Message {
        private String from;
        private String text;
        private Integer state;
        private Double time;
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Pattern PATTERN = Pattern.compile("watch\\?v=([^&?/\\\\]+)");

    @MessageMapping("/")
    public void processMessage(@Payload Message chatMessage) {
        if (chatMessage.state != null) {
            messagingTemplate.convertAndSendToUser(
                    "default",
                    "/",
                    new Message(chatMessage.from, null, chatMessage.state, chatMessage.time));
            System.out.println(chatMessage);
            return;
        }
        var originalUrl = chatMessage.text;
        var matcher = PATTERN.matcher(originalUrl);
        var ytId = "";
        if (matcher.find()) {
            ytId = matcher.group(1);
        }
        messagingTemplate.convertAndSendToUser(
                "default",
                "/",
                new Message("SERVER", ytId, null, null));
        System.out.println(chatMessage);
    }

    @PutMapping
    public ResponseEntity<Room> update(@RequestBody Room room) {
        if (roomService.getById(room.getId()) != null) {
            return new ResponseEntity<>(roomService.update(room), HttpStatus.OK);
        } else
            throw new EntityNotFoundException("Room with id=" + room.getId() + "doesn't exist");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        var room = roomService.getById(id);
        if (room != null) {
            return new ResponseEntity<>(room, HttpStatus.OK);
        } else
            throw new EntityNotFoundException("Room with id=" + id + "doesn't exist");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        var room = roomService.getById(id);
        if (room != null) {
            roomService.delete(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } else
            throw new EntityNotFoundException("Room with id=" + id + "doesn't exist");
    }
}