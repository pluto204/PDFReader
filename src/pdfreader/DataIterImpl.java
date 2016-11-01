package pdfreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import iitb.CRF.DataIter;
import iitb.CRF.DataSequence;

public class DataIterImpl implements DataIter {
	private ArrayList<DataSequence> data;
	private int current;
	
	public DataIterImpl(String file, boolean testing) {
		// TODO Auto-generated constructor stub
		data = new ArrayList<DataSequence>();
		try {
			StringBuffer sb = new StringBuffer();
			FileReader input = new FileReader(file);
			BufferedReader br = new BufferedReader(input);
			String s;
			while ((s = br.readLine()) != null) {
				if (s.length() == 0) {
					//end of sentence
					DataSequenceImpl d = new DataSequenceImpl(sb.toString(), testing);
					data.add(d);
					sb.delete(0, sb.length());
				}
				else {
					sb.append(s+"\n");
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return current < (data.size() - 1);
	}

	@Override
	public DataSequence next() {
		// TODO Auto-generated method stub
		return data.get(++current);
	}

	@Override
	public void startScan() {
		// TODO Auto-generated method stub
		current = -1;
	}

}
