package jpa.practice.relationship.elementalcollection;


import jpa.practice.relationship.elementalcollection.service.UserService;
import jpa.practice.relationship.onetoone.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    private final UserService userService;

    public MainApplication(UserService userService) {
        this.userService = userService;
    }


    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            userService.init();
            //userService.fetchUserWithDtoV1();
            userService.removeFavoriteBook();
        };
    }
}