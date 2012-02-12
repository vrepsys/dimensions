package com.conceptual.dimensions.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;

public class DateFormatMedium implements Format {

	private final DateTimeFormat dateTimeFormat;

	public DateFormatMedium() {
		dateTimeFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
	}

	public DateFormatMedium(DateTimeFormat dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	public String format(DateBox box, Date date) {
		if (date == null) {
			return "";
		} else {
			return dateTimeFormat.format(date);
		}
	}

	public DateTimeFormat getDateTimeFormat() {
		return dateTimeFormat;
	}

	public Date parse(DateBox dateBox, String dateText, boolean reportError) {
		Date date = null;
		try {
			if (dateText.length() > 0) {
				date = dateTimeFormat.parse(dateText);
			}
		} catch (IllegalArgumentException exception) {
			return null;
		}
		return date;
	}

	public void reset(DateBox dateBox, boolean abandon) {
		
	}
}