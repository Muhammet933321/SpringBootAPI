// Update işlemleri için AuthController içerisine yeni metod ekliyoruz
package com.Blix.UnityAPI.controllers.User;

import com.Blix.UnityAPI.entities.User;
import com.Blix.UnityAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserUpdateController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setLevel(updatedUser.getLevel());
            user.setGoldAmount(updatedUser.getGoldAmount());
            user.setExperienceLevel(updatedUser.getExperienceLevel());
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

}
