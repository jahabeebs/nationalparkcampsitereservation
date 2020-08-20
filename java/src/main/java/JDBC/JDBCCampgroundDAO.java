package JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO  {
	
private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundByParkId(long id) {
		List<Campground> campgroundList = new ArrayList<Campground>();
		String sqlGetCampgroundsByParkId = "SELECT * FROM campground WHERE park_id = ?";
		Campground theCampground;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundsByParkId, id);
		while(results.next()) {
			theCampground = mapRowToCampground(results);
			campgroundList.add(theCampground);
		}
		return campgroundList;
	}
		
		private Campground mapRowToCampground(SqlRowSet results) {
			Campground campground;
			campground = new Campground();
			campground.setCampground_id(results.getLong("campground_id"));
			campground.setParkId(results.getLong("park_id"));
			campground.setName(results.getString("name"));
			campground.setOpenFromMM(results.getInt("open_from_mm"));
			campground.setOpenToMM(results.getInt("open_to_mm"));
			campground.setMoneyFee(results.getBigDecimal("daily_fee"));
			return campground;
		
	}

}
