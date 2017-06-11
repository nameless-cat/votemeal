package com.agromov.votemeal.util.converters;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by A.Gromov on 08.06.2017.
 */
public class SqlDateToLocalDateConverter
        implements AttributeConverter<LocalDate, Date>
{
    @Override
    public Date convertToDatabaseColumn(LocalDate attribute)
    {
        return Date.valueOf(attribute);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData)
    {
        return dbData.toLocalDate();
    }
}
