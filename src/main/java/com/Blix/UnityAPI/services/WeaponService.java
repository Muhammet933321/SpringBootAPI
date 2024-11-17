package com.Blix.UnityAPI.services;

import com.Blix.UnityAPI.entities.Weapon;
import com.Blix.UnityAPI.repositories.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeaponService {

    @Autowired
    private WeaponRepository weaponRepository;

    // Tüm silahları al
    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }

    // ID ile silah almak
    public Weapon getWeaponById(Long id) {
        return weaponRepository.findById(id).orElse(null);
    }

    // Yeni silah kaydet
    public Weapon saveWeapon(Weapon weapon) {
        return weaponRepository.save(weapon);
    }

    // Silah silme
    public boolean deleteWeapon(Long id) {
        if (weaponRepository.existsById(id)) {
            weaponRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Silah ismine göre almak
    public Optional<Weapon> getWeaponByName(String name) {
        return weaponRepository.findByName(name);
    }

    public Optional<Weapon> updateWeaponByName(String name, Weapon weaponDetails) {
        Optional<Weapon> optionalWeapon = weaponRepository.findByName(name);

        if (optionalWeapon.isPresent()) {
            Weapon weapon = optionalWeapon.get();

            weapon.setDamage(weaponDetails.getDamage());
            weapon.setCooldown(weaponDetails.getCooldown());
            weapon.setName(weaponDetails.getName());
            return Optional.of(weaponRepository.save(weapon));
        }

        return Optional.empty(); // Silah bulunamazsa boş döndür
    }
}