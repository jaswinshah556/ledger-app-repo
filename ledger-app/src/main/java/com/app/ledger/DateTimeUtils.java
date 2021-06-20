package com.app.ledger;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @author jaswin.shah
 * @version $Id: DateTimeUtils.java, v 0.1 2021-06-21 08:34 AM jaswin.shah Exp $$
 */
@Slf4j
public class DateTimeUtils {


    public static Date getLastDateOfCurrentMonth() {
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }


}
