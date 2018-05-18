package us.dontcareabout.jiss.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RPC")
public interface RpcService extends RemoteService{
	void convertNewline(String path) throws Exception;
}