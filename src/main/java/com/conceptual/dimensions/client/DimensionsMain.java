package com.conceptual.dimensions.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class DimensionsMain extends Composite {

	private final DimensionsServiceAsync service = GWT.create(DimensionsService.class);

	private static DimensionsMainUiBinder uiBinder = GWT.create(DimensionsMainUiBinder.class);

	@UiField
	DateBox startDateBox;
	@UiField
	DateBox endDateBox;
	@UiField
	Button goButton;
	@UiField
	HTML myDimensionsHtml;
	@UiField
	AddDimensionsPanel dimensionsPanel;

	interface DimensionsMainUiBinder extends UiBinder<Widget, DimensionsMain> {
	}

	public DimensionsMain() {
		initWidget(uiBinder.createAndBindUi(this));
		startDateBox.setFormat(new DateFormatMedium());
		endDateBox.setFormat(new DateFormatMedium());
		startDateBox.setValue(new Date());
		service.getDefaultEndDate(new AsyncCallback<Date>() {
			@Override
			public void onSuccess(Date result) {			
				endDateBox.setValue(result);
			}			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Woops.. error. " + caught.getMessage());
			}
		});
	}

	public DimensionsMain(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("goButton")
	void onGoButtonClick(ClickEvent event) {
		if (startDateBox.getValue() == null || endDateBox.getValue() == null) {
			Window.alert("Please enter valid dates. Thanks");
			return;
		}
		if (startDateBox.getValue().after(endDateBox.getValue())) {
			Window.alert("Sorry, there's no 'plan your past' capabilities yet. Please enter valid dates.");
			return;
		}
		service.getMyDimensions(startDateBox.getValue(), endDateBox.getValue(), dimensionsPanel.getDimensions(),
				new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {						
						myDimensionsHtml.setHTML(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Woops.. error. " + caught.getMessage());
					}
				});
	}
}
