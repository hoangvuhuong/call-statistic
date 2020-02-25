package adstech.vn.com.logcallStatistics.repository;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.sym.Name;

import adstech.vn.com.logcallStatistics.model.Department;

@Repository
public class DepartmentRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(Department department) {
		String sql = "INSERT INTO tbl_departments(name) VALUES(:name);";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(department);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, source, keyHolder);
		return keyHolder.getKey().intValue();
	}
	public int update(Department department) {
		String sql = "UPDATE tbl_departments SET name =:name WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(department);
		return jdbcTemplate.update(sql, source);
		
	}
	 public Department getById(int id) {
		 String sql = "SELECT * FROM tbl_departments WHERE id =:id;";
		 Map<String, Object> maps = new HashMap<String, Object>();
		 maps.put("id", id);
		 return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<Department>(Department.class));
		 
		 
	 }
	 public List<Department> getAll(){
		 String sql = "SELECT * FROM tbl_departments;";
		 return jdbcTemplate.query(sql, new HashMap<>(), new BeanPropertyRowMapper<Department>(Department.class));
	 }
	 
	 public Department getByName(String departmentName) {
		 String sql = "SELECT * FROM tbl_departments WHERE name =:departmentName;";
		 Map<String, Object> maps = new HashMap<String , Object>();
		 maps.put("departmentName", departmentName);
		 List<Department> list = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Department>(Department.class));
		 if(list != null && !list.isEmpty()) {
			 return list.get(0);
		 }
		 else
			 return null;
	 }
	 
	 public List<String> getAllName(){
		 String sql = "SELECT name FROM tbl_departments;";
		 return jdbcTemplate.queryForList(sql, new HashMap<>(),String.class);
	 }
}
