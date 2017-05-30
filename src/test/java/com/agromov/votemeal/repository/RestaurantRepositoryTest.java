package com.agromov.votemeal.repository;

import com.agromov.votemeal.RestaurantTestData;
import com.agromov.votemeal.model.Restaurant;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.agromov.votemeal.RestaurantTestData.*;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public class RestaurantRepositoryTest
        extends AbstractRepositoryTest
{
    @Autowired
    private RestaurantRepository repository;

    @Test
    public void getCafeMustReturnCorrectObject() throws Exception
    {
        MATCHER.assertEquals(RESTAURANT_1, repository.get(RESTAURANT_ID));
        expectedQueries(1);
    }

    @Test
    public void getInexistentCafeMustReturnNull() throws Exception
    {
        MATCHER.assertEquals(null, repository.get(-1));
        expectedQueries(1);
    }

    @Test
    public void getAllCafesMustReturnListOfCafesSortedByName() throws Exception
    {
        MATCHER.assertCollectionEquals(RESTAURANTS, repository.getAll());
        expectedQueries(1);
    }

    @Test
    public void closeCafeMustReflectChangesInDB() throws Exception
    {
        repository.close(RESTAURANT_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT_5, RESTAURANT_4, RESTAURANT_2, RESTAURANT_3), repository.getAllOpened());
        expectedQueries(2);
    }

    @Test
    public void attemptToCloseInexistentCafeMustReturnFalse() throws Exception
    {
        Assert.assertEquals(false, repository.close(100));
        expectedQueries(1);
    }

    @Test
    public void addCafeMustReflectChangesInDB() throws Exception
    {
        Restaurant created = RestaurantTestData.getCreated();
        created.setId(repository.save(created).getId());

        MATCHER.assertCollectionEquals(Arrays.asList(created, RESTAURANT_5, RESTAURANT_4, RESTAURANT_2, RESTAURANT_1, RESTAURANT_3),
                repository.getAll());

        expectedQueries(3);
    }

    @Test
    public void openCafeMustReflectChangesInDB() throws Exception
    {
        Restaurant created = RestaurantTestData.getCreated();
        created.setClosed(true);
        created.setId(repository.save(created).getId());
        repository.open(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(created, RESTAURANT_5, RESTAURANT_4, RESTAURANT_2, RESTAURANT_1, RESTAURANT_3),
                repository.getAllOpened());
    }

    @Test
    public void getListCafeInRangeMustReturnCorrectResult() throws Exception
    {
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT_5, RESTAURANT_4, RESTAURANT_2), repository.getRange(0, 3));
        expectedQueries(1);
    }
}
