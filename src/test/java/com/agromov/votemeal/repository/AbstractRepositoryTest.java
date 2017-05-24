package com.agromov.votemeal.repository;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by A.Gromov on 23.05.2017.
 */
@Ignore
@ContextConfiguration("classpath:spring/spring-db.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AbstractRepositoryTest
{
    public final Logger log = getLogger(getClass());

}
