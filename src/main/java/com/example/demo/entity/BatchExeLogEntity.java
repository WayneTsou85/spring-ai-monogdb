package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "batch_exe_log")
public class BatchExeLogEntity {
    @MongoId
    private String id;

    @Field("jobName")
    private String jobName;

    @Field("startTime")
    private Date startTime;

    @Field("endTime")
    private Date endTime;

    @Field("status")
    private String status;

    @Field("steps")
    private List<StepExecution> steps;

    @Field("failureExceptions")
    private List<String> failureExceptions;
}
