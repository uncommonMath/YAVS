package yavs.service;

import org.springframework.stereotype.Service;
import yavs.model.lobby.Room;
import yavs.repository.lobby.RoomRepository;

@Service
public class RoomService implements IBasedService<Room, Long> {
    private final RoomRepository repo;

    public RoomService(RoomRepository repo) {
        this.repo = repo;
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
}
