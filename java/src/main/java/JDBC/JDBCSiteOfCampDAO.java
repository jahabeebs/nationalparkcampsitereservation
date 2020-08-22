package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.SiteOfCamp;
import com.techelevator.SiteOfCampDAO;

public class JDBCSiteOfCampDAO implements SiteOfCampDAO {
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate jdbcSpecial;

	public JDBCSiteOfCampDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcSpecial = new NamedParameterJdbcTemplate(dataSource);
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
	
		
//	@Override
//	public void getAvailableSitesByCampgroundId(int campgroundId, LocalDate fromDate, LocalDate toDate) {
//		
//		String arrival = "";
//		String departure = "";
//		
//		int arrivalYear = Integer.parseInt(arrival.substring(0,4));
//		int arrivalMon = Integer.parseInt(arrival.substring(5,7));
//		int arrivalDay = Integer.parseInt(arrival.substring(8));
//	
//		int depYear = Integer.parseInt(departure.substring(0,4));
//		int depMon = Integer.parseInt(departure.substring(5,7));
//		int depDay = Integer.parseInt(departure.substring(8))
//	            
//		
//
//		String sqlAvailableSites =	"select site_number, max_occupancy, accessible, max_rv_length, utilities, daily_fee from site" +
//		"join campground on site.campground_id = campground.campground_id where site.campground_id = ? and site_id not in " + 
//	    "(select site_id from reservation where (?, ?) overlaps (from_date, to_date) group by site_id) limit 5";
//		return;
//	}
	
	@Override
	public List<SiteOfCamp> sitesByDate(LocalDate arrival, LocalDate departure, Long id) {
		
		List<SiteOfCamp> results = new ArrayList<SiteOfCamp>();
		
		Set <LocalDate> dates = new HashSet<LocalDate>();

		dates.add(arrival);
		dates.add(departure);
	  
		Set <Long> anId =  new HashSet<Long>();
	    anId.add(1L);
	    
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("dates", dates);
	    parameters.addValue("id", anId);
	    
	    String sql = "SELECT * FROM site WHERE campground_id = :id AND site_id "
	            + "NOT IN (SELECT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS ( :dates ) )";
	    
	    SqlRowSet rowset = jdbcSpecial.queryForRowSet(sql, parameters);
	    
		while (rowset.next()) {
			SiteOfCamp siteList = mapRowToSite(rowset);
			results.add(siteList);
		}
	    return results;
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

	@Override
	public List<SiteOfCamp> getAvailableSitesByCampgroundId(int campgroundId, LocalDate fromDate, LocalDate toDate) {
		// TODO Auto-generated method stub
		return null;
	}






}
