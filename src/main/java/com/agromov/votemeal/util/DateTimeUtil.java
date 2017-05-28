package com.agromov.votemeal.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by A.Gromov on 28.05.2017.
 */
public class DateTimeUtil
{
    public static LocalDateTime parse(String asString)
    {
        return LocalDateTime.parse(asString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
