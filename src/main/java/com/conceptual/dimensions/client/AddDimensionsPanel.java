package com.conceptual.dimensions.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class AddDimensionsPanel extends Composite {

	private static AddGoalPanelUiBinder uiBinder = GWT
			.create(AddGoalPanelUiBinder.class);
	@UiField
	VerticalPanel dimensionsListPanel;
	@UiField
	Button addDimensionButton;

	interface AddGoalPanelUiBinder extends UiBinder<Widget, AddDimensionsPanel> {
	}

	private final List<SingleDimensionPanel> dimensionPanels = new ArrayList<SingleDimensionPanel>();

	public AddDimensionsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		createNewDimension("Meditation");
		createNewDimension("French");
		createNewDimension("Math Exam");
	}

	@UiHandler("addDimensionButton")
	void onAddDimensionButtonClick(ClickEvent event) {
		createNewDimension();
	}

	private void createNewDimension() {
		createNewDimension(null);
	}

	void createNewDimension(String dimension) {
		final SingleDimensionPanel dimensionPanel = new SingleDimensionPanel();
		if (dimension != null) {
			dimensionPanel.setText(dimension);
		}
		dimensionPanels.add(dimensionPanel);
		dimensionsListPanel.add(dimensionPanel);
		dimensionPanel.addRemoveClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dimensionPanels.remove(dimensionPanel);
				dimensionsListPanel.remove(dimensionPanel);
			}
		});
	}
	
	public List<String> getDimensions() {
		List<String> dimensions = new ArrayList<String>();
		for (SingleDimensionPanel p : dimensionPanels){
			dimensions.add(p.getText());
		}
		return dimensions;
	}

}
