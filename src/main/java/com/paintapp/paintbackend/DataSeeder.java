package com.paintapp.paintbackend;


import com.paintapp.paintbackend.model.User;
import com.paintapp.paintbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(1)
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String[][] REQUIRED_USERS = {
            {"admin2", "admin123"},
            {"user1", "password1"},
            {"user2", "password2"},
            {"user3", "password3"}
    };

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (String[] userData : REQUIRED_USERS) {
            String username = userData[0];
            String password = userData[1];

            if (!userRepository.findByUsername(username).isPresent()) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
            }
        }
    }
}
