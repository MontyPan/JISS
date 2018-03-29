package us.dontcareabout.jiss.shared;

import java.io.Serializable;

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String path;
	private String rootPackage;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRootPackage() {
		return rootPackage;
	}
	public void setRootPackage(String rootPackage) {
		this.rootPackage = rootPackage;
	}
}
