package pdfreader;

import java.util.*;

public class Encoder {
	private final String ALPHABET = "\0 -abcdđeghiklmnopqrstuvxyáàảãạăắằẳẵặâấầẩẫậíìỉĩịúùủũụưứừửữựéèẻẽẹêếềểễệóòỏõọôốồổỗộơớờởỡợýỷỳỹỵ";
	private Map<Character, Integer> codeMap;
	public Encoder()
	{
		 codeMap = new HashMap<Character, Integer>();
		for (int i = 0; i < ALPHABET.length(); i++){
			codeMap.put(ALPHABET.charAt(i), i);
		}
	}
	public int[] encode(String word)
	{
		int[] A = new int[word.length() + 1];
		int i;
		//System.out.println(A.length);
		for (i = 0; i < word.length(); i++)
		{
			//System.out.println(codeMap==null);
			char temp = word.charAt(i);
			
			if (codeMap.get(temp) == null)
			{
				System.out.println("ajsjas");
				
			}
			A[i] = codeMap.get(temp);
		}
		A[i] = 0;
		return A;
	}
	
}
