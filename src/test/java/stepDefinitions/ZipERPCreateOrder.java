package stepDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;

import com.properties.ConfigurationReader;

import base_class_API.Common_Methods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class ZipERPCreateOrder extends Common_Methods{
	
	static String zipERPorderpayload = System.getProperty("user.dir")+"/Json_Payload/OrderPayload/ZipERPorderpayload.json";
	
	static String orderresponse = System.getProperty("user.dir") + "/Json_Response/OrderResponse/orderresponse.json";
	
	static Response postRequest;
	static int statusCode;
	
	@Given("I am on an ZipERP order Create API with single line Item")
	public void i_am_on_an_zip_erp_order_create_api_with_single_line_item() throws Exception {
		
		zipERPOrderCreation();
	}
	
	@Then("I VAlidate ZipERP order response code with {int}")
	public void i_v_alidate_zip_erp_order_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	
	
	@SuppressWarnings("unchecked")
	public void zipERPOrderCreation() throws Exception {
		
		String branchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		String pathURL = "/customerorders/ze/b2b?cmd=create&branchCode="+ branchCode;
		
		hostURL();

		basicAuth();
		
		JSONArray jsonArrayReader = jsonArrayReader(System.getProperty("user.dir")+"/Json_Items_Details/Item_details.json");

		List<Map<Object, Object>> items = new ArrayList<>();
		
		items.addAll(jsonArrayReader);
		
		JSONObject jsonReader = jsonReader(zipERPorderpayload);	
		
		jsonReader.put("customerOrderRefId", "ZE-"+timesStamp()+"-1");
		
		List<Map<Object, Object>> saleReturnLineItemSet =  (List<Map<Object, Object>>) jsonReader.get("saleReturnLineItemSet");

		for (Map<Object, Object> item : items) {
			
			if (item.get("seqNo").toString().equals("1")) {
				
				for (Map<Object, Object> saleReturn : saleReturnLineItemSet) {
					
					saleReturn.put("itemId", item.get("itemId"));
					saleReturn.put("igstPercent", item.get("igstPercent"));
					
					break;
				}
			}
		}
		
		passingResponseAsMap(jsonReader);

		postRequest = postRequest(pathURL);
		
		System.out.println(postRequest.asString());

		jsonPayloadWriter(jsonReader, zipERPorderpayload);

		jsonResponseBodyWriter(postRequest, orderresponse);
		
		
	}

}
