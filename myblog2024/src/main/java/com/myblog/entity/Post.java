package com.myblog.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "posts")
public class Post {
// This code snippet defines a private long variable called "id" and
//  The @Id annotation indicates that this variable is the primary key.
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

@Column(name = "title", unique = true)
    private String title;

@Column(name = "content", unique = true)
    private String content;

@Column(name ="description", unique = true)
    private String description;

@OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
List<Comment> comments = new ArrayList<Comment>();
}
