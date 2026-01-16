package movie.movie.web;

import movie.movie.domain.User;
import movie.movie.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import movie.movie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.login(username, password);
        if (user == null) {
            return "login";
        }
        session.setAttribute("loginUser", user);
        return "redirect:/home";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         Model model){
        try {
            userService.join(username, password);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("username", username);
            return "signup";
        }

    }

    @GetMapping("/api/check-username")
    @ResponseBody
    public boolean checkUserName(@RequestParam String username) {
        return userService.checkDuplicateName(username);
    }
}
