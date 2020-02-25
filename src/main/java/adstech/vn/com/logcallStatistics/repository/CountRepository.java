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

import adstech.vn.com.logcallStatistics.model.Count;

@Repository
public class CountRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public int create(Count count) {
		String sql = "INSERT INTO tbl_count(`call`, meeting, task, score, deal_won,mail_sale,`name`, email,date_request, created_at) VALUES(:call,"
				+ " :meeting, :task, :score,:dealWon,:mailSale,:name, :email,:dateRequest, CURRENT_TIMESTAMP )";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(count);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, source,keyHolder);
		return keyHolder.getKey().intValue();
	}

	public Count findByEmailAndDate(String email, String date) {
		String sql = "SELECT * FROM tbl_count WHERE mail_sale =:email AND date_request =:dateRequest;";
		Map<String, Object> maps = new HashMap<>();
		maps.put("email", email);
		maps.put("dateRequest", date);
		List<Count> listCount = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Count>(Count.class));
		if(listCount != null && !listCount.isEmpty())
		return listCount.get(0);
		else
			
			return null;
	}
	public List<Count> getCountByDate(String date){
		String sql = "SELECT * FROM tbl_count WHERE date_request =:dateRequest;";
		Map<String , Object> maps = new HashMap<String , Object>();
		maps.put("dateRequest", date);
		return  jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<Count>(Count.class));
	}
	public int update(Count count) {
		String sql = "UPDATE tbl_count SET `call` =:call, meeting =:meeting, task =:task, score =:score, deal_won =:dealWon"
				+ ",mail_sale =:mailSale,`name` =:name, email =:email,date_request =:dateRequest, updated_at=CURRENT_TIMESTAMP  WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(count);
		return jdbcTemplate.update(sql, source);
	}

}
