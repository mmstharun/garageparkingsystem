package com.gps.src;

import java.sql.SQLException;

public class Admin {
	public static void main(String[] args) throws SQLException {
		GarageManager gm = new GarageManager();
		Integer totalRevenue = gm.generateReports();
		System.out.println(totalRevenue);
	}
}
