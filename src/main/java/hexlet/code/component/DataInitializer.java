package hexlet.code.component;

//import hexlet.code.dto.dtoUser.UserCreateDTO;
//import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
//@Profile("!test")
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    private final CustomUserDetailsService userService;

    //private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            var userData = new User();
            userData.setEmail(email);
            userData.setPasswordDigest("qwerty");

            userService.createUser(userData);
            System.out.println("Администратор создан: " + email);
        } else {
            System.out.println("Администратор уже существует: " + email);
        }
    }
}
