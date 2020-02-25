package adstech.vn.com.logcallStatistics.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.MapLikeType;

import adstech.vn.com.logcallStatistics.contract.ResponseContract;
import adstech.vn.com.logcallStatistics.model.Count;
import adstech.vn.com.logcallStatistics.model.Sale;
import adstech.vn.com.logcallStatistics.pojo.DealWon;
import adstech.vn.com.logcallStatistics.repository.CountRepository;
import adstech.vn.com.logcallStatistics.repository.SaleRepository;
import adstech.vn.com.logcallStatistics.util.CommonConstants;

@Service
public class CountService {
	@Autowired
	CountRepository countRepository;

	@Autowired
	SaleRepository saleRepository;
	@Value("${hapikey}")
	private String hapikey;
	private String root_link = "https://api.hubapi.com/engagements/v1/engagements/recent/modified?hapikey=";
	private String link_deal_won = "https://api.hubapi.com/deals/v1/deal/recent/modified?hapikey=";

	public ResponseContract<?> salesStatistic(Long timeStamp) {
		try {
			String api = root_link + hapikey + "&count=100";
			String get_deal_won = link_deal_won + hapikey + "&count=100";
			boolean hasMore = true;

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<?> request = new HttpEntity<>(headers);
			JsonNode result = restTemplate
					.exchange(api + "&since=" + timeStamp, HttpMethod.GET, request, JsonNode.class).getBody();
			hasMore = result.get("hasMore").asBoolean();
			System.out.println(hasMore);
			List<Sale> litSale = saleRepository.getAll();
			Map<Integer, Count> mapSale = new HashMap<Integer, Count>();
			Map<String, Integer> mapDeal = new HashMap<String, Integer>();
			List<String> engagementId = new ArrayList<String>();
			for (Sale sale : litSale) {
				Count count = new Count();
				count.setName(sale.getName());
				count.setMailSale(sale.getEmailSale());
				mapSale.put(sale.getOwnerId(), count);
				mapDeal.put(sale.getEmailSale(), sale.getOwnerId());
			}

			JsonNode results = result.get("results");
			for (JsonNode engagement : results) {

				Long id = engagement.get("engagement").get("id").asLong();
				if (!engagementId.contains(id.toString())) {

					if (engagement.get("engagement").get("ownerId") != null) {
						// if (engagement.get("engagement").get("createdAt").asLong() >= timeStamp) {
						if (mapSale.containsKey(engagement.get("engagement").get("ownerId").asInt())) {
							Integer key = engagement.get("engagement").get("ownerId").asInt();

							String type = engagement.get("engagement").get("type").asText();
//							if (type.trim().equals("TASK")) {
//
//								if (engagement.get("metadata") != null) {
//									if (engagement.get("metadata").get("subject") != null) {
//
//										String subject = engagement.get("metadata").get("subject").asText();
//										String subjectCopmare = removeAccent(subject).toLowerCase();
//										System.out.println(subjectCopmare);
//										if (subjectCopmare.contains("bao gia hn")
//												|| subjectCopmare.contains("bao gia hcm")
//												|| subjectCopmare.contains("bao gia fb")) {
//											mapSale.get(key).setTask(mapSale.get(key).getTask() + 1);
//										}
//									}
//								}
//
//							} else 
							if (type.trim().equals("CALL")) {

								mapSale.get(key).setCall(mapSale.get(key).getCall() + 1);
							} else if (type.trim().equals("EMAIL")) {
								mapSale.get(key).setEmail(mapSale.get(key).getEmail() + 1);
							} else if (type.trim().equals("MEETING")) {

								mapSale.get(key).setMeeting(mapSale.get(key).getMeeting() + 1);
							}
						}
					}

					// }
					engagementId.add(id.toString());
				}
			}
			int offset = result.get("offset").asInt();
			while (hasMore == true) {
				System.out.println("bao gia " + offset);
				JsonNode response = restTemplate.exchange(api + "&offset=" + offset + "&since=" + timeStamp,
						HttpMethod.GET, request, JsonNode.class).getBody();
				JsonNode responses = response.get("results");
				for (JsonNode engagement : responses) {

					Long id = engagement.get("engagement").get("id").asLong();
					if (!engagementId.contains(id.toString())) {
						if (engagement.get("engagement").get("ownerId") != null) {
							// if (engagement.get("engagement").get("createdAt").asLong() >= timeStamp) {
							if (mapSale.containsKey(engagement.get("engagement").get("ownerId").asInt())) {
								Integer key = engagement.get("engagement").get("ownerId").asInt();
								String type = engagement.get("engagement").get("type").asText();
//								if (type.trim().equals("TASK")) {
//									if (engagement.get("metadata") != null) {
//										if (engagement.get("metadata").get("subject") != null) {
//
//											String subject = engagement.get("metadata").get("subject").asText();
//											String subjectCopmare = removeAccent(subject).toLowerCase();
//											// System.out.println(subjectCopmare);
//											if (subjectCopmare.contains("bao gia hn")
//													|| subjectCopmare.contains("bao gia hcm")
//													|| subjectCopmare.contains("bao gia fb")) {
//												mapSale.get(key).setTask(mapSale.get(key).getTask() + 1);
//											}
//										}
//									}
//
//								} else
								if (type.trim().equals("CALL")) {

									mapSale.get(key).setCall(mapSale.get(key).getCall() + 1);
								} else if (type.trim().equals("EMAIL")) {
									mapSale.get(key).setEmail(mapSale.get(key).getEmail() + 1);
								} else if (type.trim().equals("MEETING")) {

									mapSale.get(key).setMeeting(mapSale.get(key).getMeeting() + 1);
								}

							}
						}
					}
					// }
					engagementId.add(id.toString());
				}
				offset = response.get("offset").asInt();
				hasMore = response.get("hasMore").asBoolean();
			}
			Timestamp timestamp = new Timestamp(timeStamp);
			Date date = timestamp;
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//			String dateCreated = dateformat.format(date);
//			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
//			cal.setTime(date);
			String dateCreated = dateformat.format(date);
			// cal.add(Calendar.HOUR, -2);
			// String from = dateformat.format(cal.getTime());
			for (Sale sale : litSale) {
				Integer ownerId = sale.getOwnerId();
				mapSale.get(ownerId).setDateRequest(dateCreated);

				Count count = countRepository.findByEmailAndDate(sale.getEmailSale(), dateCreated);
				if (count == null) {
					mapSale.get(ownerId).calculateScore();
					countRepository.create(mapSale.get(ownerId));

				} else {
					mapSale.get(ownerId).setId(count.getId());
					mapSale.get(ownerId).setDealWon(count.getDealWon());
					mapSale.get(ownerId).setTask(count.getTask());
					mapSale.get(ownerId).calculateScore();
					countRepository.update(mapSale.get(ownerId));
				}

			}
			return new ResponseContract<Map<Integer, Count>>(CommonConstants.RESPONSE_CODE_SUCCESS, null, mapSale);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_SUCCESS, null, e.getMessage());
		}
	}

	public ResponseContract<?> catchDealWon(DealWon deal) {
		try {
			Timestamp timestamp = new Timestamp(deal.getProperties().getDealstage().getTimestamp());
			Date date = timestamp;
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			String dateCreated = dateformat.format(date);
			String emailSale = deal.getProperties().getDealstage().getSourceId();
			Sale sale = saleRepository.getByEmail(emailSale);
			if (sale != null) {
				Count count = countRepository.findByEmailAndDate(emailSale, dateCreated);
				if (count == null) {
					Count countTmp = new Count();
					countTmp.setDealWon(1);
					countTmp.setMailSale(emailSale);
					countTmp.setName(saleRepository.getNameByEmail(emailSale));
					countTmp.setDateRequest(dateCreated);
					countRepository.create(countTmp);
				} else {
					count.setDealWon(count.getDealWon() + 1);
					countRepository.update(count);
				}
			}
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_SUCCESS, null, dateCreated);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, null, e.getMessage());
		}
	}

	public ResponseContract<?> getCountByDate(String date) {
		try {
			return new ResponseContract<List<Count>>(CommonConstants.RESPONSE_CODE_SUCCESS, null,
					countRepository.getCountByDate(date));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, null, e.getMessage());
		}
	}

//	public ResponseContract<?> tesst(){
//		Calendar ca= Calendar.getInstance();
//		Date timeNow = ca.getTime();
//		
//		
//		//TimeZone tz = TimeZone.getTimeZone("UTC");
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		dateFormat.setTimeZone(tz);
//		String to = dateFormat.format(timeNow);
//		Date timestamp;
//		try {
//			timestamp = dateFormat.parse(to);
//			String test = dateFormat.format(timestamp);
//			Count count = new Count();
//			count.setCreatedAt(new Date());
//			return new ResponseContract<Count>("", "", count);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}
	public ResponseContract<?> catchBaoGia(Map<String, Object> mapTask) {
		try {
			Map<String, Object> mapOwner = (Map<String, Object>) mapTask.get("associated-owner");
			String email = (String) mapOwner.get("email");
			Calendar ca = Calendar.getInstance();
			Date timeNow = ca.getTime();

			TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setTimeZone(tz);
			String dateRequest = dateFormat.format(timeNow);
			Sale sale = saleRepository.getByEmail(email);
			if (sale != null) {
				Count count = countRepository.findByEmailAndDate(email, dateRequest);
				if (count == null) {
					count = new Count();
					count.setTask(1);
					count.setDateRequest(dateRequest);
					count.setMailSale(email);
					count.setName(saleRepository.getNameByEmail(email));
					countRepository.create(count);
				} else {
					count.setTask(count.getTask() + 1);
					countRepository.update(count);
				}
			}
			return new ResponseContract<Map<String, Object>>(CommonConstants.RESPONSE_CODE_SUCCESS, email, mapTask);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, e.getMessage(), null);
		}
	}

	public ResponseContract<?> getContactByPhone(String phone) {
		try {
			String contact_hubspot = "https://api.hubapi.com/contacts/v1/search/query?q=";
			String link_get_contact = contact_hubspot + phone + "&hapikey=" + hapikey;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<?> request = new HttpEntity<>(headers);
			JsonNode result = restTemplate.exchange(link_get_contact, HttpMethod.GET, request, JsonNode.class)
					.getBody();
			Map<String, Object> mapContact = new HashMap<>();
			if (result.get("contacts") != null && !result.get("contacts").isEmpty()) {
				return new ResponseContract<Boolean>(CommonConstants.RESPONSE_CODE_SUCCESS, null, true);
			} else {
				return new ResponseContract<Boolean>(CommonConstants.RESPONSE_CODE_SUCCESS, null, false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, e.getMessage(), null);
		}
	}

	public ResponseContract<?> changeSale(Map<String, Object> mapSale) {
		try {
//			String oldName = mapSale.get("oldName").toString();
//			String newName = mapSale.get("newName").toString();
			String phone = mapSale.get("phone").toString();
			//String oldMail = mapSale.get("oldMail").toString();
			String newMail = mapSale.get("newMail").toString();
			
				Sale sale = saleRepository.getByMayNhanh(phone);
				if (sale != null) {
					sale.setEmailSale(newMail);
					String getOwnerId = "http://api.hubapi.com/owners/v2/owners?hapikey=" + hapikey + "&email="
							+ newMail;
					RestTemplate restTemplate = new RestTemplate();
					HttpHeaders headers = new HttpHeaders();
					HttpEntity<?> request = new HttpEntity<>(headers);
					JsonNode result = restTemplate.exchange(getOwnerId, HttpMethod.GET, request, JsonNode.class)
							.getBody();
					if (result != null && !result.isEmpty()) {
						Integer ownerId = result.get(0).get("ownerId").asInt();
						String name = result.get(0).get("firstName").asText() + " "
								+ result.get(0).get("lastName").asText();
						sale.setName(name);
						sale.setOwnerId(ownerId);
						saleRepository.update(sale);
						return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_SUCCESS, "Thành Công",null );
					} else
						return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_SUCCESS, "Email này không tồn tại trên Hubspot, kiểm tra lại",
								null);
				
			}else
				return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_SUCCESS, "Số máy không tồn tại", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, null, e.getMessage());
		}
	}

	public ResponseContract<?> createSale(Map<String, Object> maps) {
		try {
			String mayNhanh = (String) maps.get("mayNhanh");
			String email = (String) maps.get("email");
			Sale saleTmp = saleRepository.getByMayNhanh(mayNhanh);
			if (saleTmp == null) {
				String getOwnerId = "http://api.hubapi.com/owners/v2/owners?hapikey=" + hapikey + "&email=" + email;
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				HttpEntity<?> request = new HttpEntity<>(headers);
				JsonNode result = restTemplate.exchange(getOwnerId, HttpMethod.GET, request, JsonNode.class).getBody();
				if (result != null && !result.isEmpty()) {
					Integer ownerId = result.get(0).get("ownerId").asInt();
					String name = result.get(0).get("firstName").asText() + " "
							+ result.get(0).get("lastName").asText();
					Sale sale = new Sale();
					sale.setEmailSale(email);
					sale.setMayNhanh(mayNhanh);
					sale.setName(name);
					sale.setOwnerId(ownerId);
					saleRepository.create(sale);
					return new ResponseContract<Sale>(CommonConstants.RESPONSE_CODE_SUCCESS, null, sale);
				} else {
					return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_SUCCESS, "Email không tồn tại trên hubspot",
							null);
				}
			} else
				return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_SUCCESS, "Số máy nhánh mới đã gán cho người khác, vui lòng kiểm tra lại",
						null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, e.getMessage(), null);
		}
	}

	public ResponseContract<?> deleteByMayNhanh(String mayNhanh) {
		try {
			return new ResponseContract<Integer>(CommonConstants.RESPONSE_CODE_SUCCESS, null,
					saleRepository.deleteByMaynhanh(mayNhanh));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, e.getMessage(), null);
		}
	}

	public ResponseContract<?> getAllSale() {
		try {
			return new ResponseContract<List<Sale>>(CommonConstants.RESPONSE_CODE_SUCCESS, null,
					saleRepository.getAll());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstants.RESPONSE_CODE_FAIL, e.getMessage(), null);
		}
	}

	public static String removeAccent(String s) {

		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
}
