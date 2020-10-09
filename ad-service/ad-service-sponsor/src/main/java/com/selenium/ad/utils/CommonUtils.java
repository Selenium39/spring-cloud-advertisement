package com.selenium.ad.utils;

import com.selenium.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    /**
     * MD5加密
     */
    public static String md5(String value) {

        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     *将Date格式字符串转为Date
     */
    public static Date parseStringToDate(String value) throws AdException {
        try {
            return DateUtils.parseDate(value,parsePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new AdException(e.getMessage());
        }
    }
}
