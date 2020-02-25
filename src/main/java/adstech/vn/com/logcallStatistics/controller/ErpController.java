package adstech.vn.com.logcallStatistics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import adstech.vn.com.logcallStatistics.contract.ResponseContract;
import adstech.vn.com.logcallStatistics.pojo.DealWon;
import adstech.vn.com.logcallStatistics.service.CountService;
import adstech.vn.com.logcallStatistics.service.IErpService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class ErpController {
	@Autowired
	IErpService erpService;
	@Autowired
	CountService countService;
	@GetMapping("/erp-departments")
	public ResponseContract<?> getDepartments(){
		return erpService.getDepartment();
	}
//	@GetMapping("/getTime")
//	public ResponseContract<?> getGroups(){
//		return countService.tesst();
//	}
	@GetMapping("/test/{timestamp}")
	public ResponseContract<?> test(@PathVariable Long timestamp){
		return countService.salesStatistic(timestamp);
	}
//	@PostMapping("/deal")
//	public ResponseContract<?> getDealWon(@RequestBody DealWon deal){
//		return countService.catchDealWon(deal);
//	}
//	@PostMapping("/task")
//	public ResponseContract<?> getTask(@RequestBody Map<String, Object> mapTask){
//		return countService.catchBaoGia(mapTask);
//	}
}
