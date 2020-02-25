package adstech.vn.com.logcallStatistics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adstech.vn.com.logcallStatistics.contract.ResponseContract;
import adstech.vn.com.logcallStatistics.model.Department;
import adstech.vn.com.logcallStatistics.model.Group;
import adstech.vn.com.logcallStatistics.model.Staff;
import adstech.vn.com.logcallStatistics.pojo.Call;
import adstech.vn.com.logcallStatistics.pojo.Form;
import adstech.vn.com.logcallStatistics.pojo.Response;
import adstech.vn.com.logcallStatistics.repository.DepartmentRepository;
import adstech.vn.com.logcallStatistics.repository.GroupRepository;
import adstech.vn.com.logcallStatistics.repository.SaleRepository;
import adstech.vn.com.logcallStatistics.repository.StaffRepository;
import adstech.vn.com.logcallStatistics.util.CommonConstants;
@Service
public class CallStatisticsService implements ICallStatisticsService {
	@Autowired
	CallService callService;
	
	@Autowired
	GroupRepository groupRepository;
	
	
	@Autowired
	StaffRepository staffRepository;
	
	@Autowired
	SaleRepository saleRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	
	@Override
	public ResponseContract<?> getAllCall(Form form) {
		List<Staff> staffs = staffRepository.getAll();
		return getCalls(form, staffs);
	}

	@Override
	public ResponseContract<?> getByDepartment(Form form) {

			
			List<Staff> staffs = staffRepository.getByDepartmentId(form.getDepartmentId());
			return getCalls(form, staffs);
		
	}

	@Override
	public ResponseContract<?> getByGroup(Form form) {
		
		List<Staff> staffs = staffRepository.getByIds(form.getDepartmentId(),form.getGroupId()); 
		return getCalls(form, staffs);
	}
	private ResponseContract<?> getCalls(Form form, List<Staff> staffs){
		try {
		Response res = new Response();
		String from = form.getFrom();
		String to = form.getTo();
		List<Call> calls = new ArrayList<Call>();
		List<Call> callsResponse = new ArrayList<Call>();
		int pickUpCall =0;
		long countTime = 0;
		Map<String, Staff> maps = new HashMap<String, Staff>();
		for(Staff staff : staffs) {
			String email = staff.getEmail().trim();
			String mayNhanh = saleRepository.getMayNhanhByEmail(email);
			maps.put(mayNhanh.trim(), staff);
			List<Call> ccallsSource = callService.getListCcall(from, to,mayNhanh,"source");
			calls.addAll(ccallsSource);
			List<Call> ccallsDes = callService.getListCcall(from, to,mayNhanh,"destination");
			if(ccallsDes !=null && !ccallsDes.isEmpty()) {
				calls.addAll(ccallsDes);
			}
		}
		for(Call call : calls) {
			Call resCall = new Call();
			if(maps.containsKey(call.getSource().trim())) {
				resCall.setCid_name(maps.get(call.getSource()).getName());
				resCall.setDestination(call.getDestination());
				resCall.setDirection(call.getDirection());
				resCall.setDuration(call.getDuration());
				resCall.setMos(call.getMos());
				resCall.setPdd(call.getPdd());
				resCall.setRecording_file(call.getRecording_file());
				resCall.setSource(call.getSource());
				resCall.setStart(call.getStart());
				resCall.setStatus(call.getStatus());
				resCall.setTta(call.getTta());
				if(!call.getRecording_file().equals("") && call.getRecording_file() != null && !call.getRecording_file().isEmpty()) {
				pickUpCall++;
				}
				countTime += call.getDuration();
				callsResponse.add(resCall);
			}
			else if( maps.containsKey(call.getDestination().trim())) {
				resCall.setCid_name(maps.get(call.getDestination()).getName());
				resCall.setDestination(call.getDestination());
				resCall.setDirection(call.getDirection());
				resCall.setDuration(call.getDuration());
				resCall.setMos(call.getMos());
				resCall.setPdd(call.getPdd());
				resCall.setRecording_file(call.getRecording_file());
				resCall.setSource(call.getSource());
				resCall.setStart(call.getStart());
				resCall.setStatus(call.getStatus());
				resCall.setTta(call.getTta());
				if(!call.getRecording_file().equals("") && call.getRecording_file() != null && !call.getRecording_file().isEmpty()) {
				pickUpCall++;
				}
				countTime += call.getDuration();
				callsResponse.add(resCall);
			}

			
		
		}
		
		res.setCalls(callsResponse);
		res.setNumberCall(callsResponse.size());
		res.setPickUpCall(pickUpCall);
		long averageTime =0;
		if(pickUpCall != 0)
		averageTime= countTime/pickUpCall;
		res.setAverageTime(averageTime);
		return new ResponseContract<Response>("200", CommonConstants.RESPONSE_CODE_SUCCESS, res);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("", CommonConstants.RESPONSE_CODE_FAIL, null);
		}
	}
//	private ResponseContract<?> getCall(Form form, List<Staff> staffs){
//		try {
//			Response res = new Response();
//			String from = form.getFrom();
//			String to = form.getTo();
//			
//			List<Call> calls = callService.getListCcall(from, to);
//			List<Call> callsResponse = new ArrayList<Call>();
//			int pickUpCall =0;
//			long countTime = 0;
//			for(Staff staff : staffs) {
//				String email = staff.getEmail().trim();
//				String mayNhanh = saleRepository.getMayNhanhByEmail(email);
//				for(Call call : calls) {
//					Call resCall = new Call();
//					if(call.getSource().trim().equals(mayNhanh) || call.getDestination().trim().equals(mayNhanh)) {
//						resCall.setCid_name(staff.getName());
//						resCall.setDestination(call.getDestination());
//						resCall.setDirection(call.getDirection());
//						resCall.setDuration(call.getDuration());
//						resCall.setMos(call.getMos());
//						resCall.setPdd(call.getPdd());
//						resCall.setRecording_file(call.getRecording_file());
//						resCall.setSource(call.getSource());
//						resCall.setStart(call.getStart());
//						resCall.setStatus(call.getStatus());
//						resCall.setTta(call.getTta());
//						if(!call.getRecording_file().equals("") && call.getRecording_file() != null && !call.getRecording_file().isEmpty()) {
//						pickUpCall++;
//						}
//						countTime += call.getDuration();
//						callsResponse.add(resCall);
//					}
//				}
//			}
//			res.setCalls(callsResponse);
//			res.setNumberCall(callsResponse.size());
//			res.setPickUpCall(pickUpCall);
//			long averageTime =0;
//			if(pickUpCall != 0)
//			averageTime= countTime/pickUpCall;
//			res.setAverageTime(averageTime);
//			return new ResponseContract<Response>("200", CommonConstants.RESPONSE_CODE_SUCCESS, res);
//		}catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseContract<String>("", CommonConstants.RESPONSE_CODE_FAIL, e.getMessage());
//		}
//	}

	@Override
	public ResponseContract<?> getByStaff(Form form) {
		
		List<Staff> staffs = new ArrayList<Staff>();
		Staff staff = staffRepository.getById(form.getStaffId());
		staffs.add(staff);
		return getCalls(form, staffs);
	}
}
