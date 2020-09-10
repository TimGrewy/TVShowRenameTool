package org.dk.tim.srtgenerator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Omdøber sibtitles fra 'https://www.captiongenerator.com/make-an-el-risitas-laughing-man-interview-video' til Kapwing format
 * - Således kan man let uploade videoen i bedre kvalitet og få subtitles lang ovenpå.
 * 
 * 
 * HOW TO:
 * 1. Lig subs på disk, (læs i linje 37)
 * 2. Kopier consol output til en ny fil
 * 3. Brug denne fil på Kapwing
 * 3.1 Omdøb vid fra source folder til ."IKKE rar" og upload denne på Kap
 * 
 * 
 * 
 * 
 * 
Source:
00:00 - 00:05Så er vi samlet til et fantastisk kobberbryllups fest

Target:
13
00:01:04,885 --> 00:01:09,461
Velkommen til en særudgave af Grand Tour.
 *
 */

public class SrtGenerator {
	String subs;
	
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("C:\\Users\\Tim\\git\\TVShowRenameTool\\TVShowRenameTool\\src\\Subs.txt");
	    List<String> readAllLines = Files.readAllLines(path, Charset.forName("UTF-8"));
	    
	    String createSubEntry = createSubEntry(readAllLines);
	    
		System.out.println(createSubEntry);
	}

	private static String createSubEntry(List<String> readAllLines) {
		String result = "";

		int i = 1;
	    for (String string : readAllLines) {
			Linje linje = new Linje(string);
			result += i++;
			result += "\n";
			result += linje.getStart();
			result += " --> ";
			result += linje.getSlut();
			result += "\n";
			result += linje.subtitle;
			result += "\n\n";
		}
	    
	    return result;
	}
	
	private static class Linje {
		String startMin;
		String startSec;
		String slutMin;
		String slutSec;
		String subtitle;
		
		public Linje(String src) {
			startMin = src.substring(0, 2);
			startSec = src.substring(3, 5);
			slutMin = src.substring(8, 10);
			slutSec = src.substring(11, 13);
			subtitle = src.substring(13);
		}
		
		String getStart() {
			return "00:"+startMin+":"+startSec+",000";
		}
		String getSlut() {
			return "00:"+slutMin+":"+slutSec+",000";
		}
	}
}
