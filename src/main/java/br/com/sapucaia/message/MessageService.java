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

	
	public void sendMessage(MessagePs messagePs,String provedor) {
		Twilio.init(SID_ACCOUNT, AUTH_ACCOUNT);

		/*switch (provedor) {
		case "Whatsapp":
			Message.creator(
				new PhoneNumber("whatsapp:+" + messagePs.getTo()),
				new PhoneNumber("whatsapp:" + WHATSAPP_NUMBER), 		
						messagePs.getMessage())
				.create();
			System.out.println("Codigo enviado para Whatsapp.");
			break;
		case "Telefone":
			Message.creator(
				new PhoneNumber("+55" + messagePs.getTo()),
				new PhoneNumber(PHONE_NUMBER), 		
						messagePs.getMessage())
				.create();
			System.out.println("Codigo enviado para Telefone.");
			break;
		default:
			break;
		}*/
		System.out.println(SID_ACCOUNT);
		System.out.println(AUTH_ACCOUNT);
		System.out.println(messagePs.getMessage());
	}
}
