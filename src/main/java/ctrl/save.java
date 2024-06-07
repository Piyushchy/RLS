package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import db.Dashboard;
import db.DashboardDAO;

/**
 * Servlet implementation class save
 */
@WebServlet("/save")
public class save extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public save() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String[] pfids = request.getParameterValues("PFID");
        String[] dashboardNames = request.getParameterValues("dashboardName");
        String accessType = request.getParameter("accessType");

        boolean success = true;

        if (pfids != null && dashboardNames != null) {
            DashboardDAO dao = new DashboardDAO();

            for (String pfid : pfids) {
                for (String dashboardName : dashboardNames) {
                    Dashboard data = new Dashboard();
                    data.setPFID(pfid);
                    data.setDashboardName(dashboardName);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, 1);
                    Date nextYearDate = calendar.getTime();
                    data.setDate(nextYearDate);

                    boolean inserted = dao.inserData(data);
                    if (!inserted) {
                        success = false;
                    }
                }
            }
        } else {
            success = false;
        }

        // Prepare JSON response
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", success ? "success" : "error");
        jsonResponse.put("message", success ? "Data inserted successfully" : "Data insertion failed");

        // Set the response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the JSON response
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}
