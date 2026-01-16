package movie.movie.web;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.User;
import movie.movie.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RecordService recordService;

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("records", recordService.findMyRecords(user));
        return "home";
    }
}
