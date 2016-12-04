package pdfreader;

import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.text.PDFTextStripper; 
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Main {

	public static void main(String[] args) {
		//Test automata
		
		Automata au = new Automata();
		Encoder ec = new Encoder();
		
		
		au.loadAutomata();
		
		try{
			PDDocument document = null;
			document = PDDocument.load(new File("Lão Hạc.pdf"));
		    document.getClass();
		    if (!document.isEncrypted()) {
		        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		        stripper.setSortByPosition(true);
		        PDFTextStripper Tstripper = new PDFTextStripper();
		        String s = Tstripper.getText(document);
		        //s = s.replaceAll("\n[^\t]", " ");
		        NGramModel model = new NGramModel("resources/VNsyl.txt");
		        //StringTokenizer st = new StringTokenizer(, ",.\n ");
		        ArrayList<String> wrong = model.checkSentence("Nguyễn hà Đăng Linh đang đi chơi clgt".toLowerCase(), au);
		        for(int i = 0; i < wrong.size(); i++) {
		        	System.out.println(wrong.get(i));
		        }
		        
		        //System.out.println(s);
		        //Scanner sc = new Scanner(st);
		        /**********Test chinh ta***********************
		        String[] test = {"a dua", "lão", "hạc", "cló", "sầt"};
		        for(int i = 0; i<5; i++){
		        	String s = test[i];
		        	s = s.toLowerCase();
		        	System.out.println(au.recognizeWord(ec.encode(s)));
		        8	
		        }*/
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		//***********************Test xay dung automata************************
		/*
		BufferedReader br = null;
		try {
			
			String sCurrentLine;
			Encoder ec = new Encoder();
			Automata au = new Automata();
			br = new BufferedReader(new FileReader("resources/VNsyl.txt"));
			br.readLine();
			//building the automata
			while ((sCurrentLine = br.readLine()) != null) {
				int[] code = ec.encode(Utilities.getVowelComb(sCurrentLine.toLowerCase()));
				//sCurrentLine.replaceFirst(".", "");
				au.addWord(code);
			}
			System.out.println(au.size);
			au.saveAutomata();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	*/
	}

}
