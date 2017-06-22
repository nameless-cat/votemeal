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
        MATCHER.assertEquals(GRILLE_GURME, repository.get(MCDONALDS_ID, GRILLE_GURME.getId()));
    }

    @Test
    public void getLunchThatNotBelongsToCafeMustReturnNull() throws Exception
    {
        assertEquals(null, repository.get(SMOKED_POTATO.getId(), MCDONALDS_ID));
    }

    @Test
    public void getAllLunchesOfCafeMustReturnCorrectListOfObjects() throws Exception
    {
        MATCHER.assertCollectionEquals(Arrays.asList(GRILLE_GURME, CHEESEBURGER), repository.getAll(MCDONALDS_ID));
    }

    @Test
    public void getAllLunchesOfNonexistentCafeMustReturnEmptyList() throws Exception
    {
        MATCHER.assertCollectionEquals(Collections.emptyList(), repository.getAll(100));
    }

    @Test
    public void deleteLunchMustReflectChangesInDB() throws Exception
    {
        repository.delete(MCDONALDS_ID, GRILLE_GURME.getId());
        MATCHER.assertCollectionEquals(Collections.singletonList(CHEESEBURGER), repository.getAll(MCDONALDS_ID));
    }

    @Test
    public void deleteLunchThatNotBelongsToCafeMustReturnZeroAsResult() throws Exception
    {
        assertEquals(NO_RESULT, repository.delete(ALPEN_SUB.getId(), MCDONALDS_ID));
    }

    @Test
    public void deleteLunchOfNonexistentCafeMustReturnZeroAsResult() throws Exception
    {
        assertEquals(NO_RESULT, repository.delete(ALPEN_SUB.getId(), 100));
    }

    @Test
    public void saveLunchMustReflectChangesInDB() throws Exception
    {
        Lunch created = getCreated();
        created.setRestaurant(MCDONALDS);
        created.setId(repository.save(MCDONALDS_ID, created).getId());
        MATCHER.assertCollectionEquals(Arrays.asList(created, GRILLE_GURME, CHEESEBURGER), repository.getAll(MCDONALDS_ID));
    }

    @Test
    public void updateLunchMustReflectChangesInDB() throws Exception
    {
        Lunch updated = getUpdated();
        repository.save(MCDONALDS_ID, updated);
        MATCHER.assertCollectionEquals(Arrays.asList(updated, CHEESEBURGER), repository.getAll(MCDONALDS_ID));
    }
}