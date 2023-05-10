package stepDefinitions;

import java.util.ArrayList;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.properties.ConfigurationReader;
import base_class_API.Common_Methods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class C2CreateCustomer extends Common_Methods {

	static int statusCode;
	static int returnCode;
	static String PAN_Final;

	static String payLoadFilePath = System.getProperty("user.dir")
			+ "/Json_Payload/OrderPayload/createcustomerpayload.json";
	static String responseBodyFilePath = System.getProperty("user.dir")
			+ "/Json_Response/OrderResponse/createcustomerresponse.json";

	@Given("I am on an new customer Create API")
	public void i_am_on_an_new_customer_create_api() throws Exception {

		payloadCreate();
	}

	@Given("I create Csquare Customer")
	public void i_create_csquare_customer() {

		statusCode = response.getStatusCode();
	}

	@Then("I Validate expected response code with {int}")
	public void i_validate_expected_response_code_with(int int1) {

		Assert.assertEquals(returnCode, int1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public static void payloadCreate() throws Exception {

		String c2buid = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		String pathURL = "/b2bcustomers?cmd=create&branchCode=" + c2buid + "";

		hostURL();

		basicAuth();

		JSONObject jsonReader = jsonReader(payLoadFilePath);

		jsonReader.put("partyId", timesStamp());

		jsonReader.put("partyName", partyName());

		jsonReader.put("partyShortName", partyName());

		jsonReader.put("customerRefId", getCustomerRefId(jsonReader));

		jsonReader.put("gstin", getGST(jsonReader));

		jsonReader.put("pan", PAN_Final);

		passingResponseAsMap(jsonReader);

		postRequest(pathURL);

		String bodyData = response.getBody().asString();

		System.out.println(bodyData);

		returnCode = response.jsonPath().getInt("returnCode");

		jsonPayloadWriter(jsonReader, payLoadFilePath);

		jsonResponseBodyWriter(response, responseBodyFilePath);

	}

	public static String partyName() {

		String firstName = RandomStringUtils.randomAlphabetic(5);
		String secondName = RandomStringUtils.randomAlphabetic(5);

		String fullName = firstName + " " + secondName;

		ArrayList<String> addName = new ArrayList<>();

		addName.add("SP" + " " + fullName.toUpperCase() + " " + "MEDICAL LIMITED");

		Object change = fullName.toUpperCase() + " " + "MEDICAL LIMITED" + " " + addName;

		String partyName = change.toString();

		return partyName;
	}

	public static String getGST(JSONObject jsonReader) {

		String gst = jsonReader.get("gstin").toString();

		int parseInt1 = Integer.parseInt(gst.substring(7, 11));

		parseInt1 = parseInt1 + 1;

		String GST_Final = gst.substring(0, 7) + parseInt1 + gst.substring(11, 15);

		PAN_Final = GST_Final.substring(2, 12);

		return GST_Final;
	}

	public static String getCustomerRefId(JSONObject jsonReader) {

		String CustomerRefId = jsonReader.get("customerRefId").toString();

		int parseInt = Integer.parseInt(CustomerRefId.substring(4));

		parseInt = parseInt + 1;

		String newCustomerRefId = CustomerRefId.substring(0, 4) + parseInt;

		return newCustomerRefId;

	}

}
