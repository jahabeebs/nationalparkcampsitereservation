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

	public void makeReservation(long site_id, String name, LocalDate startDate, LocalDate endDate) {
		LocalDate createDate = LocalDate.now();
		String sqlAddReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(sqlAddReservation, site_id, name, startDate, endDate, createDate);
		
	}
		
		public Reservation getReservationbyID(long reservationid) {
			Reservation reservationA = new Reservation();

			String sqlGetReservationById = "SELECT * FROM reservation WHERE reservation_id = ?";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationById, reservationid);

			while (results.next()) {
			reservationA = mapRowToReservation(results);
			}
			return reservationA;
			}

		private Reservation mapRowToReservation(SqlRowSet results) {
			Reservation theReservation;
			theReservation = new Reservation();
			theReservation.setResId(results.getLong("reservation_id"));
			theReservation.setSiteId(results.getLong("site_id"));
			theReservation.setName(results.getString("name"));
			theReservation.setFromDate(results.getDate("from_date").toLocalDate());
			theReservation.setToDate(results.getDate("to_date").toLocalDate());
			theReservation.setMakeDate(results.getDate("create_date").toLocalDate());
			return theReservation;
		}
		
		
		
		
		
	
}
