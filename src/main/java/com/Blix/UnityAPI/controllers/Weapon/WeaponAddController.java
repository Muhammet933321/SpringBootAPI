package com.Blix.UnityAPI.controllers.Weapon;

import com.Blix.UnityAPI.entities.Weapon;
import com.Blix.UnityAPI.services.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weapons")
public class WeaponAddController {

    @Autowired
    private WeaponService weaponService;

    @PostMapping("/add")
    public ResponseEntity<Weapon> createWeapon(@RequestBody Weapon weapon) {
        Weapon savedWeapon = weaponService.saveWeapon(weapon);
        return ResponseEntity.ok(savedWeapon);
    }
}
