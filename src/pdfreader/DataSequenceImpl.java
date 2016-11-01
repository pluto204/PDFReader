package pdfreader;

import java.io.FileReader;
import java.util.ArrayList;

import iitb.CRF.DataSequence;

public class DataSequenceImpl implements DataSequence {
	//for a sentence
	public ArrayList<String> tokens;
	public ArrayList<Integer> labels;
	
	//for testing
	public ArrayList<Integer> trueLabels;
	
	public DataSequenceImpl(String s, boolean testing) {
		// TODO Auto-generated constructor stub
		tokens = new ArrayList<String>();
		labels = new ArrayList<Integer>();
		trueLabels = new ArrayList<Integer>();
		StringBuffer temp = new StringBuffer();
		int i, j = 0;
		for (i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\t') {
				
				tokens.add(temp.toString().replaceAll(":", "-"));
				
				temp.delete(0, temp.length());
			}
			else if (s.charAt(i) == '\n') {
				String l = temp.toString();
				int label;
				if (l.compareTo("B_W") == 0) {
					label = 0;
				}
				else if (l.compareTo("I_W") == 0) {
					label = 1;
				}
				else {
					label = 2;
				}
				labels.add(label);
				if (testing) {
					trueLabels.add(label);
				}
				temp.delete(0, temp.length());
			}
			else {
				temp.append(s.charAt(i));
			}
		}
			
		
	} 
	@Override
	public int length() {
		// TODO Auto-generated method stub
		return tokens.size();
	}

	@Override
	public void set_y(int i, int l) {
		// TODO Auto-generated method stub
		labels.set(i, l);
	}

	@Override
	public Object x(int i) {
		// TODO Auto-generated method stub
		return tokens.get(i);
	}

	@Override
	public int y(int i) {
		// TODO Auto-generated method stub
		return labels.get(i);
	}
	
	public double precision() {
		int sum = 0;
		for (int i = 0; i < labels.size(); i++) {
			if (labels.get(i) == trueLabels.get(i)) {
				sum++;
			}
			
		}
		return sum /(double)labels.size(); 
	}

}
