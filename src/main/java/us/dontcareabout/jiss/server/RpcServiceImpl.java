package us.dontcareabout.jiss.server;

import us.dontcareabout.gwt.server.GFServiceServlet;
import us.dontcareabout.jiss.client.RpcService;

public class RpcServiceImpl extends GFServiceServlet implements RpcService {
	private static final long serialVersionUID = 1L;

	@Override
	public void convertNewline(String path) throws Exception {
		Converter.newline(path);
	}
}
