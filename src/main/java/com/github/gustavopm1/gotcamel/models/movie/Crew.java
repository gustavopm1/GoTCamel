package com.github.gustavopm1.gotcamel.models.movie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Crew {
    Integer id;
    String name;
    String department;
}
