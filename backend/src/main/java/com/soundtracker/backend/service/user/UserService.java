package com.soundtracker.backend.service.user;
import com.soundtracker.backend.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserByEmail(String email);
    User updateUser(User user);
    void deleteUser(String email);
}
