//package pm25;
//�ܹ���ɵĹ��ܣ��ܹ�����һСʱ�ɼ�һ������
package com.cstor.envicloud.rest.client2;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
    	
        TalkingClock clock = new TalkingClock(2000,true);//һ����60000
    	 clock.start();
    	 JOptionPane.showMessageDialog(null,"Quit program?");
    	 try
    	 {
    		 TalkingClock.fop.close();
    	 }
    	 catch(Exception e)
    	 {
    		 //����д �����ߵ�
    	 }
    	 
    	 System.exit(0);
     }  
  
}
class TalkingClock
{
private int interval;
private boolean beep;
public static FileOutputStream fop = null;
private static File file = null;

String baseUrl = "http://www.pm25.in/api/querys/aqi_details.json";
// please set your accessid here
String accessId = "5j1znBVAsnSf5xQyNQyq";     
// please set your citycodes here
String citycode = "大连";


private static String getRealtimeAirInfoPostRsp(String baseUrl, String accessId, String citycode)//����
{
    
	String response = null;
	//�Ѿ��������˽ӿ�URLΪhttp://service.envicloud.cn:8082/api/getRealtimeAir?citycode={citycode}&ak={accessId}�����pm2.5��API
	String url = baseUrl + "?city=" + citycode + "&token=" + accessId;
	
	//String content = "This is the text content  lianxiao";
	//String content = getRealtimeAirInfoPostRsp(baseUrl, accessId, citycode);
	//System.out.println(content);
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
	this.interval = interval;    //this��ʾ��ʽ����     java��111
	this.beep = beep;
	file = new File("c:/pm2.5-5.6.csv");
	try
	{		
		if (!file.exists()) 
        {
         file.createNewFile();
         fop = new FileOutputStream(file);
         fop.write("PM2.5浓度\r\n".getBytes());
         fop.flush();
        }
		fop = new FileOutputStream(file,true);
			
	}
	catch(Exception e)
	{}
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
		String Content = "";
		
		
		
		System.out.println(Content = getRealtimeAirInfoPostRsp(baseUrl, accessId, citycode));		
		
		try
	    {
	    	
	     // if file doesnt exists, then create it
	        
	        //String newline = System.getProperty("line.separator");
	        byte[] contentInBytes = ("pm2.5,"+ Content + "\r\n").getBytes();
	        
	        fop.write(contentInBytes);
	        fop.flush();
	        
	     
	        System.out.println("Done");
	      
	        
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    } 
		
		Toolkit.getDefaultToolkit().beep();
	}
}
}
