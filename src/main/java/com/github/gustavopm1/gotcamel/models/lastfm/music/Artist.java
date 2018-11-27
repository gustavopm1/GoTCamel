package com.github.gustavopm1.gotcamel.models.lastfm.music;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {

    String mbid;
    String name;
}
