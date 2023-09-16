package com.eazytest.eazytest.service.email;

import com.eazytest.eazytest.dto.email.EmailDetails;

public interface EmailServiceInterface {

    String sendSimpleMessage(EmailDetails emailDetails);

    String sendMessageWithAttachment(EmailDetails emailDetails);
}
