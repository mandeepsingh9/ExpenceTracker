package fah.expensemgmt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fah.expensemgmt.model.ChartDataByCategory;
import fah.expensemgmt.model.ChartDataByIncExp;
import fah.expensemgmt.model.ChartDataByPerson;
import fah.expensemgmt.model.MainCategory;
import fah.expensemgmt.model.Record;
import fah.expensemgmt.model.ResponsiblePerson;
import fah.expensemgmt.model.SubCategory;
import fah.expensemgmt.model.User;

public class FAHDAO {
	private String jdbcURL = Messages.getString("fah.jdbcurl");
	private String jdbcUsername = Messages.getString("fah.jdbcusername");
	private String jdbcPassword = Messages.getString("fah.jdbcpass");

	private static final String VERIFY_LOGIN = Messages.getString("fah.sql.verifylogin");
	private static final String INSERT_RECORD = Messages.getString("fah.sql.insertrecord");
	private static final String FETCH_ALL_RECORDS = Messages.getString("fah.sql.fetchallrecords");
	private static final String FETCH_MAIN_CATEGORY = Messages.getString("fah.sql.fetchmaincat");
	private static final String FETCH_SUB_CATEGORY = Messages.getString("fah.sql.fetchsubcat");
	private static final String FETCH_RESP_PERSON = Messages.getString("fah.sql.fetchrespper");
	private static final String FETCH_RECORD_BY_ID = Messages.getString("fah.sql.fetchrecordbyid");
	private static final String DELETE_RECORD_BY_ID = Messages.getString("fah.sql.deleterecordbyid");
	private static final String UPDATE_RECORD_BY_ID = Messages.getString("fah.sql.updaterecordbyid");
	private static final String FETCH_CHARTBY_CATEGORY = Messages.getString("fah.sql.chartbycat");
	private static final String FETCH_CHARTBY_INCEXP = Messages.getString("fah.sql.chartbyincexp");
	private static final String FETCH_CHARTBY_RESPPERSON = Messages.getString("fah.sql.chartbyrespper");
	private static final String CHECK_DUPLICATE_RECORD = Messages.getString("fah.sql.checkduprecord");

	public FAHDAO() {
	}

	protected Connection getConnection() {

		Connection con = null;

		// driver name for mysql
		// String loadDriver = rd.getString("driver");

		Connection connection = null;
		try {
			Class.forName(Messages.getString("fah.jdbc.driver"));
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public User verifyLogin(String username, String password) {
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(VERIFY_LOGIN);) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			//System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				User user = new User();

				String uname = rs.getString("username");
				String name = rs.getString("name");

				user.setUsername(uname);
				user.setName(name);
				user.setRoleName(rs.getString("role_name"));

				if (name != null && name.trim().length() != 0) {
					return user;
				} else if (uname != null && uname.trim().length() != 0) {
					return user;
				}
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return null;
	}

	public void insertRecord(Record record) throws SQLException {
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RECORD)) {
			preparedStatement.setDate(1, record.getExpenseDt());
			preparedStatement.setString(2, record.getIncomeExpense());
			preparedStatement.setString(3, record.getDescr());
			preparedStatement.setString(4, record.getMainCategory());
			preparedStatement.setString(5, record.getSubCategory());
			preparedStatement.setFloat(6, record.getAmount());
			preparedStatement.setString(7, record.getResponsiblePerson());
			preparedStatement.setBoolean(8, record.isInactiveSw());
			preparedStatement.setTimestamp(9, record.getCreatedDt());
			preparedStatement.setString(10, record.getCreatedBy());
			preparedStatement.setTimestamp(11, record.getUpdatedDt());
			preparedStatement.setString(12, record.getUpdatedBy());
			preparedStatement.setString(13, record.getComments());

			//System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public boolean checkIfDuplicate(Record conditions) throws SQLException {

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CHECK_DUPLICATE_RECORD);) {
			preparedStatement.setDate(1, conditions.getExpenseDt());
			preparedStatement.setString(2, conditions.getIncomeExpense());
			preparedStatement.setString(3, conditions.getMainCategory());
			preparedStatement.setString(4, conditions.getSubCategory());
			preparedStatement.setFloat(5, conditions.getAmount());
			preparedStatement.setString(6, conditions.getResponsiblePerson());
			preparedStatement.setString(7, conditions.getDescr());
			//System.out.println(preparedStatement);

			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			int records = 0;
			while (rs.next()) {
				records = rs.getInt(1);
			}

			//System.out.println("Number of records: " + records);
			if (records > 0) {
				return true;
			}
		}
		return false;
	}

	public List<Record> selectAllExpenses(Record conditions) {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Record> records = new ArrayList<>();
		List<String> prepConditions = new ArrayList<String>();

		StringBuffer filters = new StringBuffer(FETCH_ALL_RECORDS);
		int index = 1;
		if (conditions.getFromDate() != null) {
			filters.append(" and e.expense_dt >= ? ");
			prepConditions.add("COND1");
		}

		if (conditions.getToDate() != null) {
			filters.append(" and e.expense_dt <= ? ");
			prepConditions.add("COND2");
		}

		if (conditions.getIncomeExpense() != null) {
			filters.append(" and e.income_expense = ? ");
			prepConditions.add("COND3");
		}

		if (conditions.getMainCategory() != null) {
			filters.append(" and e.main_category = ? ");
			prepConditions.add("COND4");
		}

		if (conditions.getSubCategory() != null) {
			filters.append(" and e.sub_category = ? ");
			prepConditions.add("COND5");
		}

		if (conditions.getResponsiblePerson() != null) {
			filters.append(" and e.responsible_person = ? ");
			prepConditions.add("COND6");
		}

		filters.append(" order by id desc ");

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(filters.toString())) {

			for (String condn : prepConditions) {

				if (condn.equals("COND1")) {
					preparedStatement.setDate(index++, conditions.getFromDate());
				}
				if (condn.equals("COND2")) {
					preparedStatement.setDate(index++, conditions.getToDate());
				}
				if (condn.equals("COND3")) {
					preparedStatement.setString(index++, conditions.getIncomeExpense());
				}
				if (condn.equals("COND4")) {
					preparedStatement.setString(index++, conditions.getMainCategory());
				}
				if (condn.equals("COND5")) {
					preparedStatement.setString(index++, conditions.getSubCategory());
				}
				if (condn.equals("COND6")) {
					preparedStatement.setString(index++, conditions.getResponsiblePerson());
				}
			}

			//System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.

			while (rs.next()) {
				Record record = new Record();
				record.setId(rs.getLong("id"));
				record.setExpenseDt(rs.getDate("expense_dt"));
				record.setIncomeExpense(rs.getString("income_expense"));
				record.setDescr(rs.getString("descr"));
				record.setMainCategory(rs.getString("main_category"));
				record.setSubCategory(rs.getString("sub_category"));
				record.setAmount(rs.getFloat("amount"));
				record.setResponsiblePerson(rs.getString("responsible_person"));
				records.add(record);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return records;
	}

	public List<MainCategory> selectAllMainCategory() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<MainCategory> records = new ArrayList<>();

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FETCH_MAIN_CATEGORY)) {
			//System.out.println(preparedStatement);

			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.

			while (rs.next()) {
				MainCategory record = new MainCategory();
				record.setId(rs.getInt("id"));
				record.setName(rs.getString("cat_name"));
				records.add(record);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return records;
	}

	public List<SubCategory> selectAllSubCategory() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<SubCategory> records = new ArrayList<>();

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FETCH_SUB_CATEGORY)) {
			//System.out.println(preparedStatement);

			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.

			while (rs.next()) {
				SubCategory record = new SubCategory();
				record.setId(rs.getInt("id"));
				record.setMainId(rs.getInt("maincat_id"));
				record.setName(rs.getString("subcat_name"));
				records.add(record);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return records;
	}

	public List<ResponsiblePerson> selectAllResponsiblePersons() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<ResponsiblePerson> records = new ArrayList<>();

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FETCH_RESP_PERSON)) {
			//System.out.println(preparedStatement);

			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.

			while (rs.next()) {
				ResponsiblePerson record = new ResponsiblePerson();
				record.setId(rs.getInt("id"));
				record.setResponsiblePerson(rs.getString("responsible_person"));
				records.add(record);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return records;
	}

	public Record selectRecordByID(int id) {
		// using try-with-resources to avoid closing resources (boiler plate code)
		Record record = new Record();

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FETCH_RECORD_BY_ID)) {
			preparedStatement.setInt(1, id);
			//System.out.println(preparedStatement);

			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.

			while (rs.next()) {

				record.setId(rs.getLong("id"));
				record.setExpenseDt(rs.getDate("expense_dt"));
				record.setIncomeExpense(rs.getString("income_expense"));
				record.setDescr(rs.getString("descr"));
				record.setMainCategory(rs.getString("main_category"));
				record.setSubCategory(rs.getString("sub_category"));
				record.setAmount(rs.getFloat("amount"));
				record.setResponsiblePerson(rs.getString("responsible_person"));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return record;
	}

	public boolean deleteRecord(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_RECORD_BY_ID);) {
			statement.setInt(1, id);
			//System.out.println(statement);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateRecord(Record record) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RECORD_BY_ID);) {
			preparedStatement.setDate(1, record.getExpenseDt());
			preparedStatement.setString(2, record.getIncomeExpense());
			preparedStatement.setString(3, record.getDescr());
			preparedStatement.setString(4, record.getMainCategory());
			preparedStatement.setString(5, record.getSubCategory());
			preparedStatement.setFloat(6, record.getAmount());
			preparedStatement.setString(7, record.getResponsiblePerson());
			preparedStatement.setString(8, record.getUpdatedBy());
			preparedStatement.setLong(9, record.getId());
			//System.out.println(preparedStatement);
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public List<ChartDataByCategory> getChartDataByCategory(Record conditions) {
		
		 //GsonBuilder builder = new GsonBuilder(); 
	     //builder.setPrettyPrinting(); 
	     //Gson gson = builder.create();
	      

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<ChartDataByCategory> records = new ArrayList<>();
		List<String> prepConditions = new ArrayList<String>();

		StringBuffer filters = new StringBuffer(FETCH_CHARTBY_CATEGORY);
		int index = 1;
		if (conditions.getFromDate() != null) {
			filters.append(" and e.expense_dt >= ? ");
			prepConditions.add("COND1");
		}

		if (conditions.getToDate() != null) {
			filters.append(" and e.expense_dt <= ? ");
			prepConditions.add("COND2");
		}

		if (conditions.getIncomeExpense() != null) {
			filters.append(" and e.income_expense = ? ");
			prepConditions.add("COND3");
		}

		if (conditions.getMainCategory() != null) {
			filters.append(" and e.main_category = ? ");
			prepConditions.add("COND4");
		}

		if (conditions.getSubCategory() != null) {
			filters.append(" and e.sub_category = ? ");
			prepConditions.add("COND5");
		}

		if (conditions.getResponsiblePerson() != null) {
			filters.append(" and e.responsible_person = ? ");
			prepConditions.add("COND6");
		}

		filters.append(" group by m.cat_name order by m.cat_name ");

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(filters.toString())) {

			for (String condn : prepConditions) {

				if (condn.equals("COND1")) {
					preparedStatement.setDate(index++, conditions.getFromDate());
				}
				if (condn.equals("COND2")) {
					preparedStatement.setDate(index++, conditions.getToDate());
				}
				if (condn.equals("COND3")) {
					preparedStatement.setString(index++, conditions.getIncomeExpense());
				}
				if (condn.equals("COND4")) {
					preparedStatement.setString(index++, conditions.getMainCategory());
				}
				if (condn.equals("COND5")) {
					preparedStatement.setString(index++, conditions.getSubCategory());
				}
				if (condn.equals("COND6")) {
					preparedStatement.setString(index++, conditions.getResponsiblePerson());
				}
			}

			//System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
					ChartDataByCategory record = new ChartDataByCategory();
					record.setMainCategory(rs.getString("main_category"));
					record.setFinalAmt(rs.getFloat("amount"));
					records.add(record);
				}
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return records;
	}

	public List<ChartDataByIncExp> getChartDataByIncExp(Record conditions) {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<ChartDataByIncExp> records = new ArrayList<>();
		List<String> prepConditions = new ArrayList<String>();

		StringBuffer filters = new StringBuffer(FETCH_CHARTBY_INCEXP);
		int index = 1;
		if (conditions.getFromDate() != null) {
			filters.append(" and e.expense_dt >= ? ");
			prepConditions.add("COND1");
		}

		if (conditions.getToDate() != null) {
			filters.append(" and e.expense_dt <= ? ");
			prepConditions.add("COND2");
		}

		if (conditions.getIncomeExpense() != null) {
			filters.append(" and e.income_expense = ? ");
			prepConditions.add("COND3");
		}

		if (conditions.getMainCategory() != null) {
			filters.append(" and e.main_category = ? ");
			prepConditions.add("COND4");
		}

		if (conditions.getSubCategory() != null) {
			filters.append(" and e.sub_category = ? ");
			prepConditions.add("COND5");
		}

		if (conditions.getResponsiblePerson() != null) {
			filters.append(" and e.responsible_person = ? ");
			prepConditions.add("COND6");
		}

		filters.append("  group by e.income_expense ");

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(filters.toString())) {

			for (String condn : prepConditions) {

				if (condn.equals("COND1")) {
					preparedStatement.setDate(index++, conditions.getFromDate());
				}
				if (condn.equals("COND2")) {
					preparedStatement.setDate(index++, conditions.getToDate());
				}
				if (condn.equals("COND3")) {
					preparedStatement.setString(index++, conditions.getIncomeExpense());
				}
				if (condn.equals("COND4")) {
					preparedStatement.setString(index++, conditions.getMainCategory());
				}
				if (condn.equals("COND5")) {
					preparedStatement.setString(index++, conditions.getSubCategory());
				}
				if (condn.equals("COND6")) {
					preparedStatement.setString(index++, conditions.getResponsiblePerson());
				}
			}

			//System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.

			while (rs.next()) {
				ChartDataByIncExp record = new ChartDataByIncExp();
				record.setIncomeExpense(rs.getString("income_expense"));
				record.setAmount(rs.getFloat("amount"));
				records.add(record);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return records;
	}

	public List<ChartDataByPerson> getChartDataByRespPerson(Record conditions) {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<ChartDataByPerson> records = new ArrayList<>();
		List<String> prepConditions = new ArrayList<String>();

		StringBuffer filters = new StringBuffer(FETCH_CHARTBY_RESPPERSON);
		int index = 1;
		if (conditions.getFromDate() != null) {
			filters.append(" and e.expense_dt >= ? ");
			prepConditions.add("COND1");
		}

		if (conditions.getToDate() != null) {
			filters.append(" and e.expense_dt <= ? ");
			prepConditions.add("COND2");
		}

		if (conditions.getIncomeExpense() != null) {
			filters.append(" and e.income_expense = ? ");
			prepConditions.add("COND3");
		}

		if (conditions.getMainCategory() != null) {
			filters.append(" and e.main_category = ? ");
			prepConditions.add("COND4");
		}

		if (conditions.getSubCategory() != null) {
			filters.append(" and e.sub_category = ? ");
			prepConditions.add("COND5");
		}

		if (conditions.getResponsiblePerson() != null) {
			filters.append(" and e.responsible_person = ? ");
			prepConditions.add("COND6");
		}

		filters.append(" group by c.responsible_person ");

		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(filters.toString())) {

			for (String condn : prepConditions) {

				if (condn.equals("COND1")) {
					preparedStatement.setDate(index++, conditions.getFromDate());
				}
				if (condn.equals("COND2")) {
					preparedStatement.setDate(index++, conditions.getToDate());
				}
				if (condn.equals("COND3")) {
					preparedStatement.setString(index++, conditions.getIncomeExpense());
				}
				if (condn.equals("COND4")) {
					preparedStatement.setString(index++, conditions.getMainCategory());
				}
				if (condn.equals("COND5")) {
					preparedStatement.setString(index++, conditions.getSubCategory());
				}
				if (condn.equals("COND6")) {
					preparedStatement.setString(index++, conditions.getResponsiblePerson());
				}
			}

			//System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.

			while (rs.next()) {
				ChartDataByPerson record = new ChartDataByPerson();
				record.setResponsiblePerson(rs.getString("responsible_person"));
				record.setAmount(rs.getFloat("amount"));
				records.add(record);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return records;
	}
}
