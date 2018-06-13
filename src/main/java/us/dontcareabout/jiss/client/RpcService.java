package us.dontcareabout.jiss.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import us.dontcareabout.jiss.shared.Project;

@RemoteServiceRelativePath("RPC")
public interface RpcService extends RemoteService{
	void convertNewline(String path) throws Exception;

	ArrayList<Project> getProjects();
	void build(Project p) throws Exception;
}
