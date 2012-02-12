package com.conceptual.dimensions.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HTML;

public class DimensionsMain extends Composite {

	private final DimensionsServiceAsync service = GWT
			.create(DimensionsService.class);
	
	private static DimensionsMainUiBinder uiBinder = GWT
			.create(DimensionsMainUiBinder.class);
	
	@UiField
	DateBox startDateBox;
	@UiField
	DateBox endDateBox;
	@UiField Button goButton;
	@UiField HTML myDimensionsHtml;

	interface DimensionsMainUiBinder extends UiBinder<Widget, DimensionsMain> {
	}

	public DimensionsMain() {
		initWidget(uiBinder.createAndBindUi(this));
		startDateBox.setFormat(new DateFormatMedium());
		endDateBox.setFormat(new DateFormatMedium());
	}

	public DimensionsMain(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("goButton")
	void onGoButtonClick(ClickEvent event) {
		service.getMyDimensions(new AsyncCallback<String>() {			
			@Override
			public void onSuccess(String result) {
				System.out.println("success="+result);
				myDimensionsHtml.setHTML(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
