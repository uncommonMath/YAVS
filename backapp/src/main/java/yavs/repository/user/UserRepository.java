package yavs.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yavs.model.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    long count();

    User findByEmail(String email);
}
