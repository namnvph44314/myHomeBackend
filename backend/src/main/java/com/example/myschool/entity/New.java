package com.example.myschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "new")
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "description")
    private String description;
    @Column(name = "icon")
    private String icon;
    @Column(name = "cover_url")
    private String coverUrl;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_at")
    private Timestamp createAt;
    @Column(name = "update_at")
    private Timestamp updateAt;
    @Column(name = "delete_at")
    private Timestamp deleteAt;
}
