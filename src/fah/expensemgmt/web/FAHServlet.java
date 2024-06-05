package fah.expensemgmt.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fah.expensemgmt.dao.FAHDAO;
import fah.expensemgmt.helper.Helper;
import fah.expensemgmt.model.ChartDataByCategory;
import fah.expensemgmt.model.ChartDataByIncExp;
import fah.expensemgmt.model.ChartDataByPerson;
import fah.expensemgmt.model.MainCategory;
import fah.expensemgmt.model.Record;
import fah.expensemgmt.model.ResponsiblePerson;
import fah.expensemgmt.model.SubCategory;
import fah.expensemgmt.model.User;

@WebServlet(urlPatterns = { "/login", "/addRecord", "/transactions", "/dashboard", "/logout", "/loadRecord",
		"/deleteRecord", "/updateRecord" })
public class FAHServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FAHDAO userDAO;

	public void init() {
		userDAO = new FAHDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/login":
				login(request, response);
				break;
			case "/addRecord":
				addRecord(request, response);
				break;
			case "/transactions":
				loadTransactions(request, response);
				break;
			case "/dashboard":
				loadDashboard(request, response);
				break;
			case "/loadRecord":
				loadRecord(request, response);
				break;
			case "/deleteRecord":
				deleteRecord(request, response);
				break;
			case "/updateRecord":
				updateRecord(request, response);
				break;
			case "/logout":
				logout(request, response);
				break;
			default:
				// listUser(request, response);
				// response.sendRedirect("login.jsp");
				break;

			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void updateRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			Record record = new Record();
			record.setExpenseDt(Helper.handledate(request.getParameter("edate")));
			record.setIncomeExpense(Helper.handlestring(request.getParameter("incexp")));
			record.setDescr(Helper.handlestring(request.getParameter("desc")));
			record.setMainCategory(Helper.handlestring(request.getParameter("category")));
			record.setSubCategory(Helper.handlestring(request.getParameter("subcategory")));
			record.setAmount(Helper.handlefloat(request.getParameter("amount")));
			record.setResponsiblePerson(Helper.handlestring(request.getParameter("comments")));
			record.setUpdatedBy(Helper.loggedInUser(request));
			record.setId(Helper.handleint(request.getParameter("id")));

			boolean result = userDAO.updateRecord(record);
			if(result) {
				request.setAttribute("result", result);
				request.setAttribute("msg", "Entry updated successfully!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading expenses");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("transactions");
		dispatcher.forward(request, response);

	}

	private void deleteRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Helper.handleint(request.getParameter("rid"));
			if (id != 0) {
				boolean result = userDAO.deleteRecord(id);
				request.setAttribute("result", result);
				request.setAttribute("msg", "Entry deleted successfully!");
			}

			Record conditions = new Record();
			conditions.setFromDate(Helper.handledate(request.getParameter("fdate")));
			conditions.setToDate(Helper.handledate(request.getParameter("tdate")));
			conditions.setIncomeExpense(Helper.handlestring(request.getParameter("incexp")));
			conditions.setMainCategory(Helper.handlestring(request.getParameter("category")));
			conditions.setSubCategory(Helper.handlestring(request.getParameter("subcategory")));
			conditions.setResponsiblePerson(Helper.handlestring(request.getParameter("comments")));
			request.setAttribute("conditions", conditions);
			

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading expenses");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("transactions");
		dispatcher.forward(request, response);
	}

	private void loadRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Preload DropDown Values
		try {
			List<MainCategory> mainCategories = userDAO.selectAllMainCategory();
			List<SubCategory> subCategories = userDAO.selectAllSubCategory();
			List<ResponsiblePerson> responsiblePersons = userDAO.selectAllResponsiblePersons();

			request.setAttribute("mainCategories", mainCategories);
			request.setAttribute("subCategories", subCategories);
			request.setAttribute("responsiblePersons", responsiblePersons);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading categories");
		}

		try {
			int id = Helper.handleint(request.getParameter("id"));
			String action = Helper.handlestring(request.getParameter("action"));

			if (id != 0) {

				if (action.equals("edit")) {
					Record record = userDAO.selectRecordByID(id);
					request.setAttribute("record", record);
				}
			}

			Record conditions = new Record();
			conditions.setFromDate(Helper.handledate(request.getParameter("fdate")));
			conditions.setToDate(Helper.handledate(request.getParameter("tdate")));
			conditions.setIncomeExpense(Helper.handlestring(request.getParameter("incexp")));
			conditions.setMainCategory(Helper.handlestring(request.getParameter("category")));
			conditions.setSubCategory(Helper.handlestring(request.getParameter("subcategory")));
			conditions.setResponsiblePerson(Helper.handlestring(request.getParameter("comments")));
			request.setAttribute("conditions", conditions);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading expenses");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("record.jsp");
		dispatcher.forward(request, response);

	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("Logged out successfully");
	}

	public void loadTransactions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Preload DropDown Values
		try {
			List<MainCategory> mainCategories = userDAO.selectAllMainCategory();
			List<SubCategory> subCategories = userDAO.selectAllSubCategory();
			List<ResponsiblePerson> responsiblePersons = userDAO.selectAllResponsiblePersons();

			request.setAttribute("mainCategories", mainCategories);
			request.setAttribute("subCategories", subCategories);
			request.setAttribute("responsiblePersons", responsiblePersons);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading categories");
		}

		
			Record conditions = new Record();
			conditions.setFromDate(Helper.handledate(request.getParameter("fdate")));
			conditions.setToDate(Helper.handledate(request.getParameter("tdate")));

			if(request.getParameter("landedFromAddRecord")==null || request.getParameter("landedFromAddRecord").trim().length()==0) {
				conditions.setIncomeExpense(Helper.handlestring(request.getParameter("incexp")));
				conditions.setMainCategory(Helper.handlestring(request.getParameter("category")));
				conditions.setSubCategory(Helper.handlestring(request.getParameter("subcategory")));
				conditions.setResponsiblePerson(Helper.handlestring(request.getParameter("comments")));
			}
			
			if (conditions.getFromDate() == null && conditions.getToDate() == null && conditions.getIncomeExpense() == null
					&& conditions.getMainCategory() == null && conditions.getSubCategory() == null
					&& conditions.getComments() == null) {

				Calendar c = Calendar.getInstance();   // this takes current date
			    c.set(Calendar.DAY_OF_MONTH, 1);
			    c.set(Calendar.HOUR_OF_DAY, 0);
			    c.set(Calendar.MINUTE, 0);
			    c.set(Calendar.SECOND, 0);
			    
				java.sql.Date startOfMonth = new java.sql.Date(c.getTimeInMillis());
				conditions.setFromDate(startOfMonth);
			}
			

		try {
			List<Record> expenses = userDAO.selectAllExpenses(conditions);
			request.setAttribute("records", expenses);
			request.setAttribute("conditions", conditions);
			request.setAttribute("landedFromAddRecord", request.getParameter("landedFromAddRecord"));

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading expenses");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
		dispatcher.forward(request, response);
	}

	public void loadDashboard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Preload DropDown Values
		try {
			List<MainCategory> mainCategories = userDAO.selectAllMainCategory();
			List<SubCategory> subCategories = userDAO.selectAllSubCategory();
			List<ResponsiblePerson> responsiblePersons = userDAO.selectAllResponsiblePersons();

			request.setAttribute("mainCategories", mainCategories);
			request.setAttribute("subCategories", subCategories);
			request.setAttribute("responsiblePersons", responsiblePersons);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading categories");
		}

		Record conditions = new Record();
		conditions.setFromDate(Helper.handledate(request.getParameter("fdate")));
		conditions.setToDate(Helper.handledate(request.getParameter("tdate")));
		conditions.setIncomeExpense(Helper.handlestring(request.getParameter("incexp")));
		conditions.setMainCategory(Helper.handlestring(request.getParameter("category")));
		conditions.setSubCategory(Helper.handlestring(request.getParameter("subcategory")));
		conditions.setResponsiblePerson(Helper.handlestring(request.getParameter("comments")));

		if (conditions.getFromDate() == null && conditions.getToDate() == null && conditions.getIncomeExpense() == null
				&& conditions.getMainCategory() == null && conditions.getSubCategory() == null
				&& conditions.getComments() == null) {

			Calendar c = Calendar.getInstance();   // this takes current date
		    c.set(Calendar.DAY_OF_MONTH, 1);
		    c.set(Calendar.HOUR_OF_DAY, 0);
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    
			java.sql.Date startOfMonth = new java.sql.Date(c.getTimeInMillis());
			conditions.setFromDate(startOfMonth);
		}
		

		try {
			List<Record> expenses = userDAO.selectAllExpenses(conditions);
			List<ChartDataByCategory> chartbycategory = userDAO.getChartDataByCategory(conditions);
			List<ChartDataByIncExp> chartbyincexp = userDAO.getChartDataByIncExp(conditions);
			List<ChartDataByPerson> chartbyperson = userDAO.getChartDataByRespPerson(conditions);
			
			
			  GsonBuilder builder = new GsonBuilder(); 
		      builder.setPrettyPrinting(); 
		      Gson gson = builder.create();
		      
		      String chartbycategoryjson = gson.toJson(chartbycategory); 
		     // System.out.println(chartbycategoryjson); 
		      
		      String chartbyincexpjson = gson.toJson(chartbyincexp); 
		     // System.out.println(chartbyincexpjson);
		      
		      String chartbypersonjson = gson.toJson(chartbyperson); 
		     // System.out.println(chartbypersonjson);
		      
			
		    request.setAttribute("chartbycategoryjson", chartbycategoryjson);
		    request.setAttribute("chartbyincexpjson", chartbyincexpjson);
		    request.setAttribute("chartbypersonjson", chartbypersonjson);
		    
			request.setAttribute("chartbycategory", chartbycategory);
			request.setAttribute("chartbyincexp", chartbyincexp);
			request.setAttribute("chartbyperson", chartbyperson);
			
			request.setAttribute("records", expenses);
			request.setAttribute("conditions", conditions);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error occured while loading expenses");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
		dispatcher.forward(request, response);
	}

	private void addRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Record record = new Record();
		record.setExpenseDt(Helper.handledate(request.getParameter("edate")));
		record.setIncomeExpense(Helper.handlestring(request.getParameter("incexp")));
		record.setDescr(Helper.handlestring(request.getParameter("desc")));
		record.setMainCategory(Helper.handlestring(request.getParameter("category")));
		record.setSubCategory(Helper.handlestring(request.getParameter("subcategory")));
		record.setAmount(Helper.handlefloat(request.getParameter("amount")));
		record.setResponsiblePerson(Helper.handlestring(request.getParameter("comments")));
		// record.setInactiveSw(Helper.handledate(request.getParameter("inactivesw")));
		record.setCreatedDt(Helper.getCurrentTime());
		record.setCreatedBy(Helper.loggedInUser(request));
		record.setUpdatedDt(null);
		record.setUpdatedBy(null);
		record.setComments(null);

		try {
			
			//Check if record does not already exists
			if(!userDAO.checkIfDuplicate(record)) {
				userDAO.insertRecord(record);
			}else {
				
				Record conditions = new Record();
				conditions.setFromDate(Helper.handledate(request.getParameter("fdate")));
				conditions.setToDate(Helper.handledate(request.getParameter("tdate")));
				conditions.setIncomeExpense(Helper.handlestring(request.getParameter("incexp")));
				conditions.setMainCategory(Helper.handlestring(request.getParameter("category")));
				conditions.setSubCategory(Helper.handlestring(request.getParameter("subcategory")));
				conditions.setResponsiblePerson(Helper.handlestring(request.getParameter("comments")));
				conditions.setDescr(Helper.handlestring(request.getParameter("desc")));
				request.setAttribute("record", record);
				
				request.setAttribute("error", "An entry already exists with the same parameters - Record addition failed !");
				RequestDispatcher dispatcher = request.getRequestDispatcher("loadRecord");
				dispatcher.forward(request, response);
				return;
			}
			
			request.setAttribute("msg", "Entry added successfully!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("transactions");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("loadRecord");
			request.setAttribute("error", "Error occured while inserting record");
			dispatcher.forward(request, response);
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String username = request.getParameter("txtEmail");
		String password = request.getParameter("txtPassword");

		User user = userDAO.verifyLogin(username, password);
		if (user != null) {
			String name = user.getName() == null ? user.getUsername() : user.getName();
			request.getSession().setAttribute("loggedinuser", name);
			request.getSession().setAttribute("loggedinuserrole", user.getRoleName());

			RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			request.setAttribute("error", "User not found in the system");
			dispatcher.forward(request, response);
		}
	}

}
