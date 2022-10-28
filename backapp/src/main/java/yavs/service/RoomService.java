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
    public Room save(Room obj) {
        return null;
    }

    @Override
    public Room update(Room obj) {
        return null;
    }

    @Override
    public Room getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
