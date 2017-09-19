package com.agromov.votemeal.web;

import com.agromov.votemeal.LunchTestData;
import com.agromov.votemeal.RestaurantTestData;
import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.service.LunchService;
import com.agromov.votemeal.service.RestaurantService;
import com.agromov.votemeal.service.VoteService;
import com.agromov.votemeal.util.LunchBuilder;
import com.agromov.votemeal.util.RestaurantBuilder;
import com.agromov.votemeal.web.json.JsonUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
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
import static com.agromov.votemeal.TestUtil.userHttpBasic;
import static com.agromov.votemeal.UserTestData.ADMIN;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.web.VoteTestData.*;
import static org.junit.Assert.assertEquals;
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

    @Before
    public void setUp() throws Exception
    {
        restaurantService.cacheEvict();
        clear2ndLevelHibernateCache();

    }

    private void clear2ndLevelHibernateCache()
    {
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }

    @Test
    public void getAllRestaurantsMustReturnListOrderedByName() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(RestaurantTestData.MATCHER.contentListMatcher(RESTAURANTS));
    }


    @Test
    public void postNewRestaurantMustReflectChangesInDB() throws Exception
    {
        Restaurant created = RestaurantTestData.getCreated();

        mockMvc.perform(post(ADMIN_URL + RESTAURANTS_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("http://localhost" + ADMIN_URL + RESTAURANTS_URL + LAST_CREATED_ID.getAndIncrement()));

        List<Restaurant> restaurants = new ArrayList<>(RESTAURANTS);
        restaurants.add(0, created);

        RestaurantTestData.MATCHER.assertCollectionEquals(restaurants, restaurantService.getAll());
    }

    @Test
    public void getRestaurantByIdMustReturnCorrectObject() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL + SUBWAY.getId())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.MATCHER.contentMatcher(SUBWAY));
    }

    @Test
    public void getNonexistentRestaurantMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL + NONEXISTENT_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //без Propagation.NEVER данные сохраняются на протяжении всей цепочки тестов
    @Transactional(propagation = Propagation.NEVER)
    @Test
    public void putRestaurantChangesMustReflectThatInDB() throws Exception
    {
        Restaurant updated = new RestaurantBuilder(MCDONALDS)
                .withAddress().street("new street address")
                .withAddress().building(12)
                .build();

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + updated.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isAccepted());

        RestaurantTestData.MATCHER.assertEquals(updated, restaurantService.get(updated.getId()));
    }


    @Test
    public void putChangesOfNonexistentRestaurantMustReturn404StatusCode() throws Exception
    {
        Restaurant updated = new RestaurantBuilder(MCDONALDS)
                .withId(NONEXISTENT_ID)
                .withAddress().street("new street address")
                .withAddress().building(12)
                .build();

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + updated.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getVoteWithoutDateMustReturnCurrentVoteList() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + VOTE_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.MATCHER.contentListMatcher(CHOCO_VOTE, SUBWAY_VOTE, BENJAMIN_VOTE));
    }


    @Test
    public void getVoteWithDateMustReturnVoteListOfThatDate() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + VOTE_URL + LocalDate.parse("2014-05-20").toString())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.MATCHER.contentListMatcher(POTATO_VOTE_PAST, MCDONALDS_VOTE_PAST));
    }

    @Test
    public void putRestaurantsToCurrentVoteMustReflectChangesInDB() throws Exception
    {
        List<Long> ids = Arrays.asList(MCDONALDS.getId(), POTATO.getId());

        mockMvc.perform(put(ADMIN_URL + VOTE_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(ids)))
                .andDo(print())
                .andExpect(status().isAccepted());

        LAST_CREATED_ID.getAndIncrement();
        LAST_CREATED_ID.getAndIncrement();

        VoteTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(CHOCO_VOTE, SUBWAY_VOTE, BENJAMIN_VOTE, POTATO_VOTE, MCDONALDS_VOTE),
                voteService.getByDate(currentDate()));
    }

    @Test
    public void putRestaurantsWithNonexistentIdsToCurrentVoteMustReturn400StatusCode() throws Exception
    {
        List<Long> ids = Arrays.asList(MCDONALDS_ID, NONEXISTENT_ID);

        mockMvc.perform(put(ADMIN_URL + VOTE_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(ids)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteRestaurantFromCurrentVoteMustReflectChangesInDB() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + VOTE_URL + CHOCO.getId())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isAccepted());

        VoteTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(SUBWAY_VOTE, BENJAMIN_VOTE),
                voteService.getByDate(currentDate()));

        int found = em.createQuery("SELECT vh FROM VoteHistory vh WHERE vh.date=?1 AND vh.restaurant.id=?2", VoteHistory.class)
                .setParameter(1, currentDate())
                .setParameter(2, CHOCO.getId())
                .getResultList()
                .size();

        assertEquals(0, found);
    }


    @Test
    public void deleteRestaurantThatNotInVoteMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + VOTE_URL + MCDONALDS_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


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
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated());

        created.setId(LAST_CREATED_ID.getAndIncrement());
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
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getLunchOfRestaurantMustReturnCorrectObject() throws Exception
    {
        mockMvc.perform(get(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + GRILLE_GURME.getId())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(LunchTestData.MATCHER.contentMatcher(GRILLE_GURME));
    }


    @Test
    public void putChangesToLunchMustReflectThatInDB() throws Exception
    {
        Lunch updated = LunchTestData.getUpdated();

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + updated.getId())
                .with(userHttpBasic(ADMIN))
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
        created.setDescription("Описание на русском");

        mockMvc.perform(put(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + GRILLE_GURME.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteLunchFromRestaurantMustReflectChangesIdDB() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + GRILLE_GURME.getId())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        LunchTestData.MATCHER.assertCollectionEquals(
                Collections.singletonList(CHEESEBURGER),
                restaurantService.get(MCDONALDS_ID).getLunches());
    }

    @Test
    public void deleteNonexistentLunchMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL + NONEXISTENT_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteLunchFromNonexistentRestaurantMustReturn404StatusCode() throws Exception
    {
        mockMvc.perform(delete(ADMIN_URL + RESTAURANTS_URL + NONEXISTENT_ID + LUNCHES_URL + GRILLE_GURME.getId())
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void postRestaurantWithInvalidFieldsMustReturn422StatusCode() throws Exception
    {
        Restaurant created = new RestaurantBuilder()
                .build();

        mockMvc.perform(post(ADMIN_URL + RESTAURANTS_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void postLunchWithInvalidFieldsMustReturn422StatusCode() throws Exception
    {
        Lunch created = new LunchBuilder()
                .withName("Name")
                .withDescription("")
                .withPrice(1.00f)
                .build();

        mockMvc.perform(post(ADMIN_URL + RESTAURANTS_URL + MCDONALDS_ID + LUNCHES_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}