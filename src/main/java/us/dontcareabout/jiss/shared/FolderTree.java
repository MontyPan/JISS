package us.dontcareabout.jiss.shared;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FolderTree {
	private String root;
	private List<FolderTree> children = new ArrayList<>();

	@JsonIgnore
	public String getName() {
		return root.substring(root.lastIndexOf('\\') + 1);
	}

	public void add(FolderTree child) {
		children.add(child);
	}

	// ==== getter / setter ==== //
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public List<FolderTree> getChildren() {
		return children;
	}
	public void setChildren(List<FolderTree> children) {
		this.children = children;
	}
}
