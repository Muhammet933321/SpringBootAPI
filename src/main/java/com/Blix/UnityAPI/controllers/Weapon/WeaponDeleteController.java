package com.Blix.UnityAPI.controllers.Weapon;

import com.Blix.UnityAPI.services.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weapons")
public class WeaponDeleteController {

    @Autowired
    private WeaponService weaponService;

    @DeleteMapping("/delete/by-id/{id}")
    public ResponseEntity<String> deleteWeapon(@PathVariable Long id) {
        boolean deleted = weaponService.deleteWeapon(id);
        if (deleted) {
            return ResponseEntity.ok("Weapon deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Weapon not found.");
        }
    }
}
