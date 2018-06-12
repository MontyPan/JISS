package us.dontcareabout.jiss.client.ui;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

import us.dontcareabout.gxt.client.draw.LSprite;
import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.util.TextUtil;
import us.dontcareabout.jiss.client.component.Toolbar;
import us.dontcareabout.jiss.client.component.Toolbar.ToolItem;
import us.dontcareabout.jiss.client.data.DataCenter;
import us.dontcareabout.jiss.client.data.ProjectReadyEvent;
import us.dontcareabout.jiss.client.data.ProjectReadyEvent.ProjectReadyHandler;
import us.dontcareabout.jiss.client.gf.StoreLayerSprite;
import us.dontcareabout.jiss.shared.Project;

public class ProjectHome extends VerticalLayoutContainer {
	private Toolbar toolbar = new Toolbar();

	public ProjectHome() {
		buildToolbar();

		add(new ProjectList(), new VerticalLayoutData(1, 1));
		add(toolbar, new VerticalLayoutData(1, 40));
	}

	private void buildToolbar() {
		ToolItem btn = new ToolItem("新增");
		toolbar.add(btn);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		DataCenter.wantProject();
	}

	class ProjectLayer extends LayerSprite {
		private LTextSprite nameTS = new LTextSprite();
		private TextButton buildBtn = new TextButton("build");
		private TextButton genEventBtn = new TextButton("Gen Event");

		public ProjectLayer(String name) {
			setBgColor(RGB.GREEN);
			setBgRadius(10);

			nameTS.setText(name);
			nameTS.setFill(RGB.WHITE);
			add(nameTS);

			buildBtn.setBgColor(RGB.YELLOW);
			add(buildBtn);

			genEventBtn.setBgColor(RGB.YELLOW);
			add(genEventBtn);
		}

		@Override
		protected void adjustMember() {
			TextUtil.autoResize(nameTS, 200, getHeight() - 20);
			nameTS.setLX(5);	//WTF：不給 LX、LY 也會沒反應？
			nameTS.setLY((getHeight() - nameTS.getBBox().getHeight()) / 2.0 + TextUtil.getYOffset(nameTS));

			buildBtn.resize(80, getHeight() - 20);
			buildBtn.setLY(10);
			buildBtn.setLX(getWidth() - 90);

			genEventBtn.resize(120, getHeight() - 20);
			genEventBtn.setLY(10);
			genEventBtn.setLX(getWidth() - 90 - 130);
		}
	}

	class ProjectList extends StoreLayerSprite<Project> {
		ProjectList() {
			super(new ListStore<>(new ModelKeyProvider<Project>() {
				@Override
				public String getKey(Project item) {
					return item.getName();
				}
			}));
			DataCenter.addProjectReady(new ProjectReadyHandler() {
				@Override
				public void onProjectReady(ProjectReadyEvent event) {
					getStore().clear();
					getStore().addAll(DataCenter.getProjects());
				}
			});
		}

		@Override
		protected void render() {
			for (Project p : getStore().getAll()) {
				add(new ProjectLayer(p.getName()));
			}
		}

		@Override
		protected void adjustMember() {
			int y = 0;
			for (LSprite ls : getMembers()) {
				if (ls instanceof ProjectLayer) {
					ProjectLayer p = (ProjectLayer) ls;
					p.setLY(y);
					p.resize(getWidth(), 100);
					y += 105;
				}
			}
		}
	}
}
