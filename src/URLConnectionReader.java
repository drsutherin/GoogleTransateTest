
import java.net.*;
import java.io.*;
import org.json.simple.*;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
    	String api_key = "";
    	try {
            FileReader file = new FileReader("api-key.txt");
            BufferedReader buff = new BufferedReader(file);
            api_key = buff.readLine();
            buff.close();         
        }
        catch(IOException ex) {
            System.out.println("Error reading file");
            System.exit(1);
        }
    	
    	String url="https://translation.googleapis.com/language/translate/v2?key=" + api_key;
    	URL object=new URL(url);

    	HttpURLConnection con = (HttpURLConnection) object.openConnection();
    	con.setDoOutput(true);
    	con.setDoInput(true);
    	con.setRequestProperty("Content-Type", "application/json");
    	con.setRequestProperty("Accept", "application/json");
    	con.setRequestMethod("POST");

    	JSONObject request   = new JSONObject();
    	JSONArray q = new JSONArray();
    	
    	q.add("Hello, my name is David.");
    	request.put("q",q);
    	request.put("source", "en");
    	request.put("target", "es");


    	OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
    	wr.write(request.toString());
    	wr.flush();
    	System.out.println("Sending request...");

    	StringBuilder sb = new StringBuilder(); 
    	int HttpResult = con.getResponseCode(); 
    	if (HttpResult == HttpURLConnection.HTTP_OK) {
    	    BufferedReader br = new BufferedReader(
    	            new InputStreamReader(con.getInputStream(), "utf-8"));
    	    String line, result = null;  
    	    while ((line = br.readLine()) != null) {  
    	    	if (line.contains("translatedText"))	{
    	    		result = line;
    	    	}
    	        sb.append(line + "\n");  
    	    }
    	    br.close();
    	    
    	    String[] arr = result.split(":");
    	    String translatedText = arr[1].substring(2, arr[1].length()-1);
    	    System.out.println(translatedText);
 
    	} else {
    	    System.out.println(con.getResponseMessage());  
    	}  
    }
}