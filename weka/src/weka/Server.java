package weka;

import java.io.*;
import java.net.*;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;
import weka.core.converters.JSONLoader;
/**
 * 实现服务器端
 * 用于接收上传的数据和供客户端下载数据
 * @author DELL
 *
 */
public class Server {
    private int port;
    private static ServerSocket server;
    private MultilayerPerceptron m_classifier;
    
    public Server(int port){
        this.port = port;
        this.build();//this 为隐形参数，可省略
    }
    
    public void run(){
        if(server==null){
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("服务已启动...");
        DataInputStream in= null; //读取Socket的输入流
        DataOutputStream dos = null;
        try {
			while (true) {
				Socket client = server.accept();
				if(client==null)
					continue;
				while(true){
					//通过ServerSocket的accept方法建立连接,并获取客户端的Socket对象
					in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
					// 将指令和文件发送到Socket的输出流中
					dos = new DataOutputStream(client.getOutputStream());
					
				    // 获取数据
				    String recvInfo = in.readUTF();
				    if(recvInfo.equals("end")){
				    	client.close();
				    	break;
				    }
//				    System.out.println(recvInfo);
				    // 验证获取的数据
				    JSONLoader jsonLoader = new JSONLoader();
				    
				    jsonLoader.setSource(new ByteArrayInputStream(recvInfo.getBytes()));
				    Instances instances = jsonLoader.getDataSet();
				    instances.setClassIndex(instances.numAttributes()-1);
				    double result = m_classifier.classifyInstance(instances.firstInstance());
				    dos.writeUTF(result+"");
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
       
    }

    public void build(){
    	MultilayerPerceptron m_classifier = new MultilayerPerceptron();
        File inputFile = new File("C:/train.csv");//训练语料文件
        CSVLoader atf = new CSVLoader();
     //   ArffLoader atf = new ArffLoader(); 
		try {
			atf.setFile(inputFile);
			// 读入训练文件  
			Instances train = atf.getDataSet();
			
			// 设置图形界面显示
			m_classifier.setGUI(true);
			// 设置自动构建
			m_classifier.setAutoBuild(true);
			// 设置隐藏层（只有设置自动构建时该参数才有效）
			m_classifier.setHiddenLayers("5,3");
			// 设置验证集大小,百分比（取值为0~100）
			m_classifier.setValidationSetSize(50);
			// 属性归一化处理
			m_classifier.setNormalizeAttributes(true);
			
			// 设置训练数据集的类属性，即对哪个数据列进行预测（属性的下标从0开始）,instancesTest.numAttributes()可以取得属性总数
			train.setClassIndex(train.numAttributes()-1); 
			
			// 训练 模型
			m_classifier.buildClassifier(train);   

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args){
        //设置服务器端口
        int port = 8888;
        new Server(port).run();
    }
}
