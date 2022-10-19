package yavs.service;

import org.springframework.stereotype.Service;
import yavs.repository.user.UserRepository;

@Service
public class UserService {

    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void govno() {
        repo.count();
    }
}
