package yavs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/default")
public interface IController<T, U> {
    @PostMapping
    ResponseEntity<T> create(@RequestBody T entity, @RequestHeader("Authorization") String token);

    @PutMapping
    ResponseEntity<T> update(@RequestBody T entity, @RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<T> getById(@PathVariable("id") U id);

    @DeleteMapping
    ResponseEntity<T> deleteById(@RequestParam("id") U id);
}
