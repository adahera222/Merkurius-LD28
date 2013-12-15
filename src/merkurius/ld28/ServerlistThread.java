package merkurius.ld28;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

 
public class ServerlistThread extends Thread {
    
	
		private static String sendGet(String url) throws Exception {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
	
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	
		return response.toString();
	}
	
	public void run() {
		while(true) {
			try {
	    		sendGet("http://serverlist.alexandre.kohen.fr?add");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				sleep(45000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
