package com.github.gustavopm1.gotcamel.services.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.person.Person;
import com.github.gustavopm1.gotcamel.services.AbstractRequestService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.PERSON_ID;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class PersonSearchByIdService extends AbstractRequestService {

    @Autowired
    @Setter
    private GotCamelConfiguration configuration;


    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getPersonUrl().replace(":id", String.valueOf(headers.get(TYPE_VALUE)));
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(Map<String, Object> params) {
        return new HashMap<>();
    }


    public Response<Person> getPersonById(@Header(PERSON_ID) String id, @Headers Map<String, Object> headers) throws InterruptedException {

        ResponseEntity<String> response = doGet(headers);
        System.out.println("Response::" + response.getBody());
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            try {
                Person person = new ObjectMapper().readValue(response.getBody(), Person.class);
                return Response.<Person>builder()
                        .found(true)
                        .body(person)
                        .build();
            } catch (Exception e) {
                log.error("Erro ao parsear pessoa!", e);
            }
        }

        return Response.<Person>builder()
                .found(false)
                .build();
    }
}
