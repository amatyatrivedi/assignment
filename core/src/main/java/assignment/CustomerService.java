package assignment;

import java.util.ArrayList;

public interface CustomerService {
	public  ArrayList<CustomerDataMapper> getCustomerData();
	
	public  ArrayList<MyPageInfo> getNodeData();
	
	public  String getNodeTestData();
}
