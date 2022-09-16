package ru.nova.novalib.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.UserRepository;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.domain.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public boolean save(UserDto userDto){
        if(userRepo.findByUserName(userDto.getUserName()).isPresent()){
            return false;
        }
        User user = new User();

        user.setUserName(userDto.getUserName());
        user.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
        user.setUserRole(User.Roles.USER);
        user.setUserCreated(LocalDate.now());
        userRepo.save(user);
        return true;
    }

    public User findByLogin(String login) {
        return userRepo.findByUserName(login).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

}
