package weka;

import java.io.File;
import java.io.IOException;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
public class MultilayerPerceptronTest {

	public static void main(String[] args) {
		MultilayerPerceptron m_classifier = new MultilayerPerceptron();
        File inputFile = new File("D:\\Program Files (x86)\\Weka-3-8\\data\\cpu.with.vendor.arff");//训练语料文件
        ArffLoader atf = new ArffLoader(); 
		try {
			atf.setFile(inputFile);
			// 读入训练文件  
			Instances train = atf.getDataSet();
			
			// 设置图形界面显示
			m_classifier.setGUI(true);
			// 设置自动构建
			m_classifier.setAutoBuild(true);
			// 设置隐藏层（只有设置自动构建时该参数才有效）
			m_classifier.setHiddenLayers("20,5");
			// 设置验证集大小,百分比（取值为0~100）
			m_classifier.setValidationSetSize(50);
			
			// 设置训练数据集的类属性，即对哪个数据列进行预测（属性的下标从0开始）,instancesTest.numAttributes()可以取得属性总数
			train.setClassIndex(2); 
			
			// 训练 模型
			m_classifier.buildClassifier(train);    
			// 指定测试数据
			inputFile = new File("D:\\Program Files (x86)\\Weka-3-8\\data\\cpu.with.vendor.arff");//测试语料文件
			atf.setFile(inputFile); 
			// 读入测试文件
			Instances test = atf.getDataSet(); 
			test.setClassIndex(2);

			// 验证预测效果
			Evaluation eval = new Evaluation(train);
			eval.evaluateModel(m_classifier, test);
			System.out.println(eval.toSummaryString("\nResults\n\n", false));
			
			// 输出其它详细信息
//			System.out.println(m_classifier.toString());	 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

