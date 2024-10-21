package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class SkipCount {

    @Field("read")
    private int read;

    @Field("write")
    private int write;

    @Field("process")
    private int process;
}
