package com.agromov.votemeal.util;

import com.agromov.votemeal.model.BaseEntity;
import com.agromov.votemeal.web.Authorized;
import org.slf4j.Logger;

import java.security.AccessControlException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by A.Gromov on 17.06.2017.
 */
public class ValidationUtils
{
    //todo сделать передачу класса в логгер динамической
    private static final Logger LOG = getLogger(ValidationUtils.class);

    public static void checkForNew(BaseEntity entity)
    {
        if (!entity.isNew())
        {
            //todo i18n
            throw new IllegalArgumentException();
        }
    }

    public static void checkForIdPresent(BaseEntity entity)
    {
        if (entity.isNew())
        {
            //todo i18n
            throw new IllegalArgumentException();
        }
    }

    public static void checkIdConsistence(BaseEntity entity, long id)
    {
        if (entity.isNew() || !Objects.equals(id, entity.getId()))
        {
            //todo i18n
            throw new IllegalArgumentException();
        }
    }

    public static void checkUserIdConsistent(Long id)
    {
        if (!id.equals(Authorized.getUser().getId()))
        {
            LOG.debug("Requested user id({}) not match with authorized user id({})", id, Authorized.getUser().getId());
            throw new AccessControlException("Access to user profile denied");
        }
    }
}
