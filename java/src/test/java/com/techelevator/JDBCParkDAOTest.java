package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import JDBC.JDBCParkDAO;

public class JDBCParkDAOTest extends DAOIntegrationTest {

	@Test
	public void testGetAllParks() {
		ParkDAO parkDAO = new JDBCParkDAO(super.getDataSource());
		assertEquals("Acadia", parkDAO.getAllParks().get(0).getName());
		assertEquals("Arches", parkDAO.getAllParks().get(1).getName());
		assertEquals("Cuyahoga Valley", parkDAO.getAllParks().get(2).getName());
	}
}
