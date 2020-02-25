package adstech.vn.com.logcallStatistics.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.sym.Name;

import adstech.vn.com.logcallStatistics.model.Staff;

@Repository
public class StaffRepository {
		@Autowired
		NamedParameterJdbcTemplate jdbcTemplate;
		public int create(Staff staff) {
			String sql ="INSERT INTO tbl_staff(name, email, group_id, department_id) VALUES(:name, :email, :groupId, :departmentId);";
			BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(staff);
			return jdbcTemplate.update(sql, source);
					
		}
		public int update(Staff staff) {
			String sql = "UPDATE tbl_staff SET name =:name, email =:email, group_id =:groupId, department_id =:departmentId;";
			BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(staff);
			return jdbcTemplate.update(sql, source); 
		}
		
		public List<Staff> getAll(){
			String sql = "SELECT * FROM tbl_staff;";
			return jdbcTemplate.query(sql, new HashMap<>(), new BeanPropertyRowMapper<Staff>(Staff.class));		
		}
		
		public List<Staff> getByDepartmentId(int departmentId){
			String sql = "SELECT * FROM tbl_staff WHERE department_id =:departmentId;";
			Map<String , Object> maps = new HashMap<String,Object>();
			maps.put("departmentId", departmentId);
			return jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Staff>(Staff.class));
		}
		
		public List<Staff> getByIds(int departmentId, int groupId){
			String sql = "SELECT * FROM tbl_staff WHERE department_id =:departmentId AND group_id =:groupId;";
			Map<String , Object> maps = new HashMap<String,Object>();
			maps.put("departmentId", departmentId);
			maps.put("groupId", groupId);
			return jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Staff>(Staff.class));
		}
		
		public Staff getByName(String name, int groupId, int departmentId) {
			String sql = "SELECT * FROM tbl_staff WHERE name =:name AND group_id =:groupId AND department_id =:departmentId;";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("name", name);
			maps.put("groupId", groupId);
			maps.put("departmentId", departmentId);
			List<Staff> list = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Staff>(Staff.class));
			if(list != null && !list.isEmpty()) {
				return list.get(0);
			}
			else
				return null;
		}
		
		public Staff getById(int id) {
			String sql = "SELECT * FROM tbl_staff WHERE id =:id;";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("id", id);
			return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<Staff>(Staff.class));
		}
		public List<String> getNameByIds(int groupId, int departmentId){
			String sql = "SELECT name FROM tbl_staff WHERE group_id =:groupId AND department_id =:departmentId;";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("groupId", groupId);
			maps.put("departmentId", departmentId);
			return jdbcTemplate.query(sql, maps,new BeanPropertyRowMapper<String>(String.class));
			}
}
