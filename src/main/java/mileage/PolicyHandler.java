package mileage;

import mileage.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PolicyHandler {
    @Autowired
    MessageRepository messageRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString) {
//        System.out.println("event###! : " + eventString);

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMsgSent(@Payload MsgSent msgSent) {
        if(msgSent.isMe()) {
            System.out.println("msg_SENT###! : " + msgSent.toJson());

            Optional<Message> memberOptional = messageRepository.findByMemberId(msgSent.getMemberId());
            Message message = memberOptional.get();

            message.setMessageContents(msgSent.getMessageContents());
            message.setMessageStatus(msgSent.getMessageStatus());

            messageRepository.save(message);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMemberJoined_SendMsg(@Payload MemberJoined memberJoined) {
        if (memberJoined.isMe() && Objects.equals(memberJoined.getMemberStatus(), "NEW")) {
            Message message = new Message();


            System.out.println("##### listener SendMsg : " + memberJoined.toJson());

            message.setMemberId(memberJoined.getMemberId());
            message.setPhoneNo(memberJoined.getPhoneNo());
            message.setMessageContents("CONTENTS");
            message.setMessageStatus("NEW");

            messageRepository.save(message);
        }
    }

}