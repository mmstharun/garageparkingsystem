package com.gps.src;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GarageUser {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
	
		GarageManager gm = new GarageManager();
		Scanner sc = new Scanner(System.in);
		
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date dateobj = new Date();
		System.out.println(dateobj);
		System.out.println(df.format(dateobj));
		
		System.out.println("Menu \n 1. Enter Garage \n 2. Leave Garage \n");
		switch (sc.nextInt()) {
		case 1:
			Integer ticketNumber = gm.generateTicket(df.format(dateobj));
			System.out.println(ticketNumber);
			break;
		case 2:
			System.out.println("Enter ticket number : ");
			int tktNo = sc.nextInt();
			String moneyToBePaid = gm.leaveGarage(tktNo, df.format(dateobj));
			System.out.println(moneyToBePaid);

		default: System.out.println("Wrong Choice!");
			break;
		}
		sc.close();
	}
}
