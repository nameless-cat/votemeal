package com.agromov.votemeal.web;

import static com.agromov.votemeal.config.LocalizationCodes.EMAIL_EXISTS;
import static com.agromov.votemeal.util.ValidationUtils.checkForIdPresent;
import static com.agromov.votemeal.util.ValidationUtils.checkForNew;
import static com.agromov.votemeal.util.ValidationUtils.checkUserIdConsistent;
import static com.agromov.votemeal.web.ExceptionInfoHandler.logAndGetErrorInfo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.service.UserService;
import com.agromov.votemeal.util.exception.BadArgumentException;
import com.agromov.votemeal.util.exception.ErrorInfo;
import com.fasterxml.jackson.annotation.JsonView;
import java.net.URI;
import java.security.AccessControlException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by A.Gromov on 12.06.2017.
 */
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

  public static final String BASE_URL = "/v1";

  @Autowired
  private UserService service;

  @Autowired
  private MessageUtils messageUtils;

  @JsonView(ViewWhen.SendUser.class)
  @GetMapping(value = "/profile/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  public User getUserProfile(@PathVariable("id") Long id) {
    checkUserIdConsistent(id);
    return service.get(id);
  }

  @JsonView(ViewWhen.SendUser.class)
  @PutMapping(value = "/profile/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<User> updateUserProfile(@PathVariable("id") Long id,
      @Valid @RequestBody User user) {
    checkForIdPresent(user);
    checkUserIdConsistent(id);
    checkUserIdConsistent(user.getId());
    return new ResponseEntity<>(service.update(user), HttpStatus.ACCEPTED);
  }

  @JsonView(ViewWhen.SendUser.class)
  @PostMapping(value = "/register", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<User> registerUser(@Valid @RequestBody User user)
      throws BadArgumentException {
    checkForNew(user);
    User saved = service.save(user);

    URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/profile" + "/{id}")
        .buildAndExpand(saved.getId()).toUri();

    return ResponseEntity.created(uriOfNewResource).body(saved);
  }

  @JsonView(ViewWhen.GetVoteHistory.class)
  @GetMapping(value = "/profile/{id}/history", produces = APPLICATION_JSON_UTF8_VALUE)
  public List<VoteHistory> getHistory(@PathVariable("id") Long id)
      throws AccessControlException {
    checkUserIdConsistent(id);
    return service.getHistory(id);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ErrorInfo catcher(HttpServletRequest request) {
    return logAndGetErrorInfo(request, "DuplicateDataException",
        messageUtils.getMessage(EMAIL_EXISTS));
  }
}
