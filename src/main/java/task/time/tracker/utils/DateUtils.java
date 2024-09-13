package task.time.tracker.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {
	public LocalDateTime convertStringToLocalDateTime(final String dateTime, final String dateFormat) {
		if (dateTime == null)
			return null;
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		return LocalDateTime.parse(dateTime, formatter);
	}

	public String convertLocalDateTimeToString(final LocalDateTime localDateTime, final String dateFormat) {
		if (localDateTime == null)
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		return localDateTime.format(formatter);
	}
}
