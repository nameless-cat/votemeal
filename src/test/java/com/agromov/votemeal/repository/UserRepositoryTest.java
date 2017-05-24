package com.agromov.votemeal.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.agromov.votemeal.UserTestData.*;

/**
 * Created by A.Gromov on 23.05.2017.
 */
public class UserRepositoryTest extends AbstractRepositoryTest
{
    @Autowired
    private UserRepository repository;

    @Test
    public void getUserMustReturnCorrectObject() throws Exception
    {
        MATCHER.assertEquals(MARIA, repository.get(MARIA_ID));
    }
}
