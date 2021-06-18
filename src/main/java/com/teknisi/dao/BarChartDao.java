package com.teknisi.dao;

import java.util.List;

import com.teknisi.model.BarChart;

public interface BarChartDao {
	List<BarChart> getAllRequestCount();
}
