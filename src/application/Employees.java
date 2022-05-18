package application;

public class Employees {
	private int Eid;
	private String Ename;
	private String Eaddress;
	private int Ephone;
	private double Esalary;
	
	public Employees(int Eid, String Ename, String Eaddress, int Ephone, double Esalary) {
		this.Eid = Eid;
		this.Ename = Ename;
		this.Eaddress = Eaddress;
		this.Ephone = Ephone;
		this.Esalary = Esalary;
	}

	public int getEid() {
		return Eid;
	}

	public void setEid(int eid) {
		Eid = eid;
	}

	public String getEname() {
		return Ename;
	}

	public void setEname(String ename) {
		Ename = ename;
	}

	public String getEaddress() {
		return Eaddress;
	}

	public void setEaddress(String eaddress) {
		Eaddress = eaddress;
	}

	public int getEphone() {
		return Ephone;
	}

	public void setEphone(int ephone) {
		Ephone = ephone;
	}

	public double getEsalary() {
		return Esalary;
	}

	public void setEsalary(double esalary) {
		Esalary = esalary;
	}
	
	
	
}
