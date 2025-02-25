package JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Park;
import com.techelevator.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		ArrayList<Park> ParkList = new ArrayList<>();
		String sqlPark = "SELECT * FROM park ORDER BY name";
		SqlRowSet parkResults = jdbcTemplate.queryForRowSet(sqlPark);
		while (parkResults.next()) {
			Park park = mapRowToPark(parkResults);
			ParkList.add(park);
		}
		return ParkList;
	}

	@Override
	public int getParkId(String parkName) {

		String sql = "SELECT park_id FROM park WHERE name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, parkName);
		int parkId = -1;

		if (result.next()) {
			parkId = result.getInt("park_id");
		}

		return parkId;
	}

	private Park mapRowToPark(SqlRowSet parkResults) {
		Park park = new Park();
		park.setParkId(parkResults.getLong("park_id"));
		park.setName(parkResults.getString("name"));
		park.setLocation(parkResults.getString("location"));
		park.setEstablishedDate(parkResults.getDate("establish_date").toLocalDate());
		park.setArea(parkResults.getInt("area"));
		park.setAnnualVisitorCount(parkResults.getLong("visitors"));
		park.setDescription(parkResults.getString("description"));
		return park;
	}

}
