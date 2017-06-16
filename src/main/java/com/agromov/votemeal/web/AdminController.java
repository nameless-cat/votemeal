package com.agromov.votemeal.web;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.service.LunchService;
import com.agromov.votemeal.service.RestaurantService;
import com.agromov.votemeal.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

/**
 * Created by A.Gromov on 14.06.2017.
 */
@RestController
@RequestMapping(AdminController.ADMIN_URL)
public class AdminController
{
    //todo добавить версию приложения в каждый путь "/v1/admin/...."
    static final String ADMIN_URL = "/admin/";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private LunchService lunchService;

    @GetMapping(value = "restaurants", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Restaurant> getRestaurants()
    {
        return restaurantService.getAll();
    }

    @GetMapping(value = "restaurants/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Restaurant getRestaurant(@PathVariable("id") Long id)
            throws EntityNotFoundException
    {
        return restaurantService.get(id);
    }



    @PostMapping(value = "restaurants", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant)
            throws IllegalArgumentException
    {
        if (!restaurant.isNew())
        {
            throw new IllegalArgumentException();
        }

        Restaurant saved = restaurantService.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_URL + "restaurants/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(saved);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "restaurants/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateRestaurant(@PathVariable long id, @RequestBody Restaurant restaurant)
            throws EntityNotFoundException, IllegalArgumentException
    {
        if (restaurant.isNew() || !Objects.equals(id, restaurant.getId()))
        {
            throw new IllegalArgumentException();
        }

        restaurantService.update(restaurant);
    }

    @GetMapping(value = {"vote/{date}", "vote"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Vote> getVoteAtDate(@PathVariable(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        return  (date == null) ? voteService.get(currentDate()) : voteService.get(date);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "vote", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addRestaurantsToCurrentVote(@RequestBody List<Long> restaurantIds)
    {
        voteService.add(restaurantIds);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "vote/{id}")
    public void removeRestaurantFromCurrentVote(@PathVariable("id") Long restaurantId)
            throws EntityNotFoundException
    {
        voteService.delete(restaurantId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "restaurants/{id}/lunches", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addLunchToRestaurantMenu(@PathVariable Long id, @RequestBody Lunch lunch)
            throws EntityNotFoundException
    {
        if (!lunch.isNew())
        {
            throw new IllegalArgumentException();
        }
        lunchService.save(id, lunch);
    }

    @GetMapping(value = "restaurants/{id}/lunches/{lunchId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Lunch getLunch(@PathVariable Long id, @PathVariable Long lunchId)
            throws EntityNotFoundException
    {
        return lunchService.get(id, lunchId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "restaurants/{id}/lunches/{lunchId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void putLunch(@PathVariable Long id, @PathVariable Long lunchId, @RequestBody Lunch lunch)
            throws EntityNotFoundException, IllegalArgumentException
    {
        if (lunch.isNew() || !Objects.equals(lunch.getId(), lunchId))
        {
            throw new IllegalArgumentException();
        }

        lunchService.update(id, lunch);
    }

    @DeleteMapping(value = "restaurants/{id}/lunches/{lunchId}")
    public void deleteLunch(@PathVariable Long id, @PathVariable Long lunchId)
            throws EntityNotFoundException
    {
        lunchService.delete(id, lunchId);
    }
}
