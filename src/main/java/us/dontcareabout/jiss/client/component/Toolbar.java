package us.dontcareabout.jiss.client.component;

import java.util.ArrayList;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

public class Toolbar extends LayerContainer {
	private ArrayList<LayerSprite> items = new ArrayList<>();
	private int margin = 5;

	@Override
	protected void onResize(int width, int height) {
		//Refactory 抽去 GF：HorizontalLayer
		double wUnit = width / items.size() - margin * 2;
		int index = 0;

		for (LayerSprite btn : items) {
			btn.resize(wUnit, height);
			btn.setLX(margin + index * (wUnit + margin * 2));
			index++;
		}

		super.onResize(width, height);
	}

	public void add(ToolItem item) {
		addLayer(item);
		items.add(item);
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public static class ToolItem extends TextButton {
		public ToolItem(String str) {
			super(str);
			setBgColor(RGB.LIGHTGRAY);
			setBgRadius(20);
			setMargin(0);
		}
	}
}
