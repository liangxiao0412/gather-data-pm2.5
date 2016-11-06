//package pm25;
//能够完成的功能：能够进行一小时采集一次数据
package com.cstor.envicloud.rest.client1;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.io.*;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

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
        TalkingClock clock = new TalkingClock(3600000,true);//一分钟60000
   //     int i;
    
     while(true)
      {  
    	 
    	 clock.start();
    	 JOptionPane.showMessageDialog(null,"Quit program?");
    	 System.exit(0);
        
       }
     }
 // json csv   
  
}
class TalkingClock
{
private int interval;
private boolean beep;

String baseUrl = "http://www.pm25.in/api/querys/pm2_5.json";
// please set your accessid here
String accessId = "5j1znBVAsnSf5xQyNQyq";     
// please set your citycodes here
String cityCode = "大连";


private static String getRealtimeAirInfoPostRsp(String baseUrl, String accessId, String citycode)//方法
{
    
	String response = null;
	//已经告诉你了接口URL为http://service.envicloud.cn:8082/api/getRealtimeAir?citycode={citycode}&ak={accessId}；结合pm2.5的API
	String url = baseUrl + "?city=" + citycode + "&token=" + accessId;
     
    ClientResource clientResource = new ClientResource(url);        
    try
    {
        Representation reply = clientResource.get();
        response = reply.getText();
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

public TalkingClock(int interval, boolean beep)
{
	this.interval = interval;
	this.beep = beep;
}

public void start()
{
	ActionListener listener = new TimePrinter();
	Timer t = new Timer(interval, listener);
	t.start();
}
public class TimePrinter implements ActionListener
{
	public void actionPerformed(ActionEvent event)
	{
		Date now = new Date();
		System.out.println("At the tone, the time is" +now);
		System.out.println(getRealtimeAirInfoPostRsp(baseUrl, accessId, cityCode));
		Toolkit.getDefaultToolkit().beep();
	}
}
}
