package com.example.ApplicantCourseApp.service;

import com.example.ApplicantCourseApp.domain.User;
import com.example.ApplicantCourseApp.exception.AuthenticationFailureException;
import com.example.ApplicantCourseApp.exception.CustomValidationException;
import com.example.ApplicantCourseApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.ApplicantCourseApp.validator.EmailValidator.validateEmail;
import static com.example.ApplicantCourseApp.validator.UserValidator.validatePassword;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User checkAuth(String email, String pass) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            if (passwordEncoder.matches(pass, user.get().getPassword())) {
                return user.get();
            } else {
                throw new AuthenticationFailureException("User email/password is incorrect.");
            }
        } else {
            throw new ResourceNotFoundException(String.format("user with %s id not found", email));
        }
    }

    public User updateUser(User user, Long userId) {
        validateUser(user);
        userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("user with %s id not found", userId)));
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return user;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("user with %s id not found", id)));
    }

    public Optional<User> getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user;
        } else {
            throw new ResourceNotFoundException(String.format("user with %s email not found", email));
        }
    }

    public User mapToUserResponse(User user) {
        User responseModel = new User();
        responseModel.setEmail(user.getEmail());
        responseModel.setAdmin(user.getAdmin());
        return responseModel;
    }

    public void validateUser(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateUniqueness(user.getEmail());
    }

    public void validateUniqueness(String email) {
        Optional<User> user = getByEmail(email);
        if (user.isPresent()) {
            throw new CustomValidationException("email already exists");
        }
    }
}
