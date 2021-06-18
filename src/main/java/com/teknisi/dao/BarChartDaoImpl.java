package com.teknisi.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.teknisi.model.BarChart;

@Repository
public class BarChartDaoImpl extends JdbcDaoSupport implements BarChartDao{
	
	 @Autowired 
	 DataSource dataSource;
	 
	 @PostConstruct
	 private void initialize(){
	    setDataSource(dataSource);
	 }

	 @Override
	 public List<BarChart> getAllRequestCount() {
		 String query = "select r.create_date as create_date ,count(r.create_date) as count, r.status as status  from (\r\n"
		 		+ "select  date_trunc('day', created_date) create_date, status from request  req\r\n"
		 		+ "where (req.created_date between now()::date-extract(dow from now())::integer-7    \r\n"
		 		+ "and now()::date-extract(dow from now())::integer) or  (req.update_date between now()::date-extract(dow from now())::integer-7  \r\n"
		 		+ "and now()::date-extract(dow from now())::integer) order by (case when req.update_date is null then req.created_date else req.update_date end) asc\r\n"
		 		+ ") r group by r.create_date, r.status order by create_date asc";
		 JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		 List<BarChart> chartList = new ArrayList<BarChart>();
		 List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);
		 for(Map<String,Object> column : rows){
			 BarChart chart = new BarChart();
			 chart.setCreated_date((Date)(column.get("create_date")));
			 chart.setCount(Integer.parseInt(column.get("count").toString()));
			 chart.setStatus(String.valueOf(column.get("status")));
			 chartList.add(chart);
			 }
		 return chartList;
	 }
}
