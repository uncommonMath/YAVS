package yavs.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
//import yavs.controller.IController;
import yavs.model.user.User;
import yavs.model.user.UserDTO;
import yavs.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController /* implements IController<User, Long> */ {
    private final UserService service;
    private final UserServiceImpl userDetailsService;

    public UserController(UserService service, UserServiceImpl userDetailsService) {
        this.service = service;
        this.userDetailsService = userDetailsService;
    }

//    @Override
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userDetailsService.create(user));
    }

//    @Override
    public ResponseEntity<User> update(User entity, String token) {
        return null;
    }

//    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var user = new User();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @Override
    public ResponseEntity<User> deleteById(Long id) {
        return null;
    }
}
