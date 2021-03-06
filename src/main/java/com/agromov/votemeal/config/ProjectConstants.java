package com.agromov.votemeal.config;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.agromov.votemeal.config.ProjectProperties.*;

/**
 * Created by A.Gromov on 17.06.2017.
 */
public class ProjectConstants
{
    private Map<String, Object> projectSettings;

    public ProjectConstants() {
        projectSettings = new ConcurrentHashMap<>();
    }

    public ProjectConstants(Map<String, Object> projectSettings)
    {
        this.projectSettings = projectSettings;
    }

    public Map<String, Object> getProjectSettings()
    {
        return projectSettings;
    }

    public void setProjectSettings(Map<String, Object> projectSettings)
    {
        this.projectSettings = projectSettings;
    }

    public LocalTime getVoteDeadline()
    {
        return (LocalTime) projectSettings.computeIfAbsent(VOTE_DEADLINE, s -> LocalTime.of(11, 0));
    }

    public int getBatchSize()
    {
        return (int) projectSettings.computeIfAbsent(JPA_BATCH_SIZE, s -> 10);
    }

    public void setParam(String param, Object value)
    {
        projectSettings.put(param, value);
    }
}
