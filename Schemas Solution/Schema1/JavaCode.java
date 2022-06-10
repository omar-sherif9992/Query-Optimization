import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class Schema1 {

	// //////////////////////////////////////////// Table Insertion Methods
	// ///////////////////////////////////////////////////////////////
	
	
	
	
	
	public static long insertDepartment(String deptName,int building, 
			int budget, Connection conn) {
		String SQL = "INSERT INTO department(dep_name,building,budget) "
				+ "VALUES(?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(2, building);
			pstmt.setString(1, deptName);
			pstmt.setInt(3, budget);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(2);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	public static long insertInstructor(int ID, String name, int salary,
			String deptName, Connection conn) {
		String SQL = "INSERT INTO instructor(ID,name,salary,dep_name)"
				+ "VALUES(?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(2, name);
			pstmt.setInt(1, ID);
			pstmt.setInt(3, salary);
			pstmt.setString(4, deptName);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	public static long insertClassroom(int building, int roomNo, int capacity,
			Connection conn) {
		String SQL = "INSERT INTO classroom(building,room_number,capacity)"
				+ "VALUES(?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(2, roomNo);
			pstmt.setInt(1, building);
			pstmt.setInt(3, capacity);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	public static long insertTimeSlot(int ID, String day, Time start, Time end,
			Connection conn) {
		String SQL = "INSERT INTO time_slot(id,day,start,end_time)"
				+ "VALUES(?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(2, day);
			pstmt.setInt(1, ID);
			pstmt.setTime(3, start);
			pstmt.setTime(4, end);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	public static long insertStudent(int ID, String name, int credit,
			String deptName, int instID, Connection conn) {
		String SQL = "INSERT INTO student(id,name,tot_credit,department,advisor_id)"
				+ "VALUES(?,?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(2, name);
			pstmt.setInt(1, ID);
			pstmt.setInt(3, credit);
			pstmt.setString(4, deptName);
			pstmt.setInt(5, instID);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	// CREATE TABLE course(course_id INT PRIMARY KEY, title VARCHAR(20), credits
	// INT, department VARCHAR(20) REFERENCES department);
	public static long insertCourse(int ID, String title, int credit,
			String deptName, Connection conn) {
		String SQL = "INSERT INTO course(course_id,title,credits,department)"
				+ "VALUES(?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(2, title);
			pstmt.setInt(1, ID);
			pstmt.setInt(3, credit);
			pstmt.setString(4, deptName);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	// CREATE TABLE pre_requiste(course_id INT, prereq_id INT,PRIMARY
	// KEY(course_id, prereq_id));
	public static long insertPrerequiste(int ID, int preID, Connection conn) {
		String SQL = "INSERT INTO pre_requiste(course_id,prereq_id)"
				+ "VALUES(?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(2, preID);
			pstmt.setInt(1, ID);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	// CREATE TABLE section(section_id INT PRIMARY KEY, semester INT, year INT,
	// instructor_id INT REFERENCES instructor, course_id INT REFERENCES
	// course,classroom_building INT REFERENCES classroom(building),
	// classroom_room_no INT REFERENCES classroom(room_number));

	public static long insertSection(int ID, int semester, int year,
			int instID, int courseID, int classroomBuilding,
			int classroomRoomNo, Connection conn) {
		String SQL = "INSERT INTO section(section_id,semester,year,instructor_id,course_id,classroom_building,classroom_room_no)"
				+ "VALUES(?,?,?,?,?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(2, semester);
			pstmt.setInt(1, ID);
			pstmt.setInt(3, year);
			pstmt.setInt(4, instID);
			pstmt.setInt(5, courseID);
			pstmt.setInt(6, classroomBuilding);
			pstmt.setInt(7, classroomRoomNo);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	// CREATE TABLE takes(student_id INT REFERENCES student, section_id INT
	// REFERENCES section, grade real, PRIMARY KEY(student_id, section_id));
	public static long insertTakes(int ID, int secID, double grade,
			Connection conn) {
		String SQL = "INSERT INTO takes(student_id,section_id,grade)"
				+ "VALUES(?,?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(2, secID);
			pstmt.setInt(1, ID);
			pstmt.setDouble(3, grade);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	// CREATE TABLE section_time(time_slot INT REFERENCES time_slot, section_id
	// INT REFERENCES section, PRIMARY KEY(time_slot, section_id));
	public static long insertSectionTime(int timeSlot, int secID,
			Connection conn) {
		String SQL = "INSERT INTO section_time(time_slot,section_id)"
				+ "VALUES(?,?);";

		long id = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(SQL,
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(2, secID);
			pstmt.setInt(1, timeSlot);

			int affectedRows = pstmt.executeUpdate();
			System.out.println("Number of affected rows is " + affectedRows);
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					// System.out.println(rs.next());
					if (rs.next()) {
						id = rs.getLong(1);
						pstmt.close();
						conn.commit();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	// ///////////////////////////////////////// Data Population Method
	// //////////////////////////////////////////////////////
	public static void populateDepartment(Connection conn) {
		for (int i = 1; i <= 60; i++) {
			String x="Dept"+i;
			if (i==60)
				x="CSEN";
			if (insertDepartment(x,i,Integer.parseInt(i+"000"),conn)==0 ) {
				System.err.println("insertion of record " + i + " in populate Department failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	public static void populateInstructor(Connection conn) {
		for (int i = 1; i <= 60; i++) {
			String deptName="Dept"+i;		
			
			if (deptName.equals("Dept60"))
				deptName="CSEN";
			
			int startID=30*(i-1)+1;	
			for (int j=startID; j<=startID+29;j++)
			{
				if (insertInstructor(j, "Slim" + j, Integer.parseInt(i+"0000"), deptName, conn) == 0) {
					System.err.println("insertion of record " + i + " in populate Instructor failed");
					break;
				} else
					System.out.println("insertion was successful");
			}

			
		}
	}

	public static void populateClassroom(Connection conn) {
		for (int i = 1; i <= 1000; i++) {
			if (insertClassroom(i, i, 100 + i, conn) == 0) {
				System.err.println("insertion of record " + i + " in populate Classroom failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	@SuppressWarnings("deprecation")
	public static void populateTimeSlot(Connection conn) {
		for (int i = 1; i <= 1000; i++) {
			if (insertTimeSlot(i, "day" + i, new Time(12, 0, 0), new Time(13,
					0, 0), conn) == 0) {
				System.err.println("insertion of record " + i + " in populate TimeSlot failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	public static void populateStudent(Connection conn) {
		for (int i = 1; i <= 60; i++) {		
			String deptName="Dept"+i;
			if (deptName.equals("Dept60"))
				deptName="CSEN";
			int startID=3000*(i-1)+1;
			
			for (int j=startID;j<=startID+2999;j++)
			{	
				
				if (insertStudent(j, "Wahba"+j , i, deptName, i, conn) == 0) {
					System.err.println("insertion of record " + i + " in populateStudent failed");
					break;
				} else
					System.out.println("insertion was successful");
			}
		}
	}

	public static void populateCourse(Connection conn) {
		for (int i = 1; i <= 60; i++) {
			String deptName="Dept"+i;
			
			if (deptName.equals("Dept60"))
				deptName="CSEN";
			
			int startID=40*(i-1)+1;		
			for (int j=startID;j<=startID+39;j++)
			{
				if (insertCourse(j, "CSEN" + j, i, deptName, conn) == 0) {
					System.err.println("insertion of record " + i + " in populateCourse failed");
					break;
				} else
					System.out.println("insertion was successful");
			}
		}
	}

	public static void populatePrerequiste(Connection conn) {
		for (int i = 1; i <=40; i++) {
			if (insertPrerequiste(i, i, conn) == 0) {
				System.err.println("insertion of record " + i + " in populate Prerequisite failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	public static void populateSection(Connection conn) {
		
		for (int i = 1; i <=500; i++) {
			int semester=1;
			int year=2019;
			if (i>=301)
			{
				semester=2;
				year=2019;
			}
			if (insertSection(i, semester, year, i, i, i, i, conn) == 0) {
				System.err.println("insertion of record " + i + "  in populate Section failed");
				break;
			} else
				System.out.println("insertion was successful");
			
		}
	}

	public static void populateTakes(Connection conn) {
		double grade = 0.7;
		int studentID=64901;
		for (int i = 1; i <= 400; i++) {
			
			if (i==251)
				studentID=1;
			if (i==301)
				studentID=65501;
			
			if (insertTakes(studentID, i, grade, conn) == 0) {
				System.err.println("insertion of record " + i + " in populate Takes failed");
				break;
			} else
				System.out.println("insertion was successful");
			studentID++;

		}
	}

	public static void populateSectionTime(Connection conn) {
		for (int i = 1; i <= 300; i++) {
			if (insertSectionTime(i, i, conn) == 0) {
				System.err.println("insertion of record " + i + " in populate SectionTime failed");
				break;
			} else
				System.out.println("insertion was successful");
		}
	}

	public static void insertSchema1(Connection connection) {
	//	populateDepartment(connection);
//		populateInstructor(connection);
//		populateClassroom(connection);
//		populateTimeSlot(connection);
		populateStudent(connection);
//		populateCourse(connection);
//		populatePrerequiste(connection);
		populateSection(connection);
//		populateTakes(connection);
//		populateSectionTime(connection);
	}

	public static void main(String[] argv) {

		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;
		try {

			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/schema1", "postgres",
					"postgres");
			insertSchema1(connection);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
