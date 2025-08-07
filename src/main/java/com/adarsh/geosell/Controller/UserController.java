package com.adarsh.geosell.Controller;
import com.adarsh.geosell.Entity.User;
import com.adarsh.geosell.Enum.BusinessType;
import com.adarsh.geosell.Enum.SubscriptionType;
import com.adarsh.geosell.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            user.setId(id);
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/business-type/{businessType}")
    public ResponseEntity<List<User>> getUsersByBusinessType(@PathVariable BusinessType businessType) {
        List<User> users = userService.getUsersByBusinessType(businessType);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/subscription/{subscriptionType}")
    public ResponseEntity<List<User>> getUsersBySubscriptionType(@PathVariable SubscriptionType subscriptionType) {
        List<User> users = userService.getUsersBySubscriptionType(subscriptionType);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count/subscription/{subscriptionType}")
    public ResponseEntity<Long> countUsersBySubscription(@PathVariable SubscriptionType subscriptionType) {
        Long count = userService.countUsersBySubscription(subscriptionType);
        return ResponseEntity.ok(count);
    }
}
