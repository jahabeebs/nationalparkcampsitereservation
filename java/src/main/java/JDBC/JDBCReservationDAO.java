package JDBC;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Reservation;
import com.techelevator.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public long makeReservation(long site_id, String name, LocalDate startDate, LocalDate endDate) {
		String sqlAddReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date) VALUES (?,?,?,?,?)";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlAddReservation, site_id, name, startDate, endDate);
		result.next();
		Long addReservationID = result.getLong(1);
		return addReservationID;
	}
}
