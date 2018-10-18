package com.github.gustavopm1.gotcamel.models.movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {
    String name;
    int year;
}
