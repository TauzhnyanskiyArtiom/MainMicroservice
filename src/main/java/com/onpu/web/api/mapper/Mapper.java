package com.onpu.web.api.mapper;

public interface Mapper<F, T> {

    T map(F object);

}
