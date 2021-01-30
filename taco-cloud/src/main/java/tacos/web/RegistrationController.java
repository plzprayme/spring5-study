package tacos.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.UserRepository;
import tacos.security.RegistrationFrom;

@RequiredArgsConstructor
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm() {
        return "registratin";
    }

    @PostMapping
    public String processRegistration(RegistrationFrom form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
