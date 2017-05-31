package com.agromov.votemeal.repository;

import com.agromov.votemeal.LunchTestData;
import com.agromov.votemeal.model.Lunch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;

import static com.agromov.votemeal.LunchTestData.*;
import static com.agromov.votemeal.RestaurantTestData.MCDONALDS;
import static com.agromov.votemeal.RestaurantTestData.MCDONALDS_ID;
import static org.junit.Assert.assertEquals;

/**
 * Created by A.Gromov on 31.05.2017.
 */
public class LunchRepositoryTest
        extends AbstractRepositoryTest
{
    @Autowired
    private LunchRepository repository;

    @Test
    public void getLunchMustReturnCorrectObject() throws Exception
    {
        MATCHER.assertEquals(GRILLE_GURME, repository.get(GRILLE_GURME.getId(), MCDONALDS_ID));
        expectedQueries(2);
    }

    @Test
    public void getLunchThatNotBelongsToCafeMustReturnNull() throws Exception
    {
        assertEquals(null, repository.get(SMOKED_POTATO.getId(), MCDONALDS_ID));
        expectedQueries(1);
    }

    @Test
    public void getAllLunchesOfCafeMustReturnCorrectListOfObjects() throws Exception
    {
        MATCHER.assertCollectionEquals(Arrays.asList(GRILLE_GURME, CHEESEBURGER), repository.getAll(MCDONALDS_ID));
        expectedQueries(2);
    }

    @Test
    public void getAllLunchesOfInexistentCafeMustReturnEmptyList() throws Exception
    {
        MATCHER.assertCollectionEquals(Collections.emptyList(), repository.getAll(100));
        expectedQueries(1);
    }

    @Test
    public void deleteLunchMustReflectChangesInDB() throws Exception
    {
        repository.delete(GRILLE_GURME.getId(), MCDONALDS_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(CHEESEBURGER), repository.getAll(MCDONALDS_ID));
        expectedQueries(3);
    }

    @Test
    public void deleteLunchThatNotBelongsToCafeMustReturnZeroAsResult() throws Exception
    {
        assertEquals(NO_RESULT, repository.delete(ALPEN_SUB.getId(), MCDONALDS_ID));
        expectedQueries(1);
    }

    @Test
    public void deleteLunchOfInexistentCafeMustReturnZeroAsResult() throws Exception
    {
        assertEquals(NO_RESULT, repository.delete(ALPEN_SUB.getId(), 100));
        expectedQueries(1);
    }

    @Test
    public void saveLunchMustReflectChangesInDB() throws Exception
    {
        Lunch created = LunchTestData.getCreated();
        created.setRestaurant(MCDONALDS);
        created.setId(repository.save(created).getId());
        MATCHER.assertCollectionEquals(Arrays.asList(created, GRILLE_GURME, CHEESEBURGER), repository.getAll(MCDONALDS_ID));
        expectedQueries(4);
    }

    @Test
    public void updateLunchMustReflectChangesInDB() throws Exception
    {
        Lunch updated = LunchTestData.getUpdated();
        repository.save(updated);
        MATCHER.assertCollectionEquals(Arrays.asList(updated, CHEESEBURGER), repository.getAll(MCDONALDS_ID));
        expectedQueries(4);
    }
}