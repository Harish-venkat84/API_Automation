package stepDefinitions;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

public class MakeInvoice extends Common_Methods {

	static List<Map<Object, Object>> batchDetails;
	static List<Map<Object, Object>> itemDetailsPickList;
	static Map<Object, Object> map;
	static JSONArray jsonReader;
	static JSONObject picklistResponse;
	static List<Map<Object, Object>> returnData;
	static int statusCode;

	static List<Object> itemDetailsPickListMulti;

	static String orderResponse = System.getProperty("user.dir") + "/Json_Response/OrderResponse/orderresponse.json";
	static String picklistrespose = System.getProperty("user.dir")
			+ "/Json_Response/PicklistResponse/generatepicklistresponse.json";

	static String makeinvoicepayload = System.getProperty("user.dir")
			+ "/Json_Payload/MakeInvoicePayload/makeinvoicepayload.json";
	static String makeinvoicerespose = System.getProperty("user.dir")
			+ "/Json_Response/MakeInvoiceResponse/makeinvoiceresponse.json";

	@Given("I Generate FC2FC transferout")
	public void i_generate_fc2fc_transferout() throws Exception {

		makeInvoiceFC2FC("single");
	}

	@Then("I VAlidate with the expected Response code {int}")
	public void i_v_alidate_with_the_expected_response_code(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Generate FC2FC transferout for multiple line Item")
	public void i_generate_fc2fc_transferout_for_multiple_line_item() throws Exception {

		makeInvoiceFC2FC("multiple");
	}

	@Then("I VAlidate Expected Response code with {int}")
	public void i_v_alidate_expected_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	static String pathURL;

	static String id;

	@SuppressWarnings("unchecked")
	public void makeInvoiceFC2FC(String lineItems) throws Exception {

		picklistResponse = jsonReader(picklistrespose);

		returnData = (List<Map<Object, Object>>) picklistResponse.get("returnData");

		String SupplierId = ConfigurationReader.getCR().getFRM().getProperty("SupplierId");

		for (Map<Object, Object> map : returnData) {

			if (map.containsKey("itemDetails")) {

				id = map.get("partShipmentOrderId").toString().substring(0, 11);
				itemDetailsPickList = (List<Map<Object, Object>>) map.get("itemDetails");

				break;
			}
		}
		pathURL = "/picklists/" + id + "/invoice?branchCode=" + SupplierId + "&cmd=create&partShipmentOrderId=" + id
				+ "-S1";

		if (lineItems.equals("single")) {

			jsonReader = jsonArrayReader(makeinvoicepayload);
			jsonReader.clear();

			map = new HashMap<>();

			for (Map<Object, Object> object : itemDetailsPickList) {

				if (object.containsKey("itemId")) {
					map.put("shortPickFlag", false);
					map.put("itemId", object.get("itemId"));

					batchDetails = (List<Map<Object, Object>>) object.get("batchDetails");

					break;
				}
			}
			for (Map<Object, Object> object2 : batchDetails) {

				if (object2.containsKey("batchId")) {
					map.put("batchId", object2.get("batchId"));
					map.put("qty", object2.get("qty"));
					break;
				}

			}

			jsonReader.add(map);

		} else if (lineItems.equals("multiple")) {

			jsonReader = jsonArrayReader(makeinvoicepayload);
			jsonReader.clear();

			for (Map<Object, Object> object : itemDetailsPickList) {

				map = new HashMap<>();

				if (object.containsKey("itemId")) {
					map.put("shortPickFlag", false);
					map.put("itemId", object.get("itemId"));

					batchDetails = (List<Map<Object, Object>>) object.get("batchDetails");
				}

				for (Map<Object, Object> object2 : batchDetails) {

					if (object2.containsKey("batchId")) {
						map.put("batchId", object2.get("batchId"));
						map.put("qty", object2.get("qty"));
					}
				}

				jsonReader.add(map);
			}
		}

		hostURL();
		basicAuth();
		passingResponseAsList(jsonReader);
		postRequest(pathURL);
		String body = response.getBody().asString();
		System.out.println(body);

		jsonPayloadWriter(jsonReader, makeinvoicepayload);
		jsonResponseBodyWriter(response, makeinvoicerespose);
	}

	@Given("I Generate B2C Invoice")
	public void i_generate_b2c_invoice() throws Exception {

		makeInvoicB2C("single");
	}

	@Then("I Validate EXPected Response code with {int}")
	public void i_validate_ex_pected_response_code_with1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Generate B2C Invoice for multiple line Item")
	public void i_generate_b2c_invoice_for_multiple_line_item() throws Exception {

		makeInvoicB2C("multiple");
	}

	@Then("I Validate B2C Order response code with {int}")
	public void i_validate_b2c_order_response_code_with11(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@SuppressWarnings("unchecked")
	public void makeInvoicB2C(String lineItems) throws Exception {

		picklistResponse = jsonReader(picklistrespose);

		returnData = (List<Map<Object, Object>>) picklistResponse.get("returnData");

		for (Map<Object, Object> map : returnData) {

			if (map.containsKey("itemDetails")) {

				itemDetailsPickList = (List<Map<Object, Object>>) map.get("itemDetails");
				break;
			}
		}

		JSONObject responseReader = jsonReader(orderResponse);
		Map<Object, Object> resTransactionId = (Map<Object, Object>) responseReader.get("returnData");
		Object responseOrderId = resTransactionId.get("orderId");

		String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		pathURL = "/picklists/" + responseOrderId + "/invoice?branchCode=" + B2CbranchCode + "&cmd=create";

		if (lineItems.equalsIgnoreCase("Single")) {

			jsonReader = jsonArrayReader(makeinvoicepayload);
			jsonReader.clear();

			map = new HashMap<>();

			for (Map<Object, Object> object : itemDetailsPickList) {

				if (object.containsKey("itemId")) {
					map.put("shortPickFlag", false);
					map.put("itemId", object.get("itemId"));

					batchDetails = (List<Map<Object, Object>>) object.get("batchDetails");

					break;
				}
			}
			for (Map<Object, Object> object2 : batchDetails) {

				if (object2.containsKey("batchId")) {
					map.put("batchId", object2.get("batchId"));
					map.put("qty", object2.get("qty"));
					break;
				}

			}
			jsonReader.add(map);

		} else if (lineItems.equals("multiple")) {

			jsonReader = jsonArrayReader(makeinvoicepayload);
			jsonReader.clear();

			for (Map<Object, Object> object : itemDetailsPickList) {

				map = new HashMap<>();

				if (object.containsKey("itemId")) {
					map.put("shortPickFlag", false);
					map.put("itemId", object.get("itemId"));

					batchDetails = (List<Map<Object, Object>>) object.get("batchDetails");
				}
				for (Map<Object, Object> object2 : batchDetails) {

					if (object2.containsKey("batchId")) {
						map.put("batchId", object2.get("batchId"));
						map.put("qty", object2.get("qty"));
					}
				}

				jsonReader.add(map);
			}
		}

		hostURL();
		basicAuth();
		passingResponseAsList(jsonReader);
		postRequest(pathURL);
		String body = response.getBody().asString();
		System.out.println(body);

		jsonPayloadWriter(jsonReader, makeinvoicepayload);
		jsonResponseBodyWriter(response, makeinvoicerespose);

	}

	@Then("I Generate Invoice")
	public void i_generate_invoice() throws Exception {

		makeInvoiceC2("single");
	}

	@Then("I validate with the expected Response code {int}")
	public void i_validate_with_the_expected_response_code(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Then("I Generate invoice")
	public void i_generate_invoice1() throws Exception {

		makeInvoiceC2("multiple");
	}

	@Then("I validate with the expected Response Code {int}")
	public void i_validate_with_the_expected_response_code1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@SuppressWarnings("unchecked")
	public void makeInvoiceC2(String lineItems) throws Exception {

		String BranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		picklistResponse = jsonReader(picklistrespose);

		returnData = (List<Map<Object, Object>>) picklistResponse.get("returnData");

		for (Map<Object, Object> map : returnData) {

			if (map.containsKey("partShipmentOrderId")) {

				pathURL = "/picklists/" + map.get("partShipmentOrderId").toString().substring(0, 14)
						+ "/invoice?branchCode=" + BranchCode + "&cmd=create&partShipmentOrderId="
						+ map.get("partShipmentOrderId");

				itemDetailsPickList = (List<Map<Object, Object>>) map.get("itemDetails");

				break;
			}
		}

		if (lineItems.equalsIgnoreCase("Single")) {

			jsonReader = jsonArrayReader(makeinvoicepayload);

			jsonReader.clear();

			map = new HashMap<>();

			for (Map<Object, Object> object : itemDetailsPickList) {

				if (object.containsKey("itemId")) {
					map.put("itemId", object.get("itemId"));

					batchDetails = (List<Map<Object, Object>>) object.get("batchDetails");

					break;
				}
			}
			for (Map<Object, Object> object2 : batchDetails) {

				if (object2.containsKey("batchId")) {
					map.put("batchId", object2.get("batchId"));
					map.put("qty", object2.get("qty"));
					break;
				}

			}

			for (Map<Object, Object> map2 : returnData) {

				if (map2.containsKey("itemDetails")) {

					itemDetailsPickList = (List<Map<Object, Object>>) map2.get("itemDetails");

					break;
				}
			}

			jsonReader.add(map);

		} else if (lineItems.equals("multiple")) {

			jsonReader = jsonArrayReader(makeinvoicepayload);
			jsonReader.clear();

			for (Map<Object, Object> object : itemDetailsPickList) {

				map = new HashMap<>();

				if (object.containsKey("itemId")) {
					map.put("itemId", object.get("itemId"));

					batchDetails = (List<Map<Object, Object>>) object.get("batchDetails");
				}

				for (Map<Object, Object> object2 : batchDetails) {

					if (object2.containsKey("batchId")) {
						map.put("batchId", object2.get("batchId"));
						map.put("qty", object2.get("qty"));
					}
				}

				jsonReader.add(map);
			}

		}
		hostURL();

		basicAuth();

		passingResponseAsList(jsonReader);
		postRequest(pathURL);
		String body = response.getBody().asString();
		System.out.println(body);

		jsonPayloadWriter(jsonReader, makeinvoicepayload);
		jsonResponseBodyWriter(response, makeinvoicerespose);
	}

	static String picklistresponse = System.getProperty("user.dir")
			+ "/Json_Response/PicklistResponse/generatepicklistresponse.json";

	static String orderresponse = System.getProperty("user.dir") + "/Json_Response/OrderResponse/orderresponse.json";

	static String makeInvoiceshortpickPayload = System.getProperty("user.dir")
			+ "/Json_Payload/MakeInvoicePayload/shortPickmakeinvoicepayload.json";
	static String makeInvoiceshortpickResponse = System.getProperty("user.dir")
			+ "/Json_Response/MakeInvoiceResponse/makeinvoiceresponse.json";

	static int qty = 0;

	static Object objQty = 0;

	@Given("I generate B2C Invoice with short pick")
	public void i_generate_b2c_invoice_with_short_pick() throws Exception {

		makeInvoiceShortPick("b2c");
	}

	@Then("I validate B2C order response code with {int}")
	public void i_validate_b2c_order_response_code_with3(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Generate The Invoice with short pick")
	public void i_generate_the_invoice_with_short_pick() throws Exception {

		makeInvoiceShortPick("FC2FC");
	}

	@Then("I Validate With THe Expected Response code {int}")
	public void i_validate_with_t_he_expected_response_code(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I Generate Invoice with short pick")
	public void i_generate_invoice_with_short_pick() throws Exception {

		makeInvoiceShortPick("C2");
	}

	@Then("I validate with THe expected Response code {int}")
	public void i_validate_with_t_he_expected_response_code1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	static String pathUrl;
	static String B2COrderID1;

	@SuppressWarnings("unchecked")
	public void makeInvoiceShortPick(String string) throws Exception {

		if (string.equalsIgnoreCase("b2c")) {

			JSONObject B2COrderID = jsonReader(orderresponse);

			Map<Object, Object> resTransactionId = (Map<Object, Object>) B2COrderID.get("returnData");

			String B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

			B2COrderID1 = resTransactionId.get("orderId").toString();

			pathUrl = "/picklists/" + B2COrderID1 + "/invoice?branchCode=" + B2CbranchCode
					+ "&cmd=create&partShipmentOrderId=" + B2COrderID1 + "-S1";
		} else if (string.equalsIgnoreCase("c2") || string.equalsIgnoreCase("fc2fc")) {

			JSONObject B2COrderID = jsonReader(picklistresponse);

			List<Map<Object, Object>> resTransactionId = (List<Map<Object, Object>>) B2COrderID.get("returnData");

			for (Map<Object, Object> map : resTransactionId) {

				if (string.equalsIgnoreCase("fc2fc")) {

					B2COrderID1 = map.get("partShipmentOrderId").toString().substring(0, 11);

					String SupplierId = ConfigurationReader.getCR().getFRM().getProperty("SupplierId");

					pathUrl = "/picklists/" + B2COrderID1 + "/invoice?branchCode=" + SupplierId
							+ "&cmd=create&partShipmentOrderId=" + B2COrderID1 + "-S1";

				} else if (string.equalsIgnoreCase("c2")) {

					B2COrderID1 = map.get("partShipmentOrderId").toString().substring(0, 14);

					String BranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

					pathUrl = "/picklists/" + B2COrderID1 + "/invoice?branchCode=" + BranchCode
							+ "&cmd=create&partShipmentOrderId=" + B2COrderID1 + "-S1";
				}
			}

		}

		JSONArray jsonReader = jsonArrayReader(makeInvoiceshortpickPayload);

		jsonReader.clear();

		picklistResponse = jsonReader(picklistrespose);

		returnData = (List<Map<Object, Object>>) picklistResponse.get("returnData");

		for (Map<Object, Object> map : returnData) {

			if (map.containsKey("itemDetails")) {

				itemDetailsPickList = (List<Map<Object, Object>>) map.get("itemDetails");

				break;
			}
		}
		Map<Object, Object> map = new HashMap<>();
		Map<Object, Object> map2 = new HashMap<>();

		for (Map<Object, Object> object : itemDetailsPickList) {

			if (object.containsKey("itemId")) {
				map.put("shortPickFlag", false);
				map.put("itemId", object.get("itemId"));

				batchDetails = (List<Map<Object, Object>>) object.get("batchDetails");

				break;
			}
		}
		for (Map<Object, Object> object2 : batchDetails) {

			if (object2.containsKey("batchId")) {
				map.put("batchId", object2.get("batchId"));
				objQty = object2.get("qty");
				float ff = Float.parseFloat(objQty.toString());

				qty = (int) (Math.round(ff));

				map.put("qty", (qty * 50) / 100);

				jsonReader.add(map);

				break;
			}
		}

		map2.putAll(map);
		map2.put("shortPickFlag", true);
		map2.put("shortPickReason", "Stock Not Available");
		map2.put("qty", (qty * 60) / 100);

		jsonReader.add(map2);

		hostURL();

		basicAuth();

		passingResponseAsList(jsonReader);

		postRequest(pathUrl);

		jsonPayloadWriter(jsonReader, makeInvoiceshortpickPayload);

		String body = response.getBody().asString();

		System.out.println(body);

		jsonResponseBodyWriter(response, makeInvoiceshortpickResponse);

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public static void transactionDownloadAsPDF() throws Exception {

		JSONObject jsonReader2 = jsonReader(makeinvoicerespose);

		List<Map<Object, Object>> returndata = (List<Map<Object, Object>>) jsonReader2.get("returnData");

		String tnxId = "";

		for (Map<Object, Object> transactionId : returndata) {

			tnxId = transactionId.get("transactionId").toString();
			break;
		}

		String BranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		String pathURL = "/transaction/softcopy?transactionId=" + tnxId + "&branchCode=" + BranchCode + "&format=pdf";
		
		hostURL();

		basicAuthForPDF();
		
		try {
			
			InputStream requestInput = getRequestAsInput(pathURL);
			
			File file = new File("./adc.pdf");
			
			Files.copy(requestInput, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			requestInput.close();
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
		
		


	}
	
}
