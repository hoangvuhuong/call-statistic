package adstech.vn.com.logcallStatistics.pojo;

public class Call {
	private String direction;
	private String cid_name;
	private String source;
	private String destination;
	private String recording_file;
	private String start;
	private int tta;
	private long duration;
	private double pdd;
	private double mos;
	private String status;
	
	
	public Call() {
		
	}
	public Call(String direction, String cid_name, String source, String destination, String recording_file,
			String start, int tta, long duration, double pdd, double mos, String status) {
		super();
		this.direction = direction;
		this.cid_name = cid_name;
		this.source = source;
		this.destination = destination;
		this.recording_file = recording_file;
		this.start = start;
		this.tta = tta;
		this.duration = duration;
		this.pdd = pdd;
		this.mos = mos;
		this.status = status;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCid_name() {
		return cid_name;
	}
	public void setCid_name(String cid_name) {
		this.cid_name = cid_name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getRecording_file() {
		return recording_file;
	}
	public void setRecording_file(String recording_file) {
		this.recording_file = recording_file;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public int getTta() {
		return tta;
	}
	public void setTta(int tta) {
		this.tta = tta;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public double getPdd() {
		return pdd;
	}
	public void setPdd(double pdd) {
		this.pdd = pdd;
	}
	public double getMos() {
		return mos;
	}
	public void setMos(double mos) {
		this.mos = mos;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
