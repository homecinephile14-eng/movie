package movie.movie.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String imagePath;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Record(User user, String imagePath, String content) {
        validate(user, imagePath, content);
        this.user = user;
        this.imagePath = imagePath;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void change(String imagePath, String content) {
        if (imagePath == null || imagePath.isBlank()) {
            throw new IllegalArgumentException("이미지 경로는 필수입니다.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("컨텐츠는 필수입니다.");
        }
        this.imagePath = imagePath;
        this.content = content;
    }

    private void validate(User user, String imagePath, String content) {
        if (user == null) {
            throw new IllegalArgumentException("사용자는 필수입니다.");
        }
        if (imagePath == null || imagePath.isBlank()) {
            throw new IllegalArgumentException("이미지 경로는 필수입니다.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("컨텐츠는 필수입니다.");
        }
    }
}
