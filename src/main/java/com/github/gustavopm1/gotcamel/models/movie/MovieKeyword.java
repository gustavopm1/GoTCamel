package com.github.gustavopm1.gotcamel.models.movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieKeyword {
    Integer id;
    String name;
}
