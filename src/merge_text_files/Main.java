package merge_text_files;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.ArrayList;

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
		} catch (UnsupportedEncodingException ex) {
			System.out.println("UnsupportedEncodingException: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("IOException: " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	private static ArrayList<File> getListFiles(File dir, String outputFileName) throws IOException {
		File[] sourceListFiles = dir.listFiles();
		ArrayList<File> targetListFiles = new ArrayList<>();
		for (File item: sourceListFiles) {
			if ( (!item.getName().equalsIgnoreCase(outputFileName)) && (item.getName().contains(".txt") || item.getName().contains(".csv")) ){
				targetListFiles.add(item);
			}
		}
		return targetListFiles;
	}
	
	private static void createOutputFile(File outputFile) throws IOException {
		if (!outputFile.createNewFile()) {
			System.out.println("Output file not created");
		}
	}
	
	private static void mergeFiles(ArrayList<File> listFiles, File outputFile) throws IOException {
		String str;

		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		
		for (File item: listFiles) {
			FileInputStream fileInputStream = new FileInputStream(item);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			while ((str = bufferedReader.readLine()) != null) {
				bufferedWriter.write(str);
				bufferedWriter.newLine();
			}
			
			fileInputStream.close();
			inputStreamReader.close();
			bufferedReader.close();
		}
		
		bufferedWriter.flush();
		
		fileOutputStream.close();
		outputStreamWriter.close();
		bufferedWriter.close();
	}
}