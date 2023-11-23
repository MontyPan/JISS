package us.dontcareabout.jiss.server.album;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.dontcareabout.jiss.shared.AlbumFolder;
import us.dontcareabout.jiss.shared.FolderTree;

@RestController
@RequestMapping("album")
public class AlbumAPI {

	@PostMapping("/tree")
	public FolderTree tree(@RequestBody String rootPath) {
		return build(new File(rootPath));
	}

	@PostMapping("/folder")
	public AlbumFolder folder(@RequestBody String path) {
		File root = new File(path);
		ArrayList<String> list = new ArrayList<>();

		AlbumFolder result = new AlbumFolder();
		result.setPath(path);
		result.setFiles(list);

		for (File f : root.listFiles()) {
			if (f.isDirectory()) { continue; }

			String name = f.getName().toLowerCase();

			if (!(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"))) { continue; }

			list.add(f.getName());
		}

		return result;
	}

	@PostMapping("/img")
	public String img(@RequestBody String path) {
		try {
			return "\"" //要用 " 包成標準 JSON 字串，不然 deserialize 的時候遇到冒號會炸
				+ "data:image/jpg;base64," //MIME type 無所謂... 反正都能正常顯示 [毆飛]
				//直接把檔案丟出去就好了，不要再沒事繞 BufferedImage 一圈... [遮臉]
				+ Base64Utils.encodeToString(Files.readAllBytes(new File(path).toPath()))
				+ "\"";
		} catch(IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	///////////////////////////////////////////////////////////////////

	private FolderTree build(File root) {
		FolderTree result = new FolderTree();
		result.setRoot(root.getAbsolutePath());

		for (File f : root.listFiles()) {
			if (f.isFile()) { continue; }
			result.add(build(f));
		}

		return result;
	}
}
