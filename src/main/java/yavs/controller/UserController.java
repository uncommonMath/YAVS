package yavs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yavs.model.user.User;
import yavs.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController implements IController<User, Long> {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public void govno() {
        service.govno();
    }

    @Override
    public ResponseEntity<User> create(User entity, String token) {
        return new ResponseEntity<>(new User(10L, "lol", "kek@mail.com", null, null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> update(User entity, String token) {
        return null;
    }

    @Override
    public ResponseEntity<User> getById(Long id) {
        var user = new User(10L, "lol", "kek@mail.com", null, null);
        System.out.println(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> deleteById(Long id) {
        return null;
    }
}
