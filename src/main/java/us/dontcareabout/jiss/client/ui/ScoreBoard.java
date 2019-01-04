package us.dontcareabout.jiss.client.ui;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;

import us.dontcareabout.jiss.client.component.ScorePanel;

public class ScoreBoard extends HorizontalLayoutContainer {
	private static final HorizontalLayoutData LD = new HorizontalLayoutData(0.5, 1, new Margins(4));
	public ScoreBoard() {
		add(new ScorePanel(RGB.RED, 50), LD);
		add(new ScorePanel(RGB.BLUE, 50), LD);
	}
}
