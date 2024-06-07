package db;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.DashboardDetails;

public class DashboardDAO {
    
    // Method to retrieve all Dashboard records
    public List<DashboardDetails> getAllDashboards() {
    	
        List<DashboardDetails> dashboardDetails = new ArrayList<>();
        
        Connection conn = ConnectionManager.getConnection();
        
        if (conn != null) {
            try {
                String sql = "SELECT * FROM rls_dashboard_mapping";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                	DashboardDetails dashboardDetailsObj = new DashboardDetails();
                    dashboardDetailsObj.setDashboardKey(rs.getString("DB_KEY"));
                    dashboardDetailsObj.setDashboardName(rs.getString("DB_NAME"));
                    dashboardDetails.add(dashboardDetailsObj);
                }
                
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dashboardDetails;
    }
    
    
    
    //check if record(dbName, pfid) exists
    //if record exists return true
    // else return false
    

    // Method to retrieve a Dashboard record by PFID
    public Dashboard getDashboardByPFID(String PFID) {
        Dashboard dashboard = null;
        Connection conn = ConnectionManager.getConnection();
        
        if (conn != null) {
            try {
                String sql = "SELECT * FROM Dashboard WHERE PFID = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, PFID);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    String dashboardName = rs.getString("Dashboardname");
                    Date date = rs.getDate("date");
                    dashboard = new Dashboard(PFID, dashboardName, date);
                }
                
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dashboard;
    }

    // Method to insert a new Dashboard record
    public boolean inserData(Dashboard dashboard) {
        boolean isInserted = false;
        Connection conn = ConnectionManager.getConnection();
        
        if (conn != null) {
            try {
                String sql = "INSERT INTO RLS_DATA_USERS (PFID, Dashboard_name, expiry_date) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dashboard.getPFID());
                pstmt.setString(2, dashboard.getDashboardName());
                pstmt.setDate(3, new Date(dashboard.getDate().getTime()));
                
                isInserted = pstmt.executeUpdate() > 0;
                
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return isInserted;
    }
    
    public boolean dashboardMappingExists(Dashboard dashboard) {
        //System.out.println("entered Revoke");
        Connection conn = ConnectionManager.getConnection();
        boolean grantExist = false;
        //System.out.println("conn status: " + conn);
        
        if (conn != null) {
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            try {
                String sql = "SELECT COUNT(*) FROM RLS_DATA_USERS WHERE PFID = ? AND Dashboard_name = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dashboard.getPFID());
                pstmt.setString(2, dashboard.getDashboardName());
                
                
                rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    grantExist = rs.getInt(1) > 0;
                }
                
                System.out.println("Grant existence check: " + rs.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close resources in the finally block to ensure they are released
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return grantExist;
    }
    public void revoke(Dashboard dashboard) throws SQLException {
    	ResultSet rs = null;
    	Connection conn = ConnectionManager.getConnection();
    	PreparedStatement pstmt = null;
    	System.out.println(dashboard.getPFID()+"\n"+dashboard.getDashboardName());
    	String sql="UPDATE rls_data_users SET LIVE_STATUS='N' WHERE PFID = ? AND Dashboard_name = ?";
    	pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, dashboard.getPFID());
        pstmt.setString(2, dashboard.getDashboardName());
        pstmt.executeUpdate();
        
    }

    // Method to update an existing Dashboard record
//    public boolean updateDashboard(Dashboard dashboard) {
//        boolean isUpdated = false;
//        Connection conn = ConnectionManager.getConnection();
//        
//        if (conn != null) {
//            try {
//                String sql = "UPDATE Dashboard SET Dashboardname = ?, date = ? WHERE PFID = ?";
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                pstmt.setString(1, dashboard.getDashboardName());
//                pstmt.setDate(2, new Date(dashboard.getDate().getTime()));
//                pstmt.setString(3, dashboard.getPFID());
//                
//                isUpdated = pstmt.executeUpdate() > 0;
//                
//                pstmt.close();
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        
//        return isUpdated;
//    }

    // Method to delete a Dashboard record by PFID
//    public boolean deleteDashboard(String PFID) {
//        boolean isDeleted = false;
//        Connection conn = ConnectionManager.getConnection();
//        
//        if (conn != null) {
//            try {
//                String sql = "DELETE FROM Dashboard WHERE PFID = ?";
//                PreparedStatement pstmt = conn.prepareStatement(sql);
//                pstmt.setString(1, PFID);
//                
//                isDeleted = pstmt.executeUpdate() > 0;
//                
//                pstmt.close();
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        
//        return isDeleted;
//    }
}
