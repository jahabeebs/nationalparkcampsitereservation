package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.SiteOfCamp;
import com.techelevator.SiteOfCampDAO;

public class JDBCSiteOfCampDAO implements SiteOfCampDAO {
	
	private NamedParameterJdbcTemplate jdbcSpecial;

	public JDBCSiteOfCampDAO(DataSource dataSource) {
		this.jdbcSpecial = new NamedParameterJdbcTemplate(dataSource);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<SiteOfCamp> sitesByDate(LocalDate arrival, LocalDate departure, Long id) {
		
		List<SiteOfCamp> results = new ArrayList<SiteOfCamp>();
		
		Set <LocalDate> dates = new HashSet<LocalDate>();

		dates.add(arrival);
		dates.add(departure);
	  
		Set <Long> anId =  new HashSet<Long>();
	    anId.add(id);
	    
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("dates", dates);
	    parameters.addValue("id", anId);
	    
	    String sql = "SELECT * FROM site WHERE campground_id = :id AND site_id "
	            + "NOT IN (SELECT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS ( :dates ) ) ORDER BY max_occupancy LIMIT 5 ";
	    
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







}
