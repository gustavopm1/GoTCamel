package com.github.gustavopm1.gotcamel.models.movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieCast {
    Integer id;
    String name;
    String character;
    String profile_path;
}
