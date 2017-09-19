package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.agromov.votemeal.UserTestData.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by A.Gromov on 23.05.2017.
 */
public class UserRepositoryTest
        extends AbstractRepositoryTest
{
    @Autowired
    private UserRepository repository;

    @Test
    public void getUserMustReturnCorrectObject() throws Exception
    {
        MATCHER.assertEquals(MARIA, repository.get(MARIA_ID));
    }

    @Test
    public void getNonexistentUserMustReturnNull() throws Exception
    {
        Assert.assertEquals(null,repository.get(100));
    }

    @Transactional
    @Test
    public void deleteUserMustRemoveHimPermanentlyFromDB() throws Exception
    {
        repository.delete(OLEG_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA, USER), repository.getAll());
//        assertTrue(repository.getHistory(OLEG_ID).isEmpty());
    }

    @Transactional
    @Test
    public void deleteNonexistentUserMustReturnZero() throws Exception
    {
        Assert.assertEquals(0, repository.delete(100));
    }

    @Test
    public void getAllUsersMustReturnCorrectListOfUsersSortedByName() throws Exception
    {
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA, OLEG, USER), repository.getAll());
    }

    @Test
    public void getAndModifyUserAndPersistMustReflectThisOnDB() throws Exception
    {
        User updated = getUpdated();
        repository.update(updated);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, updated, OLEG, USER), repository.getAll());
    }

    @Test
    public void saveUserMustPersistItToDB() throws Exception
    {
        User created = getCreated();
        created.setId(repository.save(created).getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA, created, OLEG, USER), repository.getAll());
    }

    @Test
    public void getListUserWithLimitAndOffsetInCorrectOrder() throws Exception
    {
        User created = getCreated();
        created.setId(repository.save(created).getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA, created), repository.getRange(0, 3));
    }
}
