package adstech.vn.com.logcallStatistics.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import adstech.vn.com.logcallStatistics.model.Sale;

@Repository
public class SaleRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	public int create(Sale sale) {
		String sql = "INSERT INTO tbl_sale(may_nhanh, name, email_sale, owner_id) VALUES(:mayNhanh, :name, :emailSale, :ownerId)";
		Map<String, Object> maps  = new HashMap<>();
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(sale);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, source,keyHolder);
		return keyHolder.getKey().intValue();
	}
	public Sale getByMaynhanhAndEmail(String mayNhanh, String email) {
		String sql = "SELECT * FROM tbl_sale WHERE may_nhanh=:mayNhanh AND email_sale =:email;";
		Map<String, Object> maps = new HashMap<>();
		maps.put("mayNhanh", mayNhanh);
		maps.put("email_sale", email);
		List<Sale> listSale = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Sale>(Sale.class));
		if(listSale != null && !listSale.isEmpty()) {
			return listSale.get(0);
			
			
		}else {
			return null;
			
		}
	}
	public int update(Sale sale) {
		String sql ="UPDATE tbl_sale SET name =:name , email_sale =:emailSale , owner_id =:ownerId WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(sale);
		return jdbcTemplate.update(sql, source);
	}

	public String getMayNhanhByEmail(String email) {
		String sql = "SELECT may_nhanh FROM tbl_sale WHERE email_sale =:email;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("email", email);
		List<String> list = jdbcTemplate.queryForList(sql, maps, String.class);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public Sale getByOwnerId(String ownerId) {
		String sql = "SELECT * FROM tbl_sale WHERE owner_id = :ownerId;";
		Map<String, Object> maps = new HashMap<>();
		maps.put("ownerId", ownerId);
		List<Sale> list = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Sale>(Sale.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Integer> getListOwnerId() {
		String sql = "SELECT owner_id FROM tbl_sale;";
		return jdbcTemplate.queryForList(sql, new HashMap<>(), Integer.class);

	}

	public Sale getByEmail(String email) {
		String sql = "SELECT * FROM tbl_sale WHERE email_sale = :emailSale;";
		Map<String, Object> maps = new HashMap<>();
		maps.put("emailSale", email);
		List<Sale> listSale = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Sale>(Sale.class));
		if (listSale != null && !listSale.isEmpty()) {
			return listSale.get(0);
		} else {
			return null;
		}
	}

	public String getNameByEmail(String email) {
		String sql = "SELECT `name` FROM tbl_sale WHERE email_sale =:email";
		Map<String, Object> maps = new HashMap<>();
		maps.put("email", email);
		List<String> name = jdbcTemplate.queryForList(sql, maps, String.class);
		if (name != null && !name.isEmpty()) {
			return name.get(0);
		} else {
			return null;
		}
	}

	public int deleteByMaynhanh(String mayNhanh) {
		String sql = "DELETE FROM tbl_sale WHERE may_nhanh =:mayNhanh;";
		Map<String, Object> maps = new HashMap<>();
		maps.put("mayNhanh", mayNhanh);
		return jdbcTemplate.update(sql, maps);
	}
	
	public Sale getByMayNhanh(String mayNhanh) {
		String sql = "SELECT * FROM tbl_sale WHERE may_nhanh =:mayNhanh;";
		Map<String, Object> maps = new HashMap<>();
		maps.put("mayNhanh", mayNhanh);
		List<Sale> listSale = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Sale>(Sale.class));
		if(listSale != null && !listSale.isEmpty()) {
			return listSale.get(0);
		}
		else
			return null;
	}
	
	public List<Sale> getAll() {
		String sql = "SELECT * FROM tbl_sale;";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Sale>(Sale.class));
	}
}
