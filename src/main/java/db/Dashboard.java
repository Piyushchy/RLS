package db;
import java.util.Date;

public class Dashboard {
    private String PFID;
    private String dashboardName;
    private Date date;
    
    public Dashboard() {
        super();
    }

    public Dashboard(String PFID, String dashboardName, Date date) {
        this.PFID = PFID;
        this.dashboardName = dashboardName;
        this.date = date;
    }

    // Getters and Setters
    public String getPFID() {
        return PFID;
    }

    public void setPFID(String PFID) {
        this.PFID = PFID;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    

	@Override
	public String toString() {
		return "Dashboard [PFID=" + PFID + ", dashboardName=" + dashboardName + ", date=" + date + "]";
	}
    
    
}
