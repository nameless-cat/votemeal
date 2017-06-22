package com.agromov.votemeal.util.exception;

import static com.agromov.votemeal.config.LocalizationCodes.BAD_ARGUMENT_IN_REQUEST;

/**
 * Created by A.Gromov on 22.06.2017.
 */
public class BadArgumentException
        extends RuntimeException
{
    public BadArgumentException()
    {
        super(BAD_ARGUMENT_IN_REQUEST);
    }
}
