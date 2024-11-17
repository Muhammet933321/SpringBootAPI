package com.Blix.UnityAPI.controllers.Weapon;

import com.Blix.UnityAPI.entities.Weapon;
import com.Blix.UnityAPI.services.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weapons")
public class WeaponUpdateController {

    @Autowired
    private WeaponService weaponService;

    @PutMapping("/update/by-id/{id}")
    public ResponseEntity<Weapon> updateWeapon(@PathVariable Long id, @RequestBody Weapon weapon) {
        weapon.setId(id);
        Weapon updatedWeapon = weaponService.saveWeapon(weapon);
        return ResponseEntity.ok(updatedWeapon);
    }

    @PutMapping("/update/by-name/{name}")
    public ResponseEntity<Weapon> updateWeaponByName(@PathVariable String name, @RequestBody Weapon weaponDetails) {
        Optional<Weapon> updatedWeapon = weaponService.updateWeaponByName(name, weaponDetails);

        if (updatedWeapon.isPresent()) {
            return ResponseEntity.ok(updatedWeapon.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
