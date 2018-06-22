package assignment;

import java.io.IOException;
import java.rmi.ServerException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;





import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;

import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import javax.jcr.Node;
import javax.jcr.Session;



import javax.jcr.Value;


//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceResolver;

@SlingServlet(paths="/bin/amatyaServletNew", methods = "POST", metatype=true)
public class HandleClaim extends org.apache.sling.api.servlets.SlingAllMethodsServlet {
	private static final long serialVersionUID = 2598426539166789515L;

	@Reference
	private SlingRepository repository;
	
	@Reference
	private ResourceResolverFactory resolverFactory;

	public void bindRepository(SlingRepository repository) {
		this.repository = repository; 
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {

		String dataResponse = saveData(request,response);
		response.getWriter().write(dataResponse);
	}
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {

		String dataResponse = saveData(request,response);
		response.getWriter().write(dataResponse);
	}
	
	
	protected String saveData(SlingHttpServletRequest request, SlingHttpServletResponse response){
		String jsonData ;
		String message = null;
		try
		{
			//Get the submitted form data that is sent from the
			//CQ web page  
			String id = UUID.randomUUID().toString();
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			String cat = request.getParameter("cat");
			String state = request.getParameter("state");
			String details = request.getParameter("details");
			String city = request.getParameter("city");

			//Encode the submitted form data to JSON
			JSONObject obj=new JSONObject();
			obj.put("id",id);
			obj.put("firstname",firstName);
			obj.put("lastname",lastName);
			obj.put("address",address);
			obj.put("cat",cat);
			obj.put("state",state);
			obj.put("details",details);
			obj.put("city",city);
			
			message = "1";
			
			//Get the JSON formatted data    
			 jsonData = obj.toString();

			//ResourceResolverFactory resolverFactory = null;
			// Invoke the adaptTo method to create a Session
			ResourceResolver resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
			Session session = resourceResolver.adaptTo(Session.class);
			message = "2";
			// Create a node that represents the root node
			Node root = session.getRootNode();

			// Get the content node in the JCR
			Node content = root.getNode("content/training/amatya/test");

			// Determine if the content/customer node exists
			Node enqRoot = null;
			//int enqRec = doesEnqExist(content);
			message = "3";
			// -1 means that content/customer does not exist
			if (!content.hasNode("enquery"))
				// content/customer does not exist -- create it
				enqRoot = content.addNode("enquery", "sling:OrderedFolder");
			else
				// content/customer does exist -- retrieve it
				enqRoot = content.getNode("enquery");

			message = "4";
			
			Random rand = new Random();

			int  randomNumber = rand.nextInt(500) + 1;
			
			// Store content from the client JSP in the JCR
			Node enqNode = enqRoot.addNode("test_enquiryat_" + "_" + firstName + "_" + lastName + "_" + randomNumber, "nt:unstructured");
			message = "Testing";
			// make sure name of node is unique
			enqNode.setProperty("firstName", firstName);
			enqNode.setProperty("lastName", lastName);
			enqNode.setProperty("address", address);
			enqNode.setProperty("cat", cat);
			enqNode.setProperty("state", state);
			enqNode.setProperty("details", details);
			enqNode.setProperty("city", city);
			message = "5";

			// Save the session changes and log out
			session.save();
			session.logout();
			message = "6";
			//Return the JSON formatted data
			
		}
		catch(Exception e)
		{
			jsonData= "Error "+e.getMessage()+ ' ' + message;
		}
		return jsonData;
	}
}
