package com.example.one_manage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long not_id;
    private String title;
    private String icon;
    private String target;
    private String person;
    private String text;
    private String announceDate;

}
