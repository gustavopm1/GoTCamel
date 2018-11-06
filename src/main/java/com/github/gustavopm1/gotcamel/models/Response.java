package com.github.gustavopm1.gotcamel.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class  Response<T> implements Serializable {

    T body;
    boolean found;
}
