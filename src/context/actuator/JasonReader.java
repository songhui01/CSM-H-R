package context.actuator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;

public class JasonReader {
	public static JSONObject readJson(String fileName) throws JSONException, IOException{
//		String jsonString = readFile("/Users/songhuiyue/Documents/workspace/ContextStateMachineForIVERS/config/smartCampus_json_1.json");
		String jsonString = readFile(fileName);
//		System.out.println(jsonString);
		JSONObject obj = new JSONObject(jsonString);
		return obj;
//		String pageName = obj.getJSONObject("pageInfo").getString("pageName");

//	    System.out.println(pageName);
//		
//		JSONArray arr = obj.getJSONArray("posts");
//		for (int i = 0; i < arr.length(); i++)
//		{
//		    String post_id = arr.getJSONObject(i).getString("post_id");
//		    System.out.println(post_id);
//		    JSONArray comment = arr.getJSONObject(i).getJSONArray("comments");
//		    for (int j = 0; j < comment.length(); j++)
//			{
//			    String comment_id = comment.getJSONObject(j).getString("comment_id");
//			    System.out.println(comment_id);
//			}
//		}
	}
	
	private static String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}

}
