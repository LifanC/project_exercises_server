package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ins_del {
    private Long ins_del_id;
    private String calendarDetails;
    private String ex;
    private String ins;
    private String del;
}
