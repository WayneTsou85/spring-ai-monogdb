package com.example.demo.repository;

import com.example.demo.entity.BatchExeLogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BatchExeLogRepository extends CrudRepository<BatchExeLogEntity, String> {

    List<BatchExeLogEntity> findByStartTimeAfterAndEndTimeBefore(Date startTime, Date endTime);

}
