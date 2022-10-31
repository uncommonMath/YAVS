package yavs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;
import yavs.model.lobby.Room;
import yavs.model.messages.ChatMessage;
import yavs.model.messages.YTMessage;
import yavs.model.user.MutedUser;
import yavs.service.RoomService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    private static final Room DEFAULT_ROOM = new Room();
    private final SimpUserRegistry simpUserRegistry;

    private final SimpMessagingTemplate template;

    public RoomController(RoomService service, SimpUserRegistry simpUserRegistry, SimpMessagingTemplate template) {
        this.roomService = service;
        this.simpUserRegistry = simpUserRegistry;
        this.template = template;
    }

    @GetMapping("default")
    public ResponseEntity<Room> getDefaultRoom() {
        return ResponseEntity.ok(DEFAULT_ROOM);
    }



    private static final Pattern PATTERN = Pattern.compile("watch\\?v=([^&?/\\\\]+)");

    @MessageMapping("/video/status/{roomId}")
//    @SendToUser("/out/{roomId}/")
    public void videoStatusChangedNotify(@DestinationVariable Long roomId, @Payload YTMessage message) {
        var users = roomService.getAllUsers(roomId);
        if (message.getState() != null) {
            var newMessage = new YTMessage(message.getFrom(), null, message.getState(), message.getTime());
            users.forEach(user -> template.convertAndSendToUser(user.getEmail(), "/out/video/status", newMessage));
        } else {
            var originalUrl = message.getText();
            var matcher = PATTERN.matcher(originalUrl);
            var ytId = "";
            if (matcher.find()) {
                ytId = matcher.group(1);
            }
            var newMessage = new YTMessage("SERVER", ytId, null, null);
            users.forEach(user -> template.convertAndSendToUser(user.getEmail(), "/out/video/status", newMessage));
        }
    }

    @MessageMapping("/chat/all1/{id}")
//    @SendToUser("/queue/{id}")
    public void send2(@DestinationVariable int id, MessageHeaders headers, @Payload YTMessage chatMessage) {
//        System.out.println(chatMessage);
        System.out.println("id = " + id);
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
        System.out.println(simpUserRegistry.getUserCount());
//        System.out.println(simpUserRegistry.getU);
        simpUserRegistry.getUsers().forEach(x -> System.out.println(x.getName()));
        template.convertAndSendToUser("user@mail.com", "/out/10", new YTMessage("eblan", "pizda", 0, 0.0));
    }

    /**
     * chat message-handling method
     */
    @MessageMapping("/chat/{roomId}")
    @SendTo("/out/chat")
    public void send(@DestinationVariable Long roomId, @Payload ChatMessage message) {
        var users = roomService.getAllUsers(roomId);

        simpUserRegistry.getUsers().forEach(x -> System.out.println(x.getName()));
//        roomService.addToBlacklist("user@mail.com", 10L);
//        var subscriptions = new ArrayList<>(new ArrayList<>(simpUserRegistry.getUser("user@mail.com").getSessions()).get(0).getSubscriptions());
//
//        roomService.addToBlacklist("user@mail.com", 10L);
        if (!roomService.isUserMuted(new MutedUser(message.getFrom(), roomId))) {
            users.forEach(user -> template.convertAndSendToUser(user.getEmail(), "/out/chat", message));
        }
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

    @PutMapping("/mute/{username}/{roomId}")
    public ResponseEntity<String> muteUser(@PathVariable String username,@PathVariable Long roomId) {
        roomService.muteUser(new MutedUser(username, roomId));
        return new ResponseEntity<>("Muted", HttpStatus.OK);
    }
}