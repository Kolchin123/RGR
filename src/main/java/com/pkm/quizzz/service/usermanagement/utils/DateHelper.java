package com.pkm.quizzz.service.usermanagement.utils;

import java.util.Date;

public interface DateHelper {
	//получение даты

	Date getCurrentDate();

	Date getExpirationDate(Date from, int offset);
}
