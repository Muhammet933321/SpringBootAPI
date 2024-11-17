package com.Blix.UnityAPI.controllers.Weapon;

import com.Blix.UnityAPI.entities.Weapon;
import com.Blix.UnityAPI.services.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weapons")
public class WeaponGetController {

    @Autowired
    private WeaponService weaponService;

    @GetMapping
    public List<Weapon> getAllWeapons() {
        return weaponService.getAllWeapons();
    }

    @GetMapping("/get/by-id/{id}")
    public ResponseEntity<Weapon> getWeaponById(@PathVariable Long id) {
        Weapon weapon = weaponService.getWeaponById(id);
        return weapon != null ? ResponseEntity.ok(weapon) : ResponseEntity.notFound().build();
    }
    @GetMapping("/get/by-name/{name}")
    public ResponseEntity<Weapon> getWeaponByName(@PathVariable String name) {
        Optional<Weapon> weapon = weaponService.getWeaponByName(name);
        return weapon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }
}
