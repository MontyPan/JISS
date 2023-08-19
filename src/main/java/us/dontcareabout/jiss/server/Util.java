package us.dontcareabout.jiss.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.io.Files;

public class Util {
	public static void deleteFolder(File folder, ArrayList<File> error) {
		if (!folder.exists()) { return; }

		for (File f : folder.listFiles()) {
			if (f.isFile()) { delete(f, error); }
			else { deleteFolder(f, error); }
		}

		delete(folder, error);
	}

	public static void delete(File file, ArrayList<File> error) {
		if (!file.delete()) {
			error.add(file);
		}
	}


	public static void copyFolder(File from, File to, ArrayList<File> error) {
		if (!to.exists()) { to.mkdirs(); }

		for (File f : from.listFiles()) {
			File t = new File(to, f.getName());

			if (f.isFile()) {
				try {
					Files.copy(f, t);
				} catch (IOException e) {
					error.add(f);
				}
			} else {
				copyFolder(f, t, error);
			}
		}
	}
}
