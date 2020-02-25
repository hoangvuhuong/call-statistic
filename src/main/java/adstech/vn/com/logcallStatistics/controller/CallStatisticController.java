package adstech.vn.com.logcallStatistics.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adstech.vn.com.logcallStatistics.contract.ResponseContract;
import adstech.vn.com.logcallStatistics.model.Department;
import adstech.vn.com.logcallStatistics.model.Group;
import adstech.vn.com.logcallStatistics.model.Staff;
import adstech.vn.com.logcallStatistics.pojo.DealWon;
import adstech.vn.com.logcallStatistics.pojo.Form;
import adstech.vn.com.logcallStatistics.repository.DepartmentRepository;
import adstech.vn.com.logcallStatistics.repository.GroupRepository;
import adstech.vn.com.logcallStatistics.repository.StaffRepository;
import adstech.vn.com.logcallStatistics.service.CallStatisticsService;
import adstech.vn.com.logcallStatistics.service.CountService;
import adstech.vn.com.logcallStatistics.util.CommonConstants;

@RestController
@RequestMapping("/statistic")
public class CallStatisticController {
	@Autowired
	CallStatisticsService callStatisticsService;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	CountService countService;

	@PostMapping("/show")
	public ResponseContract<?> getAll(@RequestBody Form form) {
		if (form.getDepartmentId() == null && form.getGroupId() == null && form.getStaffId() == null)
			return callStatisticsService.getAllCall(form);
		else if (form.getDepartmentId() != null && form.getGroupId() == null && form.getStaffId() == null) {
			return callStatisticsService.getByDepartment(form);
		} else if (form.getDepartmentId() != null && form.getGroupId() != null && form.getStaffId() == null)
			return callStatisticsService.getByGroup(form);
		else if (form.getDepartmentId() != null && form.getGroupId() != null && form.getStaffId() != null)
			return callStatisticsService.getByStaff(form);
		else
			return new ResponseContract<String>("Loi", "Nhap sai du lieu", null);

	}

	@GetMapping("/get-departments")
	public ResponseContract<?> getDepartment() {
		try {
			return new ResponseContract<List<Department>>("200", CommonConstants.RESPONSE_CODE_SUCCESS,
					departmentRepository.getAll());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("", CommonConstants.RESPONSE_CODE_FAIL, e.getMessage());
		}
	}

	@GetMapping("/{departmentId}/get-groups")
	public ResponseContract<?> getGroup(@PathVariable Integer departmentId) {
		try {

			return new ResponseContract<List<Group>>("200", CommonConstants.RESPONSE_CODE_SUCCESS,
					groupRepository.getByDepartmentId(departmentId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("", CommonConstants.RESPONSE_CODE_FAIL, e.getMessage());
		}
	}

	@GetMapping("/{departmentId}/{groupId}/get-staffs")
	public ResponseContract<?> getStaffs(@PathVariable("departmentId") Integer departmentId,
			@PathVariable("groupId") Integer groupId) {
		try {
			return new ResponseContract<List<Staff>>("200", CommonConstants.RESPONSE_CODE_SUCCESS,
					staffRepository.getByIds(departmentId, groupId));

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("", CommonConstants.RESPONSE_CODE_FAIL, e.getMessage());
		}
	}

	@PostMapping("/get-count-by-date")
	public ResponseContract<?> getCountByDate(@RequestBody Map<String, Object> date) {
		String dateRequest = (String) date.get("date");
		return countService.getCountByDate(dateRequest);
	}

	@GetMapping("/score/{timestamp}")
	public ResponseContract<?> test(@PathVariable Long timestamp) {
		return countService.salesStatistic(timestamp);
	}

	@PostMapping("/task")
	public ResponseContract<?> getTask(@RequestBody Map<String, Object> mapTask) {
		return countService.catchBaoGia(mapTask);
	}

	@PostMapping("/deal")
	public ResponseContract<?> getDealWon(@RequestBody DealWon deal) {
		return countService.catchDealWon(deal);
	}

	@PostMapping("/get-contact-by-email")
	public ResponseContract<?> getContactByEmail(@RequestBody Map<String, Object> phone) {
		return countService.getContactByPhone((String) phone.get("phone"));
	}
	
	@DeleteMapping("/{mayNhanh}/delete-may-nhanh")
	public ResponseContract<?> deleteByMaynhanh(@PathVariable String mayNhanh){
		return countService.deleteByMayNhanh(mayNhanh);
	}
	
	@PostMapping("/create-sale")
	public ResponseContract<?> createSale(@RequestBody Map<String, Object> mapSale){
		return countService.createSale(mapSale);
	}
	
	@GetMapping("/get-all-sale")
	public ResponseContract<?> getAll(){
		return countService.getAllSale();
	}
	
	@PutMapping("/change-sale")
	public ResponseContract<?> changeSale(@RequestBody Map<String, Object> mapSale){
		return countService.changeSale(mapSale);
	}
}
