package us.dontcareabout.jiss.server.misc;

import us.dontcareabout.jiss.server.git.OverwirteCommitter;

public class ImgDeployer {
	public static void deploy(String repoPath, String user, String password) throws Exception {
		try {
			new OverwirteCommitter().repoPaht(repoPath)
				.credential(user, password).commit("Auto Commit.");
		} catch (Exception e) {
			throw e;
		}
	}
}
