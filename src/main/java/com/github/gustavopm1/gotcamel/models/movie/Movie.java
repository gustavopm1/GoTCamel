package com.github.gustavopm1.gotcamel.models.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    Integer id;
    @JsonProperty("original_title")
    String original_title;
    String overview;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date release_date;

    Integer runtime;
    List<Genre> genres;
    List<MovieKeyword> keywords;
    List<MovieCast> cast;
    List<Crew> crew;
}
