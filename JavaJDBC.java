import java.io.*;
import java.util.Scanner;
import java.sql.*;

public class IP_JAVA_Phan_Tien {
	
	//1. Enter a new employee
	public static void option1(String eName, String e_address, Connection con) {
		int emptype=0,max_prod_day=0;
		String position=null, degree = null, product_type = null;
		try {
			CallableStatement pstp = con.prepareCall("{call Query1(?,?)}");
			pstp.setString(1, eName);
			pstp.setString(2, e_address);
			pstp.executeUpdate();
			System.out.println("Option1 -- Employee added !");
			
			// Assign employee to either Worker/ TechnicalStaff or QC
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			// Select employee type:
			System.out.println("Enter employee type:\n\t 1: Worker\n\t 2: Quality Controller\n\t 3: Technical Staff");
			emptype = in.nextInt();
			in.nextLine();
			switch (emptype) {
			case 1: 
				System.out.println("Enter max_prod_day for Worker: ");
				max_prod_day = in.nextInt();
				CallableStatement pstp1 = con.prepareCall("{call Query1a(?,?)}");
				pstp1.setString(1, eName);
				pstp1.setInt(2, max_prod_day);
				pstp1.executeUpdate();
				break;
			case 2:
				System.out.println("Enter product type for Quality Controller: ");
				product_type = in.nextLine();
				CallableStatement pstp2 = con.prepareCall("{call Query1b(?,?)}");
				pstp2.setString(1, eName);
				pstp2.setString(2, product_type);
				pstp2.executeUpdate();
				break;
				
			case 3:
				System.out.println("Enter position of Technical Staff: ");
				position = in.nextLine();
				System.out.println("Enter degree of Technical Staff[BS,MS,PhD]: ");
				degree = in.nextLine();
				CallableStatement pstp3 = con.prepareCall("{call Query1c(?,?,?)}");
				pstp3.setString(1,  eName);
				pstp3.setString(2, position);
				pstp3.setString(3, degree);
				pstp3.executeUpdate();
				break;
				
			default:
				System.out.println("Please select employee type:?? ");
			
			}
			
		} catch (SQLException e) {System.err.println(e.getMessage());
		}
		
	}
	
	//2. Enter a new product associated with the persons...
	public static void option2(int pID, String size, String prod_date, int p_time, String w_name, String con_name, Connection con) {
		int ptype=0;
		String software = null, color = null, weight = null, is_rep = null, t_name = null, r_date = null;
		try {
			CallableStatement pstp = con.prepareCall("{call Query2(?,?,?,?,?,?)}");
			pstp.setInt(1, pID);
			pstp.setString(2, size);
			pstp.setString(3, prod_date);
			pstp.setInt(4, p_time);
			pstp.setString(5, w_name);
			pstp.setString(6, con_name);
			pstp.executeUpdate();
			
			// Assign product to product1/ product2 or product3:
			
			@SuppressWarnings({"resource" })
			Scanner in = new Scanner(System.in);
			// Select product type:
			System.out.println("Enter product type [1,2,3]:");
			ptype = in.nextInt();
			in.nextLine();
			switch(ptype) {
			case 1:
				System.out.println("Enter software for product 1: ");
				software = in.nextLine();
				CallableStatement pstp1 = con.prepareCall("{call Query2a(?,?)}");
				pstp1.setInt(1, pID);
				pstp1.setString(2,software);
				pstp1.executeUpdate();
				break;
				
			case 2:
				System.out.println("Enter color for product 2: ");
				color = in.nextLine();
				CallableStatement pstp2 = con.prepareCall("{call Query2b(?,?)}");
				pstp2.setInt(1, pID);
				pstp2.setString(2, color);
				pstp2.executeUpdate();			
				break;
				
			case 3:
				System.out.println("Enter weight for product 3: ");
				weight = in.nextLine();
				CallableStatement pstp3 = con.prepareCall("{call Query2c(?,?)}");
				pstp3.setInt(1,pID);
				pstp3.setString(2, weight);
				pstp3.executeUpdate();
				break;
			
			default:
				System.out.println("Please select product type:?? ");
				break;
			}
			// If product is repair?
			System.out.print("Is the product is repaired? [y/n]\t :");
			is_rep = in.next();
			in.nextLine();
			if (is_rep.equalsIgnoreCase("y")) { //enter techstaff name
				System.out.println("Enter technical staff name: ");
				t_name = in.nextLine();
				System.out.println("Enter repair date [YYYY-MM-DD] :");
				r_date = in.nextLine();
				CallableStatement pstp4 = con.prepareCall("{call Query2d(?,?,?)}");
				pstp4.setInt(1, pID);
				pstp4.setString(2, r_date);
				pstp4.setString(3, t_name);
				pstp4.executeUpdate();
			}
			
			System.out.println("Product added !");
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
	}
	
	//3. Enter a customer associated with some products
	public static void option3(String c_name, String c_address, Connection con) {
		int pID =0;
		try {
			CallableStatement pstp = con.prepareCall("{call Query3(?,?)}");
			pstp.setString(1, c_name);
			pstp.setString(2, c_address);
			pstp.executeUpdate();
			
			//Associate customer with product:
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			System.out.println("Enter associated product id: ");
			pID = in.nextInt();
			in.nextLine();
			CallableStatement pstp1 = con.prepareCall("{call Query3a(?,?)}");
			pstp1.setInt(1, pID);
			pstp1.setString(2, c_name);
			pstp1.executeUpdate();
			
			System.out.println("Customer " +c_name+ " with associated product ID " +pID+ " added !");
					
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
		
	}
	
	//4. Create a new account associated with a product
	public static void option4(int acc_number,String date_created,int cost,int pID,Connection con) {
		int ptype =0;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Select which product type that account associated with [1,2,3]: ");
		ptype = in.nextInt();
		try {
			CallableStatement pstp = con.prepareCall("{call Query4"+ptype+"(?,?,?,?)}");
			pstp.setInt(1, acc_number);
			pstp.setString(2, date_created);
			pstp.setInt(3, cost);
			pstp.setInt(4, pID);
			pstp.executeUpdate();
			
			System.out.println("Account number "+acc_number+" is added to product ID "+pID+" of product type "+ptype+ " ");
		
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
		
	}
	
	//5. Enter a complaint associated with a customer and product
	@SuppressWarnings("resource")
	public static void option5(int c_id,int i_pID,String c_name, String c_date,String c_descript, String c_treatment, Connection con) {
		String is_rep = null, r_date = null, t_name = null, query = null;
		PreparedStatement pstView;
		ResultSet result;
		int count_pID = 0, count_p1 = 0;
		try {
			CallableStatement pstp = con.prepareCall("{call Query5(?,?,?,?,?,?)}");
			pstp.setInt(1,c_id);
			pstp.setInt(2, i_pID);
			pstp.setString(3, c_name);
			pstp.setString(4, c_date);
			pstp.setString(5, c_descript);
			pstp.setString(6, c_treatment);
			pstp.executeUpdate();
			
			// If product is repair?
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			System.out.print("Is the product being repaired? [y/n]\t :");
			is_rep = in.next();
			in.nextLine();
			if (is_rep.equalsIgnoreCase("y")) {
				//Check if pID is in Repair:
				query = "select count(pID) from Repair where pID="+i_pID;
				pstView = con.prepareStatement(query);
				result = pstView.executeQuery();
				while (result.next())
				{
					count_pID = result.getInt(1);
				}
				if (count_pID > 0) {
					// get techname from Repair
					query = "select eName from Repair where pID="+i_pID;
					pstView = con.prepareStatement(query);
					result = pstView.executeQuery();
					while (result.next())
					{
						t_name = result.getString(1);
					}
				}
				else {
					// check pID is from Product1?
					query = "select count(pID) from Product1 where pID="+i_pID;
					pstView = con.prepareStatement(query);
					result = pstView.executeQuery();
					while (result.next())
					{
						count_p1 = result.getInt(1);
					}
					if (count_p1>0) {
						query = "select eName from TechnicalStaff where degree='MS' or degree='PhD'";
					}
					else {
						query = "select eName from TechnicalStaff";
					}
					pstView = con.prepareStatement(query);
					result = pstView.executeQuery();
					while (result.next())
					{
						// Display availabe technical name:
						System.out.println(result.getString(1));
					}
					System.out.println("Please select technical name as listed above: ");
					t_name = in.nextLine();
				}
				
				
				// Insert into Repair (pID, r_date, t_name) -- procedure 2d
				System.out.println("Enter repaired date [YYYY-MM-DD]: ");
				r_date = in.nextLine();				
				CallableStatement pstp1 = con.prepareCall("{call Query2d(?,?,?)}");
				pstp1.setInt(1, i_pID);
				pstp1.setString(2, r_date);
				pstp1.setString(3, t_name);
				pstp1.executeUpdate();
				// Insert into Repair_comp (c_id, pID, r_date)
				CallableStatement pstp2 = con.prepareCall("{call Query5a(?,?,?)}");
				pstp2.setInt(1, c_id);
				pstp2.setInt(2, i_pID);
				pstp2.setString(3, r_date);
				pstp2.executeUpdate();
			}
			
			System.out.println("Added Complaint id "+c_id+" with customer "+c_name+" and product id "+i_pID+" ");
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
		
	}
	
	//6. Enter an accident associated with appr. employee and product
	public static void option6(int accident_num, int acc_lost_days,Connection con ) {
		String is_acc = null, query = null, accident_date = null, e_type = null, e_disp = null;
		int pID;
		
		try {
			// Is accident related to produced or repaired:
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			System.out.print("Is the accident related to produced [p] or repaired[r] ?\t:");
			is_acc = in.next();
			in.nextLine();
			System.out.println("Enter the associated product id from the following options: " );
			if (is_acc.equalsIgnoreCase("p")) {
				e_type = "Worker: ";
				query = "SELECT pID FROM Product";
							}
			else {
				e_type = "Technical Staff: ";
				query = "SELECT pID FROM Repair";
			}
			try {
				PreparedStatement pstView = con.prepareStatement(query);
				ResultSet result = pstView.executeQuery();
				System.out.println("--pID------");
				while (result.next())
				{
					System.out.println(result.getString(1));
				}
				System.out.println("-----------");
				
			} catch (SQLException e) {System.err.println(e.getMessage());
			
			}
			pID = in.nextInt();
			// Get accident_date:
			if(is_acc.equalsIgnoreCase("p")) {
				query = "SELECT pID, prod_date, w_name FROM Product where pID="+pID; 
				
			}
			else {
				query = "SELECT pID, r_date, eName FROM Repair where pID="+pID;
				
			}
			 PreparedStatement pstView = con.prepareStatement(query);
			 ResultSet result = pstView.executeQuery();
			 while (result.next())
			 {
				 accident_date = result.getDate(2).toString();
				 e_disp = result.getString(3);
			 }
			// Insert into Accident table 
			CallableStatement pstp = con.prepareCall("{call Query6(?,?,?)}");
			pstp.setInt(1, accident_num);
			pstp.setString(2, accident_date);
			pstp.setInt(3, acc_lost_days);
			pstp.executeUpdate();
			// Insert into Acc_produce or Acc_repair
			if(is_acc.equalsIgnoreCase("p")) {
				CallableStatement pstp1 = con.prepareCall("{call Query6a(?,?)}");
				pstp1.setInt(1, accident_num);
				pstp1.setInt(2, pID);
				pstp1.executeUpdate();
			}
			else {
				CallableStatement pstp2 = con.prepareCall("{call Query6b(?,?,?)}");
				pstp2.setInt(1, accident_num);
				pstp2.setInt(2, pID);
				pstp2.setString(3, accident_date);
				pstp2.executeUpdate();
			}
			System.out.println("Added accident number: "+accident_num+" associated with product id: "+pID+" and "+e_type+e_disp+ " ");
		} catch (SQLException e) {System.err.println(e.getMessage());
		
		}
	}
	//7. Retrieve the date produced and time spent to particular product
	public static void option7(int pID,Connection con) { 
		try {
			PreparedStatement pstp = con.prepareCall("{call Query7(?)}");
			pstp.setInt(1, pID);
			ResultSet result = pstp.executeQuery();
			System.out.println("Product Date: \tProduct Time:\t");
			while (result.next())
			{
				System.out.println(result.getDate(3) + "\t" + result.getInt(4));
			}
		} catch (SQLException e) {System.err.println(e.getMessage());
		
		}
		
	}
	
	//8. Retrieve all prods made by a particular worker
	public static void option8(String w_name, Connection con) {
		try {
			
			PreparedStatement pstp = con.prepareCall("{call Query8(?)}");
			pstp.setString(1, w_name);
			ResultSet result = pstp.executeQuery();
			System.out.println("pID:\tsize:\tprod_date:\tp_time:\tWorker:\t\tController:");
			while (result.next())
			{
				System.out.println(result.getInt(1)+"\t"
						+ result.getString(2)+"\t"+result.getDate(3)+"\t"
						+ result.getInt(4)+"\t"+result.getString(5)+"\t"
						+ result.getString(6));
			}
		} catch (SQLException e) {System.err.println(e.getMessage());
		
		}
	}
	
	//9. Retrieve the total number of errors a particular QC made
	public static void option9(String con_name, Connection con) {
		try {
			PreparedStatement pstp = con.prepareCall("{call Query9(?)}");
			pstp.setString(1, con_name);
			ResultSet result = pstp.executeQuery();
			while (result.next())
			{
				System.out.println("Total number of errors made by "+con_name+" : "+result.getInt(1));
			}
			
		} catch (SQLException e) {System.err.println(e.getMessage());
		
		}
		
	}
	
	//10. Retrieve the total costs of the products in prod3 which was repaired at the request by particular QC
	public static void option10(String con_name, Connection con) {
		try {
			PreparedStatement pstp = con.prepareCall("{call Query10(?)}");
			pstp.setString(1, con_name);
			ResultSet result = pstp.executeQuery();
			while (result.next())
			{
				System.out.println("Total cost of products requested by "+con_name+" :"+result.getInt(1));
			}
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
	}
	
	//11. Retrieve all customers who purchased all prods of a particular color
	public static void option11(String color, Connection con) {
		try {
			PreparedStatement pstp = con.prepareCall("{call Query11(?)}");
			pstp.setString(1, color);
			ResultSet result = pstp.executeQuery();
			System.out.println("Customers purchased products of color "+color+" :");
			while (result.next()) {
				System.out.println(result.getString(1));
			}
			
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
		
	}
	
	//12. Retrieve the total number of work days lost due to acc. in repairing the products got complaints
	public static void option12(Connection con) {
		try {
			PreparedStatement pstp = con.prepareCall("{call Query12()}");
			ResultSet result = pstp.executeQuery();
			while (result.next()) {
				System.out.println("Total number of lost work days: "+result.getInt(1));
			}
		} catch(SQLException e) {System.err.println(e.getMessage());
			
		}
		
	}
	
	//13. Retrieve all customers who are also workers
	public static void option13(Connection con) {
		try {
			PreparedStatement pstp = con.prepareCall("{call Query13()}");
			ResultSet result = pstp.executeQuery();
			System.out.println("Customer name: ");
			while (result.next()) {
				System.out.println(result.getString(1));
			}
		} catch (SQLException e) {System.err.println(e.getMessage());
		
		}
		
	}
	
	//14. Retrieve all customers who purchased products made/ certified/ repaired themself
	public static void option14(Connection con ) {
		try {
			PreparedStatement pstp = con.prepareCall("{call Query14()}");
			ResultSet result = pstp.executeQuery();
			System.out.println("Customer name: ");
			while (result.next()) {
				System.out.println(result.getString(1));
			}
			
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
		
	}
	
	//15. Retrieve the avg. cost of all products made in a particular year
	public static void option15(int year, Connection con) {
		try {
			PreparedStatement pstp = con.prepareCall("{call Query15(?)}");
			pstp.setInt(1, year);
			ResultSet result = pstp.executeQuery();
			while (result.next()) {
				System.out.println("Average cost for all products of year "+year+" :"+result.getFloat(1));
			}
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
		
	}
	
	//16. Switch the position btw a technical staff and a QC
	public static void option16(String t_name, String con_name, Connection con) {
		String position = null, degree = null, product_type = null;
		try {
			//get position and degree from current Tech
			PreparedStatement pstp = con.prepareCall("{call Query16a(?)}");
			pstp.setString(1, t_name);
			ResultSet tech = pstp.executeQuery();
			while (tech.next())
			{
				position = tech.getString(1);
				degree = tech.getString(2);
			}
			//get product_type from current Controller
			PreparedStatement pstp1 = con.prepareCall("{call QUery16b(?)}");
			pstp1.setString(1, con_name);
			ResultSet controller = pstp1.executeQuery();
			while (controller.next())
			{
				product_type = controller.getString(1);
			}
			
			//insert new controller (Query1b) with tech name
			CallableStatement pstp2 = con.prepareCall("{call Query1b(?,?)}");
			pstp2.setString(1, t_name);
			pstp2.setString(2, product_type);
			pstp2.executeUpdate();
			
			//insert new technical (Query1c) with controller name
			CallableStatement pstp3 = con.prepareCall("{call Query1c(?,?,?)}");
			pstp3.setString(1, con_name);
			pstp3.setString(2, position);
			pstp3.setString(3, degree);
			pstp3.executeUpdate();
			
			//update con_name in Product (Query16c)
			CallableStatement pstp4 = con.prepareCall("{call Query16c(?,?)}");
			pstp4.setString(1, con_name);
			pstp4.setString(2, t_name);
			pstp4.executeUpdate();
			
			//update t_name in Repair (Query16d)
			CallableStatement pstp5 = con.prepareCall("{call Query16d(?,?)}");
			pstp5.setString(1, con_name);
			pstp5.setString(2, t_name);
			pstp5.executeUpdate();
			
			// delete old controller and tech (Query16e)
			CallableStatement pstp6 = con.prepareCall("{call Query16e(?,?)}");
			pstp6.setString(1, con_name);
			pstp6.setString(2, t_name);
			pstp6.executeUpdate();
			
			System.out.println("Already switch the role between Tech "+t_name+" and Controller "+con_name+" .");
		} catch (SQLException e) {System.err.println(e.getMessage());
			
		}
	}
	
	//17. Delete all accidents whose dates are in some range
	public static void option17(String date1,String date2,Connection con) {
		try {
			CallableStatement pstp = con.prepareCall("{call Query17(?,?)}");
			pstp.setString(1, date1);
			pstp.setString(2, date2);
			pstp.executeUpdate();
			System.out.println("Accidents are deleted! ");
		} catch(SQLException e) {System.err.println(e.getMessage());
		
		}
		
	}
	
	//18. Import
	public static void option18() {
		
	}
	
	//19. Export
	public static void option19(String filename,Connection con)  {
		int i=0;
		String query;
		try 
		(PrintWriter out = new PrintWriter(filename)) {
			try {
				query = "select c_name, c_address from Customer order by c_name";
				
			    PreparedStatement pstp = con.prepareStatement(query);
				ResultSet result = pstp.executeQuery();
				out.println("Customer Name, Customer Address");
				while (result.next()) {
					i++;
					out.print(result.getString(1)+" ,");
					out.println(result.getString(2));
				}
				System.out.println("File successful exported for Customer table");
				
			} catch (SQLException e) {System.out.println(e.getMessage());
			
			}
		} catch (FileNotFoundException e) {System.out.println("Error");}
	
	}
	
	//20. Quit
	public static void option20(Connection con) {
		try {
			con.close();
			System.out.println("Option20 -- Quit.");
		} catch (SQLException e) {System.err.println(e.getMessage());}
		
	}
	
	public static int getOption (Scanner sc) {
		int opt;
		// Display options:
		System.out.println("\n<<WELLCOME TO THE DATABASE SYSTEM OF FUTURE, INC.>>");
		System.out.println("");
		System.out.println("1. Enter a new employee");
		System.out.println("2. Enter a new product associated with the person who made the product, repaired the product if it is repaired, or checked the product.");
		System.out.println("3. Enter a customer associated with some products. ");
		System.out.println("4. Create a new account associated with some products.");
		System.out.println("5. Enter a complaint associated with a customer and product." );
		System.out.println("6. Enter an accident associated with appropriate employee and product.");
		System.out.println("7. Retrieve the date produced and time spent to produce a particular product.");
		System.out.println("8. Retrieve all products made by a paritcular worker.");
		System.out.println("9. Retrieve the total number of errors a particular quality controller made.");
		System.out.println("10. Retrieve the total costs of the products in the product3 category which were repaired at the request fo a particular quality controller.");
		System.out.println("11. Retrieve all customers who purchased all products of a particular color.");
		System.out.println("12. Retrieve the total number of work days lost due to accidents in repairing the products which got complaints.");
		System.out.println("13. Retrieve all customers who are also workers.");
		System.out.println("14. Retrieve all the customers who have purchased the products made or certified or repaired by themselves.");
		System.out.println("15. Retrieve the average cost of all products made in a particular year.");
		System.out.println("16. Switch the position between a technical staff and a quality controller.");
		System.out.println("17. Delete all accidents whose dates are in some range.");
		System.out.println("18. Import: Enter new customers from a data file until the file is empty.");
		System.out.println("19. Export: Retrieve all customers (in name order) and output them to a data file.");
		System.out.println("20. Quit");
		System.out.println("");
		
		
		// Read and retrieve user's option input:
		System.out.println("Enter option number 1-20: ");
		opt = sc.nextInt();
		return opt;
	}
	
	public static Scanner sc() {
		Scanner sc = new Scanner(System.in);
		return sc;
	}
	// Main program:
	public static void main(String args[]) {
		try {
			//Connect to database
			String hostName = "phan0004-sql-server.database.windows.net";
			String dbName = "cs-dsa-4513-sql-db";
			String user = "phan0004";
			String password = "Austin0309";
			
			String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameIncertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
			Connection con = DriverManager.getConnection(url);
			
			// Read input from user
			Scanner sc = new Scanner(System.in);
			
			// Declare variable
			String eName, e_address, size, prod_date, w_name, con_name, c_name, c_address, date_created;
			String c_date, c_descript, c_treatment, accident_date, color, date1, date2, t_name, filename, cust_export;
			int pID, p_time, acc_number, cost, c_id, accident_num, acc_lost_days,year;
			
			// Perform queries 1-20
			int optno = 0;
			
			try {
				while (optno !=20) {
					optno = getOption(sc);
					switch (optno) {
					
					case 1:
						System.out.println("Enter a new employee: ");
						sc.nextLine();
						eName = sc.nextLine();
						System.out.println("Enter employee address: ");
						e_address = sc.nextLine();
						option1(eName,e_address,con);
						break;
					
					case 2:
						System.out.println("Enter product ID:");
						pID = sc.nextInt();
						sc.nextLine();
						System.out.println("Enter product size [small,medium,large]: ");
						size = sc.nextLine();
						System.out.println("Enter produced date [YYYY-MM-DD]: ");
						prod_date = sc.nextLine();
						System.out.println("Enter produced time spent: ");
						p_time = sc.nextInt();
						sc.nextLine();
						System.out.println("Enter a worker name: ");
						w_name = sc.nextLine();
						System.out.println("Enter a quality controller name: ");
						con_name = sc.nextLine();
						option2(pID,size,prod_date,p_time,w_name,con_name,con);
						break;
					
					case 3:
						System.out.println("Enter a new customer: ");
						sc.nextLine();
						c_name = sc.nextLine();
						System.out.println("Enter customer's address: ");
						c_address = sc.nextLine();			
						
						option3(c_name,c_address,con);
						break;
					
					case 4:
						System.out.println("Enter account number of product: ");
						acc_number = sc.nextInt();
						sc.nextLine();
						System.out.println("Enter the date which account is created [YYYY-MM-DD]: ");
						date_created = sc.nextLine();
						System.out.println("Enter producing cost: ");
						cost = sc.nextInt();
						sc.nextLine();
						System.out.println("Enter associated product ID belong to account: ");
						pID = sc.nextInt();
												
						option4(acc_number,date_created,cost,pID,con);
						break;
						
					case 5:
						System.out.print("Enter complaint ID: ");
						c_id = sc.nextInt();
						sc.nextLine();
						System.out.println("Enter product ID associated with the complaint: ");
						pID = sc.nextInt();
						sc.nextLine();
						System.out.println("Enter customer name that has the complaint: ");
						c_name = sc.nextLine();
						System.out.println("Enter the date which complaint occured [YYYY-MM-DD]: ");
						c_date = sc.nextLine();
						System.out.println("Enter the description of the complaint: ");
						c_descript = sc.nextLine();
						System.out.println("Enter the treatment for this complaint: ");
						c_treatment = sc.nextLine();
						
						option5(c_id,pID,c_name,c_date,c_descript,c_treatment,con);
						break;
					case 6:
						System.out.println("Enter accident number: ");
						accident_num = sc.nextInt();
						sc.nextLine();
						System.out.println("Enter lost days due to accident: ");
						acc_lost_days = sc.nextInt();
						option6(accident_num,acc_lost_days,con);
						break;
					case 7:
						System.out.println("Enter product Id: ");
						pID = sc.nextInt();
						option7(pID,con);
						break;
					case 8:
						System.out.println("Enter worker name: ");
						sc.nextLine();
						w_name = sc.nextLine();	
						option8(w_name,con);
						break;
					case 9:
						System.out.println("Enter quality controller name: ");
						sc.nextLine();
						con_name = sc.nextLine();
						option9(con_name,con);
						break;
					case 10:
						System.out.println("Enter requested quality controller name: ");
						sc.nextLine();
						con_name = sc.nextLine();
						option10(con_name,con);
						break;
					case 11:
						System.out.println("Enter color: ");
						sc.nextLine();
						color = sc.nextLine();
						option11(color,con);
						break;
					case 12:
						option12(con);
						break;
					case 13:
						option13(con);
						break;
					case 14:
						option14(con);
						break;
					case 15:
						System.out.println("Enter a particular year [YYYY]: ");
						year = sc.nextInt();
						option15(year,con);
						break;
					case 16:
						System.out.println("Enter technical staff name to be switched: ");
						sc.nextLine();
						t_name = sc.nextLine();
						System.out.println("Enter controller name to be switched: ");
						con_name = sc.nextLine();
						option16(t_name,con_name,con);
						break;
					case 17:
						System.out.println("Enter date ranges in format [YYY-MM-DD].");
						System.out.println("First, enter date range from [YYY-MM-DD]: ");
						sc.nextLine();
						date1 = sc.nextLine();
						System.out.println("And enter date range to [YYYY-MM-DD): ");
						date2 = sc.nextLine();
						option17(date1,date2,con);
						break;
					case 18:
						try {
						System.out.println("Enter input filename: ");
						filename = sc.next();
						File nf = new File(filename);
						BufferedReader readfile = new BufferedReader(new FileReader(nf));
						String Line ="";
						while((Line = readfile.readLine())!=null) {
							String[] parts = Line.split(",");
							String part1 = parts[0];
							String part2 = parts[1];
							CallableStatement pstp = con.prepareCall("{call Query3(?,?)}");
							pstp.setString(1, part1);
							pstp.setString(2, part2);
							pstp.executeUpdate();
						}
						System.out.println("---Done import file and insert customer data---");
						readfile.close();
						}
						catch (Exception e) {
							System.out.println("Get error: " + e.getMessage());
						}
						break;
					case 19:
						System.out.println("Enter filename to output customer: ");
						filename = sc.next();
						option19(filename,con);
						break;
					case 20:
						option20(con);
						break;
					
					default:
						System.out.println("Please enter option number 1-20");
						break;
					}
					
				}
				
			} catch (Exception e) { System.out.println("Error: " + e.getMessage());
				
			}
				
			
		} catch (SQLException e) { System.err.println(e.getMessage());
		  }
		}
	
	

	
}