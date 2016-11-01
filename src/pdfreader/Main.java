package pdfreader;

import java.io.*;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.text.PDFTextStripper; 
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Main {

	public static void main(String[] args) {
		Automata au = new Automata();
		Encoder ec = new Encoder();
		String test = new String("abc.");
		test = test.replaceAll("[().,!?]", "");
		
		System.out.println(test);
		/*
		au.loadAutomata();
		try{
			PDDocument document = null;
			document = PDDocument.load(new File("Lão Hạc.pdf"));
		    document.getClass();
		    if (!document.isEncrypted()) {
		        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		        stripper.setSortByPosition(true);
		        PDFTextStripper Tstripper = new PDFTextStripper();
		        String st = Tstripper.getText(document);
		        //System.out.println(st);
		        Scanner sc = new Scanner(st);
		        //*********Test chinh ta***********************
		        String[] test = {"a dua", "lão", "hạc", "cló", "sầt"};
		        for(int i = 0; i<5; i++){
		        	String s = test[i];
		        	s = s.toLowerCase();
		        	System.out.println(au.recognizeWord(ec.encode(s)));
		        	
		        }
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		//***********************Test xay dung automata************************
		/*
		BufferedReader br = null;
		try {
			
			String sCurrentLine;
			Encoder ec = new Encoder();
			Automata au = new Automata();
			br = new BufferedReader(new FileReader("resources/Viet11K.txt"));
			//building the automata
			while ((sCurrentLine = br.readLine()) != null) {
				int[] code = ec.encode(sCurrentLine);
				//sCurrentLine.replaceFirst(".", "");
				au.addWord(code);
			}
			au.saveAutomata();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}*/

	}

}
