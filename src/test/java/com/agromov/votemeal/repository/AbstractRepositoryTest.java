package com.agromov.votemeal.repository;

import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by A.Gromov on 23.05.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-db-test.xml",
        "classpath:spring/spring-app-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
abstract public class AbstractRepositoryTest
{
    public final Logger log = getLogger(getClass());

    private int expectedQueries;

    private final String DATA_SOURCE_NAME = "ProxyDS";

    public int getExpectedQueries()
    {
        return expectedQueries;
    }

    public void expectedQueries(int expectedQueries)
    {
        this.expectedQueries = expectedQueries;
    }

    @Before
    public void setUp() throws Exception
    {
        QueryCountHolder.clear();
        QueryCount queryCount = new QueryCount();
        queryCount.setTotal(0);
        QueryCountHolder.put(DATA_SOURCE_NAME, queryCount);
        expectedQueries(-1);
    }

    @After
    public void queryCount()
    {
        if (getExpectedQueries() != -1)
            Assert.assertEquals(getExpectedQueries(), QueryCountHolder.get(DATA_SOURCE_NAME).getTotal());
    }

}
