package adstech.vn.com.logcallStatistics.pojo;

public class DealWon {
	private Integer portalId;
	private String objectType;
	private String objectTypeId;
	private Long objectId;
	private Property properties;
	public Integer getPortalId() {
		return portalId;
	}
	public void setPortalId(Integer portalId) {
		this.portalId = portalId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getObjectTypeId() {
		return objectTypeId;
	}
	public void setObjectTypeId(String objectTypeId) {
		this.objectTypeId = objectTypeId;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Property getProperties() {
		return properties;
	}
	public void setProperties(Property properties) {
		this.properties = properties;
	}
}
