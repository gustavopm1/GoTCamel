package com.github.gustavopm1.gotcamel.services.lastfm.music;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.lastfm.music.Artist;
import com.github.gustavopm1.gotcamel.services.LastFMAbstractRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class ArtistSearchByIdService extends LastFMAbstractRequestService {


    @Override
    public String getURL(Map<String, Object> headers) {
        return null;
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return null;
    }

    @Override
    public Map<String, String> getParams(Map<String, Object> params) {
        return null;
    }


    public Response<Artist> getArtistById(@Header(TYPE_VALUE) String midb, @Headers Map<String,Object> headers ){
        ResponseEntity<String> response = doGet(headers);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            try {



            }catch (Exception e) {
                log.error("Error on parse!", e);
            }
        }

        return Response.<Artist>builder()
                .found(false)
                .build();
    }



}
