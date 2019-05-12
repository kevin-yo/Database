import java.sql.Connection;
import java.sql.DriverManager;
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
		String options = "[1] Delete Trip \n"
				+ "[2] Add Trip \n"
				+ "[3] Change driver of a trip \n"
				+ "[4] Change bus of a trip \n"
				+ "[5] Display stops of a trip \n"
				+ "[6] Display weekly schedule of a driver \n"
				+ "[7] Add driver \n"
				+ "[8] Add bus \n"
				+ "[9] Delete bus \n"
				+ ": ";
		
		Scanner input = new Scanner(System.in);
		while(true)
		{

			System.out.print(options);
			
			switch(input.nextLine())
			{
			case "1":
				deleteTripOffering(stmt);
				break;
			case "2":
				addTripOffering(stmt);
				break;
			case "3":
				changeDriver(stmt);
				break;
			case "4":
				changeBus(stmt);
				break;
			case "5":
				displayStops(stmt);
				break;
			case "6":
				displayWeeklySchedule(stmt);
				break;
			case "7":
				addDriver(stmt);
				break;
			case "8":
				addBus(stmt);
				break;
			case "9":
				deleteBus(stmt);
				break;
			case "0":
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
	
	public static void deleteTripOffering(Statement stmt) throws SQLException {
		
	}
	
	public static void addTripOffering(Statement stmt) throws SQLException {
		
	}
	
	public static void changeDriver(Statement stmt) throws SQLException {
		
	}
	
	public static void changeBus(Statement stmt) throws SQLException {
		
	}
	
	public static void displayStops(Statement stmt) throws SQLException {
		
	}
	
	public static void displayWeeklySchedule(Statement stmt) throws SQLException {
		
	}
	
	public static void addDriver(Statement stmt) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Driver name: ");
		String driverName = input.nextLine();
		System.out.print("Enter driver telephone number: ");
		String driverTelephoneNumber = input.nextLine();
		
		stmt.execute("INSERT INTO Driver VALUES "
				+ "(\'" + driverName + "\', \'" + driverTelephoneNumber + "\')");
	}
	
	public static void addBus(Statement stmt) throws SQLException {
		
	}
	
	public static void deleteBus(Statement stmt) throws SQLException {
		
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
				+ "Date VARCHAR(50) NOT NULL, "
				+ "ScheduledStartTime VARCHAR(50) NOT NULL, "
				+ "ScheduledArrivalTime VARCHAR(50) NOT NULL, "
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
				+ "DriverTelephoneNumber CHAR(9) NOT NULL, "
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
				+ "DATE VARCHAR(50) NOT NULL, "
				+ "ScheduledStartTime VARCHAR(50) NOT NULL, "
				+ "StopNumber CHAR(6) NOT NULL, "
				+ "ScheduledArrivalTime VARCHAR(50) NOT NULL, "
				+ "ActualStartTime VARCHAR(50) NOT NULL, "
				+ "ActualArrivalTime VARCHAR(50) NOT NULL, "
				+ "NumberOfPassengerIn INTEGER NOT NULL, "
				+ "NumberOfPassengerOut INTEGER NOT NULL, "
				+ "PRIMARY KEY ( TripNumber, Date, ScheduledStartTime, StopNumber ) "
				+ ")";
		stmt.execute(tableQuery);
		
		tableQuery = "CREATE TABLE TripStopInfo "
				+ "(TripNumber CHAR(6) NOT NULL, "
				+ "StopNumber CHAR(6) NOT NULL, "
				+ "SequenceNumber INTEGER NOT NULL, "
				+ "DrivingTime VARCHAR(50) NOT NULL, "
				+ "PRIMARY KEY ( TripNumber, StopNumber ) "
				+ ")";
		stmt.execute(tableQuery);
		
	}

}
