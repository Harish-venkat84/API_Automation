package stepDefinitions;

import java.util.HashMap;
import java.util.LinkedList;
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
import io.restassured.specification.RequestSpecification;

@SuppressWarnings("unchecked")
public class C2CustomerOrder extends Common_Methods {

	static String TransactionId;
	static String batchId;
	static int returnCode;
	static RequestSpecification httprequest;
	static int statusCode;

	static String itemDetails = System.getProperty("user.dir") + "/Json_Items_Details/Item_details.json";

	static String createOrderPayload = System.getProperty("user.dir")
			+ "/Json_Payload/OrderPayload/C2orderpayload.json";
	static String createOrderResponse = System.getProperty("user.dir")
			+ "/Json_Response/OrderResponse/orderresponse.json";

	@Given("I am on an customer order Create API with single {string} line Item")
	public void i_am_on_an_customer_order_create_api_with_single_line_item(String string) throws Exception {

		C2order("", "single", "");
	}

	@Then("I validate expected response code with {int}")
	public void i_validate_expected_response_code_with1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an customer order Create API with single {string} line Item and IGST {int}")
	public void i_am_on_an_customer_order_create_api_with_single_line_item_and_igst(String string, int int2)
			throws Exception {

		C2order(String.valueOf(int2), string, "IGST");
		
	}

	@Then("I validate expected Response Code With {int}")
	public void i_validate_expected_response_code_with3(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an customer order Create API with single {string} line Item and CGST {int}")
	public void i_am_on_an_customer_order_create_api_with_single_line_item_and_cgst(String string, Integer int1)
			throws Exception {

		C2order(String.valueOf(int1), string, "");
	}

	@Then("I Validate Expected Response Code With {int}")
	public void i_validate_expected_response_code_with2(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am On an customer order Create API with multiple {string} line Items")
	public void i_am_on_an_customer_order_create_api_with_multiple_line_items(String string) throws Exception {

		C2order("", string, "");
	}

	@Then("I validate expected response code With {int}")
	public void i_validate_expected_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an customer order Create API with multiple {string} line Items-partstock")
	public void i_am_on_an_customer_order_create_api_with_multiple_line_items_partstock(String string)
			throws Exception {

		C2order("", string, "");
		;
	}

	@Then("I VALidate expected response code With {int}")
	public void i_va_lidate_expected_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an customer order Create API with multiple {string} line Items with no stock")
	public void i_am_on_an_customer_order_create_api_with_multiple_line_items_with_no_stock(String string)
			throws Exception {

		C2order("", string, "");

	}

	@Then("I Validate expected Response code with {int}")
	public void i_validate_expected_response_code_with11(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	
	@Given("I am on an customer order Create API with single {string} line Item with TO {int}")
	public void i_am_on_an_customer_order_create_api_with_single_line_item_with_to(String string, Integer int1) throws Exception {
	
		C2order(String.valueOf(int1), string, "TO");
	}
	
	@Then("I Validate Expected Response Code WIh {int}")
	public void i_validate_expected_response_code_w_ih(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Test(priority = 1)
	public static void C2order(String partyId, String string, String GST) throws Exception {

		String buid = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		String pathURL = "/customerorders/b2b?cmd=create&branchCode=" + buid + "";

		JSONObject jsonReader = jsonReader(createOrderPayload);

		if (GST.equalsIgnoreCase("IGST") || GST.equalsIgnoreCase("CGST")) {

			jsonReader.put("partyId", partyId);

		}else if (GST.equalsIgnoreCase("TO")) {
			
			jsonReader.put("partyId", partyId);
			
		} else {

			jsonReader.put("partyId", "1246476");
		}

		jsonReader.put("customerOrderRefId", customerOrderRefId());

		jsonReader.put("buId", buid);

		List<Map<Object, Object>> map = (List<Map<Object, Object>>) jsonReader.get("saleReturnLineItemSet");

		JSONArray item = jsonArrayReader(itemDetails);

		List<Map<Object, Object>> items = new LinkedList<>();

		items.addAll(item);

		if (string.equals("single")) {

			map.clear();

			for (Map<Object, Object> map2 : items) {

				if (map2.get("seqNo").toString().equals("1")) {

					Map<Object, Object> itemMap = new HashMap<>();

					itemMap.put("itemId", map2.get("itemId"));
					itemMap.put("seqNum", 1);
					itemMap.put("quantity", map2.get("qty"));

					map.add(itemMap);

					break;
				}
			}

			jsonReader.put("totalItemCount", 1);

		} else if (string.equals("multiple")) {

			map.clear();

			int count = 0;

			for (Map<Object, Object> map2 : items) {

				Map<Object, Object> multiplrItems = new HashMap<>();

				multiplrItems.put("itemId", map2.get("itemId"));
				multiplrItems.put("seqNum", map2.get("seqNo"));
				multiplrItems.put("quantity", map2.get("qty"));
				count++;

				map.add(multiplrItems);
			}

			jsonReader.put("totalItemCount", count);
		}

		hostURL();
		basicAuth();
		passingResponseAsMap(jsonReader);
		postRequest(pathURL);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonPayloadWriter(jsonReader, createOrderPayload);

		jsonResponseBodyWriter(response, createOrderResponse);
	}
}
