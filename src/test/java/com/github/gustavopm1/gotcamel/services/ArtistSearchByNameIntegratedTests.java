package com.github.gustavopm1.gotcamel.services;

import com.github.gustavopm1.gotcamel.exceptions.music.ArtistNotFoundException;
import com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.lastfm.music.Artist;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.services.lastfm.music.ArtistSearchService;
import com.github.gustavopm1.gotcamel.services.themoviedb.movie.MovieSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"route","prod"})
public class ArtistSearchByNameIntegratedTests {

    @Autowired
    ArtistSearchService artistSearchService;

    @Test
    public void integratedTestGETArtistByName() throws ArtistNotFoundException {

        Artist artist = Artist.builder()
                .mbid("bfcc6d75-a6a5-4bc6-8282-47aec8531818")
                .name("Cher")
                .build();

        HashMap< String, Object> headers = new HashMap<>();

        headers.put(TYPE_NAME, SearchType.ARTISTNAME);
        headers.put(TYPE_VALUE, artist.getName());

        Response<Artist> response = artistSearchService.getArtist(artist.getName(), headers);

        assertTrue(response.isFound());
        assertEquals(artist.getMbid(), response.getBody().getMbid());

    }

}
