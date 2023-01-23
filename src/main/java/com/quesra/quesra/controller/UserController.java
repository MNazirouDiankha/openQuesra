package com.quesra.quesra.controller;


import com.quesra.quesra.domain.ConfirmationToken;
import com.quesra.quesra.domain.User;
import com.quesra.quesra.dto.ConnectDto;
import com.quesra.quesra.repository.UserRepository;
import com.quesra.quesra.service.ConfirmationTokenService;
import com.quesra.quesra.service.EmailSenderService;
import com.quesra.quesra.service.UserService;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;

    public UserController(UserService userService, EmailSenderService emailSenderService, ConfirmationTokenService confirmationTokenService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.confirmationTokenService = confirmationTokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create-user")
    public User saveUser(@RequestBody User user)
    {
        User newUser = userService.saveUser(user);
        try {
            emailSenderService.sendEmail(user,newUser.getEmail(),"Activation de Compte");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return  userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") long id){
        return userService.getUserById(id);
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") long id , @RequestBody User user){
        return userService.updateUser(id, user);
    }
    @PostMapping("connection")
    public User connect(@RequestBody ConnectDto user){
        return userService.connectUser(user);
    }
    @DeleteMapping("/{id}")
    public List<User> deleteuser(@PathVariable("id") long id) {
        userService.delete(id);
        return userService.getAllUsers();
    }

    @PostMapping("/register")  public User registerUser(@RequestBody User user)
        {
        User newUser = userService.saveUser(user);
        try {
            emailSenderService.sendEmail(user,newUser.getEmail(),"Activation de Compte");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return newUser;
        }
    @PutMapping("/activate") public void activate(@RequestParam("email") String email)
    {
        System.out.println("email sender activate " +email);
        User userActivate = userService.findByEmail(email);
        userActivate.setEnabled(true);
    }
}

