package yavs.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yavs.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    long count();

    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String email);

    @Override
    void deleteById(Long id);
}
