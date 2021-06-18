package com.teknisi.model;

import java.util.Date;

public class BarChart {
	private Date created_date;
	private Integer count;
	private String status;
	
	public BarChart() {
		
	}

	public BarChart(Date created_date, Integer count, String status) {
		super();
		this.created_date = created_date;
		this.count = count;
		this.status = status;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BarChart [created_date=");
		builder.append(created_date);
		builder.append(", count=");
		builder.append(count);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
