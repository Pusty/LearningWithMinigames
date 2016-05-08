package me.pusty.util.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonReader;




public class JsonHandler {
	JsonReader jsonReader;
	/**Json File Handler*/
	public JsonHandler() {
		jsonReader = new JsonReader();
	}
	
	/**Returns JsonObject from File**/
	public JsonValue getObjectFromFile(InputStream is) {
		  JsonValue jsonobject = null;
	        jsonobject = jsonReader.parse(is);
	        return jsonobject;
	}
	
	/**Returns JsonArray from File**/
	public JsonValue getArrayFromFile(InputStream is) {
		JsonValue jsonarray = null;
	        BufferedReader bufferedreader = null;
	        try {
	            bufferedreader = new BufferedReader(new InputStreamReader(is));
	            jsonarray = jsonReader.parse(is);
	        }
	        finally {
	           try {if(bufferedreader!=null)bufferedreader.close();} catch (Exception e) {e.printStackTrace();}
	        }
	        return jsonarray;
	}


	
	
}
