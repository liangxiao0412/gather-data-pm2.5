package weka;

import java.net.*;
import java.io.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * 用Socket实现文件服务器的客户端
 * 包含发送文件和接收文件功能
 *
 */
public class Client {
    private Socket client;
    
    public Client(){
    	
    }
    //构造方法
    public Client(String host,int port){
            try {
                //创建Socket对象
                client = new Socket(host,port);
                System.out.println("服务器连接成功！");       
            } catch (UnknownHostException e) {
                System.out.println("无法解析的主机！");
            } catch (IOException e) {
                System.out.println("服务器连接失败！");
            }        
    }
   
    // 发送数据，模拟数据流
    public void sendData(){
    	DataOutputStream dos = null;
    	DataInputStream dis = null;
		try {
			// 将指令和文件发送到Socket的输出流中
			dos = new DataOutputStream(client.getOutputStream());
			// 接收数据的流
			dis = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			
			File inputFile = new File("C:/test.txt");// 训练语料文件
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(inputFile)));
//			String col[] = { "commercial_type", "trade_money",
//					"distance(commercial-family)", "money_contrast",
//					"money_contrast-average", "outliers?" };
			String line = null;
			String header = "{"
					+ "\"relation\" : \"test\","
					+ "\"attributes\" : ["
					+ "{"
					+ "\"name\" : \"co_value\","
					+ "\"type\" : \"numeric\","
					+ "\"class\" : false,"
					+ "\"weight\" : 1.0"
					+ "},"
					+ "{"
					+ "\"name\" : \"No2_value\","
					+ "\"type\" :\"numeric\","
					+ "\"class\" : false,"
					+ "\"weight\" : 1.0"
					+ "},"
					+ "{"
					+ "\"name\" : \"O3_value\","
					+ "\"type\" : \"numeric\","
					+ "\"class\" : false,"
					+ "\"weight\" : 1.0"
					+ "},"
					+ "{"
					+ "\"name\" : \"PM10_value\","
					+ "\"type\" : \"numeric\","
					+ "\"class\" : false,"
					+ "\"weight\" : 1.0"
					+ "},"
					+ "{"
					+ "\"name\" : \"PM2.5_value\","
					+ "\"type\" : \"numeric\","
					+ "\"class\" : false,"
					+ "\"weight\" : 1.0"
					+ "},"
					+ "{"
					+ "\"name\" : \"So2_value\","
					+ "\"type\" : \"numeric\","
					+ "\"class\" : false,"
					+ "\"weight\" : 1.0"
					+ "},"
					+ "{"
					+ "\"name\" : \"API\","
					+ "\"type\" : \"numeric\","
					+ "\"class\" : false,"
					+ "\"weight\" : 1.0"
					+ "}"
					+ "]"
					+ "}";
			br.readLine();
			while ((line = br.readLine()) != null) {
				String val[] = line.split(",");//消除之间的空隙
				JSONObject object = new JSONObject();
				object.put("header", JSON.parseObject(header));
				JSONObject data = new JSONObject();
				data.put("sparse", false);
				data.put("weight", 1.00000001);
				data.put("values", val);
				JSONArray jsonArray = new JSONArray();
				jsonArray.add(data);
				object.put("data", jsonArray);
				dos.writeUTF(object.toJSONString());
				System.out.println(object.toJSONString());
				//清空缓存，将文件名发送出去
				dos.flush();
				String result = null;
				while((result = dis.readUTF()) != null){
					System.out.println("验证结果为："+result);
					break;
				}
			}
			dos.writeUTF("end");
			dos.flush();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public static void main(String[] args){
        String hostName = "localhost";
        int port = 8888;
        Client client = new Client(hostName,port);;
        client.sendData();
    }
}