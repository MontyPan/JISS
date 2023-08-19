package us.dontcareabout.jiss.shared;

import java.util.List;

public class AlbumFolder {
	private String path;
	private  List<String> file;

	public String getPath() {
		return path;
	}
	public void setPath(String root) {
		this.path = root;
	}
	public List<String> getFiles() {
		return file;
	}
	public void setFiles(List<String> file) {
		this.file = file;
	}
}
