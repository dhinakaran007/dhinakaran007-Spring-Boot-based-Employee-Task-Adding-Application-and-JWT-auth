package com.employee.timetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.timetrack.bean.Associate;
import com.employee.timetrack.repo.AssociateRepository;

@Service
public class LoginService {

    @Autowired
    private AssociateRepository associateRepository;

    public boolean authenticate(String username, String password) {
        Associate associate = associateRepository.findByUsername(username);
        if (associate != null) {
            System.out.println("Associate found with username: " + associate.getUsername());
            System.out.println("Stored password: " + associate.getPassword());
            System.out.println("Provided password: " + password);
            
            // Assuming the password is stored in plain text in the database for demonstration purposes
            // In production, passwords should be hashed and compared securely
            boolean passwordsMatch = associate.getPassword().equals(password);
            System.out.println("Passwords match: " + passwordsMatch);
            
            return passwordsMatch;
        }
        System.out.println("No associate found with username: " + username);
        return false;
    }

}
