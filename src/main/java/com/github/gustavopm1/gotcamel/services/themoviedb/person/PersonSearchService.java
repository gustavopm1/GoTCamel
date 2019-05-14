package com.github.gustavopm1.gotcamel.services.themoviedb.person;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.themoviedb.person.Person;
import com.github.gustavopm1.gotcamel.services.TheMovieDBAbstractRequestService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class PersonSearchService extends TheMovieDBAbstractRequestService<Person> {

    @Autowired
    @Setter
    private PersonSearchByIdService personSearchByIdService;

    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getPersonUrlName();
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(Map<String, Object> params) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("query",String.valueOf(params.get(TYPE_VALUE)));
        return parameters;
    }

    @Override
    public Response<Person> requestMovieDBTemplate(String parameter, Map<String, Object> headers) throws MovieNotFoundException {
        Response<Person> personResponse = super.requestMovieDBTemplate(parameter, headers);
        headers.put(TYPE_NAME, SearchType.MOVIEID);
        headers.put(TYPE_VALUE, personResponse.getBody().getId());
        return personSearchByIdService.requestMovieDBTemplate(Integer.toString(personResponse.getBody().getId()),headers);
    }

    @Override
    protected Person createBody(String responseBody) throws IOException {
        JsonNode json = new ObjectMapper().readTree(responseBody);
        Optional<List<Person>> optionalPeople = Optional.ofNullable(new ObjectMapper().readValue(json.get("results").toString(), new TypeReference<List<Person>>(){}));
        return optionalPeople.map(person -> person.get(0)).orElse(null);
    }
}
