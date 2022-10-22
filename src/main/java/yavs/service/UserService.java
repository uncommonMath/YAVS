package yavs.service;

import org.springframework.stereotype.Service;
import yavs.model.user.User;
import yavs.model.user.UserDTO;
import yavs.repository.user.UserRepository;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(UserDTO newUser) {
        return repo.save(newUser.toUser());
    }
}
