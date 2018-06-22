/**
 * 
 */
package assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Reference;

import javax.jcr.Session;



//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory ;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.ResourceResolver; 



//QUeryBuilder APIs
import com.day.cq.search.QueryBuilder; 
import com.day.cq.search.Query; 
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.result.SearchResult;
import com.day.cq.search.result.Hit; 

@Component

@Service

/**
 * @author amatya.trivedi
 *
 */
public class CustomerServiceImpl implements CustomerService {

	/** Default log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private Session session;

	//Inject a Sling ResourceResolverFactory
	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private QueryBuilder builder;
	
	@Override
	public ArrayList<CustomerDataMapper>  getCustomerData() {
		// TODO Auto-generated method stub
		ArrayList<CustomerDataMapper> arrList = null;
		try { 
			//Invoke the adaptTo method to create a Session 
			ResourceResolver resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
			session = resourceResolver.adaptTo(Session.class);

			// create query description as hash map (simplest way, same as form post)
			Map<String, String> map = new HashMap<String, String>();

			// create query description as hash map (simplest way, same as form post)

			map.put("path", "/content/training/amatya/test/enquery");
			map.put("type", "nt:unstructured");
			//map.put("orderby", "@enq_id");

			Query query = builder.createQuery(PredicateGroup.create(map), session);

			query.setStart(0);
			query.setHitsPerPage(20);

			SearchResult result = query.getResult();

			// iterating over the results			
			arrList = new ArrayList<CustomerDataMapper>();
			for (Hit hit : result.getHits()) {
				
				CustomerDataMapper row = new CustomerDataMapper();
				ValueMap valueMap = hit.getProperties();
				try{
					row.setFirstName(valueMap.get("firstName").toString());
					row.setLastName(valueMap.get("lastName").toString());
					row.setAddress(valueMap.get("address").toString());
					row.setCat(valueMap.get("cat").toString());
					row.setState(valueMap.get("state").toString());
					row.setDetails(valueMap.get("details").toString());
					row.setCity(valueMap.get("city").toString());		
					
				}
				catch(Exception e){
					row.setFirstName("Test Name" + e);
				}
				arrList.add(row);
			}

			//close the session
			session.logout();

			// return data 
			//return arrList;

		}
		catch(Exception e){
			log.info(e.getMessage());
		}
		
		return arrList;
	}

	@Override
	public ArrayList<MyPageInfo> getNodeData() {
		// TODO Auto-generated method stub
		ArrayList<MyPageInfo> arrList = null;
		try{
			ResourceResolver resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
			session = resourceResolver.adaptTo(Session.class);
	
			// create query description as hash map (simplest way, same as form post)
			Map<String, String> map = new HashMap<String, String>();
	
			// create query description as hash map (simplest way, same as form post)
	
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -5);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			    String strDate= formatter.format(cal.getTime()); 
			    
			map.put("path", "/content/training/");
            map.put("type", "cq:Page");
            map.put("1_property", "cq:lastModified");
            map.put("1_property.lowerBound",strDate);
            map.put("1_property.lowerOperation ", ">");
			Query query = builder.createQuery(PredicateGroup.create(map), session);
	
			query.setStart(0);
			query.setHitsPerPage(20);
	
			SearchResult result = query.getResult();
	
			// iterating over the results			
			arrList = new ArrayList<MyPageInfo>();
			for (Hit hit : result.getHits()) {
				
				MyPageInfo row = new MyPageInfo();
				//ValueMap valueMap = hit.getProperties();
				
				row.setTitle(hit.getTitle());
				row.setPath(hit.getPath());
				arrList.add(row);
			}
		}
		catch(Exception e){
			log.info(e.getMessage());
		}
		
		return arrList;
	}

	@Override
	public String getNodeTestData() {
		// TODO Auto-generated method stub
		return "This is testing";
	}
}
