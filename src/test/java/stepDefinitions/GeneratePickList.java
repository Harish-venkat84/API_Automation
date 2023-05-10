package stepDefinitions;

import java.util.ArrayList;
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
import io.restassured.response.Response;

public class GeneratePickList extends Common_Methods {

	static int statusCode;

	static String orderresponse = System.getProperty("user.dir") + "/Json_Response/OrderResponse/orderresponse.json";

	static String picklistpayload = System.getProperty("user.dir")
			+ "/Json_Payload/PicklistPayload/generatepicklistpayload.json";
	static String picklistrespose = System.getProperty("user.dir")
			+ "/Json_Response/PicklistResponse/generatepicklistresponse.json";

	@Given("I generate B2C Picklist sinlge item")
	public void i_generate_b2c_picklist_sinlge_item() throws Exception {

		generatePickList("");
	}

	@Then("I Validate EXPected response code with {int}")
	public void i_validate_ex_pected_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Then("I generate Picklist")
	public void i_generate_picklist() throws Exception {

		generatePickList("");
	}

	@Then("I validate with the expected response {int}")
	public void i_validate_with_the_expected_response(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I generate FC2FC Picklist")
	public void i_generate_fc2fc_picklist() throws Exception {

		generatePickList("FC2FC");
	}

	@Then("I VAlidate with the expected response {int}")
	public void i_v_alidate_with_the_expected_response(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	
	@Given("I generate ZipERP Picklist sinlge item")
	public void i_generate_zip_erp_picklist_sinlge_item() throws Exception{
		
		generatePickList("ZipERP");
	}
	
	
	@Then("I VAlidate ExPected response code with {int}")
	public void i_v_alidate_ex_pected_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	static Map<Object, Object> resTransactionId;
	static String picklisturl;

	@SuppressWarnings("unchecked")
	@Test
	public void generatePickList(String string) throws Exception {

		ArrayList<String> txnid = new ArrayList<>();

		JSONObject jsonReader = jsonReader(picklistpayload);

		if (string.equals("FC2FC")) {

			String SupplierId = ConfigurationReader.getCR().getFRM().getProperty("SupplierId");

			picklisturl = "/customerorders/picklist?branchCode=" + SupplierId + "&cmd=create";

			JSONArray responseReader = jsonArrayReader(orderresponse);

			List<Map<Object, Object>> fc2fcTnxID = new ArrayList<>();
			fc2fcTnxID.addAll(responseReader);

			resTransactionId = fc2fcTnxID.get(1);

			resTransactionId = (Map<Object, Object>) resTransactionId.get("returnData");

			txnid.add(resTransactionId.get("orderTxnId").toString());

			jsonReader.put("customerOrderTxnIds", txnid);

		}else if (string.equalsIgnoreCase("ZipERP")) {
			
			String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

			picklisturl = "/customerorders/picklist?branchCode=" + B2CbranchCode + "&cmd=create";

			JSONObject responseReader = jsonReader(orderresponse);

			resTransactionId = (Map<Object, Object>) responseReader.get("returnData");

			Object responseTransactionId = resTransactionId.get("transactionId");

			txnid.add(responseTransactionId.toString());

			jsonReader.put("customerOrderTxnIds", txnid);
			
		} else {

			String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

			picklisturl = "/customerorders/picklist?branchCode=" + B2CbranchCode + "&cmd=create";

			JSONObject responseReader = jsonReader(orderresponse);

			resTransactionId = (Map<Object, Object>) responseReader.get("returnData");

			Object responseTransactionId = resTransactionId.get("transactionId");

			txnid.add(responseTransactionId.toString());

			jsonReader.put("customerOrderTxnIds", txnid);
		}

		hostURL();
		basicAuth();
		passingResponseAsMap(jsonReader);
		Response postRequest = postRequest(picklisturl);

		String body = postRequest.getBody().asString();

		System.out.println(body);

		jsonPayloadWriter(jsonReader, picklistpayload);

		jsonResponseBodyWriter(postRequest, picklistrespose);
	}
}
