package yavs.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yavs.model.invite.Invite;
import yavs.model.lobby.Lobby;
import yavs.model.notification.Alert;
import yavs.model.notification.Notification;
import yavs.repository.invite.InviteRepository;
import yavs.repository.lobby.LobbyRepository;
import yavs.repository.lobby.RoomRepository;

@Service
public class LobbyService implements IBasedService<Lobby, Long> {
    private final LobbyRepository lobbyRepository;
    private final RoomRepository roomRepository;
    private final InviteRepository inviteRepository;
    private final SimpMessagingTemplate template;

    public LobbyService(LobbyRepository repo, RoomRepository roomRepository, InviteRepository inviteRepository, SimpMessagingTemplate template) {
        this.lobbyRepository = repo;
        this.roomRepository = roomRepository;
        this.inviteRepository = inviteRepository;
        this.template = template;
    }

    @Override
    public Lobby save(Lobby lobby) {
        var savedLobby = lobbyRepository.save(lobby);
        inviteRepository.save(new Invite(savedLobby));
        return savedLobby;
    }

    @Override
    public Lobby update(Lobby lobby) {
        var oldLobbyInstance = lobbyRepository.findById(lobby.getId()).orElse(null);
        if (oldLobbyInstance == null) {
            throw new RuntimeException();
        } else {
            var oldRooms = oldLobbyInstance.getRooms();
            var newRooms = lobby.getRooms();
            oldRooms.stream().filter(oldRoom -> !newRooms.contains(oldRoom)).forEach(roomRepository::delete);
            roomRepository.saveAll(newRooms);
            return lobbyRepository.save(lobby);
        }
    }

    @Transactional
    public void sendAlertToUsers(Alert alert) {
        var lobby = lobbyRepository.findById(alert.getLobbyId()).orElse(null);
        if (lobby != null) {
            var usersToSendAlert = lobby.getEnabledNotificationsUsers();
            usersToSendAlert.forEach(user -> template.convertAndSendToUser(
                    user.getEmail(), "/out/alert",
                    new Notification(user.getUsername(), lobby.getName(), "some text about alert")
            ));
        }
    }

    @Override
    public Lobby getById(Long id) {
        return lobbyRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        lobbyRepository.deleteById(id);
    }
}
