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

public class LocalDateFormatter implements Formatter<LocalDate> {


    private DateFormatParser parser;

    public LocalDateFormatter(DateFormatParser parser) {
        this.parser=parser;
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isEmpty(text.trim())) {
            return null;
        }
        if ("null".equals(text.trim().toLowerCase())) {
            return null;
        }
        //直接使用日期格式

        LocalDate localDate = parser.parseAsLocalDate(text);

        if (Objects.nonNull(localDate)) {
            return localDate;
        }
        LocalDateTime localDateTime=parser.parseAsLocalDateTime(text);
        if (Objects.nonNull(localDateTime)) {
            return localDateTime.toLocalDate();
        }
        //按offsetdatetime进行转换
        OffsetDateTime offsetDateTime = parser.parseAsOffsetDateTime(text);
        if (Objects.nonNull(offsetDateTime)) {
            //使用原来的时区数据
            return offsetDateTime.toLocalDate();
        }
        throw new ParseException("无法将文字:["+text+"]解析为LocalDate格式",0);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return Objects.isNull(object) ? "null":DateTimeFormatter.ISO_LOCAL_DATE.format(object);
    }
}
