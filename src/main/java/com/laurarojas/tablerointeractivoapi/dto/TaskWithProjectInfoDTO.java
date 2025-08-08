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
public class TaskWithProjectInfoDTO {
    private String taskId;
    private String taskName;
    private String priority;
    private int estimation;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    
    private String boardId;
    private String boardName;
    
    private String projectId;
    private String projectName;
    
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
}