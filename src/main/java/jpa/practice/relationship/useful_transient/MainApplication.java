package jpa.practice.relationship.useful_transient;


import jpa.practice.relationship.useful_transient.service.OrderService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final OrderService orderService;

    public MainApplication(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            orderService.init();
            //orderService.fetchV1Orders();
            //orderService.fetchV1OrdersDto();
            //orderService.fetchV2Orders();
            //orderService.fetchV3Orders();
            orderService.fetchV3OrdersDto();
        };
    }
}