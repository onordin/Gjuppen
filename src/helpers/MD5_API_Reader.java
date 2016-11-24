package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MD5_API_Reader {
	
	
	public static String getResult(String hash) throws Exception {
		
		String start = "http://md5decrypt.net/Api/api.php?hash=";
		String end = "&hash_type=md5&email=conny_w80@live.se&code=14ab5ef08e059357";
		String urlToCall = start + hash + end; 
		System.out.println(urlToCall);
		
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToCall);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		
		return result.toString();
	   }

}
