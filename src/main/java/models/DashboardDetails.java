package models;

public class DashboardDetails {
	
	private String dashboardName;
	private String dashboardKey;
	
	public String getDashboardName() {
		return dashboardName;
	}
	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}
	public String getDashboardKey() {
		return dashboardKey;
	}
	public void setDashboardKey(String dashboardKey) {
		this.dashboardKey = dashboardKey;
	}
	
	@Override
	public String toString() {
		return "DashboardDetails [dashboardName=" + dashboardName + ", dashboardKey=" + dashboardKey + "]";
	}
	
	
	
}
