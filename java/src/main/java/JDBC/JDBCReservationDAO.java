package JDBC;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
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
}
