package yavs.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import yavs.model.lobby.Room;
import yavs.service.RoomService;

import javax.persistence.Entity;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/room")
public class RoomController implements IController<Room, Long> {
    private final RoomService service;

    private static final Room DEFAULT_ROOM = new Room();

    public RoomController(RoomService service) {
        this.service = service;
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
