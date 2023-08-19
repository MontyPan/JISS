package us.dontcareabout.jiss.server.album;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.dontcareabout.jiss.shared.AlbumFolder;

//XXX 只處理 JPG

@RestController
@RequestMapping("album")
public class AlbumAPI {

	@PostMapping("/folder")
	public AlbumFolder folder(@RequestBody String path) {
		File root = new File(path);
		ArrayList<String> list = new ArrayList<>();

		AlbumFolder result = new AlbumFolder();
		result.setPath(path);
		result.setFiles(list);

		for (File f : root.listFiles()) {
			if (f.isDirectory()) { continue; }
			if (!(f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg"))) { continue; }

			list.add(f.getName());
		}

		return result;
	}

	@PostMapping("/img")
	public String img(@RequestBody String path) {
		try {
			//要用 " 包成標準 JSON 字串，不然 deserialize 的時候遇到冒號會炸
			return "\"" + toDataUri(ImageIO.read(new File(path)), "jpg") + "\"";
		} catch(IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String toDataUri(BufferedImage image, String type) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, baos);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return "data:image/" + type + ";base64," + Base64Utils.encodeToString(baos.toByteArray());
	}
}
