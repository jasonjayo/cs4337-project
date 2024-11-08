package com.example.manager.service;

import com.example.manager.model.Manager;
import com.example.manager.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Optional<Manager> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public Manager updateManager(Long id, Manager updatedManager) {
        Optional<Manager> existingManagerOptional = managerRepository.findById(id);
        if (existingManagerOptional.isPresent()) {
            Manager existingManager = existingManagerOptional.get();
            existingManager.setName(updatedManager.getName());
            existingManager.setEmail(updatedManager.getEmail());
            existingManager.setPhoneNumber(updatedManager.getPhoneNumber());
            return managerRepository.save(existingManager);
        } else {
            return null;
        }
    }

    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}