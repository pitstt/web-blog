package com.pitst.blog.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String anons;

    @Column(name = "full_text")
    private  String fullText;

    private int views;
}
