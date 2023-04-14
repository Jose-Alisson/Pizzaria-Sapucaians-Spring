package br.com.sapucaia.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class MessageService {

	@Value("${twilio.account.sid}")
	private String SID_ACCOUNT;

	@Value("${twilio.auth.token}")
	private String AUTH_ACCOUNT;

	@Value("${twilio.whatsapp.number}")
	private String WHATSAPP_NUMBER;

	@Value("${twilio.phone.number}")
	private String PHONE_NUMBER;

	
	public void sendMessageWhatsapp(MessagePs messagePs) {
		Twilio.init(SID_ACCOUNT, AUTH_ACCOUNT);

		Message message = Message.creator(
				new PhoneNumber("whatsapp:+558173127515"),
				new PhoneNumber("whatsapp:+14155238886"), 		
						messagePs.getMessage())
				.create();
		
		System.out.println(WHATSAPP_NUMBER);
		System.out.println(messagePs.getTo());
	}
	
	public void sendMessagePhone(MessagePs messagePs) {
		/*Twilio.init(SID_ACCOUNT, AUTH_ACCOUNT);

		Message message = Message.creator(
						new PhoneNumber("whatsapp:" + WHATSAPP_NUMBER), 
						new PhoneNumber("whatsapp:" + messagePs.getTo()),
						messagePs.getMessage())
				.create();*/
		
		System.out.println(SID_ACCOUNT);
	}
}
