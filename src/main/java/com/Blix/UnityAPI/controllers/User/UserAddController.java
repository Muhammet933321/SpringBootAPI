package com.Blix.UnityAPI.controllers.User;


import com.Blix.UnityAPI.entities.User;
import com.Blix.UnityAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserAddController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken");
        }
        user.setLevel(1);
        user.setGoldAmount(0);
        user.setExperienceLevel(0);

        userRepository.save(user);
        return ResponseEntity.ok("User added successfully");
    }

}
