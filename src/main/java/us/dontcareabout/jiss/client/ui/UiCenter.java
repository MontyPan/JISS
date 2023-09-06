package us.dontcareabout.jiss.client.ui;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.jiss.client.ui.event.ChangeAlbumEvent;
import us.dontcareabout.jiss.client.ui.event.ChangeAlbumEvent.ChangeAlbumHandler;

public class UiCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	public static void changeAlbum(String path) {
		eventBus.fireEvent(new ChangeAlbumEvent(path));
	}

	public static HandlerRegistration addChangeAlbum(ChangeAlbumHandler handler) {
		return eventBus.addHandler(ChangeAlbumEvent.TYPE, handler);
	}
}
