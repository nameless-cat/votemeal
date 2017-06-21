package com.agromov.votemeal.web;

import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.AccessControlException;
import java.util.List;

import static com.agromov.votemeal.util.ValidationUtils.*;

/**
 * Created by A.Gromov on 12.06.2017.
 */
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController
{
    public static final String BASE_URL = "/v1";

    @Autowired
    private UserService service;

    //todo пользователю отдается поле с ролью. Подумать, нужно ли оставить так как есть или отдавать только в определенных ситуациях
    @GetMapping(value = "/profile/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUserProfile(@PathVariable("id") Long id)
    {
        checkUserIdConsistent(id);
        return service.get(id);
    }

    @PutMapping(value = "/profile/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> updateUserProfile(@PathVariable("id") Long id, @Valid @RequestBody User user)
    {
        //todo нужно проверить старый пароль при сохранении нового. -> несколько полей пароля -> UserTo
        checkForIdPresent(user);
        checkUserIdConsistent(id);
        checkUserIdConsistent(user.getId());
        return new ResponseEntity<User>(service.update(user), HttpStatus.ACCEPTED);
    }

    @JsonView(ViewWhen.SendUser.class)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user)
            throws IllegalArgumentException
    {
        //todo несколько полей пароля -> UserTo
        checkForNew(user);
        User saved = service.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/profile" + "/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(saved);
    }

    @JsonView(ViewWhen.GetVoteHistory.class)
    @GetMapping(value = "/profile/{id}/history", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<VoteHistory> getHistory(@PathVariable("id") Long id)
            throws AccessControlException
    {
        checkUserIdConsistent(id);
        return service.getHistory(id);
    }

    /**
     * todo сделать через {@link ExceptionInfoHandler}
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void catcher()
    {
        System.out.println("email duplicate");
    }
}
