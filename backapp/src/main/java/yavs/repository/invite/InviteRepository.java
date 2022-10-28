package yavs.repository.invite;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yavs.model.invite.Invite;

import java.util.UUID;

@Repository
public interface InviteRepository extends CrudRepository<Invite, UUID> {
}
