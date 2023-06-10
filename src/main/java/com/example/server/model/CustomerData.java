package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerData {
    private Long id;
    private String userNameId;
    private String userName;
    private String passWord;
    private String createTime;
    private String updateTime;
}
