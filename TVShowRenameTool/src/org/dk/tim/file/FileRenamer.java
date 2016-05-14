package org.dk.tim.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 	Renames either all srt files or video files to match their thier counter part
 *	
 *	Ask for a directory to process
 *	Lists all avi files in this folder
 *  Lists all srt files in the folder
 *   - Fails if there are not an equal number of srt and video files
 *  
 *  Renames either srt or video files based on user input
 *
 */
public class FileRenamer {
	/** Defines which file names you want to KEEP */
	public enum KEEP {
		SRT_NAMES, VID_NAMES
	}

	private static FileTool fileTool = new FileTool();

	public static void main(String[] args) {
		System.out.println("Begin");
		List<File> listFilesInDirectory = fileTool.listFilesInDirectory(Paths.get(readLine("Directory?: ")));
		//		List<File> listFilesInDirectory = fileTool.listFilesInDirectory(Paths.get("G:\\Downloads\\Game of Thrones Season 2"));
		List<File> srtFiles = new ArrayList<File>();
		List<File> aviFiles = new ArrayList<File>();

		for (File file : listFilesInDirectory) {
			String name = file.getName();
			if (name.indexOf("srt") > -1) {
				srtFiles.add(file);
			} else {
				aviFiles.add(file);
			}
		}

		if (srtFiles.size() != aviFiles.size()) {
			throw new IllegalArgumentException("Lists sizes does not match: Avi: " + aviFiles.size() + " Srt: " + srtFiles.size());
		}

		KEEP keep = readFromConsole_VideoOrSrtInput();

		for (int i = 0; i < srtFiles.size(); i++) {
			File srtFile = srtFiles.get(i);
			File aviFile = aviFiles.get(i);
			String videoExtension = getVideoExtension(aviFile.getName());

			if (keep == KEEP.VID_NAMES) {
				String newName = aviFile.getName().replace(videoExtension, ".srt"); //The new name will be the entire name of the avi file, except the videos extension which will be srt
				rename(srtFile, newName);
			} else {
				String newName = srtFile.getName().replace(".srt", videoExtension); //Copy the name of the SRT file, but replace "srt" with the orig video name
				rename(aviFile, newName);
			}

		}

		System.out.println("DONE");
	}

	private static KEEP readFromConsole_VideoOrSrtInput() {
		while (true) {
			String input = readLine("Type 'vid' or 'srt' to use this as the base for renaming");
			if (input.equalsIgnoreCase("vid")) {
				return KEEP.VID_NAMES;
			} else if (input.equalsIgnoreCase("srt")) {
				return KEEP.SRT_NAMES;
			}
		}
	}

	private static String getVideoExtension(String name) {
		int lastIndexOf = name.lastIndexOf(".");
		return name.substring(lastIndexOf);
	}

	private static void rename(File srtFile, String newName) {
		File file2 = new File(srtFile.getParentFile().getPath() + "\\" + newName);
		System.out.println("Renaming: " + srtFile.getName() + "|" + newName);
		boolean success = fileTool.renameFile(srtFile, file2);
		System.out.println(": " + success);
	}

	private static String readLine(String question) {
		System.out.println(question);
		try {
			InputStreamReader streamReader = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			return bufferedReader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
