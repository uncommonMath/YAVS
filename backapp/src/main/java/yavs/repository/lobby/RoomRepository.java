package yavs.repository.lobby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yavs.model.lobby.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}