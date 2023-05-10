package derby_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.properties.PropertiesWriter;

public class Derby_Connection {

	static Connection connection;

	static Statement statement;

	static ResultSet executeQuery;

	static String url = "jdbc:derby://localhost:1527/D:\\Netmeds\\DB\\db_rwos_common;user=rwos;password=rwos";

	static int count = 0;

	static List<Integer> itemId = new ArrayList<>();
	
	public Derby_Connection() throws Exception {
		
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		connection = DriverManager.getConnection(url);

		statement = connection.createStatement();
	}
	
	public static Derby_Connection getDerbyConnection() throws Exception {
		
		Derby_Connection connect = new Derby_Connection();
		
		return connect;
		
	}
	

	public void itemIdFromDerby(String Query) throws Exception {

		executeQuery = statement.executeQuery(Query);

		while (executeQuery.next()) {

			itemId.add(executeQuery.getInt("ITEM_ID"));

		}
		
//		Random rd = new Random();
//		int next = rd.nextInt(itemId.size());
//		
//		int itemId1 = Integer.parseInt(ConfigurationReader.getCR().getFRM().getTestItemId1());
//		int itemId2 = Integer.parseInt(ConfigurationReader.getCR().getFRM().getTestItemId12());
//
//		if (itemId1 != itemId.get(next) && itemId2  != itemId.get(next)) {
//			
//			PropertiesWriter.getPropertiesWriter().setProperties("TestItemId1", Integer.toString(itemId.get(next)));
//			
//		}else if (itemId1 == itemId.get(next) || itemId2  == itemId.get(next)) {
//			
//			PropertiesWriter.getPropertiesWriter().setProperties("TestItemId1",Integer.toString(itemId.get(rd.nextInt(itemId.size()))));
//		}
//		
//		int nextInt = rd.nextInt(itemId.size());
//		
//			
//		if (itemId1 != itemId.get(nextInt) && itemId2  != itemId.get(nextInt)) {
//		
//			PropertiesWriter.getPropertiesWriter().setProperties("TestItemId12",Integer.toString(itemId.get(nextInt)));
//				
//		}else if (itemId1 != itemId.get(nextInt) || itemId2  != itemId.get(nextInt)) {
//			
//			PropertiesWriter.getPropertiesWriter().setProperties("TestItemId12", Integer.toString(itemId.get(rd.nextInt(itemId.size()))));
//		}

		connection.close();
		
		System.out.println(itemId);
		
	}
	
	static LinkedList<Object> gst = new LinkedList<>();
	static LinkedList<Object> partyID = new LinkedList<>();
	
	public void test(String Query) throws Exception {
		
		executeQuery = statement.executeQuery(Query);

		while (executeQuery.next()) {

			Object int1 = executeQuery.getObject("PARTY_ID");
			
			Object int2 = executeQuery.getObject("GSTIN");
			
			gst.add(int2);
			
			partyID.add(int1);
			
		}
		
		Random rd = new Random();
		
		int randomNum = rd.nextInt(partyID.size());
		
		Object object = gst.get(randomNum);
		Object object2 = partyID.get(randomNum);
		
		if (object.toString().length() == 15 && object2 != null) {
			
//			System.out.println(object);
			
			PropertiesWriter.getPropertiesWriter().setProperties("existingPartyID", object2.toString());
		
//			System.out.println(object2);
		
		}else if (object.toString().length() != 15 || object2 == null) {
			
			int randomNum2 = rd.nextInt(partyID.size());
			object = gst.get(randomNum2);
			object2 = partyID.get(randomNum2);
			PropertiesWriter.getPropertiesWriter().setProperties("existingPartyID", object2.toString());
//			System.out.println(object);
//			System.out.println(object2);
		}
		
	}

//	public static void main(String[] args) throws Exception {
//
////	getDerbyConnection().itemIdFromDerby("select * from RWITEM i left join RWBATCHINVENTORY t on t.BATCH_ID = i.ITEM_ID where AVAILABLE_QTY > 2");
//	
//		
//		getDerbyConnection().test("select * from RWPARTY t where t.GSTIN  like '%27_%' and t.ENTITY_TYPE = 'CR'");
//	}

}
