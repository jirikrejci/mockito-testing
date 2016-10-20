package com.jksoft.mockito_testing;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jirka on 20.10.2016.
 */

public class WateringScheduler {
    public int getNumberOfPlantsSheduledOnDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        return month +1;
    }
}
