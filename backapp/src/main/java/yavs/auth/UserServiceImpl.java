package yavs.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        var user = repo.findByEmail(email).or
        System.out.println("в ебаном лоад бай юзер хуй");
        return repo.findByToken(email).orElseThrow(() -> new UsernameNotFoundException("")).toUserDetails();
    }

    public User create(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        return repo.save(user);
    }

    public void delete(User user) {

    }

    public boolean validate(String customToken) {
        System.out.println("валидация");
        return true;
    }
}
