package com.lgt.support;

import lombok.Data;

@Data
public class Paging {
    Integer offset;
    Integer limit;

    public Paging(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }
}
