package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Toeic {
    private Long toeicId;
    private String english;
    private String chinese;
    private String example;
    private String explain;

}
