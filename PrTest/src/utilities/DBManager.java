package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {

	private Connection con = null;
	private Statement stmt = null;
	ResultSet res;

	public void DBConnection(String DBPath, String Username, String Password) throws Exception {
		try {

			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

			System.out.println("Driver Registered");
			con = DriverManager.getConnection(DBPath, Username, Password);
			System.out.println("Database Connected");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println("Stm created");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ResultSet GetResultSet(String DBQuery) throws Exception {

		try {

			res = stmt.executeQuery(DBQuery);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return res;
	}

	public void UpdateQueryExecutor(String DBQuery) throws Exception {

		try {

			stmt.executeUpdate(DBQuery);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void SetPractoLabData(String city, String lab, String json, String remarks, String DocURL, String Rating,
			String RatingCount, String Prime, String tableName) throws SQLException {

		try {
			String datam = "('" + city + "','" + lab + "','" + json + "','" + remarks + "');";

			System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName
					+ "(City, Lab, JSON, Remarks, DocURL, Rating, RatingCount, Prime)  values ('" + city + "','" + lab
					+ "','" + json + "','" + remarks + "','" + DocURL + "','" + Rating + "','" + RatingCount + "','"
					+ Prime + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetPractoURLData(String DocURL, String Clinic, String ClinicURL, String Days, String Time, String Fee,
			String Book, String Remarks, String ClinicAdd, String ClinicGoogleAdd, String tableName)
			throws SQLException {

		try {
			String datam = "('" + DocURL + "','" + Clinic + "','" + ClinicURL + "','" + Days + "');";

			System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName
					+ "(DocURL, Clinic, ClinicURL, Days, Time, Fee, Book, Remarks, ClinicAdd, ClinicGoogleAdd)  values ('"
					+ DocURL + "','" + Clinic + "','" + ClinicURL + "','" + Days + "','" + Time + "','" + Fee + "','"
					+ Book + "','" + Remarks + "','" + ClinicAdd + "','" + ClinicGoogleAdd + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetMasterData(String City, String DocURL, String DocName, String DocImageURL, String DocGoogleAdd,
			String About, String Specializations, String Experience, String Services, String Languages, String IsPrime,
			String Rating, String RatingVotes, String FeedbackVotes, String HealthFeed, String Remarks,
			String Procedure, String Source, String tableName) throws SQLException {

		try {

			String datam = "('" + City + "','" + DocURL + "','" + DocName + "','" + DocImageURL + "','" + DocGoogleAdd
					+ "','" + About + "','" + Specializations + "','" + Experience + "','" + Services + "','"
					+ Languages + "','" + IsPrime + "','" + Rating + "','" + RatingVotes + "','" + FeedbackVotes + "','"
					+ HealthFeed + "','" + Remarks + "','" + Source + "');";

			System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName
					+ "(City, DocURL, DocName, DocImageURL, DocGoogleAdd, About, Specializations, Experience, Services, Languages, IsPrime, Rating, RatingVotes, FeedbackVotes, HealthFeed, Remarks, Procedures, Source)  values ('"
					+ City + "','" + DocURL + "','" + DocName + "','" + DocImageURL + "','" + DocGoogleAdd + "','"
					+ About + "','" + Specializations + "','" + Experience + "','" + Services + "','" + Languages
					+ "','" + IsPrime + "','" + Rating + "','" + RatingVotes + "','" + FeedbackVotes + "','"
					+ HealthFeed + "','" + Remarks + "','" + Procedure + "','" + Source + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetLinkedClinicsData(String DocURL, String Clinic, String ClinicURL, String Days, String Time,
			String Fee, String Remarks, String ClinicAdd, String ClinicGoogleAdd, String clinicContact, String IsPrime,
			String DocDiscountedFee, String DocFeeDiscount, String Source, String tableName) throws SQLException {

		try {

			String datam = "('" + DocURL + "','" + Clinic + "','" + ClinicURL + "','" + Days + "');";

			System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName
					+ "(DocURL, Clinic, ClinicURL, Days, Time, Fee, Remarks, ClinicAdd, ClinicGoogleAdd, ClinicNumber, IsPrime, DiscountedFee, Discount, Source)  values ('"
					+ DocURL + "','" + Clinic + "','" + ClinicURL + "','" + Days + "','" + Time + "','" + Fee + "','"
					+ Remarks + "','" + ClinicAdd + "','" + ClinicGoogleAdd + "','" + clinicContact + "','" + IsPrime
					+ "','" + DocDiscountedFee + "','" + DocFeeDiscount + "','" + Source + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetAwardsData(String DocURL, String Award, String Membership, String Registration, String Source,
			String tableName) throws SQLException {

		try {

			String datam = "('" + DocURL + "','" + Award + "','" + Membership + "','" + Registration + "');";

			// System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate(
					"INSERT INTO " + tableName + "(DocURL, Award, Membership, Registration, Source)  values ('" + DocURL
							+ "','" + Award + "','" + Membership + "','" + Registration + "','" + Source + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetSpecializationData(String DocURL, String Specialization, String Remarks, String Source,
			String tableName) throws SQLException {

		try {

			String datam = "('" + DocURL + "','" + Specialization + "','" + Remarks + "','" + Source + "');";

			// System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName + "(DocURL, Specialization, Remarks, Source)  values ('"
					+ DocURL + "','" + Specialization + "','" + Remarks + "','" + Source + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetEducationData(String DocURL, String EduYear, String EduIns, String EduQual, String ExpYear,
			String ExpLoc, String ExpRole, String Remarks, String Source, String tableName) throws SQLException {

		try {

			String datam = "('" + DocURL + "','" + EduYear + "','" + EduIns + "','" + EduQual + "');";

			System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName
					+ "(DocURL, EduYear, EduIns, EduQual, ExpYear, ExpLoc, ExpRole, Remarks, Source)  values ('"
					+ DocURL + "','" + EduYear + "','" + EduIns + "','" + EduQual + "','" + ExpYear + "','" + ExpLoc
					+ "','" + ExpRole + "','" + Remarks + "','" + Source + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetClinicsTimeData(String DocURL, String Day, String Time, String Remarks, String Source,
			String tableName) throws SQLException {

		try {

			String datam = "('" + DocURL + "','" + Day + "','" + Remarks + "','" + Source + "');";

			// System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName + "(DocURL, Day, Time, Remarks, Source)  values ('" + DocURL
					+ "','" + Day + "','" + Time + "','" + Remarks + "','" + Source + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public void SetLinkedDoctorData(String DocURL, String DocName, String URL, String Remarks, String Source,
			String tableName) throws SQLException {

		try {

			String datam = "('" + DocURL + "','" + DocName + "','" + Remarks + "','" + Source + "');";

			// System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {

			stmt.executeUpdate("INSERT INTO " + tableName + "(DocURL, Specialization, URL, Remarks, Source)  values ('"
					+ DocURL + "','" + DocName + "','" + URL + "','" + Remarks + "','" + Source + "');");

		} catch (Exception e) {
			e.printStackTrace();
		}

		tear();

	}

	public int UpdateQuery(String DBQuery) throws Exception {

		int i = 0;
		try {
			i = stmt.executeUpdate(DBQuery);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return i;
	}

	public void tear() {
		con = null;
	}

}
