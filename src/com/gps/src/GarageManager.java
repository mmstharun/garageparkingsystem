package com.gps.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GarageManager {
	
	int totalParkingCap = 20;
	int regParkingCap = 15;
	int handParkingCap = 5;
	int regSmallParkingCap = 5;
	int regMediumParkingCap = 5;
	int regLargeParkingCap = 5;
	int handSmallParkingCap = 2;
	int handMediumParkingCap = 2;
	int handLargeParkingCap = 1;
	
	Scanner sc = new Scanner(System.in);
	
	//String myDriver = "com.mysql.jdbc.Driver";
	String myURL = "jdbc:mysql://localhost:3306/gps";
	
	public Integer generateTicket(String inDateTime) throws ClassNotFoundException, SQLException {
		
		//Class.forName(myDriver);
		Connection con = DriverManager.getConnection(myURL, "root", "1996");
		
		System.out.println("Choose a Garage Type \n 1. Regular \n 2. Handicapped \n");
		int garageType = sc.nextInt();
		System.out.println("Choose a Garage Size \n 1. Small \n 2. Medium \n 3. Large");
		int garageSize = sc.nextInt();
		int cap = getSize(garageType, garageSize);
		
		
		Integer ticketNo = insertIntoAdminTb(inDateTime); 
		
		if(garageType == 1 && garageSize == 1) {
			if(cap<regSmallParkingCap) {
				String query = "INSERT INTO PARKING_SPOTS(GARAGE_TYPE, GARAGE_SIZE, GARAGE_CAP) VALUES(?,?,?)";
				PreparedStatement prepStmt = con.prepareStatement(query);
				prepStmt.setString(1, "R");
				prepStmt.setString(2, "S");
				prepStmt.setBoolean(3, true);
				prepStmt.execute();
			}
		}
		else if(garageType == 1 && garageSize == 2) {
			if(cap<regMediumParkingCap) {
				String query = "INSERT INTO PARKING_SPOTS(GARAGE_TYPE, GARAGE_SIZE, GARAGE_CAP) VALUES(?,?,?)";
				PreparedStatement prepStmt = con.prepareStatement(query);
				prepStmt.setString(1, "R");
				prepStmt.setString(2, "M");
				prepStmt.setBoolean(3, true);
				prepStmt.execute();
			}
		}
		else if(garageType == 1 && garageSize == 3) {
			if(cap<regLargeParkingCap) {
				String query = "INSERT INTO PARKING_SPOTS(GARAGE_TYPE, GARAGE_SIZE, GARAGE_CAP) VALUES(?,?,?)";
				PreparedStatement prepStmt = con.prepareStatement(query);
				prepStmt.setString(1, "R");
				prepStmt.setString(2, "L");
				prepStmt.setBoolean(3, true);
				prepStmt.execute();
			}
		}
		
		else if(garageType == 2 && garageSize == 1) {
			if(cap<handSmallParkingCap) {
				String query = "INSERT INTO PARKING_SPOTS(GARAGE_TYPE, GARAGE_SIZE, GARAGE_CAP) VALUES(?,?,?)";
				PreparedStatement prepStmt = con.prepareStatement(query);
				prepStmt.setString(1, "H");
				prepStmt.setString(2, "S");
				prepStmt.setBoolean(3, true);
				prepStmt.execute();
			}
		}
		else if(garageType == 2 && garageSize == 2) {
			if(cap<handMediumParkingCap) {
				String query = "INSERT INTO PARKING_SPOTS(GARAGE_TYPE, GARAGE_SIZE, GARAGE_CAP) VALUES(?,?,?)";
				PreparedStatement prepStmt = con.prepareStatement(query);
				prepStmt.setString(1, "H");
				prepStmt.setString(2, "M");
				prepStmt.setBoolean(3, true);
				prepStmt.execute();
			}
		}
		else if(garageType == 2 && garageSize == 3) {
			if(cap<handLargeParkingCap) {
				String query = "INSERT INTO PARKING_SPOTS(GARAGE_TYPE, GARAGE_SIZE, GARAGE_CAP) VALUES(?,?,?)";
				PreparedStatement prepStmt = con.prepareStatement(query);
				prepStmt.setString(1, "H");
				prepStmt.setString(2, "L");
				prepStmt.setBoolean(3, true);
				prepStmt.execute();
			}
		}

		return ticketNo;
	}

	private Integer insertIntoAdminTb(String inDateTime) throws ClassNotFoundException, SQLException {
		Integer ticketNo = 0;
	//	Class.forName(myDriver);
		Connection con = DriverManager.getConnection(myURL, "root", "1996");
		
		String query = "INSERT INTO ADMIN_TB1(in_time) VALUES("+"'"+inDateTime+"'"+")";
	
		System.out.println(query);
		PreparedStatement prepStmt = con.prepareStatement(query);
		//prepStmt.setString(1, inDateTime);
		
		
		prepStmt.execute();
		
		String ouQuery = "SELECT ticket_no FROM ADMIN_TB1 WHERE IN_TIME="+inDateTime;
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(ouQuery);
		
		while (rs.next()) {
			ticketNo = rs.getInt("TICKET_NO");
		}

		return ticketNo;
	}

	private int getSize(int garageType, int garageSize) throws ClassNotFoundException, SQLException {

		int cap = 0;
		String stGarageType = null;
		String stGarageSize = null;
		switch(garageType) {
		case 1: stGarageType = "R";
			break;
		case 2: stGarageType = "H";
			break;
		default: System.out.println("WrongChoice");
			break;
		}
		
		switch(garageSize) {
		case 1: stGarageSize = "S";
			break;
		case 2: stGarageSize = "M";
			break;
		case 3: stGarageSize = "L";
				break;
		default: System.out.println("WrongChoice");
			break;
		}
	//	Class.forName(myDriver);
		Connection con = DriverManager.getConnection(myURL, "root", "1996");
		
		String query = "SELECT COUNT(GARAGE_ID) FROM PARKING_SPOTS WHERE GARAGE_TYPE = ? AND GARAGE_SIZE =?";
		
		System.out.println(query);
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, stGarageType);
		stmt.setString(2, stGarageSize);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			cap = rs.getInt("COUNT(GARAGE_ID)");
		}
		
		System.out.println(cap);
		return cap;
	}

	public String leaveGarage(int tktNo, String outDate) throws SQLException, ParseException {
		Integer money = 0;
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date dateobj = new Date();
		
		String inTime = getInTime(tktNo);
		String outTime = outDate;
		Date d1 = df.parse(inTime);
		Date d2 = df.parse(outTime);
		
		Integer diff = d2.compareTo(d1);
		if(diff == 2) {
			money = 50;
		}
		if(diff == 4) {
			money = 100;
		}
		if(diff >= 8) {
			money = 500;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String day = sdf.format(dateobj);
		
		if((day.equals("Monday"))) {
			money = money - ((money*20)/100);
		}
		if((day.equals("Saturday"))||(day.equals("Sunday"))) {
			money = money + ((money*20)/100);
		}
		
		return money.toString();
	}

	private String getInTime(int tktNo) throws SQLException {

		Connection con = DriverManager.getConnection(myURL, "root", "1996");
		
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		String inDate = null;
		String ouQuery = "SELECT in_time FROM ADMIN_TB1 WHERE ticket_no=?";
		
		PreparedStatement stmt = con.prepareStatement(ouQuery);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			inDate = rs.getString("in_date");
		}

		return df.format(inDate);
	}

	public Integer generateReports() throws SQLException {
		Integer totRevenue = 0;
		String query = "SELECT SUM(money_col) AS overallRevenue FROM admin_tb2;";
		Connection con = DriverManager.getConnection(myURL, "root", "1996");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			totRevenue = rs.getInt("overallRevenue");
		}
		return totRevenue;
	}
}