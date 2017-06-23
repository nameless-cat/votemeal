package com.agromov.votemeal.util;

import com.agromov.votemeal.config.ProjectConstants;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by A.Gromov on 23.06.2017.
 */
public class JpaUtils
{
    @Autowired
    private EntityManager em;

    @Autowired
    private ProjectConstants pc;

    @Transactional
    public <T> void batchInsert(Class<? extends BatchUpdatable> clazz, List<T> args) throws IllegalAccessException, InstantiationException
    {
        Integer defaultBatchSize = em.unwrap(Session.class).getJdbcBatchSize();

        int batchSize = pc.getBatchSize();
        //local batch size change
        em.unwrap(Session.class).setJdbcBatchSize(batchSize);

        for (int i = 0; i < args.size(); ++i)
        {

            em.persist(clazz.newInstance().setArgument(args.get(i)));

            if (i > 0 && i % batchSize == 0)
            {
                //flush a batch of inserts and release memory
                em.flush();
                em.clear();
            }
        }
        em.unwrap(Session.class).setJdbcBatchSize(defaultBatchSize);
    }

    public interface BatchUpdatable<T, C>
    {
        C setArgument(T argument);
    }
}
