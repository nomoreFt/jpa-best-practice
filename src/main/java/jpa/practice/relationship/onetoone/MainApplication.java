package jpa.practice.relationship.onetoone;


import jpa.practice.relationship.onetomany2.service.BoardService;
import jpa.practice.relationship.onetoone.entity.Member;
import jpa.practice.relationship.onetoone.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    private final MemberService memberService;

    public MainApplication(MemberService memberService) {
        this.memberService = memberService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            memberService.init();
            //memberService.fetchMemberWithMemberDetailsById();
            //memberService.fetchMemberWithMemberDetailsByIdViaDtoV1();
            memberService.fetchAllViaDtosV1();
        };
    }
}