package th.co.bookstore.common.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectDateUtils {

	private static final Logger log = LoggerFactory.getLogger(ProjectDateUtils.class);
	
	public static class DATE_PATTERN {
		public static final String DD_MM_YYYY = "dd/MM/yyyy";
	}
	
	public static String convertDate(Date date, String pattern) {
		String strDate = "";
		try {
			if (date != null && StringUtils.isNotBlank(pattern)) {
				strDate = DateFormatUtils.format(date, pattern);
			}
		} catch (Exception e) {
			log.error("convert date exception : {}", e.getMessage());
		}
		return strDate;
	}
}
