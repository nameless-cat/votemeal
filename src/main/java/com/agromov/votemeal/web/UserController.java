package com.agromov.votemeal.web;

import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.service.UserService;
import com.agromov.votemeal.util.Authorized;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.AccessControlException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by A.Gromov on 12.06.2017.
 */
@RestController
public class UserController
{
    private static final Logger LOG = getLogger(UserController.class);

    @Autowired
    private UserService service;

    //todo пользователю отдается поле с ролью. Подумать, нужно ли оставить так как есть или отдавать только в определенных ситуациях
    @GetMapping(value = "/profile/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUserProfile(@PathVariable("id") Long id)
    {
        checkIdConsistent(id);
        return service.get(id);
    }

    @PutMapping(value = "/profile/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> updateUserProfile(@PathVariable("id") Long id, @RequestBody User user)
    {
        checkIdConsistent(id);
        checkIdConsistent(user.getId());
        return new ResponseEntity<User>(service.update(user), HttpStatus.ACCEPTED);
    }

    @JsonView(ViewWhen.GetVoteHistory.class)
    @GetMapping(value = "/profile/{id}/history", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<VoteHistory> getHistory(@PathVariable("id") Long id)
    {
        checkIdConsistent(id);
        return service.getHistory(id);
    }

    @JsonView(ViewWhen.SendUser.class)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody User user)
    {
        // todo проверить id на null прежде чем сохранять
        User saved = service.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/profile" + "/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(saved);
    }

    private void checkIdConsistent(Long id)
    {
        if (!id.equals(Authorized.getId()))
        {
            LOG.debug("Requested user id({}) not match with authorized user id({})", id, Authorized.getId());
            throw new AccessControlException("Access to user profile denied");
        }
    }
}
