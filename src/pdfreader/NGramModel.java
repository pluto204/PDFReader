package pdfreader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class NGramModel {
	public static Hashtable<String, Hashtable<String, Integer>> ngram;
	public SyllableTree tree;
	
	public NGramModel(String file) {
		ngram = new Hashtable<String, Hashtable<String, Integer>>();
		tree = new SyllableTree(file);
	}
	
	void construct(String corpus) {
		try {
			ngram.clear();
			FileReader fr = new FileReader(corpus);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while ((s = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s, " ");
				String pre = "";
				boolean existedPre = false;
				while (st.hasMoreTokens()) {
					String syl = st.nextToken();
					boolean existedSyl = tree.search(syl);
					if (existedSyl) {
						if (ngram.containsKey(syl)) {
							Hashtable<String, Integer> value = ngram.get(syl);
							if (existedPre) {
								if (value.containsKey(pre)) {
									value.put(pre, value.get(pre) + 1);
								}
								else {
									value.put(pre, 1);
									
								}
							}
						}
						else {
							Hashtable<String, Integer> tempHash = new Hashtable<String, Integer>();
							if (existedPre) {
								tempHash.put(pre, 1);
							}
							ngram.put(syl, tempHash);
									
						}
					}
					pre = syl;
					existedPre = existedSyl;
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void loadNgram() {
		
	}
	
	void smoothing() {
		
	}
	
	public void testNgram() {
		for (int i = 0; i < 10; i++) {
			Hashtable<String, Integer> temp = new Hashtable<String, Integer>();
			
			for (int j = 0; j < 5; j++) {
				temp.put("sub-string" + j, j + 10);
			}
			ngram.put("string" + i, temp);
		}
		saveNgram();
	}
	
	public void saveNgram() {
		try {
			FileWriter fw = new FileWriter("output/ngram");
			
			Enumeration<String> keys = ngram.keys();
			while (keys.hasMoreElements()) {
				String s = keys.nextElement();
				fw.write(s);
				Hashtable<String, Integer> subTable = ngram.get(s);
				Enumeration<String> subkeys = subTable.keys();
				while (subkeys.hasMoreElements()) {
					String ss = subkeys.nextElement();
					int count = subTable.get(ss);
					fw.write(" "+ ss + " " + count);
				}
				fw.write("\n");
			}
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test(String input) {
		SyllableTree st = new SyllableTree("resources/VNSyl.txt");
		boolean check = st.search("dai");
		
	}
	
	
	public boolean check(String word, Automata au) {
		int na = 0, pa = 0, i, len, index = 0;
		char alpha[] = new char[3];
		boolean vSide = false;
		char c = word.charAt(0);
		if (Utilities.isVowel(c)) {
			vSide = true;
			na++;
		}
		else {
			vSide = false;
			pa++;
		}
		alpha[index] = c;
		i = 1;
		len = word.length();
		while(i < len) {
			c = word.charAt(i);
			boolean isVowel = Utilities.isVowel(c);
			if (isVowel && vSide == false) {
				//from pa to na-> check the pa in alpha, 
				
				if(!checkConsonantComb(alpha, index+1)) {
					//System.out.println(word);
					return false;
				}
				//check done!
				na++;
				index = 0;
				vSide = true;
			}
			else if (!isVowel && vSide == true) {
				//from na to pa-> check na in alpha
				if(!checkVowelComb(alpha, index+1, au)) {
					
					return false;
				}
				//check done
				pa++;
				index = 0;
				vSide = false;
			}
			else {
				//no switch
				index++;
			}
			if (na != 1 || pa > 2 || index > 2) return false;
			alpha[index] = c;
			 
			i++;
		}
		return true;
	}
	public ArrayList<String> checkSentence(String sen, Automata au) {
		ArrayList<String> wrong = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(sen, ".,!?:\n\"");
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			boolean check = tree.search(temp);
			if (!check) {
				check = check(temp, au);
				if (!check) {
					wrong.add(temp);
				}
			}
		}
		return wrong;
	}
	
	public boolean checkVowelComb(char[] alpha, int len, Automata au) {
		if (len == 1) return true;
		Encoder ec = new Encoder();
		int code[] = ec.encode(alpha, len);
		return code == null? false : au.recognizeWord(code);
		//return true;
	}
	
	public boolean checkConsonantComb(char[] alpha, int len) {
		if (len == 3) {
			if (alpha[0] == 'n' &&
					alpha[1] == 'g' &&
					alpha[2] == 'h') {
				return true;
			} 
		}
		else if (len == 2) {
			char c1 = alpha[0], c2 = alpha[1];
			if (c2 == 'h') {
				if (c1 == 'c' || c1 == 'p' || 
						c1 == 't' || c1 == 'n' ||
						c1 == 'g' || c1 == 'k'
						)
					return true;
			}
			else if ((c1 == 't' && c2 == 'r') ||
						(c1 == 'n' && c2 == 'g'))
				return true;
		}
		else {
			return true;
		}
		return false;
	}
	
	
	
	
	public static void main(String[] arg) {
		NGramModel m = new NGramModel("resources/VNsyl.txt");
		boolean check = m.tree.search("");
		System.out.println(check);
	}
}

class SyllableTree{
	class SyllableNode {
		String content;
		SyllableNode left;
		SyllableNode right;
		public SyllableNode(String s) {
			content = s;
		}
	};
	SyllableNode root;
	public SyllableTree(String file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			root = new SyllableNode(br.readLine());
			while ((s = br.readLine()) != null) {
				append(s, root);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	void append(String s, SyllableNode r) {
		if (s.compareTo(r.content) < 0) {
			if (r.left == null) {
				r.left = new SyllableNode(s);
			}
			else {
				append(s, r.left);
			}
		}
		else if (s.compareTo(r.content) > 0) {
			if (r.right == null) {
				r.right = new SyllableNode(s);
			}
			else {
				append(s, r.right);
			}
		}
	}
	public boolean search(String s) {
		return search(s, root);
	}
	boolean search(String s, SyllableNode n) {
		if (n == null) {
			return false;
		}
		else if (n.content.compareTo(s) == 0) {
			return true;
		}
		else {
			return s.compareTo(n.content) < 0 ?  search(s, n.left) : search(s, n.right);
		}
	}
}
