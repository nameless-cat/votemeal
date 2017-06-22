package com.agromov.votemeal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by A.Gromov on 22.06.2017.
 */
@Component
public class MessageUtils {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code) {
        return getMessage(code, LocaleContextHolder.getLocale());
    }

    public String getMessage(MessageSourceResolvable resolvable) {
        return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
    }
}

