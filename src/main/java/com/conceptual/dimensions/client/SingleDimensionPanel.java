package com.conceptual.dimensions.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SingleDimensionPanel extends Composite {

	private static SingleGoalPanelUiBinder uiBinder = GWT
			.create(SingleGoalPanelUiBinder.class);
	@UiField Button removeButton;
	@UiField TextBox goalTextBox;

	interface SingleGoalPanelUiBinder extends UiBinder<Widget, SingleDimensionPanel> {
	}

	public SingleDimensionPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public String getText() {
		return goalTextBox.getValue();
	}
	
	public void setText(String dimensionName) {
		goalTextBox.setValue(dimensionName);
	}

	public void addRemoveClickHandler(ClickHandler clickHandler) {
		removeButton.addClickHandler(clickHandler);
	}

}
