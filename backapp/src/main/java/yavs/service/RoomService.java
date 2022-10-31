package yavs.service;

import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import yavs.model.lobby.Room;
import yavs.model.user.MutedUser;
import yavs.model.user.User;
import yavs.repository.lobby.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService implements IBasedService<Room, Long> {
    private final RoomRepository repo;
    private final SimpUserRegistry simpUserRegistry;
    private final ArrayList<MutedUser> blacklist;

    public RoomService(RoomRepository repo, SimpUserRegistry simpUserRegistry) {
        this.repo = repo;
        this.simpUserRegistry = simpUserRegistry;
        this.blacklist = new ArrayList<>();
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

    public boolean isUserMuted(MutedUser user) {
        return blacklist.contains(user);
    }

    public void muteUser(MutedUser user) {
        blacklist.add(user);
    }

    public List<User> getAllUsers(Long roomId) {
        return repo.findById(roomId).orElse(null).getParticipants();
    }
}