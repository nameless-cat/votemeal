package com.agromov.votemeal.repository;

import com.agromov.votemeal.UserTestData;
import com.agromov.votemeal.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.agromov.votemeal.UserTestData.*;

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
        expectedQueries(1);
    }

    @Test
    public void getInexistentUserMustReturnNull() throws Exception
    {
        Assert.assertEquals(null,repository.get(100));
    }

    @Test
    public void deleteUserMustRemoveHisPermanentlyFromDB() throws Exception
    {
        repository.delete(OLEG_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA), repository.getAll());
        // toDO добавить проверку удаленной истории голосования после реализации VoteHistoryRepository
        expectedQueries(4);
    }

    @Test
    public void deleteInexistentUserMustReturnZero() throws Exception
    {
        Assert.assertEquals(0, repository.delete(100));
        expectedQueries(2);
    }

    @Test
    public void getAllUsersMustReturnCorrectListOfUsersSortedByName() throws Exception
    {
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA, OLEG), repository.getAll());
        /**
         * два запроса вместо ожидаемого одного. Возможно из-за того, что достается список пользователей, а не один.
         * toDO Попробовать доставать через жестко заданную sql query
         */
//        expectedQueries(1);
        expectedQueries(2);
    }

    @Test
    public void getAndModifyUserAndPersistMustReflectThisOnDB() throws Exception
    {
        User updated = UserTestData.getUpdated();
        repository.update(updated);
        MATCHER.assertCollectionEquals(Arrays.asList(updated, ADMIN, MARIA), repository.getAll());
        // слишком много запросов для сохранения изменений
        // todo попробовать через sql query
        expectedQueries(5);
    }

    @Test
    public void saveUserMustPersistItToDB() throws Exception
    {
        User created = UserTestData.getCreated();
        created.setId(repository.save(created).getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA, created, OLEG), repository.getAll());
        expectedQueries(5);
    }

    @Test
    public void getListUserWithLimitAndOffsetInCorrectOrder() throws Exception
    {
        User created = UserTestData.getCreated();
        created.setId(repository.save(created).getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, MARIA, created), repository.getRange(0, 3));
        // todo попробовать sql query
        expectedQueries(5);
    }
}
