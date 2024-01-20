package saf.io.passwordmanager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saf.io.passwordmanager.entity.User;
import saf.io.passwordmanager.model.CredentialsRequest;
import saf.io.passwordmanager.model.UserRequest;
import saf.io.passwordmanager.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserService service;

  @Autowired
  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("/all")
  public List<User> getAllUsers() {
    return service.getAllUsers();
  }

  @PostMapping("/")
  public ResponseEntity<Void> addUser(@RequestBody UserRequest userRequest) {
    service.addUser(userRequest);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateUser(
      @PathVariable("id") String userId, @RequestBody UserRequest userRequest) {
    service.updateUser(userId, userRequest);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") String userId) {
    service.deleteUser(userId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/account")
  public ResponseEntity<Void> addPassword(
      @RequestHeader("id") String userId, @RequestBody CredentialsRequest credentialsRequest) {
    service.addPassword(userId, credentialsRequest);
    return ResponseEntity.ok().build();
  }
}
