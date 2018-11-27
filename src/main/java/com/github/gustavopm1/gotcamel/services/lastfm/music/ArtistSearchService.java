package com.github.gustavopm1.gotcamel.services.lastfm.music;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.exceptions.music.ArtistNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.lastfm.music.Artist;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.services.LastFMAbstractRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class ArtistSearchService extends LastFMAbstractRequestService {


    @Override
    public String getURL(Map<String, Object> headers) {
           return "";
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(Map<String, Object> params) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("method", "artist.search");
        parameters.put("format", getFormat());
        parameters.put("artist",String.valueOf(params.get(TYPE_VALUE)));
        return parameters;
    }


    public Response<Artist> getArtist(@Header(TYPE_VALUE) String artistName, @Headers Map<String,Object> headers ) throws ArtistNotFoundException {
        ResponseEntity<String> response = doGet(headers);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            try {

                List<Artist> artists = getFormat().equals("json") ? getArtistsFromJsonResponse(response) : new ArrayList<>();

                Artist artist = artists.stream()
                        .filter(art -> artistName.equalsIgnoreCase(art.getName()))
                        .findFirst()
                        .orElse(null);

                return Response.<Artist>builder()
                        .found(artist != null)
                        .body(artist)
                        .build();

            }catch (Exception e) {
                log.error("Error on parse!", e);
                throw new ArtistNotFoundException(String.format("artist with name %s not found!",artistName) );
            }
        }

        return Response.<Artist>builder()
                .found(false)
                .build();
    }

    List<Artist> getArtistsFromJsonResponse(ResponseEntity<String> response ) throws Exception{
        JsonNode json = new ObjectMapper().readTree(response.getBody());
        JsonNode jsonResults = json.get("results");
        JsonNode jsonArtistMatches = jsonResults.get("artistmatches");
        List<Artist> artists = new ObjectMapper().readValue(jsonArtistMatches.get("artist").toString(), new TypeReference<List<Artist>>() {});
        return artists;
    }



}
