package adstech.vn.com.logcallStatistics.service;

import adstech.vn.com.logcallStatistics.contract.ResponseContract;
import adstech.vn.com.logcallStatistics.pojo.Response;

public interface IErpService {
	public ResponseContract<?> getDepartment();
	public ResponseContract<?> getGroup(Integer departmentId);
	public ResponseContract<?> getStaff(Integer departmentId,Integer groupId);
	
}
