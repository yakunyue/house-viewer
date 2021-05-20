package com.fxg.house.viewer.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DateFormatter implements Formatter<Date> {

    private DateFormatParser parser;
    public DateFormatter(DateFormatParser parser)  {
        this.parser=parser;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isEmpty(text.trim())) {
            return null;
        }
        if ("null".equals(text.trim().toLowerCase())) {
            return null;
        }
        //传入为无时区格式
        LocalDate date = parser.parseAsLocalDate(text);
        if (Objects.nonNull(date)) {
            //表示时，强制为有时区格式，为东8区
            Calendar cl=Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"),Locale.ENGLISH);
            cl.set(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),0,0,0);
            cl.set(Calendar.MILLISECOND,0);
            return cl.getTime();
        }
        //按本地日期时间格式偿试转换
        LocalDateTime dateTime = parser.parseAsLocalDateTime(text);
        if (Objects.nonNull(dateTime)) {
            //需要转为一个带时区的对象，默认使用东8区北京时间
            return Date.from(dateTime.atOffset(ZoneOffset.ofHours(8)).toInstant());
        }
        //按offsetdatetime进行转换
        OffsetDateTime offsetDateTime = parser.parseAsOffsetDateTime(text);
        if (Objects.nonNull(offsetDateTime)) {

            return Date.from(offsetDateTime.toInstant());
        }
        throw new ParseException("无法将文字:["+text+"]解析为Date格式",0);
    }

    @Override
    public String print(Date object, Locale locale) {
        //这种格式是错主的
        return Objects.isNull(object) ? "null":DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId .of("GMT+0800")).format(object.toInstant());
    }

}
