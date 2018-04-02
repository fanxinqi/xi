package com.xyb.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

//@Entity
//@Table(name = "member_Category")
public class MaterialEntity {
    private List<String> imageList;
    private String manufacturer;
    private long specificationId;
    private int num;
}
