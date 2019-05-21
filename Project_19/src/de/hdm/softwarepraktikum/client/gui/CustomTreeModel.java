package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

public class CustomTreeModel implements TreeViewModel{

		    /**
		     * Get the {@link NodeInfo} that provides the children of the specified
		     * value.
		     */
		    public <T> NodeInfo<?> getNodeInfo(T value) {
		      /*
		       * Create some data in a data provider. Use the parent value as a prefix
		       * for the next level.
		       */
		      ListDataProvider<String> dataProvider = new ListDataProvider<String>();
		      for (int i = 0; i < 2; i++) {
		        dataProvider.getList().add(value + "." + String.valueOf(i));
		      }

		      // Return a node info that pairs the data with a cell.
		      return new DefaultNodeInfo<String>(dataProvider, new TextCell());
		    }

		    /**
		     * Check if the specified value represents a leaf node. Leaf nodes cannot be
		     * opened.
		     */
		    public boolean isLeaf(Object value) {
		      // The maximum length of a value is ten characters.
		      return value.toString().length() > 10;
		    }
		  }
