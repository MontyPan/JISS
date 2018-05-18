package us.dontcareabout.jiss.server;

import java.io.File;
import java.util.ArrayList;

/**
 * 清除 Windows（7） temp 目錄下的壞東西。
 * <p>
 * 目前會砍掉下列東西：
 * <ul>
 * 	<li>GWT 檔案：gwt*byte-cache、ImageResourceGenerator*.*</li>
 * 	<li>GWT 目錄：gwt-codeserver-*tmp、ResourceProvider*</li>
 * </ul>
 */
public class TempCleaner {
	public static ArrayList<File> clean() {
		final ArrayList<File> error = new ArrayList<>();
		final File folder = new File(System.getenv("LocalAppData"), "Temp");

		for (File f : folder.listFiles()) {
			if (f.isFile()) { processFile(f, error); }
			else { processFolder(f, error); }
		}

		return error;
	}

	private static void processFile(File file, ArrayList<File> error) {
		if(file.getName().matches("gwt.*byte-cache")) {
			delete(file, error);
			return;
		}

		if (file.getName().startsWith("ImageResourceGenerator")) {
			delete(file, error);
			return;
		}

		//XXX 以前還有個 uiBinder_com.* 的條件，現在弄不出來... O.o
	}

	private static void processFolder(File folder, ArrayList<File> error) {
		if (folder.getName().matches("gwt-codeserver-.*tmp")) {
			deleteFolder(folder, error);
			return;
		}

		if (folder.getName().startsWith("ResourceProvider")) {
			deleteFolder(folder, error);
		}
	}

	private static void deleteFolder(File folder, ArrayList<File> error) {
		for (File f : folder.listFiles()) {
			if (f.isFile()) { delete(f, error); }
			else { deleteFolder(f, error); }
		}

		delete(folder, error);
	}

	private static void delete(File file, ArrayList<File> log) {
		if (!file.delete()) {
			log.add(file);
		}
	}
}
