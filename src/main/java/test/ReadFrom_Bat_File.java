package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadFrom_Bat_File {

	public static void main(String[] args) {

		ProcessBuilder pb = new ProcessBuilder("./file.bat");

		ArrayList<String> name = new ArrayList<>();
		ArrayList<String> tags = new ArrayList<>();
		
		try {
			
			Process p = pb.start();

			StringBuilder str = new StringBuilder();

			InputStreamReader isr = new InputStreamReader(p.getInputStream());

			BufferedReader br = new BufferedReader(isr);

			String line;
			
			while ((line = br.readLine()) != null) {

				str.append(line + "\n");
				
				name.add(line);
			}

			int code = p.waitFor();

			if (code == 0) {

//				System.out.println(str);
				
//				System.out.println(name);

//				System.exit(0);  // this line stop the program 
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		String newTag = "";
	
		for (String string : name) {
			
			char[] charArray = string.toCharArray();
			
			for (int i = 0; i < charArray.length; i++) {
				
				newTag = "";
				
				
				if (charArray[i] == ':') {
					
					for (int j = i+1; j < charArray.length; j++) {
						
						Character car = charArray[j];
						
//						System.out.println(charArray[j]);
						
						newTag = newTag + car.toString();
						
					}
					
					tags.add(newTag.trim());
					
				}
				
			}
			
		}
		
		System.out.println(tags);
	
	}

}
