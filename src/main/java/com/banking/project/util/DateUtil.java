package com.banking.project.util;

import com.banking.project.exception.OperationResultException;
import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.enums.OperationResultCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";
    public static final String SIMPLE_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static String getDateAsString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date getDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw OperationResultException.builder()
                    .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                            "Tarih parse edilirken hata oluştu"))
                    .build();
        }
    }

    public static Date dateFormat() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return getDate(getDateAsString(calendar.getTime(), SIMPLE_DATE_TIME_FORMAT), SIMPLE_DATE_TIME_FORMAT);
    }
}
