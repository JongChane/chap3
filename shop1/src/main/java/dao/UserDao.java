package dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.User;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<User> mapper =
			new BeanPropertyRowMapper<User>(User.class);
	private Map<String,Object> param = new HashMap<>();
	@Autowired //spring-db.xml에서 설정된 dataSource 객체 주입
	public void setDataSource(DataSource dataSource) {
		//dataSource : db에 연결된 객체.
		//NamedParameterJdbcTemplate : spring 프레임워크 jdbc 템플릿
		template = new NamedParameterJdbcTemplate(dataSource);
	}
	public void insert(User user) {
		//param : user 객체의 프로퍼티를 이용하여 db에 값 등록
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into useraccount (userid, username, password, birthday,"
				+ " phoneno, postcode, address, email) values (:userid, :username, :password, "
				+ " :birthday, :phoneno, :postcode, :address, :email)";
		template.update(sql, param);
	}
	public User selectOne(String userid) {
		param.clear();
		param.put("userid", userid);
		return template.queryForObject //select 문의 경우
			("select * from useraccount where userid=:userid", param, mapper);
	}
	public void updateUser(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "update useraccount set username=:username,"
				+ " birthday=:birthday, phoneno=:phoneno, postcode=:postcode,"
				+ " address=:address, email=:email where userid=:userid";
		template.update(sql, param);
		
	}
	public void deleteUser(String userid) {
		param.clear();
		param.put("userid", userid);
		String sql = "delete from useraccount where userid=:userid";
		template.update(sql, param);
	}
	public void updatePassword(String userid, String chgpass) {
		param.clear();
		param.put("userid", userid);
		param.put("password", chgpass);
		String sql = "update useraccount set password=:password where userid=:userid";
		template.update(sql, param);
		
	}

}
