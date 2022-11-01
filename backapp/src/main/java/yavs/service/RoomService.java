package yavs.service;

import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import yavs.auth.UserServiceImpl;
import yavs.model.lobby.Room;
import yavs.model.user.User;
import yavs.repository.lobby.RoomRepository;

import java.util.List;

@Service
public class RoomService implements IBasedService<Room, Long> {
    private final RoomRepository repo;
    private final SimpUserRegistry simpUserRegistry;
    private final UserServiceImpl userService;

    public RoomService(RoomRepository repo, SimpUserRegistry simpUserRegistry, UserServiceImpl userService) {
        this.repo = repo;
        this.simpUserRegistry = simpUserRegistry;
        this.userService = userService;
    }

    @Override
    public Room save(Room room) {
        return repo.save(room);
    }

    @Override
    public Room update(Room room) {
        return repo.save(room);
    }

    @Override
    public Room getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public boolean isUserMuted(User user, Long roomId) {
        var room = repo.findById(roomId).orElse(null);
        if (room != null) {
            return room.getMutedUsers().contains(user);
        } else {
            return false;
        }
    }

    public void muteUserByHost(Long roomId, User user) {
//        blacklist.add(user);
        var room = repo.findById(roomId).orElse(null);
        if (room != null) {
            room.getMutedUsers().add(user);
            update(room);
        }
    }

    public void muteUserByAnotherUser(Long roomId, Long requesterUserId, Long toBeMutedUserId) {
        var room = repo.findById(roomId).orElse(null);
        if (room != null) {
            var requesterUser = room.getParticipants().stream().filter(user -> user.getId().equals(requesterUserId)).findFirst().get();
            var toBeMutedUser = room.getParticipants().stream().filter(user -> user.getId().equals(toBeMutedUserId)).findFirst().get();

            requesterUser.addToBlacklist(toBeMutedUser);
            userService.update(requesterUser);
        }
    }

    public List<User> getAllUsers(Long roomId) {
        return repo.findById(roomId).orElse(null).getParticipants();
    }

    public User getUserByUsername(String name) {
        return userService.findByEmail(name);
    }
}