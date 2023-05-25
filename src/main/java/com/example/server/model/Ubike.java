package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ubike extends CreateTime {
    private Long uBikeId;
    private Integer sno;
    private String sna;
    private Integer tot;
    private Integer sbi;
    private String sarea;
    private String mday;
    private Double lat;
    private Double lng;
    private String ar;
    private String sareaen;
    private String snaen;
    private String aren;
    private Integer bemp;
    private Integer act;
    private String srcUpdateTime;
    private String updateTime;
    private String infoTime;
    private String infoDate;

}
