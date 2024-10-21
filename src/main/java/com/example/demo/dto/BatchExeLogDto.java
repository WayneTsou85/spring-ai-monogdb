package com.example.demo.dto;


import com.example.demo.entity.BatchExeLogEntity;

import java.util.Date;

public record BatchExeLogDto(String jobName, Date startTime, Date endTime, String status) {

    public BatchExeLogDto(String jobName, Date startTime, Date endTime, String status) {
        this.jobName = jobName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public BatchExeLogDto(BatchExeLogEntity entity) {
        this(entity.getJobName(), entity.getStartTime(), entity.getEndTime(), entity.getStatus());
    }
}
