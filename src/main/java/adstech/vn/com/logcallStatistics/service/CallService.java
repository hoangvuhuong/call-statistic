package adstech.vn.com.logcallStatistics.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import adstech.vn.com.logcallStatistics.pojo.Call;

@Service
public class CallService {
	private final String urlCCall = "https://api.ccall.vn/cdrs/json";
	@Value("${api_key}")
	private String api_key;
	@Value("${api_secret}")
	private String api_secret;
	private static final Logger LOGGER = LoggerFactory.getLogger(CallService.class);

	public List<Call> getListCcall(String from, String to, String source, String sourceOrDes) {
		try {

			List<Call> listCall = new ArrayList<Call>();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			Map<String, String> date_range = new HashMap<String, String>();
			date_range.put("from", from);
			date_range.put("to", to);
			Map<String, Object> api = new HashMap<String, Object>();
			api.put("api_key", api_key);
			api.put("api_secret", api_secret);
			// test
			api.put(sourceOrDes, source);
			api.put("date_range", date_range);
			Map<String, Object> submission = new HashMap<String, Object>();
			submission.put("submission", api);
			HttpEntity<?> request = new HttpEntity<>(submission, headers);
			JsonNode result = restTemplate.postForObject(urlCCall, request, JsonNode.class);
			JsonNode rows = result.get("response");
			for (JsonNode row : rows) {
				Call call = new Call();
				if (!row.get("Cdr").get("cid_name").asText().equals("null")) {
					call.setCid_name(row.get("Cdr").get("cid_name").asText());
				} else {
					call.setCid_name("none");
				}
				if (!row.get("Cdr").get("destination").asText().equals("null")) {
					call.setDestination(row.get("Cdr").get("destination").asText());
				} else {
					call.setDestination("none");
				}
				if (!row.get("Cdr").get("direction").asText().equals("null"))
					call.setDirection(row.get("Cdr").get("direction").asText());
				else {
					call.setDirection("none");
				}

				String tmp = row.get("Cdr").get("duration").asText();
				Duration d = Duration.between(LocalTime.MIN, LocalTime.parse(tmp));
				long seconds = d.getSeconds();
				call.setStart(row.get("Cdr").get("start").asText());
				call.setDuration(seconds);
				String tta = row.get("Cdr").get("tta").asText();
				if (!tta.equals("null")) {
					tta = row.get("Cdr").get("tta").asText().replace('s', ' ').trim();
					call.setTta(Integer.parseInt(tta));
				} else
					call.setTta(0);
				String pdd = row.get("Cdr").get("pdd").asText().replace('s', ' ').trim();
				if (pdd != null) {
					call.setPdd(Double.parseDouble(pdd));
				} else
					call.setPdd(0.0);
				if (!row.get("Cdr").get("recording_file").asText().equals("null"))
					call.setRecording_file(row.get("Cdr").get("recording_file").asText());
				else {
					call.setRecording_file("");
				}
				if (!row.get("Cdr").get("source").asText().equals("null")) {
					call.setSource(row.get("Cdr").get("source").asText());
				} else {
					call.setSource("none");
				}
				if (!row.get("Cdr").get("status").asText().equals("null")) {
					call.setStatus(row.get("Cdr").get("status").asText().trim());
				} else {
					call.setStatus("none");
				}
				if (!row.get("Cdr").get("mos").asText().equals("null")) {
					call.setMos(Double.parseDouble(row.get("Cdr").get("mos").asText()));
				} else {
					call.setMos(0.0);
				}
				listCall.add(call);
			}
			return listCall;
		} catch (Exception e) {

			LOGGER.error("Loi: " + e.toString());
			return null;
		}
	}
}
