package com.teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teknisi.dao.BarChartDao;
import com.teknisi.model.BarChart;

@Service
public class BarChartServiceImpl implements BarChartService{
	
	@Autowired BarChartDao barChartDao;
	
	public List<BarChart> showAllRequestCount(){
		return barChartDao.getAllRequestCount();
	}
}
