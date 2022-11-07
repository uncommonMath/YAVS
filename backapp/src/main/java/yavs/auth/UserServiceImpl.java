package yavs.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yavs.model.user.User;
import yavs.repository.user.UserRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String token) {
        var user = repo.findByToken(token).orElse(null);
        return user != null ? user.toUserDetails() : null;
    }

    public User create(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        return repo.save(user);
    }

    public void delete(User user) {
         repo.delete(user);
    }

    public boolean validate(String token) {
        var user =  repo.findByToken(token).orElse(null);
        return user != null;
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public User update(User user) {
        return repo.save(user);
    }
}
