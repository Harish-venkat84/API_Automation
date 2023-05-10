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

public class CustomerOrderCancel extends Common_Methods {

	static int statusCode;

	static String orderresponse = System.getProperty("user.dir") + "/Json_Response/OrderResponse/orderresponse.json";

	static String cancelOderresponse = System.getProperty("user.dir")
			+ "/Json_Response/OrderCancelResponse/openstateordercancelresponse.json";

	@Given("I am on an B2C Order Cancel in open state with single line Item")
	public void i_am_on_an_b2c_order_cancel_in_open_state_with_single_line_item() throws Exception {

		orderCancelOpenState("B2C");
	}

	@Then("I validate EXPected response code with {int}")
	public void i_validate_ex_pected_response_code_with2(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Then("I cancel order in open state")
	public void i_cancel_order_in_open_state() throws Exception {

		orderCancelOpenState("C2");
	}

	@Then("I validate with the Expected Response {int}")
	public void i_validate_with_the_expected_response(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an FC2FC Cancel Order in open state with single line Item")
	public void i_am_on_an_fc2fc_cancel_order_in_open_state_with_single_line_item() throws Exception {

		orderCancelOpenState("FC2FC");
	}

	@Then("I VAlidate epected response code With {int}")
	public void i_v_alidate_epected_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an ZipERP order cancel in open state")
	public void i_am_on_an_zip_erp_order_cancel_in_open_state() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("I validate ZipERP order cancel response code with {int}")
	public void i_validate_zip_erp_order_cancel_response_code_with(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	static Map<Object, Object> resTransactionId;
	static String pathurl;

	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void orderCancelOpenState(String string) throws Exception {

		if (string.equalsIgnoreCase("B2C") || string.equalsIgnoreCase("c2")) {

			JSONObject responseReader = jsonReader(orderresponse);

			Map<Object, Object> resTransactionId = (Map<Object, Object>) responseReader.get("returnData");

			String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

			if (string.equalsIgnoreCase("B2C")) {

				pathurl = "/customerorders/" + resTransactionId.get("orderId").toString() + "?cmd=cancel&branchCode="
						+ B2CbranchCode + "&reason=customer edit";

			} else if (string.equalsIgnoreCase("c2")) {

				pathurl = "/customerorders/peorder/" + resTransactionId.get("transactionId").toString() + "?branchCode="
						+ B2CbranchCode + "&cmd=cancel&reason=expired'";

			}
		} else if (string.equalsIgnoreCase("FC2FC")) {

			JSONArray responseReader = jsonArrayReader(orderresponse);

			String SupplierId = ConfigurationReader.getCR().getFRM().getProperty("SupplierId");

			List<Map<Object, Object>> fc2fcTnxID = new ArrayList<>();
			fc2fcTnxID.addAll(responseReader);

			resTransactionId = fc2fcTnxID.get(1);

			resTransactionId = (Map<Object, Object>) resTransactionId.get("returnData");

			pathurl = "/customerorders/peorder/" + resTransactionId.get("orderTxnId").toString() + "?branchCode="
					+ SupplierId + "&cmd=cancel&reason=expired";
		}

		hostURL();

		basicAuth();

		postRequest(pathurl);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonResponseBodyWriter(response, cancelOderresponse);
	}

	static String orderHoldAndResumeResponse = System.getProperty("user.dir")
			+ "/Json_Response/OrderCancelResponse/orderholdandresumeresponse.json";

	@Given("I am going to put B2C order on hold")
	public void i_am_going_to_put_b2c_order_on_hold() throws Exception {

		orderHoldAndResume("hold");
	}

	@Then("I Validate With the Expected Response {int}")
	public void i_validate_with_the_expected_response5(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am going to resume the B2C order")
	public void i_am_going_to_resume_the_b2c_order() throws Exception {

		orderHoldAndResume("resume");
	}

	@Then("I Validate With the EXpected Response {int}")
	public void i_validate_with_the_e_xpected_response(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	static String holdandresumeurl;

	@SuppressWarnings("unchecked")
	public void orderHoldAndResume(String String) throws Exception {

		JSONObject responseReader = jsonReader(orderresponse);

		Map<Object, Object> resTransactionId = (Map<Object, Object>) responseReader.get("returnData");

		String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		if (String.equalsIgnoreCase("hold")) {

			holdandresumeurl = "/customerorders/" + resTransactionId.get("orderId").toString() + "?cmd=hold&branchCode="
					+ B2CbranchCode + "&reason=customer edit";

		} else if (String.equalsIgnoreCase("resume")) {

			holdandresumeurl = "/customerorders/" + resTransactionId.get("orderId").toString()
					+ "?cmd=resume&branchCode=" + B2CbranchCode + "&reason=customer edit";
		}

		hostURL();

		basicAuth();

		postRequest(holdandresumeurl);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonResponseBodyWriter(response, orderHoldAndResumeResponse);
	}

	static String picklistresponse = System.getProperty("user.dir")
			+ "/Json_Response/PicklistResponse/generatepicklistresponse.json";
	static String unpickListResponse = System.getProperty("user.dir")
			+ "/Json_Response/OrderCancelResponse/unpicklistresponse.json";

	@Given("I generate B2C unpicklist")
	public void i_generate_b2c_unpicklist() throws Exception {

		generateUnpickList("b2c");
	}

	@Then("I Validate with The expected ResponSe {int}")
	public void i_validate_with_the_expected_respon_se(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I generate FC2FC unpicklist")
	public void i_generate_fc2fc_unpicklist() throws Exception {

		generateUnpickList("FC2FC");
	}

	@Then("I Validate with THe expected ResponSe {int}")
	public void i_validate_with_t_he_expected_respon_se(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Then("I generate unpicklist")
	public void i_generate_unpicklist() throws Exception {

		generateUnpickList("c2");
	}

	@Then("I validate with the expected ResponSe {int}")
	public void i_validate_with_the_expected_respon_se1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I generate ZipERP unpicklist")
	public void i_generate_zip_erp_unpicklist() throws Exception {

		generateUnpickList("zipERP");
	}

	@Then("I Validate ZipERP unpicklist Response {int}")
	public void i_validate_zip_erp_unpicklist_response(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	static Object responseTransactionId;
	static String unpickPathURL;
	static String tnxID;

	@SuppressWarnings("unchecked")
	@Test
	public void generateUnpickList(String string) throws Exception {

		JSONObject responseReader = jsonReader(picklistresponse);

		List<Map<Object, Object>> fc2FCtxnID = (List<Map<Object, Object>>) responseReader.get("returnData");

		for (Map<Object, Object> map : fc2FCtxnID) {

			if (map.containsKey("transactionId")) {

				tnxID = map.get("transactionId").toString();
			}
		}
		if (string.equalsIgnoreCase("c2") || string.equalsIgnoreCase("B2C") || (string.equalsIgnoreCase("zipERP"))) {

			String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");
			unpickPathURL = "/picklists/" + tnxID + "?branchCode=" + B2CbranchCode + "&cmd=unpick";

		} else if (string.equalsIgnoreCase("FC2FC")) {

			String SupplierId = ConfigurationReader.getCR().getFRM().getProperty("SupplierId");
			unpickPathURL = "/picklists/" + tnxID + "?branchCode=" + SupplierId + "&cmd=unpick";
		}

		hostURL();
		basicAuth();
		postRequest(unpickPathURL);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonResponseBodyWriter(response, unpickListResponse);
	}

	static String makeinvoicerespose = System.getProperty("user.dir")
			+ "/Json_Response/MakeInvoiceResponse/makeinvoiceresponse.json";

	static String cancelInvoicePayload = System.getProperty("user.dir")
			+ "/Json_Payload/MakeInvoicePayload/invoicestatecancelinvoicepayload.json";
	static String cancelInvoiceResponse = System.getProperty("user.dir")
			+ "/Json_Response/OrderCancelResponse/cancelInvoiceresponse.json";

	@Given("I cancel the B2C invoice")
	public void i_cancel_the_b2c_invoice() throws Exception {

		cancelInvoice("b2c");
	}

	@Then("I Validate B2C Order REsponse code with {int}")
	public void i_validate_b2c_order_r_esponse_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Then("I Cancel The invoice")
	public void i_cancel_the_invoice() throws Exception {

		cancelInvoice("c2");
	}

	@Then("I Validate With the expected Response code {int}")
	public void i_validate_with_the_expected_response_code(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Cancel The FC2FC invoice")
	public void i_cancel_the_fc2fc_invoice() throws Exception {

		cancelInvoice("fc2fc");
	}

	@Then("I VAlidate WIth The Expected Response code {int}")
	public void i_v_alidate_w_ith_the_expected_response_code(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	static String cancelInvoicePathURL;
	static Object responseOrderId;

	@SuppressWarnings("unchecked")
	public void cancelInvoice(String string) throws Exception {

		JSONObject responseReader = jsonReader(makeinvoicerespose);

		List<Map<Object, Object>> txnid1 = (List<Map<Object, Object>>) responseReader.get("returnData");

		for (Map<Object, Object> map : txnid1) {

			if (string.equalsIgnoreCase("b2c") || string.equalsIgnoreCase("fc2fc")) {

				responseTransactionId = map.get("transactionId");

				responseOrderId = map.get("customerOrderRefId");

			} else if (string.equalsIgnoreCase("c2")) {

				responseTransactionId = map.get("transactionId");

				responseOrderId = map.get("orderId");
			}

			break;
		}

		if (string.equalsIgnoreCase("b2c") || string.equalsIgnoreCase("c2")) {

			String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

			cancelInvoicePathURL = "/customerinvoices/cancel?branchCode=" + B2CbranchCode;

		} else if (string.equalsIgnoreCase("fc2fc")) {

			String SupplierId = ConfigurationReader.getCR().getFRM().getProperty("SupplierId");

			cancelInvoicePathURL = "/customerinvoices/cancel?branchCode=" + SupplierId;
		}

		hostURL();

		basicAuth();

		JSONObject jsonReader = jsonReader(cancelInvoicePayload);

		jsonReader.put("transactionId", responseTransactionId);

		jsonReader.put("orderId", responseOrderId);

		passingResponseAsMap(jsonReader);

		postRequest(cancelInvoicePathURL);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonPayloadWriter(jsonReader, cancelInvoicePayload);

		jsonResponseBodyWriter(response, cancelInvoiceResponse);

	}

	static String updateShippingStatusResponse = System.getProperty("user.dir")
			+ "/Json_Response/MakeInvoiceResponse/deliveredstatusupdateresponse.json";

	@Given("I update the shipping status as delivered")
	public void i_update_the_shipping_status_as_delivered() throws Exception {

		updateShippingStatus("DELIVERED", "");
	}

	@Then("I Validate With THE Expected Response {int}")
	public void i_validate_with_the_expected_response2(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I update the shipping status as in-transit")
	public void i_update_the_shipping_status_as_in_transit() throws Exception {

		updateShippingStatus("IN_TRANSIT", "");
	}

	@Then("I validate with THE expected response {int}")
	public void i_validate_with_the_expected_response1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Update the Shipping Status")
	public void i_update_the_shipping_status() throws Exception {

		updateShippingStatus("DELIVERED", "");
	}

	@Then("I Validate with the Expected Response {int}")
	public void i_validate_with_the_expected_response3(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Update the Shipping Status as in-transit")
	public void i_update_the_shipping_status_as_in_transit1() throws Exception {

		updateShippingStatus("IN_TRANSIT", "");
	}

	@Then("I Validate With The Expected Response {int}")
	public void i_validate_with_the_expected_response4(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Update the Shipping Status as delivered")
	public void i_update_the_shipping_status_as_delivered1() throws Exception {

		updateShippingStatus("DELIVERED", "FC2FC");
	}

	@Then("I VAlidate wIth the Expected Response {int}")
	public void i_v_alidate_w_ith_the_expected_response(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Update The shipping status as in-transit")
	public void i_update_the_shipping_status_as_in_transit2() throws Exception {

		updateShippingStatus("IN_TRANSIT", "FC2FC");
	}

	@Then("I VAlidate With The Expected Response {int}")
	public void i_v_alidate_with_the_expected_response(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void updateShippingStatus(String string, String orderType) throws Exception {

		JSONObject responseReader = jsonReader(makeinvoicerespose);

		List<Map<Object, Object>> txnid1 = (List<Map<Object, Object>>) responseReader.get("returnData");

		for (Map<Object, Object> map : txnid1) {

			responseTransactionId = map.get("transactionId");

			break;
		}

		String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");
		String SupplierId = ConfigurationReader.getCR().getFRM().getProperty("SupplierId");

		if (string.equals("DELIVERED")) {

			if (orderType.equalsIgnoreCase("FC2FC")) {

				pathurl = "/customerinvoices/shippingstatus?branchCode=" + SupplierId + "&transactionId="
						+ responseTransactionId + "&status=DELIVERED&updateTime=" + CurrentTime()
						+ "&shippingProvider=shipsy&trackingNumber=PBF00007350";
			} else {

				pathurl = "/customerinvoices/shippingstatus?branchCode=" + B2CbranchCode + "&transactionId="
						+ responseTransactionId + "&status=DELIVERED&updateTime=" + CurrentTime()
						+ "&shippingProvider=shipsy&trackingNumber=PBF00007350";
			}

		} else if (string.equals("IN_TRANSIT")) {

			if (orderType.equalsIgnoreCase("FC2FC")) {

				pathurl = "/customerinvoices/shippingstatus?branchCode=" + SupplierId + "&transactionId="
						+ responseTransactionId + "&status=IN_TRANSIT&updateTime=" + CurrentTime()
						+ "&shippingProvider=shipsy&trackingNumber=PBF00007350";
			} else {

				pathurl = "/customerinvoices/shippingstatus?branchCode=" + B2CbranchCode + "&transactionId="
						+ responseTransactionId + "&status=IN_TRANSIT&updateTime=" + CurrentTime()
						+ "&shippingProvider=shipsy&trackingNumber=PBF00007350";
			}
		}

		hostURL();
		basicAuth();
		postRequest(pathurl);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonResponseBodyWriter(response, updateShippingStatusResponse);

	}

}
