package movie.movie.service;


import lombok.RequiredArgsConstructor;
import movie.movie.domain.User;
import movie.movie.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long join(String username, String password) {
        validateRule(username, password);
        validateDuplicate(username);
        User user = new User(username, password);
        userRepository.save(user);
        return user.getId();
    }

    private void validateRule(String username, String password) {
        if (username == null || username.length() < 6 || username.length() > 50) {
            throw new IllegalStateException("아이디는 6자 이상 50자 이하여야 합니다.");
        }
        if (password == null || password.length() < 6 || password.length() > 50) {
            throw new IllegalStateException("비밀번호는 6자 이상 50자 이하여야 합니다.");
        }
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) return null;
        if(!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    private void validateDuplicate(String username) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean checkDuplicateName(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
