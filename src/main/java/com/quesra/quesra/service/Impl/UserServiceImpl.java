package com.quesra.quesra.service.Impl;

import com.quesra.quesra.domain.User;
import com.quesra.quesra.dto.ConnectDto;
import com.quesra.quesra.repository.UserRepository;
import com.quesra.quesra.service.UserService;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(long id, User user) {

        User existingUser = userRepository.getOne(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail().toString());
        return userRepository.save(existingUser);

    }

    @Override
    public User connectUser(ConnectDto user) {
        try {
            User existedUser = userRepository.findByEmail(user.getEmail());
            System.out.println("salut");
            System.out.println(existedUser);

            boolean b = BCrypt.checkpw(user.getPassword(), existedUser.getPassword());
            System.out.println("Email : " + user.getEmail());
            if(!b) throw new RuntimeException("invalid email or password");
            return existedUser;


        }
        catch (Exception e){
            throw new RuntimeException("invalid email or password");
        }
    }

    @Override
    public void delete(long id) {
        User existingUser = userRepository.getOne(id);
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
