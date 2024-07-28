package jpa.practice.relationship.onetomany2;


import jpa.practice.relationship.manytomany.service.BookstoreService;
import jpa.practice.relationship.onetomany2.service.BoardService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    private final BoardService boardService;

    public MainApplication(BoardService boardService) {
        this.boardService = boardService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            boardService.init();
            //boardService.fetchPostWithAllThrowException();
            //boardService.fetchPostWithAllSeparate();
            boardService.fetchPostAllWithAll();
        };
    }
}