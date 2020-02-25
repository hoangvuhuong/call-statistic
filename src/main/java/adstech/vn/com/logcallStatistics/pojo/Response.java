package adstech.vn.com.logcallStatistics.pojo;

import java.util.List;



public class Response {
	private int numberCall;
	private int pickUpCall;
	private Long  averageTime;
	private List<Call> calls;
	public int getNumberCall() {
		return numberCall;
	}
	public void setNumberCall(int numberCall) {
		this.numberCall = numberCall;
	}
	public int getPickUpCall() {
		return pickUpCall;
	}
	public void setPickUpCall(int pickUpCall) {
		this.pickUpCall = pickUpCall;
	}
	public Long getAverageTime() {
		return averageTime;
	}
	public void setAverageTime(Long averageTime) {
		this.averageTime = averageTime;
	}
	public List<Call> getCalls() {
		return calls;
	}
	public void setCalls(List<Call> calls) {
		this.calls = calls;
	}
	
}
