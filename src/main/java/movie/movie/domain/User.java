package movie.movie.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(String username, String password) {
        validate(username, password);
        this.username = username;
        this.password = password;
    }

    public void change(String username, String password) {
        validate(username, password);
        this.username = username;
        this.password = password;
    }

    private void validate(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("아이디는 필수입니다.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
    }
}
