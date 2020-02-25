package adstech.vn.com.logcallStatistics.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Count {
	private Integer id;
	private String name;
	private Integer email;
	private Integer call;
	private Integer meeting;
	private Integer task;
	private Integer score;
	private Integer dealWon;
	private String mailSale;
	private String dateRequest;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	public Count() {
		
		this.id = 0;
		this.email = 0;
		this.call = 0;
		this.meeting = 0;
		this.task = 0;
		this.score = 0;
		this.dealWon = 0;
		
		
	}
	
	public String getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(String dateRequest) {
		this.dateRequest = dateRequest;
	}

	public Integer getDealWon() {
		return dealWon;
	}
	public void setDealWon(Integer dealWon) {
		this.dealWon = dealWon;
	}
	public String getMailSale() {
		return mailSale;
	}
	public void setMailSale(String mailSale) {
		this.mailSale = mailSale;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getEmail() {
		return email;
	}
	public void setEmail(Integer email) {
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCall() {
		return call;
	}
	public void setCall(Integer call) {
		this.call = call;
	}
	public Integer getMeeting() {
		return meeting;
	}
	public void setMeeting(Integer meeting) {
		this.meeting = meeting;
	}
	public Integer getTask() {
		return task;
	}
	public void setTask(Integer task) {
		this.task = task;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public int calculateScore() {
		this.score= (this.call*2 + this.task*8 + this.meeting*10 + this.email*1 + this.dealWon*20) ;
		return score;
	}
	
}
