package adstech.vn.com.logcallStatistics.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import adstech.vn.com.logcallStatistics.contract.ResponseContract;
import adstech.vn.com.logcallStatistics.model.Department;
import adstech.vn.com.logcallStatistics.model.Group;
import adstech.vn.com.logcallStatistics.util.CommonConstants;

@Service
public class ErpService implements IErpService {
	@Value("${token}")
	private String token;
	@Value("${link_erp}")
	private String link_erp;

	@Override
	public ResponseContract<?> getDepartment() {
		try {
		String getDepartment = link_erp + "/departments";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.setBearerAuth(token);
		
		HttpEntity<?> request = new HttpEntity<>(headers);
		JsonNode result = restTemplate.exchange(getDepartment,HttpMethod.GET,request, JsonNode.class).getBody();
		List<Department> listDepartments = new ArrayList<Department>();
		JsonNode response = result.get("data");
		for(JsonNode node : response) {
			Department deparment = new Department();
			deparment.setId(node.get("term_id").asInt());
			deparment.setName(node.get("term_name").asText());
			listDepartments.add(deparment);
		}
		return new ResponseContract<List<Department>>(CommonConstants.RESPONSE_CODE_SUCCESS, "", listDepartments);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, e.getMessage(),null);
		}
	}

	@Override
	public ResponseContract<?> getGroup(Integer departmentId) {
		try {
			String getGroup = link_erp + "/groups";
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			
			headers.setBearerAuth(token);
			
			HttpEntity<?> request = new HttpEntity<>(headers);
			JsonNode result = restTemplate.exchange(getGroup,HttpMethod.GET,request, JsonNode.class).getBody();
			List<Group> listGroups = new ArrayList<Group>();
			JsonNode response = result.get("data");
			for(JsonNode node : response) {
				if(departmentId == node.get("term_parent").asInt()) {
				Group group = new Group();
				
				group.setId(node.get("term_id").asInt());
				group.setName(node.get("term_name").asText());
				group.setDepartmentId(node.get("term_parent").asInt());
				listGroups.add(group);
				}
			}
			return new ResponseContract<List<Group>>(CommonConstants.RESPONSE_CODE_SUCCESS, "", listGroups);
			}catch (Exception e) {
				e.printStackTrace();
				return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, e.getMessage(),null);
			}
	}

	@Override
	public ResponseContract<?> getStaff(Integer departmentId,Integer groupId) {
		// TODO Auto-generated method stub
		return null;
	}

}
