package stepDefinitions;

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

public class B2CCustomerOrders extends Common_Methods {

	static String B2CbranchCode;
	static Response postRequest;
	static int statusCode;
	static int count;
	static Object responseTransactionId;
	static String batchId;
	static String TransactionId;

	static String orderpayload = System.getProperty("user.dir")
			+ "/Json_Payload/OrderPayload/B2Ccustomerorderpayload.json";
	static String orderresponse = System.getProperty("user.dir") + "/Json_Response/OrderResponse/orderresponse.json";

	static String itemDetails = System.getProperty("user.dir") + "/Json_Items_Details/Item_details.json";

	@Given("I am on an B2C order Creation API with single {string} line Item")
	public void i_am_on_an_b2c_order_creation_api_with_single_line_item(String string) throws Exception {

		b2cCustomerOrder("", string, "");
	}

	@Then("I Validate B2C Order Response Code with {int}")
	public void i_validate_b2c_order_response_code_with1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order Creation API with <{string}> line Item_IGST {int}")
	public void i_am_on_an_b2c_order_creation_api_with_line_item_igst(String string, Integer int1) throws Exception {

		b2cCustomerOrder(int1.toString(), string, "IGST");
	}

	@Then("I Validate B2C Order Response code with {int}")
	public void i_validate_b2c_order_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order Creation API with <{string}> line Item_CGST {int}")
	public void i_am_on_an_b2c_order_creation_api_with_line_item_cgst(String string, Integer int1) throws Exception {

		b2cCustomerOrder(int1.toString(), string, "CGST");
	}

	@Then("I VAlidate B2C Order Response code with {int}")
	public void i_v_alidate_b2c_order_response_code_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order Create API with multiple {string} line Item")
	public void i_am_on_an_b2c_order_create_api_with_multiple_line_item(String string) throws Exception {

		b2cCustomerOrder("", string, "");
	}

	@Then("I VAlidate B2C order response code with {int}")
	public void i_v_alidate_b2c_order_response_code_with1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order Create API with multiple {string} line Item_nostock")
	public void i_am_on_an_b2c_order_create_api_with_multiple_line_item_nostock(String string) throws Exception {

		b2cCustomerOrder("", string, "");
	}

	@Then("I VAlidate B2C Order Response Code with {int}")
	public void i_v_alidate_b2c_order_response_code_with2(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order Create API with multiple {string} line Item_partstock")
	public void i_am_on_an_b2c_order_create_api_with_multiple_line_item_partstock(String string) throws Exception {

		b2cCustomerOrder("", string, "");
	}

	@Then("I VAlidate b2c Order Response Code with {int}")
	public void i_v_alidate_b2c_order_response_code_with3(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order create API with {string} same shipping and billing address")
	public void i_am_on_an_b2c_order_create_api_with_same_shipping_and_billing_address(String string) throws Exception {

		b2cCustomerOrder("", string, "");
	}

	@Then("I validate b2C order REsponse COde with {int}")
	public void i_validate_b2c_order_r_esponse_c_ode_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order create API with {string} different shipping and billing address")
	public void i_am_on_an_b2c_order_create_api_with_different_shipping_and_billing_address(String string)
			throws Exception {

		b2cCustomerOrder("Different Address", string, "");
	}

	@Then("I Validate b2C order REsponse COde with {int}")
	public void i_validate_b2c_order_r_esponse_c_ode_with1(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2C order create API with {string} single without discount {double}")
	public void i_am_on_an_b2c_order_create_api_with_single_without_discount(String string, Double double1)
			throws Exception {

		discountPer = double1;

		b2cCustomerOrder("", string, "");
	}

	@Then("I Validate b2C Order REsponse COde with {int}")
	public void i_validate_b2c_order_r_esponse_c_ode_with3(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);

	}

	@Given("I am on an B2C order create API with {string} single with discount {double}")
	public void i_am_on_an_b2c_order_create_api_with_single_with_discount(String string, Double double1)
			throws Exception {

		discountPer = double1;
		b2cCustomerOrder("", string, "");
	}

	@Then("I Validate B2C Order Response COde with {int}")
	public void i_validate_b2c_order_response_c_ode_with(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2c Order create APi with shippling {string} charge {double}")
	public void i_am_on_an_b2c_order_create_a_pi_with_shippling_charge(String string, Double double1) throws Exception {

		shippingCharges = double1;

		b2cCustomerOrder("", string, "");

		shippingCharges = null;

	}

	@Then("I validate B2C order response Code with {int}")
	public void i_validate_b2c_order_response_code_with3(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}

	@Given("I am on an B2c Order create APi with out shippling {string} charge")
	public void i_am_on_an_b2c_order_create_a_pi_with_out_shippling_charge(String string) throws Exception {

		b2cCustomerOrder("", string, "");
	}

	@Then("I validate B2C order response COde with {int}")
	public void i_validate_b2c_order_response_c_ode_with4(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	
	@Given("I am on an B2c Order create APi with COD {string} {string}")
	public void i_am_on_an_b2c_order_create_a_pi_with_cod(String string, String string2)throws Exception {
		
		b2cCustomerOrder("", string, string2);
	}
	
	@Then("I validate B2c order response Code with {int}")
	public void i_validate_b2c_order_response_code_with5(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	
	@Given("I am on an B2c Order create APi with prepaid {string} {string}")
	public void i_am_on_an_b2c_order_create_a_pi_with_prepaid(String string, String string2) throws Exception{
		
		b2cCustomerOrder("", string, string2);
	}
	
	@Then("I validate b2c order response Code with {int}")
	public void i_validate_b2c_order_response_code_with6(int int1) {

		statusCode = response.jsonPath().getInt("returnCode");
		Assert.assertEquals(statusCode, int1);
	}
	
	static Double discountPer;
	static Double shippingCharges;

	@SuppressWarnings({ "unchecked", })
	@Test
	public static void b2cCustomerOrder(String p_CodeAndAddress, String singleOrMultiple, String GST) throws Exception {

		B2CbranchCode = ConfigurationReader.getCR().getFRM().getProperty("BranchCode");

		String pathURL = "/customerorders?branchCode=" + B2CbranchCode + "&cmd=create";

		hostURL();

		basicAuth();

		JSONObject jsonReader = jsonReader(orderpayload);

		if (shippingCharges != null) {

			jsonReader.put("shippingCharges", shippingCharges);

		} else {

			jsonReader.put("shippingCharges", 0.00);

		}
		
		if (GST.equalsIgnoreCase("COD")) {
			
			jsonReader.put("paymentMode", GST);
			
		}else if (GST.equalsIgnoreCase("prepaid")) {
			
			jsonReader.put("paymentMode", GST);
		}

		jsonReader.put("branchCode", B2CbranchCode);
		jsonReader.put("orderId", setB2COrderID());

		Map<Object, Object> billingAddress = (Map<Object, Object>) jsonReader.get("billingAddress");

		Map<Object, Object> shippingAddress = (Map<Object, Object>) jsonReader.get("shippingAddress");

		if (GST.equalsIgnoreCase("IGST") || GST.equalsIgnoreCase("CGST")) {

			billingAddress.clear();

			billingAddress.putAll(sameShipAndBillingAddress());

			shippingAddress.clear();

			shippingAddress.putAll(sameShipAndBillingAddress());

			billingAddress.put("postalCode", p_CodeAndAddress);

			shippingAddress.put("postalCode", p_CodeAndAddress);

		} else if (p_CodeAndAddress.equalsIgnoreCase("Different Address")) {

			billingAddress.clear();

			billingAddress.putAll(sameShipAndBillingAddress());

			shippingAddress.clear();

			shippingAddress.putAll(b2cshippingAddress());

		} else {

			billingAddress.clear();

			billingAddress.putAll(sameShipAndBillingAddress());

			shippingAddress.clear();

			shippingAddress.putAll(sameShipAndBillingAddress());

			billingAddress.put("postalCode", 600002);

			shippingAddress.put("postalCode", 600002);
		}

		if (singleOrMultiple.equalsIgnoreCase("single")) {

			List<Map<Object, Object>> singleIterm = (List<Map<Object, Object>>) jsonReader.get("items");

			singleIterm.clear();

			JSONArray items = jsonArrayReader(itemDetails);

			List<Map<Object, Object>> item = items;

			for (Map<Object, Object> map : item) {

				if (map.get("seqNo").toString().equals("1")) {

					if (discountPer == null) {

						singleIterm.add(map);

					} else if (discountPer != null) {

						map.put("discountPer", discountPer);

						singleIterm.add(map);

						discountPer = null;
					}

					break;
				}

			}

			jsonReader.put("lineItemCount", 1);

		} else if (singleOrMultiple.equals("multiple")) {

			List<Map<Object, Object>> multipleItems = (List<Map<Object, Object>>) jsonReader.get("items");

			multipleItems.clear();

			JSONArray items = jsonArrayReader(itemDetails);

			multipleItems.addAll(items);

			count = 0;

			for (Map<Object, Object> object : multipleItems) {

				if (object.containsKey("itemId")) {

					count++;

					jsonReader.put("lineItemCount", count);
				}
			}
		}

		passingResponseAsMap(jsonReader);

		postRequest = postRequest(pathURL);

		jsonPayloadWriter(jsonReader, orderpayload);

		jsonResponseBodyWriter(postRequest, orderresponse);

		if (postRequest.jsonPath().getInt("returnCode") == 8301) {

			String string = postRequest.jsonPath().getString("returnText");

			String[] split = string.split("value");

			char[] charArray = split[1].toCharArray();

			String billAmout = "";

			for (int i = 0; i < charArray.length; i++) {

				if (charArray[i] == '[') {

					for (int j = i + 1; j < charArray.length; j++) {

						if (charArray[j] != ']') {

							billAmout = billAmout + charArray[j];

						} else if (charArray[j] == ']') {

							break;
						}
					}
				}
			}

			Float amount = new Float(billAmout);

			jsonReader.put("totalDueAmt", amount);
			jsonReader.put("totalBillAmt", amount);

			passingResponseAsMap(jsonReader);

			postRequest = postRequest(pathURL);

			jsonPayloadWriter(jsonReader, orderpayload);

			jsonResponseBodyWriter(postRequest, orderresponse);
		}
		if (postRequest.jsonPath().getInt("returnCode") != 8301) {

			System.out.println(postRequest.asString());

		} else if (postRequest.jsonPath().getInt("returnCode") == 1000) {

			System.out.println(postRequest.asString());
		}
	}

}
