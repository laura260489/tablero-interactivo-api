package com.laurarojas.tablerointeractivoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO {
    private String name;
    private String priority;
    private int estimation;
    private Date startDate;
    private Date endDate;
    private String status;
    private String boardId;
    private String userId;
}