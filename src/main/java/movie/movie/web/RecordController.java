package movie.movie.web;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.Record;
import movie.movie.domain.User;
import movie.movie.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/upload")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file,
                         @RequestParam String content,
                         HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return "redirect:/";
        }

        recordService.saveRecord(user, file, content);
        return "redirect:/home";
    }

    @GetMapping("/record/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) return "redirect:/";

        Record record = recordService.getRecord(id);
        if (!record.getUser().getId().equals(user.getId())) {
            return "redirect:/home";
        }

        model.addAttribute("record", record);
        return "edit";
    }


    @PostMapping("/record/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam(required = false) MultipartFile file,
                       @RequestParam String content,
                       HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) return "redirect:/";

        recordService.updateRecord(id, user, file, content);
        return "redirect:/home";
    }


    @PostMapping("/record/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) return "redirect:/";

        recordService.deleteRecord(id, user);
        return "redirect:/home";
    }
}
