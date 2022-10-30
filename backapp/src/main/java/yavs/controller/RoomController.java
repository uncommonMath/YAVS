package yavs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import yavs.model.lobby.Room;
import yavs.model.messages.ChatMessage;
import yavs.model.messages.YTMessage;
import yavs.service.RoomService;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Pattern PATTERN = Pattern.compile("watch\\?v=([^&?/\\\\]+)");

    @MessageMapping("/")
    public void processMessage(@Payload YTMessage chatMessage) {
        if (chatMessage.getState() != null) {
            messagingTemplate.convertAndSendToUser(
                    "default",
                    "/",
                    new YTMessage(chatMessage.getFrom(), null, chatMessage.getState(), chatMessage.getTime()));
            System.out.println(chatMessage);
            return;
        }
        var originalUrl = chatMessage.getText();
        var matcher = PATTERN.matcher(originalUrl);
        var ytId = "";
        if (matcher.find()) {
            ytId = matcher.group(1);
        }
        messagingTemplate.convertAndSendToUser(
                "default",
                "/",
                new YTMessage("SERVER", ytId, null, null));
        System.out.println(chatMessage);
    }

    @MessageMapping("/video/status/{roomId}")
    @SendTo("out/{roomId}/")
    public YTMessage videoStatusChangedNotify(@DestinationVariable Long roomId, @Payload YTMessage message) {
        if (message.getState() != null) {
            return new YTMessage(message.getFrom(), null, message.getState(), message.getTime());
        } else {
            var originalUrl = message.getText();
            var matcher = PATTERN.matcher(originalUrl);
            var ytId = "";
            if (matcher.find()) {
                ytId = matcher.group(1);
            }
            return new YTMessage("SERVER", ytId, null, null);
        }
    }

    @MessageMapping("/lol/{id}")
    @SendTo("/out/govno/{id}")
    public YTMessage send(@DestinationVariable int id, MessageHeaders headers, @Payload YTMessage chatMessage) {
        System.out.println(chatMessage);
        System.out.println("id = " + id);
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
        return new YTMessage("eblan", "pizda", 0, 0.0);
    }

    @MessageMapping("/govno")
    @SendToUser("/out/govno")
    public YTMessage govno(MessageHeaders headers, @Payload YTMessage chatMessage) {
        System.out.println(chatMessage);
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
        return new YTMessage("eblan", "pizda", 0, 0.0);
    }

    /**
     * chat message-handling method
     * @return received message
     */
    @MessageMapping("/chat/{roomId}")
    @SendTo("/out/chat/{roomId}")
    public ChatMessage send(@DestinationVariable Long roomId, @Payload ChatMessage message) {
        return message;
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