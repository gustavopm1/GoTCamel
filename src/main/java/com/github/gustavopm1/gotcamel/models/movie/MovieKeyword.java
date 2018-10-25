package com.github.gustavopm1.gotcamel.models.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieKeyword {
    Integer id;
    String name;
}
