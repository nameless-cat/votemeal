package com.agromov.votemeal.web;

import com.agromov.votemeal.LunchTestData;
import com.agromov.votemeal.RestaurantTestData;
import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.service.LunchService;
import com.agromov.votemeal.service.RestaurantService;
import com.agromov.votemeal.service.VoteService;
import com.agromov.votemeal.util.LunchBuilder;
import com.agromov.votemeal.util.RestaurantBuilder;
import com.agromov.votemeal.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.agromov.votemeal.LunchTestData.CHEESEBURGER;
import static com.agromov.votemeal.LunchTestData.GRILLE_GURME;
import static com.agromov.votemeal.RestaurantTestData.*;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.web.VoteTestData.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by A.Gromov on 14.06.2017.
 */
public class AdminControllerTest
        extends AbstractControllerTest
{
    private static final String ADMIN_URL = AdminController.ADMIN_URL;
    private static final String RESTAURANTS_URL = "restaurants/";
    private static final String LUNCHES_URL = "/lunches/";
    private static final String VOTE_URL = "vote/";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private LunchService lunchService;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void getAllRestaurantsMustReturnListOrderedByName() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL))
                .andDo(print())
                .andExpect(RestaurantTestData.MATCHER.contentListMatcher(RESTAURANTS));
    }

    @Transactional
    @Test
    public void postNewRestaurantMustReflectChangesInDB() throws Exception
    {
        Restaurant created = RestaurantTestData.getCreated();

        mockMvc.perform(post(ADMIN_URL + RESTAURANTS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("http://localhost" + ADMIN_URL + RESTAURANTS_URL + LAST_CREATED_ID));

        List<Restaurant> restaurants = new ArrayList<>(RESTAURANTS);
        restaurants.add(0, created);

        RestaurantTestData.MATCHER.assertCollectionEquals(restaurants, restaurantService.getAll());
    }

    @Test
    public void getRestaurantByIdMustReturnCorrectObject() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL + SUBWAY.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.MATCHER.contentMatcher(SUBWAY));
    }

    @Test
    public void getNonexistentRestaurantMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL + NONEXISTENT_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    public void putRestaurantChangesMustReflectThatInDB() throws Exception
    {
        Restaurant updated = new RestaurantBuilder(MCDONALDS)
                .withAddress().street("new street address")
                .withAddress().building(12)
                .build();

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isAccepted());

        RestaurantTestData.MATCHER.assertEquals(updated, restaurantService.get(updated.getId()));
    }

    @Transactional
    @Test
    public void putChangesOfNonexistentRestaurantMustReturn404StatusCode() throws Exception
    {
        Restaurant updated = new RestaurantBuilder(MCDONALDS)
                .withId(NONEXISTENT_ID)
                .withAddress().street("new street address")
                .withAddress().building(12)
                .build();

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getVoteWithoutDateMustReturnCurrentVoteList() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + VOTE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.MATCHER.contentListMatcher(CHOCO_VOTE, SUBWAY_VOTE, BENJAMIN_VOTE));
    }


    //todo не проходит если запускать все вместе. Только по-отдельности
    @Test
    public void getVoteWithDateMustReturnVoteListOfThatDate() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + VOTE_URL + LocalDate.parse("2014-05-20").toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.MATCHER.contentListMatcher(POTATO_VOTE_PAST, MCDONALDS_VOTE_PAST));
    }

    @Transactional
    @Test
    public void putRestaurantsToCurrentVoteMustReflectChangesInDB() throws Exception
    {
        List<Long> ids = Collections.singletonList(MCDONALDS_ID);

        mockMvc.perform(put(ADMIN_URL + VOTE_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(ids)))
                .andDo(print())
                .andExpect(status().isAccepted());

        VoteTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(CHOCO_VOTE, SUBWAY_VOTE, BENJAMIN_VOTE, MCDONALDS_VOTE),
                voteService.get(currentDate()));
    }

    @Transactional
    @Test
    public void deleteRestaurantFromCurrentVoteMustReflectChangesInDB() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + VOTE_URL + CHOCO.getId()))
                .andDo(print())
                .andExpect(status().isAccepted());

        VoteTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(SUBWAY_VOTE, BENJAMIN_VOTE),
                voteService.get(currentDate()));

        //todo сделать проверку на каскадное удаление
    }

    @Transactional
    @Test
    public void deleteRestaurantThatNotInVoteMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + VOTE_URL + MCDONALDS_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    public void postLunchToRestaurantMustReflectChangesInDB() throws Exception
    {
        String name = "new burger";

        Lunch created = new LunchBuilder()
                .withName(name)
                .withDescription("Harmful and delicious")
                .withPrice(100.00f)
                .build();

        mockMvc.perform(post(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated());

        created.setId(LAST_CREATED_ID);
        created.setRestaurant(MCDONALDS);

        em.flush();

        LunchTestData.MATCHER.assertEquals(
                created,
                restaurantService.get(MCDONALDS_ID).getLunches().stream().filter(l -> l.getName().equals(name)).findFirst().get());
    }

    @Test
    public void postLunchToNonexistentRestaurantMustReturn404StatusCode() throws Exception
    {
        Lunch created = new LunchBuilder()
                .withName("new burger")
                .withDescription("Harmful and delicious")
                .withPrice(100.00f)
                .build();

        mockMvc.perform(post(ADMIN_URL + RESTAURANTS_URL + NONEXISTENT_ID + LUNCHES_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getLunchOfRestaurantMustReturnCorrectObject() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + GRILLE_GURME.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(LunchTestData.MATCHER.contentMatcher(GRILLE_GURME));
    }

    @Transactional
    @Test
    public void putChangesToLunchMustReflectThatInDB() throws Exception
    {
        Lunch updated = LunchTestData.getUpdated();

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isAccepted());

        LunchTestData.MATCHER.assertEquals(updated, lunchService.get(MCDONALDS_ID, updated.getId()));
    }

    @Test
    public void putChangesToNonexistentLunchMustReturn400StatusCode() throws Exception
    {
        Lunch created = LunchTestData.getCreated();

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + GRILLE_GURME.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void deleteLunchFromRestaurantMustReflectChangesIdDB() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + GRILLE_GURME.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        LunchTestData.MATCHER.assertCollectionEquals(
                Collections.singletonList(CHEESEBURGER),
                restaurantService.get(MCDONALDS_ID).getLunches());
    }

    @Transactional
    @Test
    public void deleteNonexistentLunchMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + NONEXISTENT_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    public void deleteLunchFromNonexistentRestaurantMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + RESTAURANTS_URL + NONEXISTENT_ID + LUNCHES_URL + GRILLE_GURME.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}