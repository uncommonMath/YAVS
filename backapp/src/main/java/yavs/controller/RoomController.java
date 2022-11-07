package yavs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;
import yavs.model.lobby.Room;
import yavs.model.messages.ChatMessage;
import yavs.model.messages.YTMessage;
import yavs.model.user.User;
import yavs.service.RoomService;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    private final SimpUserRegistry simpUserRegistry;
    private final SimpMessagingTemplate template;
    private static final Pattern PATTERN = Pattern.compile("watch\\?v=([^&?/\\\\]+)");

    public RoomController(RoomService service, SimpUserRegistry simpUserRegistry, SimpMessagingTemplate template) {
        this.roomService = service;
        this.simpUserRegistry = simpUserRegistry;
        this.template = template;
    }

    @MessageMapping("/video/status/{roomId}")
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

    /**
     * chat message-handling method
     */
    @MessageMapping("/chat/{roomId}")
    public void chat(@DestinationVariable Long roomId, @Payload ChatMessage message, Principal principal) {
        var sender = roomService.getUserByUsername(principal.getName());
        var users = roomService.getAllUsers(roomId);
//        System.out.println(simpleBrokerRegistration.toString());
        if (!roomService.isUserMuted(sender, roomId)) {
            users.stream()
                    .filter(user -> !user.getBlacklist().contains(sender.getId()))
                    .forEach(user -> template.convertAndSendToUser(user.getEmail(),
                            "/out/chat", message));
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

    @PostMapping("/mute/global")
    public ResponseEntity<String> muteUser(@RequestBody User userToBeMuted,@RequestParam Long roomId) {
        roomService.muteUserByHost(roomId, userToBeMuted);
        return new ResponseEntity<>("Muted", HttpStatus.OK);
    }

    @PostMapping("/mute/p2p")
    public ResponseEntity<String> muteUser(@RequestParam Long roomId, @RequestParam Long requesterUserId, @RequestBody Long toBeMutedUserId) {
        roomService.muteUserByAnotherUser(roomId, requesterUserId, toBeMutedUserId);
        return new ResponseEntity<>("Muted", HttpStatus.OK);
    }
}