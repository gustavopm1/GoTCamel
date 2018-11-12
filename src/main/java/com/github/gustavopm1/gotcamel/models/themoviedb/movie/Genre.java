package com.github.gustavopm1.gotcamel.models.themoviedb.movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    Integer id;
    String name;
}
