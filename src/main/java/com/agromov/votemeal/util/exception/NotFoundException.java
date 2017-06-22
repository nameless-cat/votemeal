package com.agromov.votemeal.util.exception;

import static com.agromov.votemeal.config.LocalizationCodes.ENTITY_NOT_FOUND;

/**
 * Created by A.Gromov on 22.06.2017.
 */
public class NotFoundException
    extends RuntimeException
{
    public NotFoundException()
    {
        super(ENTITY_NOT_FOUND);
    }
}
