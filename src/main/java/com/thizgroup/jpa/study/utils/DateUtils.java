package com.thizgroup.jpa.study.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gangquan.hu
 * @Package: com.thizgroup.mybatis.study.utils.DateUtils
 * @Description: TODO
 * @date 2019/8/12 15:57
 */
public class DateUtils {

  public static Date parse(String dateStr,String pattern){
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    try {
      return dateFormat.parse(dateStr);
    } catch (ParseException e) {
      throw new RuntimeException("date string format error");
    }
  }



}
