package us.dontcareabout.jiss.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class Util {
	private static final Gson gson = new Gson();

	public static <T> T parseJson(File file, Class<T> classT) {
		try {
			InputStreamReader fr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			T result = gson.fromJson(fr, classT);
			fr.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
