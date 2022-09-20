package ru.nova.novalib.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nova.novalib.dao.BookmarkRepository;
import ru.nova.novalib.dao.UserRepository;
import ru.nova.novalib.domain.Bookmark;
import ru.nova.novalib.domain.Role;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.domain.dto.UserDto;

import java.time.LocalDate;
import java.util.Collections;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private BookmarkRepository bookmarkRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, BookmarkRepository bookmarkRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    @Transactional
    public boolean save(UserDto userDto){
        if(userRepository.findByUsername(userDto.getUserName()).isPresent()){
            return false;
        }
        User user = new User();

        user.setUsername(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getUserPassword()));
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setUserCreated(LocalDate.now());
        user.addBookmark(new Bookmark("Читаю", Bookmark.Type.READING, user));
        user.addBookmark(new Bookmark("Буду читать", Bookmark.Type.WILL, user));
        user.addBookmark(new Bookmark("Прочитано", Bookmark.Type.FINISHED, user));
        user.addBookmark(new Bookmark("Брошено", Bookmark.Type.STOPPED, user));
        user.addBookmark(new Bookmark("Не интересно", Bookmark.Type.NOT_INTERESTING, user));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByUsername(login).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

}
