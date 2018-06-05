package us.dontcareabout.jiss.server;

import static us.dontcareabout.jiss.server.Util.delete;
import static us.dontcareabout.jiss.server.Util.deleteFolder;

import java.io.File;
import java.util.ArrayList;

/**
 * 清除 Windows（7） temp 目錄下的壞東西。
 * <p>
 * 目前會砍掉下列東西：
 * <ul>
 * 	<li>Eclipse 檔案：problem-index*.zip</li>
 * 	<li>GWT 檔案：gwt*byte-cache、ImageResourceGenerator*.*</li>
 * 	<li>GWT 目錄：gwt-codeserver-*tmp、ResourceProvider*</li>
 * 	<li>KKMan 檔案：~D*.TMP</li>
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
		if (file.getName().matches("~D.*TMP")) {
			delete(file, error);
			return;
		}

		if(file.getName().matches("gwt.*byte-cache")) {
			delete(file, error);
			return;
		}

		if (file.getName().startsWith("ImageResourceGenerator")) {
			delete(file, error);
			return;
		}

		if (file.getName().matches("problems-index.*zip")) {
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
}
