package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class StepExecution {

    @Field("stepName")
    private String stepName;

    @Field("startTime")
    private Date startTime;

    @Field("endTime")
    private Date endTime;

    @Field("status")
    private String status;

    @Field("readCount")
    private int readCount;

    @Field("writeCount")
    private int writeCount;

    @Field("commitCount")
    private int commitCount;

    @Field("rollbackCount")
    private int rollbackCount;

    @Field("filterCount")
    private int filterCount;

    @Field("skipCount")
    private SkipCount skipCount;
}
