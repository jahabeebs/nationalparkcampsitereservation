package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.CampSite;
import com.techelevator.CampSiteDAO;

public class JDBCCampsiteDAO implements CampSiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampsiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CampSite> getAvailableSitesByCampId(int campgroundId, LocalDate fromDate, LocalDate toDate) {

		List<CampSite> availableSites = new ArrayList<CampSite>();
		String sqlAvailableSites = "JOIN campground on site.campground_id = campground.campground_id "
				+ "WHERE site.campground_id = ? " + "and site_id NOT IN " + "(select site.site_id from site "
				+ "JOIN reservation ON reservation.site_id = site.site_id "
				+ "WHERE ? > reservation.from_date and ? < reservation.to_date) ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableSites, campgroundId, fromDate, toDate, toDate,
				fromDate);

		while (results.next()) {
			CampSite siteList = mapRowToSite(results);
			availableSites.add(siteList);
		}

		return availableSites;
	}

	private CampSite mapRowToSite(SqlRowSet results) {
		CampSite campSite = new CampSite();

		campSite.setSiteId(results.getLong("site_id"));
		campSite.setCampGroundId(results.getInt("campground_id"));
		campSite.setSiteNumber(results.getInt("site_number"));
		campSite.setMaxOccupancy(results.getInt("max_occupancy"));
		campSite.setAccessible(results.getBoolean("accessible"));
		campSite.setMaxRvLength(results.getInt("max_rv_length"));
		campSite.setHasUtilities(results.getBoolean("utilities"));

		return campSite;

	}

}
