package adstech.vn.com.logcallStatistics.runner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import adstech.vn.com.logcallStatistics.service.CountService;


@Component
@EnableScheduling
@EnableAsync
public class CountRunner {
	@Autowired
	CountService countService;
	private static final Logger LOGGER = LoggerFactory.getLogger(CountRunner.class);
	
	@Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh")
	public void countSchedule() {
		Calendar ca= Calendar.getInstance();
		Date timeNow = ca.getTime();
		
		
		TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00");
		dateFormat.setTimeZone(tz);
		String to = dateFormat.format(timeNow);
		Date timestamp;
		try {
			timestamp = dateFormat.parse(to);
			countService.salesStatistic(timestamp.getTime() - 82800000 + 25200000);
			LOGGER.info("Thống kê lúc " + to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
