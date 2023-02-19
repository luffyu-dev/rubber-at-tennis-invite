package com.rubber.at.tennis.invite.service.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author luffyu
 * Created on 2023/2/15
 */
public class MyDataUtil {


    /**
     * 判断是否是同一周
     */
    public static boolean isSameWeek(Date d1 ,Date d2){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR);
    }

}
