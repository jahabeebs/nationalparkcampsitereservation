package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.CampSite;
import com.techelevator.CampsiteDAO.CampSiteDAO;

public class JDBCCampsiteDAO implements CampSiteDAO {

	@Override
	public List<CampSite> getAvailableSitesByCampId(int campgroundId, LocalDate fromDate, LocalDate toDate) {
		
		List<CampSite> availableSites = new ArrayList<CampSite>();
		// String sqlAvailableSites = dont know yet
		
	//	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableSites, campgroundId, fromDate, toDate, toDate, fromDate);
		
		/*
		 * while (results.next()) { CampSite siteList = mapRowToSite(results);
		 * availableSites.add(siteList); }     lecture model
		 */

		return null;
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
