package us.dontcareabout.jiss.server.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class OverwirteCommitter {
	private Repository repo;
	private CredentialsProvider cp;

	public OverwirteCommitter repoPaht(String path) throws IOException  {
		repo = new FileRepository(new File(path, ".git"));
		return this;
	}

	public OverwirteCommitter credential(String user, String password) {
		cp = new UsernamePasswordCredentialsProvider(user, password);
		return this;
	}

	public void commit(String message) throws GitAPIException, CheckoutConflictException, IOException {
		ObjectId oid = repo.resolve("HEAD^");

		Git git = new Git(repo);
		git.reset().setRef(oid.getName()).call();
		git.add().addFilepattern(".").call();
		git.commit().setAll(true).setMessage(message).call();
		git.push().setCredentialsProvider(cp).setForce(true).call();
		git.close();
	}
}