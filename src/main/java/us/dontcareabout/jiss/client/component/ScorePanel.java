package us.dontcareabout.jiss.client.component;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

import us.dontcareabout.gxt.client.draw.component.TextButton;

public class ScorePanel extends VerticalLayoutContainer {
	private static final HorizontalLayoutData HLD = new HorizontalLayoutData(0.5, 1, new Margins(4));

	private TextButton score = new TextButton();
	private TextButton plus = new TextButton("＋");
	private TextButton minus = new TextButton("－");

	private int value;

	public ScorePanel(Color color, int initScore) {
		value = initScore;
		score.setBgColor(color);
		score.setTextColor(RGB.WHITE);
		refresh();
		add(score, new VerticalLayoutData(1, 0.8));

		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
		hlc.add(plus, HLD);
		hlc.add(minus, HLD);
		add(hlc, new VerticalLayoutData(1, 0.2));

		plus.setBgRadius(10);
		plus.setBgColor(RGB.LIGHTGRAY);
		plus.addSpriteSelectionHandler(new SpriteSelectionHandler() {
			@Override
			public void onSpriteSelect(SpriteSelectionEvent event) {
				value++;
				refresh();
			}
		});

		minus.setBgRadius(10);
		minus.setBgColor(RGB.LIGHTGRAY);
		minus.addSpriteSelectionHandler(new SpriteSelectionHandler() {
			@Override
			public void onSpriteSelect(SpriteSelectionEvent event) {
				value--;
				refresh();
			}
		});
	}

	private void refresh() {
		score.setText(String.valueOf(value));
		score.redraw();
	}
}
