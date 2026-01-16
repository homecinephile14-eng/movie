package movie.movie.repository;

import movie.movie.domain.User;
import movie.movie.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUserOrderByCreatedAtDesc(User user);
}
