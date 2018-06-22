package assignment;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;



public class SimpleMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		/*logger.error("EMAILL8888888888888888LLLLLLLL 111111111");
		// Declare a MessageGateway service
		MessageGateway<Email> messageGateway;
		final WorkflowData workflowData = item.getWorkflowData();
		final String type = workflowData.getPayloadType();


		// Get the path to the JCR resource from the payload
		final String path = workflowData.getPayload().toString();*/

		// Set up the Email message
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		//email.setSmtpPort(25);
		email.setSSL(true);
		email.setAuthentication("testtrainingmagnon@gmail.com", "M@gn0ntbwa");

		// Set the mail values
		String emailToRecipients = "amatya.trivedi@magnon-egplus.com";

		try {
			email.addTo(emailToRecipients);
			email.setSubject("AEM Custom Step");
			email.setFrom("amatya.trivedi93@gmail.com");
			email.setMsg("This message is to inform you that the CQ Home page created\n"
					+ "<b>Path:</b>");
			
			email.send();
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			System.out.print("EMAILLLLLLLLLL" + e);
			e.printStackTrace();
		}

		// Inject a MessageGateway Service and send the message
		/*messageGateway = messageGatewayService.getGateway(Email.class);

		// Check the logs to see that messageGateway is not null
		messageGateway.send((Email) email);*/
		


	}

}
