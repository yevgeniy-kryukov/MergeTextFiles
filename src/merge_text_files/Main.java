package merge_text_files;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		final String QUIT_TEXT = "quit";
		String folderPath;
		String outputFileName;
		
		try {
			Scanner in = new Scanner(System.in);

			do {
				System.out.print("1. Enter the full path to the directory with files (type \"" + QUIT_TEXT + "\" for exit):");	
				folderPath = in.nextLine();
			} while(folderPath.isEmpty());
					
			if (folderPath.equalsIgnoreCase(QUIT_TEXT)) return;

			do {
				System.out.print("2. Enter the name(*.txt(csv)) of the output file (type \"" + QUIT_TEXT + "\" for exit):");	
				outputFileName = in.nextLine();
			} while(outputFileName.isEmpty());

			if (outputFileName.equalsIgnoreCase(QUIT_TEXT)) return;
			
			in.close();
			
			new MergeTextFiles(folderPath, outputFileName).run();
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}
}