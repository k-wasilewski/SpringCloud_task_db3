package com.springcloud.task_db3;

import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;

import javax.sql.DataSource;

public class TaskConfigurer extends DefaultTaskConfigurer {

    public TaskConfigurer(DataSource dataSource){
        super(dataSource);
    }

}
