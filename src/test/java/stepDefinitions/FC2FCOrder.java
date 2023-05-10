package stepDefinitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.properties.ConfigurationReader;
import base_class_API.Common_Methods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class FC2FCOrder extends Common_Methods {
	static String fc2fcTransactionId;
	static String batchId;
	static String OrderRefId;
	static int statusCode;

	static String orderPayload = System.getProperty("user.dir") + "/Json_Payload/OrderPayload/FC2FCorderpayload.json";
	static String orderResponse = System.getProperty("user.dir") + "/Json_Response/OrderResponse/orderresponse.json";

	static String picklistpayload = System.getProperty("user.dir")
			+ "/Json_Payload/FC2FCPicklistPayload/singleitempicklistpayload.json";
	static String picklistresponse = System.getProperty("user.dir")
			+ "/Json_Response/FC2FCPicklistResponse/singleitempicklistresponse.json";

	static String makeinvoicepayload = System.getProperty("user.dir")
			+ "/Json_Payload/FC2FCMakeinvoicePayload/singleitemmakeinvoicepayload.json";
	static String makeinvoiceresponse = System.getProperty("user.dir")
			+ "/Json_Response/FC2FCMakeinvoiceResponse/singleitemmakeinvoiceresponse.json";

	static String itemDetails = System.getProperty("user.dir") + "/Json_Items_Details/Item_details.json";

	@Given("I am on an FC2FC order Create API with single line Item")
	public void i_am_on_an_fc2fc_order_create_api_with_single_line_item() throws Exception {

		fc2fcOrderCreation("single","","");
	}

	@Then("I VAlidate expected response code with {int}")
	public void i_v_alidate_expected_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("[0].returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an FC2FC order Create API with multiple line Item")
	public void i_am_on_an_fc2fc_order_create_api_with_multiple_line_item() throws Exception {

		fc2fcOrderCreation("multiple","","");
	}

	@Then("I VAlidate Expected response code with {int}")
	public void i_v_alidate_expected_response_code_with1(int int1) {

		statusCode = response.jsonPath().getInt("[0].returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an FC2FC order Create API with multiple line Item_no stock")
	public void i_am_on_an_fc2fc_order_create_api_with_multiple_line_item_no_stock() throws Exception {

		fc2fcOrderCreation("multiple","","");
	}

	@Then("I VAlidate EXpected response COde with {int}")
	public void i_v_alidate_e_xpected_response_c_ode_with(int int1) {

		statusCode = response.jsonPath().getInt("[0].returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	
	@Given("I am on an FC2FC order Create API with multiple line Item_part stock")
	public void i_am_on_an_fc2fc_order_create_api_with_multiple_line_item_part_stock() throws Exception{
		
		fc2fcOrderCreation("multiple","","");
		
		}

	@Then("I VAlidate Expected response COde with {int}")
	public void i_v_alidate_expected_response_c_ode_with(int int1) {

		statusCode = response.jsonPath().getInt("[0].returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an FC2FC order Create API with single {string}  line Item_CGST {int}")
	public void i_am_on_an_fc2fc_order_create_api_with_single_line_item_cgst(String string, Integer int1) throws Exception {

		fc2fcOrderCreation(string, String.valueOf(int1), "CGST");
	}

	@Then("I VAlidate EXpected Response code with {int}")
	public void i_v_alidate_e_xpected_response_code_with1(int int1) {

		statusCode = response.jsonPath().getInt("[0].returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	

	@Given("I am on an FC2FC order Create API with single {string} line Item_IGST {int}")
	public void i_am_on_an_fc2fc_order_create_api_with_single_line_item_igst(String string, Integer int1) throws Exception{

		fc2fcOrderCreation(string, String.valueOf(int1), "IGST");
	}

	@Then("I VAlidate EXpected response code with {int}")
	public void i_v_alidate_e_xpected_response_code_with(int int1) {
		
		statusCode = response.jsonPath().getInt("[0].returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	

	static Map<Object, Object> newItemList;

	@SuppressWarnings("unchecked")
	@Test
	public void fc2fcOrderCreation(String string, String supplierID, String GST) throws Exception {

		String BranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		String pathurl = "/purchase/orders?cmd=create&type=long&branchCode=" + BranchCode + "&ncOrderRef="
				+ fc2fcOrderRef() + "";

		JSONObject jsonReader = jsonReader(orderPayload);
		JSONArray items = jsonArrayReader(itemDetails);

		List<Map<Object, Object>> itemList = new ArrayList<>();
		itemList.addAll(items);

		jsonReader.put("branchCode", BranchCode);

		List<Map<Object, Object>> map = (List<Map<Object, Object>>) jsonReader.get("items");

		if (string.equals("single")) {

			map.clear();

			newItemList = new HashMap<>();

			for (Map<Object, Object> item : itemList) {

				if (item.get("seqNo").toString().equals("1")) {

					newItemList.put("itemId", item.get("itemId"));
					newItemList.put("qty", item.get("qty"));
					newItemList.put("seqNo", item.get("seqNo"));
					newItemList.put("freeQty", 0);
				}

			}
			
			if (GST.equalsIgnoreCase("CGST") || GST.equalsIgnoreCase("IGST")){
				
				jsonReader.put("supplierId", supplierID);
				
			}else {
			
				jsonReader.put("supplierId", ConfigurationReader.getCR().getFRM().getProperty("SupplierId"));
			}
			
			map.add(newItemList);

			jsonReader.put("lineItemCount", 1);

		} else if (string.equals("multiple")) {

			map.clear();

			int count = 0;

			for (Map<Object, Object> item : itemList) {

				newItemList = new HashMap<>();

				if (item.containsKey("itemId")) {

					newItemList.put("itemId", item.get("itemId"));
					newItemList.put("qty", item.get("qty"));
					newItemList.put("seqNo", item.get("seqNo"));
					newItemList.put("freeQty", 0);
					count++;
				}

				map.add(newItemList);
			}

			jsonReader.put("lineItemCount", count);
		}

		hostURL();

		basicAuth();

		passingResponseAsMap(jsonReader);

		postRequest(pathurl);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonPayloadWriter(jsonReader, orderPayload);

		jsonResponseBodyWriter(response, orderResponse);
	}
}
