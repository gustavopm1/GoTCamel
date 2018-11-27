package com.github.gustavopm1.gotcamel.services.themoviedb.person;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class PersonSearchService extends TheMovieDBAbstractRequestService {

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


    public Response<Person> getPerson(@Header(TYPE_VALUE) String personName, @Headers Map<String,Object> headers) throws InterruptedException {

        ResponseEntity<String> response = doGet(headers);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            try {

                JsonNode json = new ObjectMapper().readTree(response.getBody());

                List<Person> people = new ObjectMapper().readValue(json.get("results").toString(), new TypeReference<List<Person>>(){});

                Response<Person> responsePerson = Response.<Person>builder()
                        .found(true)
                        .body(people.get(0))
                        .build();


                headers.put(TYPE_NAME, SearchType.MOVIEID);
                headers.put(TYPE_VALUE, responsePerson.getBody().getId());

                return personSearchByIdService.getPersonById(Integer.toString(people.get(0).getId()),headers);

            }catch (Exception e){
                log.error("Erro ao parsear filme!", e);
            }
        }

        return Response.<Person>builder()
                .found(false)
                .build();
    }




}
