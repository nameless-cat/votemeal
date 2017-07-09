package com.agromov.votemeal.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import javax.annotation.PostConstruct;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * Created by A.Gromov on 23.05.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db-test.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
abstract public class AbstractControllerTest
{
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static
    {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected static AtomicLong LAST_CREATED_ID = new AtomicLong(30);

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }
}
