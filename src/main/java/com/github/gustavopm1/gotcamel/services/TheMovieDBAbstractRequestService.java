package com.github.gustavopm1.gotcamel.services;

import com.github.gustavopm1.gotcamel.services.AbstractRequestService;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public abstract class TheMovieDBAbstractRequestService extends AbstractRequestService {

    /*
     * BASEURL terá que ser "https://api.themoviedb.org/3/"
     * Movie: https://api.themoviedb.org/3/movie/<<movieID>>?api_key=<<apiKeyTMDB>>
     * Search: https://api.themoviedb.org/3/search/movie?api_key=<<apiKeyTMDB>>&query=<<movieName>>
     * */

    @Override
    protected void setAPIKey(Map<String, String> headers) {
        headers.put(API_KEY, configuration.getApiKeyTMDB());
    }

    @Override
    protected String getBaseUrl() {
        return configuration.getBaseUrlTMDB();
    }

    @Override
    protected ResponseEntity handleErrors(Exception e){
        if(e.getMessage().contains("429"))
            return ResponseEntity.status(429).build();
        if(e.getMessage().contains("404"))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }
}
