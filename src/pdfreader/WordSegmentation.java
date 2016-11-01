package pdfreader;

import java.util.Properties;

import iitb.CRF.*;
import iitb.Model.FeatureGenImpl;

public class WordSegmentation {
	FeatureGenImpl featGen;
	CRF crfModel;
	Properties options;
	public static void main(String[] args) throws Exception {
		WordSegmentation ws = new WordSegmentation();
		//ws.train();
		ws.test();
		
	}
	
	void train() throws Exception {
		DataIterImpl trainData = new DataIterImpl("resources/train.txt", false);
		allocModel();
		featGen.train(trainData);
		double weights[] = crfModel.train(trainData);
		crfModel.write("output/model");
		featGen.write("output/features");
	}
	
	void test() throws Exception {
		DataIterImpl testData = new DataIterImpl("resources/test.txt", true);
		testData.startScan();
		allocModel();
		featGen.read("output/features");
		crfModel.read("output/model");
		
		while (testData.hasNext()) {
			DataSequence d = testData.next();
			crfModel.apply(d);
			featGen.mapStatesToLabels(d);
			System.out.println(((DataSequenceImpl)d).precision());
		}
	}
	
	void allocModel() throws Exception {
		featGen = new FeatureGenImpl("naive", 3);
		options = new Properties();
		crfModel = new CRF(3, featGen, options);
	}
}
