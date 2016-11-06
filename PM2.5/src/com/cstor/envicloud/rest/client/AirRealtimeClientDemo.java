//package pm25;
//好用--能够利用接口进行对数据的搜集
package com.cstor.envicloud.rest.client;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.io.*;

 
//import org.json.JSONObject;
//import org.restlet.data.MediaType;
//import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
 
//import com.google.gson.Gson;
 
public class AirRealtimeClientDemo
{
    public static void main(String[] args)
    {
        String baseUrl = "http://www.pm25.in/api/querys/pm2_5.json";
        // please set your accessid here
        String accessId = "5j1znBVAsnSf5xQyNQyq";     
         
        // please set your citycodes here
        //List<String> citycodes = new ArrayList<String>();
        //citycodes.add("210200");
        String cityCode = "大连";
         
        System.out.println(getRealtimeAirInfoPostRsp(baseUrl, accessId, cityCode));
    }
     
    private static String getRealtimeAirInfoPostRsp(String baseUrl, String accessId,
                                                  String citycode)
    {
        String url = baseUrl + "?city=" + citycode + "&token=" + accessId;
             
        //Map<String, Object> reqMap = new HashMap<String, Object>();
        //reqMap.put("accessId", accessId);
        //reqMap.put("citycodes", citycodes);
         
        //Gson gson = new Gson();
        //String reqJson = gson.toJson(reqMap);
         
        String response = "";
        //StringBuffer tStringBuffer = new StringBuffer();
        ClientResource clientResource = new ClientResource(url);        
        try
        {
            //Representation rep = new JsonRepresentation(new JSONObject(reqJson));
            //rep.setMediaType(MediaType.APPLICATION_JSON);
            Representation reply = clientResource.get();
            response = reply.getText();
            
            		
           	//BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(reply.getStream()));          
            //while ((response = tBufferedReader.readLine()) != null){
            //tStringBuffer.append(response);
            //}

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            clientResource.release();
        }
         
        return response;
    }   
}

