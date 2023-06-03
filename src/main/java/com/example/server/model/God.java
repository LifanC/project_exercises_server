package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class God {
    private Long godId;
    private String name;
    private String chatper;
    private String section;
    private String detailCn;
    private String detailEn;
}
