package assignment;

import org.apache.felix.scr.annotations.*;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

import org.osgi.framework.Constants;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.felix.scr.annotations.Service;

@Component


@Service
  
@Properties({
    @Property(name = Constants.SERVICE_DESCRIPTION, value = "Amatya Assignment Workflow"),
    @Property(name = Constants.SERVICE_VENDOR, value = "Adobe"),
    @Property(name = "process.label", value = "Amatya Assignment Workflow") })

public class WorkFlow implements WorkflowProcess {
	
	@Reference
	private MessageGatewayService messageGatewayService;

	@Override
	public void execute(WorkItem arg0, WorkflowSession arg1, MetaDataMap arg2)
			throws WorkflowException {
		// TODO Auto-generated method stub
		
		//logger.error("EMAILL8888888888888888LLLLLLLL 111111111");
		// Declare a MessageGateway service
		MessageGateway<Email> messageGateway;
		final WorkflowData workflowData = arg0.getWorkflowData();
		final String type = workflowData.getPayloadType();


		// Get the path to the JCR resource from the payload
		final String path = workflowData.getPayload().toString();

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
					+ "Path:" + path);
			
			//email.send();
			
			// Inject a MessageGateway Service and send the message
			messageGateway = messageGatewayService.getGateway(Email.class);

			// Check the logs to see that messageGateway is not null
			messageGateway.send((Email) email);
			
			arg1.terminateWorkflow(arg0.getWorkflow());
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			System.out.print("EMAILLLLLLLLLL" + e);
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

}
