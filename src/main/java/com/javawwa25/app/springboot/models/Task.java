package com.javawwa25.app.springboot.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long task_id;

    private String comments;

    private String task_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date task_created;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date task_startDate;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date task_endDate;

    @Enumerated(EnumType.STRING)
    private Priority task_priority;

    @Enumerated(EnumType.STRING)
    private Progress task_progress;

    // Mapping Tasks with Project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    // mapping tasks with user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
