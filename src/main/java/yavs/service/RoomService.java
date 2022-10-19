package yavs.service;

import org.springframework.stereotype.Service;
import yavs.repository.lobby.RoomRepository;

@Service
public class RoomService {
    private final RoomRepository repo;

    public RoomService(RoomRepository repo) {
        this.repo = repo;
    }
}
