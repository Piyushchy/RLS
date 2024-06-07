package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DashboardDAO;
import models.DashboardDetails;

/**
 * Servlet implementation class fetchDashboardDetails
 */
@WebServlet("/fetchDashboardDetails")
public class fetchDashboardDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fetchDashboardDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DashboardDAO dao = new DashboardDAO();
		List<DashboardDetails> dashboardDetails = dao.getAllDashboards();
		
		JSONArray jsonArray = new JSONArray();
        for (DashboardDetails dashboardDetail : dashboardDetails) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dashboardKey", dashboardDetail.getDashboardKey());
            jsonObject.put("dashboardName", dashboardDetail.getDashboardName());
            jsonArray.put(jsonObject);
        }

        // Set response content type to application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write JSON data to response
        PrintWriter out = response.getWriter();
        out.print(jsonArray.toString());
        out.flush();

	}

}
