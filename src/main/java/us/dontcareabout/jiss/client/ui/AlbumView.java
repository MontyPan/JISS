package us.dontcareabout.jiss.client.ui;

import com.google.gwt.dom.client.NativeEvent;
import com.sencha.gxt.core.client.util.KeyNav;

import us.dontcareabout.gwt.client.util.ImageUtil;
import us.dontcareabout.gxt.client.draw.LImageSprite;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.jiss.client.data.DataCenter;
import us.dontcareabout.jiss.shared.AlbumFolder;

//Refactory 不能直接用 SimpleLayoutLayer 是值得吐嘈的事情
public class AlbumView extends LayerContainer {
	private LImageSprite image = new LImageSprite();
	private CenterLayoutLayer root = new CenterLayoutLayer(image, 1000, 1000);

	private AlbumFolder albumRoot;
	private int index;

	public AlbumView() {
		//Refactory 可以吃 input event 的招數，加成 method
		this.getElement().setAttribute("tabindex", "1");
		this.addLayer(root);

		DataCenter.addAlbumFolderReady(e -> {
			albumRoot = e.data;
			showImage(0);
			focus();
		});
		DataCenter.addDataUriImageReady(e -> {
			image.setResource(ImageUtil.toResource(e.data));
			adjustMember(getOffsetWidth(), getOffsetHeight());
			redrawSurface();
		});

		new KeyNav() {
			@Override
			public void onDown(NativeEvent evt) { next(); }

			@Override
			public void onRight(NativeEvent evt) { next(); }

			@Override
			public void onPageDown(NativeEvent evt) { next(); }

			@Override
			public void onUp(NativeEvent evt) { prev(); }

			@Override
			public void onLeft(NativeEvent evt) { prev(); }

			@Override
			public void onPageUp(NativeEvent evt) { prev(); }
		}.bind(this);;
	}

	public void refresh(String path) {
		DataCenter.wantAlbumFolder(path);
	}

	@Override
	protected void adjustMember(int width, int height) {
		root.resize(width, height);
	}

	private void showImage(int i) {
		index = i;
		DataCenter.wantDataUriImage(albumRoot.getPath() + "\\" + albumRoot.getFiles().get(i));
	}

	private void next() {
		if (index == albumRoot.getFiles().size() - 1) { return; }
		showImage(index + 1);
	}
	private void prev() {
		if (index <= 0) { return; }
		showImage(index - 1);
	}

	/**
	 * 只有一個固定大小的 sprite，如果本身 size 大於 sprite，會將其置中。
	 * <p>
	 * 由於 GXT「sprite remove 之後就無法再加回去」的特性，所以不提供切換 sprite 的功能。
	 */
	//TODO GF 增加一個 HasSize interface，然後把傳入型態改成 HasSize
	//XXX 可以考慮改成給 margin，然後變成相對泛用的 layout？
	//Refactory 需要從頭深思熟慮一下，所以先不放去 gf
	class CenterLayoutLayer extends LayerSprite {
		private final LImageSprite sprite;

		private double fixWidth;
		private double fixHeight;

		public CenterLayoutLayer(LImageSprite sprite, double fixWidth, double fixHeight) {
			this.sprite = sprite;
			this.fixWidth = fixWidth;
			this.fixHeight = fixHeight;
			add(sprite);
		}

		public double getFixWidth() {
			return fixWidth;
		}

		public void setFixWidth(double fixWidth) {
			this.fixWidth = fixWidth;
			adjustMember();
		}

		public double getFixHeight() {
			return fixHeight;
		}

		public void setFixHeight(double fixHeight) {
			this.fixHeight = fixHeight;
			adjustMember();
		}

		@Override
		protected void adjustMember() {
			sprite.setWidth(fixWidth);
			sprite.setHeight(fixHeight);
			sprite.setLX(getWidth() > fixWidth ? (getWidth() - fixWidth) / 2 : 0);
			sprite.setLY(getHeight() > fixHeight ? (getHeight() - fixHeight) / 2 : 0);
		}
	}
}
