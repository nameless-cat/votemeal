package com.agromov.votemeal.web;

import com.agromov.votemeal.UserTestData;
import com.agromov.votemeal.model.User;
import com.agromov.votemeal.service.UserService;
import com.agromov.votemeal.util.UserBuilder;
import com.agromov.votemeal.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static com.agromov.votemeal.TestUtil.userHttpBasic;
import static com.agromov.votemeal.UserTestData.*;
import static com.agromov.votemeal.web.UserController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

/**
 * Created by A.Gromov on 12.06.2017.
 */
public class UserControllerTest
        extends AbstractControllerTest
{
    @Autowired
    private UserService service;

    private static final String PROFILE_URL = "/profile/";

    @Test
    public void getUserProfileMustReturnDataOfAuthorizedUser() throws Exception
    {
        mockMvc.perform(get(BASE_URL + PROFILE_URL + MARIA.getId())
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MARIA))
                .andExpect(status().isOk());
    }

    @Test
    public void attemptToAccessToAnotherUserProfileMustResults403Response() throws Exception
    {
        mockMvc.perform(get(BASE_URL + PROFILE_URL + OLEG.getId())
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void putChangesOfUserProfileMustReflectChangesInDBAndReturn202Status() throws Exception
    {
        User updated = UserTestData.getUpdated();

        mockMvc.perform(put(BASE_URL + PROFILE_URL + MARIA_ID)
                .with(userHttpBasic(MARIA))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isAccepted());

        assertEquals(updated, service.get(MARIA_ID));
    }


    @Test
    public void getUserHistoryMustReturnCorrectListOfVoteHistory() throws Exception
    {
        mockMvc.perform(get(BASE_URL + PROFILE_URL + MARIA.getId() + "/history")
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(VOTE_MATCHER.contentListMatcher((MARIA_HISTORY)))
                .andExpect(status().isOk());
    }

    @Test
    public void getOtherUserHistoryMustReturn403StatusCode() throws Exception
    {
        mockMvc.perform(get(BASE_URL + PROFILE_URL + OLEG.getId() + "/history")
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void registerNewUserMustRefleChangesInDB() throws Exception
    {
        User user = new UserBuilder()
                .withEmail("new.user@yandex.ru")
                .withName("NewUser")
                .withPassword("psswwwd")
                .build();

        mockMvc.perform(post(BASE_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(user)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = service.get(LAST_CREATED_ID++);
        assertEquals(user.getEmail(), created.getEmail());
        assertEquals(user.getName(), created.getName());
    }

    @Test
    public void putUpdatedUserWithInvalidFieldsMustReturn422StatusCode() throws Exception
    {
        User updated = UserTestData.getUpdated();
        updated.setName("");
        updated.setEmail("email");
        updated.setPassword("");

        mockMvc.perform(put(BASE_URL + PROFILE_URL + MARIA_ID)
                .with(userHttpBasic(MARIA))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Transactional(propagation = Propagation.NEVER)
    @Test
    public void tryToSaveUserWithDuplicatedEmailMustReturn409StatusCode() throws Exception
    {
        User user = UserTestData.getUpdated();
        user.setEmail("user@gmail.com");

        mockMvc.perform(put(BASE_URL + PROFILE_URL + MARIA_ID)
                .with(userHttpBasic(MARIA))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(user)))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}