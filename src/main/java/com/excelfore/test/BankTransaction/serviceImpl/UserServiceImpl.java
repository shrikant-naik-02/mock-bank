package com.excelfore.test.BankTransaction.serviceImpl;

import com.excelfore.test.BankTransaction.exception.UserAlreadyHasAccountException;
import com.excelfore.test.BankTransaction.model.User;
import com.excelfore.test.BankTransaction.repository.UserRepository;
import com.excelfore.test.BankTransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
//        Username or accountHoldername unique taking decision on the basis of that only
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            throw new UserAlreadyHasAccountException("Username already taken.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("USER"); // If default role if not provided
        }

        return userRepository.save(user);
    }
}
