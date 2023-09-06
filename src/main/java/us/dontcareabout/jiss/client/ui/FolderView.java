package us.dontcareabout.jiss.client.ui;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.tree.Tree;

import us.dontcareabout.jiss.client.data.DataCenter;
import us.dontcareabout.jiss.shared.FolderTree;

//用 ContentPanel 是因為會加在 BorderLayoutContainer，就順便 XD
public class FolderView extends ContentPanel {
	private TreeStore<FolderTree> store = new TreeStore<>(item -> item.getRoot());
	private Tree<FolderTree, String> tree;

	public FolderView() {
		tree = new Tree<>(store, new ValueProvider<FolderTree, String>() {
			@Override
			public String getValue(FolderTree object) {
				return object.getName();
			}

			@Override
			public void setValue(FolderTree object, String value) {}

			@Override
			public String getPath() { return null; }
		});
		store.addSortInfo(new StoreSortInfo<>(
			(e1, e2) -> {
				//希望有子目錄的排前面、但是名字按照順序
				//所以是 e2.getChildren() 擺前面
				int childrenCompare = Integer.compare(e2.getChildren().size(), e1.getChildren().size());
				return childrenCompare == 0 ? e1.getName().compareTo(e2.getName()) : childrenCompare;
			},
			SortDir.ASC)
		);
		tree.getSelectionModel().addSelectionHandler(e -> {
			UiCenter.changeAlbum(e.getSelectedItem().getRoot());
		});
		add(tree);

		DataCenter.addFolderTreeReady(e -> {
			store.add(e.data);
			build(e.data);
			tree.setExpanded(e.data, true);
		});
	}

	private void build(FolderTree root) {
		root.getChildren().forEach(node -> {
			store.add(root, node);
			build(node);
		});
	}
}
