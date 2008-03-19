package com.infinity.datamarket.comum.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarSample {
	private Date date;

	private Date dateSecond;

	
	private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public CalendarSample() {
	}

	public Date getDateSecond() {
		return dateSecond;
	}

	public void setDateSecond(Date dateSecond) {
		this.dateSecond = dateSecond;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		if (date != null) {
			this.date = date;
			System.out.println("Data informada: " + formatDate.format(date));
		}
	}

	public String processAction() {
		if (this.dateSecond != null) {
			System.out.println("Segunda data informada: "
					+ formatDate.format(dateSecond));
		}
		return null;
	}
}