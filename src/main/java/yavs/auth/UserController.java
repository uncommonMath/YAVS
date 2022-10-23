package yavs.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yavs.model.user.User;
import yavs.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    private final UserServiceImpl userDetailsService;

    public UserController(UserService service, UserServiceImpl userDetailsService) {
        this.service = service;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userDetailsService.create(user));
    }

    public ResponseEntity<User> update(User entity, String token) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var user = new User();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> deleteById(Long id) {
        return null;
    }
}
