package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.Dashboard;
import db.DashboardDAO;

/**
 * Servlet implementation class Revoke
 */
@WebServlet("/Revoke")
public class Revoke extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Revoke() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] pfids = request.getParameterValues("PFID");
        String[] dashboardNames = request.getParameterValues("dashboardName");
        DashboardDAO dao = new DashboardDAO();

        boolean success = true;

        if (pfids != null && dashboardNames != null) {
        	String ipAddress = request.getRemoteAddr();
            for (String pfid : pfids) {
                for (String dashboardName : dashboardNames) {
                    Dashboard dashboard = new Dashboard();
                    dashboard.setPFID(pfid);
                    dashboard.setDashboardName(dashboardName);

                    boolean exists = dao.dashboardMappingExists(dashboard);

                    if (exists) {
                        try {
                            dao.revoke(dashboard);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            success = false;
                        }
                    } else {
                        success = false;
                    }
                }
                System.out.println("IP Address submitted: " + ipAddress);
            }
        } else {
            success = false;
        }

        // Set the response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Prepare JSON response
        PrintWriter out = response.getWriter();
        out.print("{\"status\":\"" + (success ? "success" : "error") + "\"}");
        out.flush();
    }
}
