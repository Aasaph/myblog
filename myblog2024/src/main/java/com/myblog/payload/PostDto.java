package com.myblog.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostDto {

    private long id;
@NotEmpty
@Size(min = 10,message = "Post desc should be at least 10 characters")
    private String title;

@NotEmpty
    private String content;
@NotEmpty
    private String description;
}
