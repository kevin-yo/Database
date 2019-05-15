import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PomonaTransitSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try 
		{
			Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Kevin\\Documents\\database\\PomonaTransitSystem.mdb");
			Statement stmt = conn.createStatement();
			
//			createTables(stmt); // only run this on first run to create tables, comment out after
//			fillTables(stmt);
			run(stmt);
			
			stmt.close();
			conn.close();
		}
		catch(Exception err)
		{
			System.out.println(err);
		}
		
		
	}
	
	public static void run(Statement stmt) throws SQLException {
		String options = "[1] Display Trip Schedule\n"
				+ "[2] Delete Trip \n"
				+ "[3] Add Trip \n"
				+ "[4] Change driver of a trip \n"
				+ "[5] Change bus of a trip \n"
				+ "[6] Display stops of a trip \n"
				+ "[7] Display weekly schedule of a driver \n"
				+ "[8] Add driver \n"
				+ "[9] Add bus \n"
				+ "[10] Delete bus \n"
				+ "[11] Record actual data of trip\n"
				+ ": ";
		
		Scanner input = new Scanner(System.in);
		while(true)
		{

			System.out.print(options);
			
			switch(input.nextLine())
			{
			case "1":
				displayTripSchedule(stmt);
				break;
			case "2":
				deleteTripOffering(stmt);
				break;
			case "3":
				addTripOffering(stmt);
				break;
			case "4":
				changeDriver(stmt);
				break;
			case "5":
				changeBus(stmt);
				break;
			case "6":
				displayStops(stmt);
				break;
			case "7":
				displayWeeklySchedule(stmt);
				break;
			case "8":
				addDriver(stmt);
				break;
			case "9":
				addBus(stmt);
				break;
			case "10":
				deleteBus(stmt);
				break;
			case "11":
				// #8 on lab
				break;
			case "Q":
			case "q":
				System.out.println("Thank you!");
				input.close();
				return;
				default:
					break;
			}
		}
		
	}
	
	public static void displayTripSchedule(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter StartLocationName: ");
		String StartLocationName = input.nextLine();
		System.out.print("Enter DestinationName: ");
		String DestinationName = input.nextLine();
		System.out.print("Enter Date: ");
		String Date = input.nextLine();
		
		ResultSet rs = stmt.executeQuery("SELECT TripOffering.ScheduledStartTime, TripOffering.ScheduledArrivalTime, TripOffering.DriverName, TripOffering.BusID "
										+ "FROM TripOffering, Trip "
										+ "WHERE Trip.TripNumber = TripOffering.TripNumber AND "
												+ "Trip.StartLocationName LIKE '" + StartLocationName + "' AND "
												+ "Trip.DestinationName LIKE '" + DestinationName + "' AND "
												+ "TripOffering.Date = #" + Date + "# "
												+ "ORDER BY TripOffering.ScheduledStartTime;");
		ResultSetMetaData rsmd = rs.getMetaData();
		int numOfCol = rsmd.getColumnCount();
		
		String format = "%40s";
		for(int i = 1; i <= numOfCol; i++) {
			System.out.printf(format, rsmd.getColumnName(i));
		}
		System.out.println();
		while(rs.next()) {
			for(int i = 1; i <= numOfCol; i++) {
				System.out.printf(format, rs.getString(i));
			}
		}
		System.out.println("\n");
		rs.close();
	}
	
	public static void deleteTripOffering(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Trip#: ");
		String tripNumber = input.nextLine();
		System.out.print("Enter Date: ");
		String date = input.nextLine();
		System.out.print("Enter ScheduledStartTime: ");
		String startTime = input.nextLine();
		
		int completed = stmt.executeUpdate("DELETE FROM TripOffering WHERE TripNumber = " + tripNumber + 
				" AND Date = #" + date + "# AND ScheduledStartTime = " + 
				startTime + "");
		if(completed == 1) {
			System.out.println("Record removed from table.\n");
		}
		else {
			System.out.println("Your entry did not match a row.\n");
		}
	}
	
	public static void addTripOffering(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Trip#: ");
		String tripNumber = input.nextLine();
		System.out.print("Enter Date: ");
		String date = input.nextLine();
		System.out.print("Enter ScheduledStartTime: ");
		String startTime = input.nextLine();
		System.out.print("Enter ScheduledArrivalTime: ");
		String arrivalTime = input.nextLine();
		System.out.print("Enter Driver Name: ");
		String driverName = input.nextLine();
		System.out.print("Enter Bus ID: ");
		String busID = input.nextLine();
		
		stmt.execute("INSERT INTO TripOffering VALUES "
				+ "('" + tripNumber + "', #" + date + "#, '" + startTime +
				"', '" + arrivalTime + "', '" + driverName + "', '" + busID
				+ "')");
	}
	
	public static void changeDriver(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Trip#: ");
		String tripNumber = input.nextLine();
		System.out.print("Enter Date: ");
		String date = input.nextLine();
		System.out.print("Enter ScheduledStartTime: ");
		String startTime = input.nextLine();
		System.out.print("Enter New Driver Name: ");
		String driverName = input.nextLine();
		
		int completed = stmt.executeUpdate("UPDATE TripOffering "
				+ "SET DriverName = '" + driverName + "' WHERE TripNumber = " 
				+ tripNumber + " AND Date = #" + date + "# AND ScheduledStartTime = "
				+ startTime);
		if(completed == 1) {
			System.out.println("Successfully updated name.\n");
		}
		else {
			System.out.println("Trip does not exist.\n");
		}
	}
	
	public static void changeBus(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Trip#: ");
		String tripNumber = input.nextLine();
		System.out.print("Enter Date: ");
		String date = input.nextLine();
		System.out.print("Enter ScheduledStartTime: ");
		String startTime = input.nextLine();
		System.out.print("Enter New Bus ID: ");
		String busID = input.nextLine();
		
		int completed = stmt.executeUpdate("UPDATE TripOffering "
				+ "SET BusID = '" + busID + "' WHERE TripNumber = " 
				+ tripNumber + " AND Date = #" + date + "# AND ScheduledStartTime = "
				+ startTime);
		
		if(completed == 1) {
			System.out.println("Successfully updated bus.\n");
		}
		else {
			System.out.println("Trip does not exist.\n");
		}
	}
	
	public static void displayStops(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter TripNumber: ");
		String tripNumber = input.nextLine();
		
		ResultSet rs = stmt.executeQuery("SELECT TripNumber, StopNumber, SequenceNumber, DrivingTime "
				+ "FROM TripStopInfo "
				+ "WHERE TripNumber = '" + tripNumber+"'");

		ResultSetMetaData rsmd = rs.getMetaData();
		int numOfCol = rsmd.getColumnCount();

		String format = "%40s";
		for(int i = 1; i <= numOfCol; i++) {
			System.out.printf(format, rsmd.getColumnName(i));
		}
		System.out.println();
		while(rs.next()) {
			for(int i = 1; i <= numOfCol; i++) {
				System.out.printf(format, rs.getString(i));
			}
		}
		System.out.println("\n");

		rs.close();
	}
	
	public static void displayWeeklySchedule(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter DriverName: ");
		String driverName = input.nextLine();
		System.out.print("Enter Date: ");
		String date = input.nextLine();
		
		ResultSet rs = stmt.executeQuery("SELECT TO.TripNumber, TO.Date, TO.ScheduledStartTime, TO.ScheduledArrivalTime, TO.BusID"
						+ "FROM TripOffering TO, Driver D"
						+ "WHERE D.DriverName = " + driverName + "AND "
						+ "TO.DriverName = D.DriverName AND "
						+ "(TO.Date - " + date + ") < 7;");
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int numOfCol = rsmd.getColumnCount();
		
		String format = "%40s";
		for(int i = 1; i <= numOfCol; i++) {
			System.out.printf(format, rsmd.getColumnName(i));
		}
		
		while(rs.next()) {
			for(int i = 1; i <= numOfCol; i++) {
				System.out.printf(format, rs.getString(i));
			}
		}
		
		rs.close();
	}
	
	public static void addDriver(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Driver name: ");
		String driverName = input.nextLine();
		System.out.print("Enter driver telephone number: ");
		String driverTelephoneNumber = input.nextLine();
		
		stmt.execute("INSERT INTO Driver VALUES "
				+ "('" + driverName + "', '" + driverTelephoneNumber + "')");
	}
	
	public static void addBus(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter BusID: ");
		String busID = input.nextLine();
		System.out.print("Enter Model: ");
		String model = input.nextLine();
		System.out.print("Enter Year: ");
		String year = input.nextLine();
		
		stmt.execute("INSERT INTO Bus VALUES " 
					+ "('" + busID + "', '" + model + "', '" + year +"')");
	}
	
	public static void deleteBus(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter BusID: ");
		String busID = input.nextLine();
		System.out.print("Enter Model: ");
		String model = input.nextLine();
		System.out.print("Enter Year: ");
		String year = input.nextLine();
	
		int completed = stmt.executeUpdate("DELETE FROM BUS " 
										+ "WHERE BusID = '" + busID + "' AND "
										+ "Model = '" + model + "' AND "
										+ "Year = '" + year + "'");
		
		if(completed == 1) {
			System.out.println("Record removed from table.\n");
		}
		
		else {
			System.out.println("Your entry did not match a row");
		}
	}
	
	public static void recordActualTripData(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter TripNumber: ");
		String tripNumber = input.nextLine();
		
		ResultSet rs = stmt.executeQuery("SELECT T.TripNumber, TSI.StopNumber, T.Date, T.ScheduledStartTime, T.ScheduledArrivalTime "
										+ "FROM Trip T, TripStopInfo TSI "
										+ "WHERE T.TripNumber = " + tripNumber + " AND "
										+ "T.TripNumber = TSI.TripNumber;");

		String stopNumber = rs.getString(2);
		String date = rs.getString(3);
		String scheduledStartTime = rs.getString(4);
		String scheduledArrivalTime = rs.getString(5);
		
		System.out.print("ScheduledStartTime: " + scheduledStartTime
						+ "\nEnter ActualStartTime: ");
		String actualStartTime = input.nextLine();
		System.out.print("ScheduledArrivalTime: " + scheduledArrivalTime 
						+ "\nEnter ActualArrivalTime: ");
		String actualArrivalTime = input.nextLine();
		System.out.print("Enter NumberOfPassengerIn: ");
		String numOfPassIN = input.nextLine();
		System.out.print("Enter TNumberOfPassengerOut: ");
		String numOfPassOUT = input.nextLine();
		
		stmt.execute("INSERT INTO ActualTripInfo VALUES " 
				+ "(\'" + tripNumber + "\', \'" + date + "\', \'" + scheduledStartTime + "\', \'" + stopNumber + "\', \'" + scheduledArrivalTime 
				+ "\', \'" + actualStartTime + "\', \'" + actualArrivalTime + "\', \'" + numOfPassIN + "\', \'" + numOfPassOUT + "\')");
	}
	
	public static void fillTables(Statement stmt) throws SQLException {

		// ADD Trip (TripNumber, StartLocation, DestinationName)
		stmt.execute("INSERT INTO Trip VALUES "
				+ "('10', 'Temple Ave', 'Sunset Blvd')");
		
		// ADD TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime,
		//                   DriverName, BusID)
		stmt.execute("INSERT INTO TripOffering VALUES "
				+ "('10', #05/1/2019#, '1500', '1530', 'Jon', '999')");
		
		// ADD Bus ENTRIES (BusID, Model, Year)
		stmt.execute("INSERT INTO Bus VALUES "
				+ "('999', 'Minibus', '2008')");
		
		//	ADD Driver ENTRIES (DriverName, DriverTelephoneNumbe)
		stmt.execute("INSERT INTO Driver VALUES "
				+ "('Jon', '9091234567')");
		stmt.execute("INSERT INTO Driver VALUES "
				+ "('Tyrion', '1234567890')");
		stmt.execute("INSERT INTO Driver VALUES "
				+ "('Drogon', '9876543210')");
		
		// ADD Stop ENTRIES (StopNumber, StopAddress)
		stmt.execute("INSERT INTO Stop VALUES "
				+ "('100', 'Temple Ave')");
		stmt.execute("INSERT INTO Stop VALUES "
				+ "('105', 'Kellogg St')");
		stmt.execute("INSERT INTO Stop VALUES "
				+ "('110', 'Sunset Blvd')");
		
		//	ADD ActualTripStopInfo ENTRIES
		// (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime,
		//  ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
		stmt.execute("INSERT INTO ActualTripStopInfo VALUES "
				+ "('10', #5/1/2019#, 0800, 100, 0830, 0800, 0835,"
				+ "10, 5)");
		
		// ADD TripStopInfo ENTRIES (TripNumber, StopNumber, SequenceNumber, DrivingTime)
		stmt.execute("INSERT INTO TripStopInfo VALUES "
				+ "('10', '100', '2', 100)");
	}
	
	public static void createTables(Statement stmt) throws SQLException {
		
		String tableQuery = "CREATE TABLE Trip "
				+ "( TripNumber CHAR(6) NOT NULL, "
				+ "StartLocationName VARCHAR(50) NOT NULL, "
				+ "DestinationName VARCHAR(50) NOT NULL, "
				+ "PRIMARY KEY ( TripNumber ) "
				+ ")";
		stmt.execute(tableQuery);
		
		tableQuery = "CREATE TABLE TripOffering "
				+ "( TripNumber CHAR(6) NOT NULL, "
				+ "Date DATE NOT NULL, "
				+ "ScheduledStartTime INTEGER NOT NULL, "
				+ "ScheduledArrivalTime INTEGER NOT NULL, "
				+ "DriverName VARCHAR(50) NOT NULL, "
				+ "BusID CHAR(6) NOT NULL, "
				+ "PRIMARY KEY ( TripNumber, Date, ScheduledStartTime ) "
				+ ")";
		stmt.execute(tableQuery);
		
		tableQuery = "CREATE TABLE Bus "
				+ "(BusID CHAR(6) NOT NULL, "
				+ "Model VARCHAR(50) NOT NULL, "
				+ "Year CHAR(4) NOT NULL, "
				+ "PRIMARY KEY ( BusID ) "
				+ ")";
		stmt.execute(tableQuery);
		
		tableQuery = "CREATE TABLE Driver "
				+ "(DriverName VARCHAR(50) NOT NULL, "
				+ "DriverTelephoneNumber CHAR(10) NOT NULL, "
				+ "PRIMARY KEY ( DriverName ) "
				+ ")";
		stmt.execute(tableQuery);
		
		tableQuery = "CREATE TABLE Stop "
				+ "(StopNumber CHAR(6) NOT NULL, "
				+ "StopAddress VARCHAR(50) NOT NULL, "
				+ "PRIMARY KEY ( StopNumber ) "
				+ ")";
		stmt.execute(tableQuery);
		
		tableQuery = "CREATE TABLE ActualTripStopInfo "
				+ "(TripNumber CHAR(6) NOT NULL, "
				+ "DATE DATE NOT NULL, "
				+ "ScheduledStartTime INTEGER NOT NULL, "
				+ "StopNumber CHAR(6) NOT NULL, "
				+ "ScheduledArrivalTime INTEGER NOT NULL, "
				+ "ActualStartTime INTEGER NOT NULL, "
				+ "ActualArrivalTime INTEGER NOT NULL, "
				+ "NumberOfPassengerIn INTEGER NOT NULL, "
				+ "NumberOfPassengerOut INTEGER NOT NULL, "
				+ "PRIMARY KEY ( TripNumber, Date, ScheduledStartTime, StopNumber ) "
				+ ")";
		stmt.execute(tableQuery);
		
		tableQuery = "CREATE TABLE TripStopInfo "
				+ "(TripNumber CHAR(6) NOT NULL, "
				+ "StopNumber CHAR(6) NOT NULL, "
				+ "SequenceNumber INTEGER NOT NULL, "
				+ "DrivingTime INTEGER NOT NULL, "
				+ "PRIMARY KEY ( TripNumber, StopNumber ) "
				+ ")";
		stmt.execute(tableQuery);
		
	}

}
