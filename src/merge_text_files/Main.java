package merge_text_files;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static void main(String args[]) {
		final String QUIT_TEXT = "quit";
		String folderPath = "";
		String outputFileName = "";
		
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
		
		File dir = new File(folderPath);
		File outputFile = new File(folderPath + "\\" + outputFileName);		
		
		if (!dir.exists()) {
			System.out.println("Directory \"" + dir + "\" not exists");
			return;
		}
		
		if (!dir.isDirectory()) {
			System.out.println("\"" + dir + "\" is not directory");
			return;
		}
		
		ArrayList<File> listFiles = getListFiles(dir, outputFileName);
		
		if (listFiles.size() == 0) {
			System.out.println("Text files not found");
			return;
		}
		
		if (outputFile.exists()) {
			System.out.println("Output file is already exists");
			return;
		}
		
		createOutputFile(outputFile);
		
		mergeFiles(listFiles, outputFile);
	}
	
	private static ArrayList<File> getListFiles(File dir, String outputFileName) {
		ArrayList<File> listFiles = new ArrayList<File>();
		for (File item: dir.listFiles()) {
			if ( (!item.getName().equalsIgnoreCase(outputFileName)) && (item.getName().indexOf(".txt") > -1 || item.getName().indexOf(".csv") > -1) ){
				listFiles.add(item);
			}
		}
		return listFiles;
	}
	
	private static void createOutputFile(File outputFile) {	
		try {
			if (!outputFile.createNewFile()) {
				System.out.println("Output file not created");
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private static void mergeFiles(ArrayList<File> listFiles, File outputFile) {
		final int BUF_SIZE = 256; 
		char buf[] = new char[BUF_SIZE];
		int c;
		
		try {
			FileWriter writer = new FileWriter(outputFile.getAbsolutePath(), true);
			for (File item: listFiles) {	
				FileReader reader = new FileReader(item.getAbsolutePath());
				while ((c = reader.read(buf)) > 0) {
					if (c < BUF_SIZE) {
						buf = Arrays.copyOf(buf, c);
					}
					writer.write(buf);
				}
				reader.close();
			}
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}