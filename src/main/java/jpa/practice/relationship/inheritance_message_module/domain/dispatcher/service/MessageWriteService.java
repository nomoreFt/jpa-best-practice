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
public class MessageWriteService {
    private final AlimtalkRepository alimtalkRepository;
    private final SmsRepository smsRepository;
    private final MailRepository mailRepository;

    private final MessageSendService messageSendService;

    @Transactional
    public void writeInit(){
        Sms sms = Sms.builder().sender("010-1234-5678").receiver("010-9876-5432").smsContent("Hello from SMS!").build();
        Sms sms2 = Sms.builder().sender("010-1234-5679").receiver("010-9876-5431").smsContent("Hello2 from SMS!").build();
        Alimtalk alimtalk = Alimtalk.builder().sender("Kakao").receiver("010-9876-5432").kakaoMessage("Hello from KakaoTalk!").build();
        Mail mail = Mail.builder().sender("noreply@example.com").receiver("user@example.com").emailContent("Hello from Email!").build();

        sms.dispatchMessage();
        sms2.dispatchMessage();
        alimtalk.dispatchMessage();
        mail.dispatchMessage();

        Sms save = smsRepository.save(sms);
        Sms save1 = smsRepository.save(sms2);
        Alimtalk save2 = alimtalkRepository.save(alimtalk);
        Mail save3 = mailRepository.save(mail);


        messageSendService.processMessages(List.of(save, save1, save2, save3));
    }

}
