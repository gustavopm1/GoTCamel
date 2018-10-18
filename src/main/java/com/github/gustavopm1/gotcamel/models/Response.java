package com.github.gustavopm1.gotcamel.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {

    T body;
    boolean found;
}
