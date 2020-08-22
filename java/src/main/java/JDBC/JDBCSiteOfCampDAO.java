package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.SiteOfCamp;
import com.techelevator.SiteOfCampDAO;

public class JDBCSiteOfCampDAO implements SiteOfCampDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteOfCampDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public List<SiteOfCamp> getSitesByCampgroundId(int campgroundId) {
//		List<SiteOfCamp> sitesList = new ArrayList<SiteOfCamp>();
//		String sqlSitesbyId = "SELECT * FROM site JOIN campground on site.campground_id = campground.campground_id"
//				+ "WHERE site.campground_id = ? ";
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSitesbyId, campgroundId);
//		while (results.next()) {
//			SiteOfCamp siteList = mapRowToSite(results);
//			sitesList.add(siteList);
//		}
//		// TODO Auto-generated method stub
//		return sitesList;
//	}
	
		
	@Override
	public List<SiteOfCamp> getAvailableSitesByCampgroundId(int campgroundId, LocalDate fromDate, LocalDate toDate) {
		
		String arrival = "";
		String departure = "";
		
		int arrivalYear = Integer.parseInt(arrival.substring(0,4));
		int arrivalMon = Integer.parseInt(arrival.substring(5,7));
		int arrivalDay = Integer.parseInt(arrival.substring(8));
	
		int depYear = Integer.parseInt(departure.substring(0,4));
		int depMon = Integer.parseInt(departure.substring(5,7));
		int depDay = Integer.parseInt(departure.substring(8));
		
		
		
		Set <LocalDate> dates = new HashSet<LocalDate>();
		dates.add(LocalDate.of(arrivalYear, arrivalMon, arrivalDay));
		dates.add(LocalDate.of(depYear, depMon, depDay));
		
	    Set <Long> anId =  new HashSet<Long>();
	    anId.add(1L);
	    
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("dates", dates);
	    parameters.addValue("id", anId);
	            
	    String sqlSelect = "SELECT * FROM site WHERE campground_id = :id AND site_id "
	            + "NOT IN (SELECT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS ( :dates ) )";

		List<SiteOfCamp> availableSites = new ArrayList<SiteOfCamp>();
		//dates for postgresql must be formatted 'YEAR-MONTH-DAY' like '2020-09-29'
		String sqlAvailableSites = "SELECT * FROM site JOIN campground on site.campground_id = campground.campground_id "
				+ "WHERE site.campground_id = ? " + "and site_id NOT IN " + "(SELECT site.site_id FROM site "
				+ "JOIN reservation ON reservation.site_id = site.site_id "
				+ "WHERE ? > reservation.from_date and ? < reservation.to_date) ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableSites, parameters);

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
