package jpa.practice.relationship.inheritance_message_module;


import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Alimtalk;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Mail;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Sms;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.MessageReadService;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.MessageSendService;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.MessageWriteService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@EnableAsync
@SpringBootApplication
public class MainApplication {
    private final MessageWriteService messageWriteService;
    private final MessageSendService messageSendService;
    private final MessageReadService messageReadService;

    public MainApplication(MessageWriteService messageWriteService, MessageSendService messageSendService, MessageReadService messageReadService) {
        this.messageWriteService = messageWriteService;
        this.messageSendService = messageSendService;
        this.messageReadService = messageReadService;
    }


    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            messageWriteService.writeInit();
            messageReadService.readAll();
        };
    }
}