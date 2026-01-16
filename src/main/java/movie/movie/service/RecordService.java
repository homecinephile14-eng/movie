package movie.movie.service;

import lombok.RequiredArgsConstructor;
import movie.movie.domain.Record;
import movie.movie.domain.User;
import movie.movie.repository.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    public List<Record> findMyRecords(User user) {
        return recordRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional
    public void saveRecord(User user, MultipartFile file, String content) throws IOException {
        String projectPath = System.getProperty("user.dir") + "/uploads/";
        File directory = new File(projectPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        Record record = new Record(user, "/images/" + fileName, content);
        recordRepository.save(record);
    }

    public Record getRecord(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
    }

    @Transactional
    public void deleteRecord(Long id, User user) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));

        if (!record.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        recordRepository.delete(record);
    }

    @Transactional
    public void updateRecord(Long id, User user, MultipartFile file, String content) throws IOException {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));

        if (!record.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        String imagePath = record.getImagePath();

        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/uploads/";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            imagePath = "/images/" + fileName;
        }

        record.change(imagePath, content);
    }
}
