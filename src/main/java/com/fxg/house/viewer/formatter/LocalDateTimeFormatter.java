package com.fxg.house.viewer.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {



    private DateFormatParser parser;
    public LocalDateTimeFormatter(DateFormatParser parser) {
        this.parser=parser;
    }

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isEmpty(text.trim())) {
            return null;
        }
        if ("null".equals(text.trim().toLowerCase())) {
            return null;
        }
        LocalDateTime localDateTime=parser.parseAsLocalDateTime(text);
        if (Objects.nonNull(localDateTime)) {
            return localDateTime;
        }
        OffsetDateTime offsetDateTime = parser.parseAsOffsetDateTime(text);
        if (Objects.nonNull(offsetDateTime)) {
            //使用原来的时区数据
            return offsetDateTime.toLocalDateTime();
        }
        LocalDate localDate = parser.parseAsLocalDate(text);
        if (Objects.nonNull(localDate)) {
            //默认使用00：00：00作为时间
            return LocalDateTime.of(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth(),0,0,0,0);
        }
        throw new ParseException("无法将文字:["+text+"]解析为LocalDateTime格式",0);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return Objects.isNull(object) ? "null":DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(object);
    }

}

