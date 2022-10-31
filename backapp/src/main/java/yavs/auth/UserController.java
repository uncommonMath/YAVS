package yavs.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yavs.model.user.User;

import javax.persistence.EntityNotFoundException;

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

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        if (userService.getUserById(user.getId()) != null) {
            return ResponseEntity.ok(userService.update(user));
        } else {
            throw new EntityNotFoundException("User with id=" + user.getId() + " not found!");
        }
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('write')")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var user = new User();
        System.out.println("kek");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);
    }
}
