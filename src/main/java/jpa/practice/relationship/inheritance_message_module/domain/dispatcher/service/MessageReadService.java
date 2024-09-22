package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Alimtalk;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Mail;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Sms;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.repository.AlimtalkRepository;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.repository.MailRepository;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageReadService {
    private final AlimtalkRepository alimtalkRepository;
    private final SmsRepository smsRepository;
    private final MailRepository mailRepository;

    private final MessageSendService messageSendService;

    @Transactional
    public void readAll(){
        smsRepository.findAll().forEach(sms -> {
            sms.dispatchMessage();
        });

    }

}
