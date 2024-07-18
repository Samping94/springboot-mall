package com.sam.springbootmall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderQueryParams {
    private Integer userId;
    private Integer limit;
    private Integer offset;
}
