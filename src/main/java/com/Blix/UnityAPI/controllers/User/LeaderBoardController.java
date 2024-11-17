package com.Blix.UnityAPI.controllers.User;

import com.Blix.UnityAPI.entities.User;
import com.Blix.UnityAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderBoardController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getLeaderboard() {
        List<User> leaderboard = userService.getTop5Users();
        return ResponseEntity.ok(leaderboard);
    }
}
