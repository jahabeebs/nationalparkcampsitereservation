package JDBC;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Reservation;

public class JDBCReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public long makeReservation(long site_id, String name, LocalDate startDate, LocalDate endDate) {
		
		String sqlAddReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date) VALUES (?,?,?,?,?)";
		return site_id;
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation reservation;
		reservation = new Reservation();
		reservation.setResId(results.getLong("reservation_id"));
		reservation.setSiteId(results.getLong("site_id"));
		reservation.setName(results.getString("name"));
		reservation.setFromDate(results.getDate("from_date").toLocalDate());
		reservation.setToDate(results.getDate("to_date").toLocalDate());
		reservation.setMakeDate(results.getDate("create_date").toLocalDate());
		return reservation;
	}
		
	
	

}
