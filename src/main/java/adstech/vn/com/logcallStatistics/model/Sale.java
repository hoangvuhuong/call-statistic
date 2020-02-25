package adstech.vn.com.logcallStatistics.model;

public class Sale {
	private Integer id;
	private String mayNhanh;
	private String name;
	private String emailSale;
	private int ownerId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMayNhanh() {
		return mayNhanh;
	}
	public void setMayNhanh(String mayNhanh) {
		this.mayNhanh = mayNhanh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailSale() {
		return emailSale;
	}
	public void setEmailSale(String emailSale) {
		this.emailSale = emailSale;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
}