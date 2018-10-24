package com.github.gustavopm1.gotcamel.models.movie;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Movie {
    Integer id;
    String original_title;
    String overview;
    int year;
    Date release_date;
    Integer runtime;
    List<Genre> genres;
    List<MovieKeyword> keywords;
    List<MovieCast> cast;
    List<Crew> crew;
}
