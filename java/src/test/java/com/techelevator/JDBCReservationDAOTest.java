package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import JDBC.JDBCReservationDAO;

public class JDBCReservationDAOTest extends DAOIntegrationTest {
	
	@Test
	public void testMakeReservation() {
		ReservationDAO reservation1 = new JDBCReservationDAO(super.getDataSource());
		reservation1.makeReservation(1, "Habibi", LocalDate.of(2020, 8, 19), LocalDate.of(2020, 9, 19));
		JdbcTemplate jd = new JdbcTemplate(super.getDataSource());
		String sqlString = "SELECT from_date FROM reservation WHERE name = 'Habibi'";
		//SqlRowSet results = jd.queryForRowSet(sqlString);
		List <String> data = jd.queryForList(sqlString, String.class);
		String data1 = data.toString();
		Reservation reservation = new Reservation();
		reservation.setFromDate(LocalDate.of(2020, 8, 19));
	    assertEquals(reservation.getFromDate().toString(), data1.substring(1, 11));
		
	}
}

//JdbcTemplate jdbcTemplate = new JdbcTemplate(super.getDataSource());
//String sql = "SELECT from_date FROM reservation WHERE name = 'Habib Fam'";
//MapSqlParameterSource parameters = new MapSqlParameterSource();
//Set <String> name = new HashSet<String>();
//parameters.addValue("name", name);