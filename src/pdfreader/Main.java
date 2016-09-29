package pdfreader;

import java.io.File; 
import java.io.IOException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.text.PDFTextStripper; 
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Main {

	public static void main(String[] args) {
		try{
			PDDocument document = null;
			document = PDDocument.load(new File("Lão Hạc.pdf"));
		    document.getClass();
		    if (!document.isEncrypted()) {
		        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		        stripper.setSortByPosition(true);
		        PDFTextStripper Tstripper = new PDFTextStripper();
		        String st = Tstripper.getText(document);
		        System.out.println(st);
		        Scanner sc = new Scanner(st);
		        for(int i = 0; i<10; i++){
		        	String s = sc.next();
		        	System.out.println(s);
		        	
		        	//test
		        	System.out.println(s.charAt(1));
		        }
		    }
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
