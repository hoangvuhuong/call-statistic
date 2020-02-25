package adstech.vn.com.logcallStatistics.service;

import adstech.vn.com.logcallStatistics.contract.ResponseContract;
import adstech.vn.com.logcallStatistics.pojo.Form;

public interface ICallStatisticsService {
	public ResponseContract<?> getAllCall(Form form);
	
	public ResponseContract<?> getByDepartment(Form form);
	
	public ResponseContract<?> getByGroup(Form form);
	
	public ResponseContract<?> getByStaff(Form form);
}
