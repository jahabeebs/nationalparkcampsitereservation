package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.SiteOfCamp;
import com.techelevator.SiteOfCampDAO;

public class JDBCSiteOfCampDAO implements SiteOfCampDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteOfCampDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<SiteOfCamp> getSitesByCampgroundId(int campgroundId) {
		List<SiteOfCamp> sitesList = new ArrayList<SiteOfCamp>();
		String sqlSitesbyId = "SELECT * FROM site JOIN campground on site.campground_id = campground.campground_id "
				+ "WHERE site.campground_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSitesbyId, campgroundId);
		while (results.next()) {
			SiteOfCamp siteList = mapRowToSite(results);
			sitesList.add(siteList);
		}
		// TODO Auto-generated method stub
		return sitesList;
	}
	
		
	
	public List<SiteOfCamp> getAvailableSitesByCampgroundId(int campgroundId, LocalDate fromDate, LocalDate toDate) {

		List<SiteOfCamp> availableSites = new ArrayList<SiteOfCamp>();
		String sqlAvailableSites = "SELECT * FROM site JOIN campground on site.campground_id = campground.campground_id "
				+ "WHERE site.campground_id = ? " + "and site_id NOT IN " + "(select site.site_id from site "
				+ "JOIN reservation ON reservation.site_id = site.site_id "
				+ "WHERE ? > reservation.from_date and ? < reservation.to_date) ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableSites, campgroundId, fromDate, toDate, toDate,
				fromDate);

		while (results.next()) {
			SiteOfCamp siteList = mapRowToSite(results);
			availableSites.add(siteList);
		}

		return availableSites;
	}

	private SiteOfCamp mapRowToSite(SqlRowSet results) {
		SiteOfCamp campSite = new SiteOfCamp();

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
