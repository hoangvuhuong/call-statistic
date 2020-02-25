package adstech.vn.com.logcallStatistics.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import adstech.vn.com.logcallStatistics.model.Group;

@Repository
public class GroupRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public Group getByName(String name, int departmentId) {
		String sql = "SELECT * FROM tbl_group WHERE name =:name AND department_id =:departmentId";
		Map<String , Object> maps = new HashMap<String, Object>();
		maps.put("name", name);
		maps.put("departmentId", departmentId);
		List<Group> list = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Group>(Group.class));
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		else
			return null;
	}
	
	public List<Group> getByDepartmentId(int departmentId) {
		String sql = "SELECT * FROM tbl_group WHERE department_id =:departmentId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("departmentId", departmentId);
		return jdbcTemplate.query(sql, maps,new BeanPropertyRowMapper<Group>(Group.class));
	}
}
