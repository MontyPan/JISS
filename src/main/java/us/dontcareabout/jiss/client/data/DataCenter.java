package us.dontcareabout.jiss.client.data;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.jiss.client.data.event.AlbumFolderReadyEvent;
import us.dontcareabout.jiss.client.data.event.AlbumFolderReadyEvent.AlbumFolderReadyHandler;
import us.dontcareabout.jiss.client.gf.PostRequest;
import us.dontcareabout.jiss.shared.AlbumFolder;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	public interface AlbumFolderMapper extends ObjectMapper<AlbumFolder> {};
	public static AlbumFolderMapper albumFolderMapper = GWT.create(AlbumFolderMapper.class);

	public static HandlerRegistration addAlbumFolderReady(AlbumFolderReadyHandler handler) {
		return eventBus.addHandler(AlbumFolderReadyEvent.TYPE, handler);
	}

	public static void wantAlbumFolder(String path) {
		PostRequest<AlbumFolder> req = new PostRequest<>();
		req.path("album/folder").data(path).reader(albumFolderMapper)
			.callback(data -> eventBus.fireEvent(new AlbumFolderReadyEvent(data)))
			.send();
	}
}
