package yavs.repository.lobby;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yavs.model.lobby.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
}