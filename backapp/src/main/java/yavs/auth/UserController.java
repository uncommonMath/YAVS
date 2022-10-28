package yavs.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yavs.model.user.User;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userDetailsService) {
        this.userService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    public ResponseEntity<User> update(User entity, String token) {
        return null;
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('write')")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var user = new User();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);
    }
}
