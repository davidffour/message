package mileage;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Table(name="Message_table")
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String phoneNo;
    private String messageContents;
    private String messageStatus;

    @PostPersist
    public void onPostPersist(){
        MsgSent msgSent = new MsgSent();
        BeanUtils.copyProperties(this, msgSent);

        if (phoneNo.substring(0,3).equals("011")) {
            System.out.println("##### MESSAGE SENT" + phoneNo);
            msgSent.setMessageStatus("SUCCESS"); 
            msgSent.setMessageContents("MESSAGE SEND SUCCESS");
        } else {
            msgSent.setMessageStatus("FAIL");
            msgSent.setMessageContents("MESSAGE SEND FAIL");
        }
        msgSent.publishAfterCommit();



        System.out.println("##### MESSAGE SENT");
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getMessageContents() {
        return messageContents;
    }

    public void setMessageContents(String messageContents) {
        this.messageContents = messageContents;
    }
    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

}


