package base_class_API;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.properties.ConfigurationReader;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Common_Methods {

	static RequestSpecification request;
	static JSONObject json;
	public static Response response;
	static JSONArray jsonArray;
	static ObjectMapper mapper = new ObjectMapper();
	public static Scanner scanner;

	public static void hostURL() throws Exception {

		RestAssured.baseURI = ConfigurationReader.getCR().getFRM().getHosturl();
	}

	public static void basicAuth() throws Exception {

		request = RestAssured.given().auth()
				.basic(ConfigurationReader.getCR().getFRM().getauUsername(),
						ConfigurationReader.getCR().getFRM().getauPassword())
				.header("Content-Type", "application/json").when();
	}

	public static void basicAuthForPDF() throws IOException {

		request = RestAssured.given().auth()
				.basic(ConfigurationReader.getCR().getFRM().getauUsername(),
						ConfigurationReader.getCR().getFRM().getauPassword())
				.header("Content-Type", "application/octet-stream").when();
	}

	public static void passingResponseAsMap(JSONObject map) {

		request.body(map.toJSONString());
	}

	public static void passingResponseAsList(JSONArray list) {

		request.body(list.toJSONString());
	}

	public static Response postRequest(String postPathurl) {

		response = request.post(postPathurl).then().extract().response();

		return response;
	}

	public static byte[] getRequest(String getPathurl) {

		byte[] asByteArray = request.get(getPathurl).asByteArray();
		
		return asByteArray;
	}
	
	public static InputStream getRequestAsInput(String getPathurl) {

		InputStream input = request.get(getPathurl).asInputStream();
		
		return input;
	}

	public static InputStream getRequestForPDF(String getPathurl) {

		InputStream asInputStream = request.get(getPathurl).then().extract().asInputStream();

		return asInputStream;
	}

	@SuppressWarnings("unchecked")
	public static JSONArray jsonArrayReader(String filePath)
			throws StreamReadException, DatabindException, IOException {

		List<Map<Object, Object>> readValue = mapper.readValue(Paths.get(filePath).toFile(), List.class);

		jsonArray = new JSONArray();

		jsonArray.addAll(readValue);

		return jsonArray;
	}

	public static JSONObject jsonReader(String filePath) throws StreamReadException, DatabindException, IOException {

		@SuppressWarnings("unchecked")
		Map<Object, Object> readValue = mapper.readValue(Paths.get(filePath).toFile(), Map.class);
		json = new JSONObject(readValue);
		return json;
	}

	public static void jsonPayloadWriter(Object object, String filePath) throws IOException {

		mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), object);
	}

	public static void jsonResponseBodyWriter(Response response, String filePath) throws IOException {

		FileWriter file = new FileWriter(filePath);
		file.write(response.getBody().asString());
		file.close();
	}

	public static String CurrentTime() throws Exception {

		Timestamp liveTimes = new Timestamp(System.currentTimeMillis());
		return liveTimes.toString();
	}

	public static String scan() {

		if (scanner == null) {

			scanner = new Scanner(System.in);
		}
		return scanner.nextLine();
	}
	
	public static String timesStamp() {

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SS");
		Date date = new Date();

		String format = formatter.format(date);

		String[] split = format.split(":");

		String newNum = "";
		for (String time : split)
		{
			newNum = newNum + time;
		}
		
		newNum = newNum.substring(0,7);
		
		return newNum;
	}

	public static String setB2COrderID() throws Exception {
		
		String b2cOrderID = "NMS-234907582";
		
		String createOrderID = b2cOrderID.substring(0,4);
		
		createOrderID = createOrderID + timesStamp();
		
		return createOrderID;
	}
	
	public static String customerOrderRefId() throws IOException, ConfigurationException {

		String customerOrderID = "CSQ-CO-103227237";
		
		String substring = customerOrderID.substring(0,7);
		
		substring = substring + timesStamp();

		return substring;
	}
	
	//TEST30111
	public String fc2fcOrderRef() throws Exception {
		

		String fc2fcOrderRef = "TEST30111";

		String substring = fc2fcOrderRef.substring(0,4);
		
		substring = substring + timesStamp();
		
		return substring; 

	}
	
	public static Map<Object, Object> sameShipAndBillingAddress() {
		
		Map<Object, Object> Address = new HashMap<>();
		
		Address.put("customerName", "Network Medicine");
		Address.put("addressLine1", "outer ring road");
		Address.put("addressLine2", "akme harmony 2c 701");
		Address.put("city", "CHENNAI");
		Address.put("state", "Tamilnadu");
		Address.put("country", "India");
		Address.put("postalCode", "600001");
		Address.put("phone", "9620629843");
		
		return Address;
	}
	public static Map<Object, Object> b2cshippingAddress() {
		
		Map<Object, Object> address = new HashMap<>();
		
		address.put("customerName", "Netmeds Marketplace");
		address.put("addressLine1", "5th Floor, EA Chambers");
		address.put("addressLine2", "No 49 & 50 L, Whites Rd");
		address.put("city", "Mumbai");
		address.put("state", "Maharashtra ");
		address.put("country", "India");
		address.put("postalCode", "400011");
		address.put("phone", "9876543210");
		
		return address;
	}
	
	public static void main(String[] args) {
			
		System.out.println(timesStamp());

	}
	
	
}
