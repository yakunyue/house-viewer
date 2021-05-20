package com.fxg.house.viewer.formatter;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


//思路是先根据text全部转为java8date对象，然后再往joda和date转换
//不自己进行解析处理，使用偿试解析机制，如果成功赠返回，否则不返回，返回无法解析
public class DateFormatParser {


    private ArrayList<DateTimeFormatter> dateParser = new ArrayList<>();
    private ArrayList<DateTimeFormatter> timeParser = new ArrayList<>();
    private ArrayList<DateTimeFormatter> dateTimeParser = new ArrayList<>();
    private ArrayList<DateTimeFormatter> offsetDateTimeParser = new ArrayList<>();
    private ArrayList<DateTimeFormatter> offsetTimeParser = new ArrayList<>();


    public DateFormatParser() {
        //添加本地日期解析器
        //TODO 测一下使用带时区的日期进行解析，会不会有问题
        dateParser.add(DateTimeFormatter.ISO_LOCAL_DATE);//2011-12-03
        dateParser.add(this.createFromPattern("yyyy-MM-dd"));
        dateParser.add(this.createFromPattern("yyyy-M-d"));
        dateParser.add(this.createFromPattern("ddMMMyyyy"));
        dateParser.add(this.createFromPattern("ddMMMMyyyy"));
        dateParser.add(this.createFromPattern("dMMMyyyy"));
        dateParser.add(this.createFromPattern("dMMMMyyyy"));
        dateParser.add(this.createFromPattern("yyyyMMMdd"));
        dateParser.add(this.createFromPattern("yyyyMMMd"));
        dateParser.add(this.createFromPattern("yyyy MM dd"));
        dateParser.add(this.createFromPattern("yyyy M d"));
        dateParser.add(this.createFromPattern("yyyy/MM/dd"));
        dateParser.add(this.createFromPattern("yyyy/M/d"));
        dateParser.add(this.createFromPattern("yy/M/d"));//17-8-8
        dateParser.add(DateTimeFormatter.BASIC_ISO_DATE);//20111203
        dateParser.add(DateTimeFormatter.ISO_ORDINAL_DATE);//'2012-337'
        //这条特殊一点，虽然带时区格式，但是无法用有时间的日期表示，因为少了关键时间信息，没有offsetDate这种对象去处理他除非设置使用默认的00：00：00，但不不好
//        dateParser.add(DateTimeFormatter.ISO_OFFSET_DATE);//2011-12-03+01:00
        //添加带时区的日期解析器


        //添加时间形解析器
        timeParser.add(DateTimeFormatter.ISO_LOCAL_TIME);//''10:15:30''
        timeParser.add(this.createFromPattern("H:m:s"));
        timeParser.add(this.createFromPattern("HH:mm:ss.SSS"));
        timeParser.add(this.createFromPattern("H:m:s.SSS"));
        timeParser.add(this.createFromPattern("aKK:mm:ss"));
        timeParser.add(this.createFromPattern("aK:m:s"));
        timeParser.add(this.createFromPattern("aKK:mm:ss.SSS"));
        timeParser.add(this.createFromPattern("aK:m:s.SSS"));
        timeParser.add(this.createFromPattern("a KK:mm:ss"));
        timeParser.add(this.createFromPattern("a K:m:s"));
        timeParser.add(this.createFromPattern("a KK:mm:ss.SSS"));
        timeParser.add(this.createFromPattern("a K:m:s.SSS"));
        timeParser.add(this.createFromPattern("KK:mm:ss a"));
        timeParser.add(this.createFromPattern("K:m:s a"));
        timeParser.add(this.createFromPattern("KK:mm:ss.SSS a"));
        timeParser.add(this.createFromPattern("K:m:s.SSS a"));

        offsetTimeParser.add(DateTimeFormatter.ISO_OFFSET_TIME);//'10:15:30+01:00'

        //添加日期时间类型的解析器
        dateTimeParser.add(DateTimeFormatter.ISO_LOCAL_DATE_TIME);//'2011-12-03T10:15:30'
        dateTimeParser.add(this.createFromPattern("yyyy-M-d'T'H:m:s"));
        dateTimeParser.add(this.createFromPattern("yyyy-M-d'T'H:m:s.SSS"));
        dateTimeParser.add(this.createFromPattern("yyyy-M-d H:m:s"));
        dateTimeParser.add(this.createFromPattern("yyyy-M-d H:m:s.SSS"));
        dateTimeParser.add(this.createFromPattern("yyyy-M-d H:m"));
        dateTimeParser.add(this.createFromPattern("yyyy-MM-dd HH:mm"));
        dateTimeParser.add(this.createFromPattern("yyyyMMddHHmmss"));//微信日期交互格式

        offsetDateTimeParser.add(DateTimeFormatter.ISO_OFFSET_DATE_TIME);//2011-12-03T10:15:30+01:00'
        offsetDateTimeParser.add(DateTimeFormatter.ISO_INSTANT);//'2011-12-03T10:15:30Z'
        offsetDateTimeParser.add(DateTimeFormatter.RFC_1123_DATE_TIME);//'Tue, 3 Jun 2008 11:05:30 GMT'
        //
    }

    public LocalDate parseAsLocalDate(String text) {

        LocalDate result=null;
        for (int i=0;i<dateParser.size();i++)
        {
            try {
                result = dateParser.get(i).parse(text, LocalDate::from);
                if (Objects.nonNull(result)) {
                    break;
                }
            } catch (DateTimeParseException ex) {
            }
        }
        return result;
    }

    public LocalTime parseAsLocalTime(String text) {
        LocalTime result=null;
        for (int i=0;i<timeParser.size();i++)
        {
            try {
                result = timeParser.get(i).parse(text, LocalTime::from);
                if (Objects.nonNull(result)) {
                    break;
                }
            } catch (DateTimeParseException ex) {
            }
        }
        return result;
    }

    public OffsetDateTime parseAsOffsetDateTime(String text) {
        //按offset再解析一次
        OffsetDateTime result=null;
        for (int i=0;i<offsetDateTimeParser.size();i++)
        {
            try {
                result = offsetDateTimeParser.get(i).parse(text,OffsetDateTime::from);
                if (Objects.nonNull(result)) {
                    return result;
                }
            } catch (DateTimeParseException ex) {
            }
        }
        return  result;
    }

    public OffsetTime parseAsOffsetTime(String text) {
        //按offset再解析一次
        OffsetTime result=null;
        for (int i=0;i<offsetTimeParser.size();i++)
        {
            try {
                result = offsetTimeParser.get(i).parse(text,OffsetTime::from);
                if (Objects.nonNull(result)) {
                    return result;
                }
            } catch (DateTimeParseException ex) {
            }
        }
        return  result;
    }


    //这里要处理那种long形的，timestamp
    public LocalDateTime parseAsLocalDateTime(String text) {
        LocalDateTime result=null;
        for (int i=0;i<dateTimeParser.size();i++)
        {
            try {
                result = dateTimeParser.get(i).parse(text, LocalDateTime::from);
                if (Objects.nonNull(result)) {
                    return result;
                }
            } catch (DateTimeParseException ex) {
            }
        }

        return result;
    }

    private DateTimeFormatter createFromPattern(String pattern)
    {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH))
                .toFormatter(Locale.ENGLISH);
    }

}
