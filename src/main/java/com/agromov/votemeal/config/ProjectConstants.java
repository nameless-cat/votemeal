package com.agromov.votemeal.config;

import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
import java.util.Map;

import static com.agromov.votemeal.config.ProjectProperties.*;

/**
 * Created by A.Gromov on 17.06.2017.
 */
public class ProjectConstants
{
    private Map<String, Object> projectSettings;

    public ProjectConstants() {}

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
        Object deadline = projectSettings.get(VOTE_DEADLINE);

        if (deadline == null)
        {
            return LocalTime.of(11, 0);
        }

        return (LocalTime) deadline;
    }


}