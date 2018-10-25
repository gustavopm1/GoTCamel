package com.github.gustavopm1.gotcamel.models.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Crew {
    Integer id;
    String name;
    String department;
}
