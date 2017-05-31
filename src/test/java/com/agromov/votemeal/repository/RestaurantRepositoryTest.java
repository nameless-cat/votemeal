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
        MATCHER.assertEquals(MCDONALDS, repository.get(MCDONALDS_ID));
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
        repository.close(MCDONALDS_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(SUBWAY, BENJAMIN, POTATO, CHOCO), repository.getAllOpened());
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

        MATCHER.assertCollectionEquals(Arrays.asList(created, SUBWAY, BENJAMIN, POTATO, MCDONALDS, CHOCO),
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
        MATCHER.assertCollectionEquals(Arrays.asList(created, SUBWAY, BENJAMIN, POTATO, MCDONALDS, CHOCO),
                repository.getAllOpened());
    }

    @Test
    public void getListCafeInRangeMustReturnCorrectResult() throws Exception
    {
        MATCHER.assertCollectionEquals(Arrays.asList(SUBWAY, BENJAMIN, POTATO), repository.getRange(0, 3));
        expectedQueries(1);
    }
}
