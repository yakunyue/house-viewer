package com.fxg.house.viewer.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    private DateFormatParser parser;
    public LocalTimeFormatter(DateFormatParser parser) {
        this.parser=parser;
    }

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isEmpty(text.trim())) {
            return null;
        }
        if ("null".equals(text.trim().toLowerCase())) {
            return null;
        }
        LocalTime localTime=parser.parseAsLocalTime(text);
        if (Objects.nonNull(localTime)) {
            return localTime;
        }
        LocalDateTime localDateTime = parser.parseAsLocalDateTime(text);
        if (Objects.nonNull(localDateTime)) {
            return localDateTime.toLocalTime();
        }
        OffsetDateTime offsetDateTime = parser.parseAsOffsetDateTime(text);
        if (Objects.nonNull(offsetDateTime)) {
            //使用原来的时区数据
            return offsetDateTime.toLocalTime();
        }
        OffsetTime offsetTime = parser.parseAsOffsetTime(text);
        if (Objects.nonNull(offsetTime)) {
            //使用原来的时区数据
            return offsetTime.toLocalTime();
        }
        throw new ParseException("无法将文字:["+text+"]LocalTime",0);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return Objects.isNull(object) ? "null":DateTimeFormatter.ISO_LOCAL_TIME.format(object);
    }
}
