package adstech.vn.com.logcallStatistics.pojo;

import java.util.List;
import java.util.Map;

public class DealStage {
	private List<Map<String, Object>> versions;
	private String value;
	private Long timestamp;
	private String sourceId;
	public List<Map<String, Object>> getVersions() {
		return versions;
	}
	public void setVersions(List<Map<String, Object>> versions) {
		this.versions = versions;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
}
