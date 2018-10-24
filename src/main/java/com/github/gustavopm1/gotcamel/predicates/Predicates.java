package com.github.gustavopm1.gotcamel.predicates;

import com.github.gustavopm1.gotcamel.models.SearchType;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.MOVIE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_NAME;

public class Predicates {



    public static ComposablePredicate isFindMovieByName(){
        return e ->{
            System.out.println("\nTYPE_NAME: "+e.getIn().getHeader(TYPE_NAME));
            return (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.MOVIENAME);
        };
    }

    public static ComposablePredicate isFindMovieById(){
        return e-> (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.MOVIEID);
    }

    public static ComposablePredicate isFindMovieCrewByMovieId(){
        return e -> (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.CREWMOVIEID);
    }

    public static ComposablePredicate isFindMovieCrewByMovieName(){
        return e-> {
            boolean isthatit = (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.CREWMOVIENAME);
            return isthatit;
        };
    }

    public static ComposablePredicate isFindMovieCastByMovieId(){
        return e -> (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.CASTMOVIEID);
    }

    public static ComposablePredicate isFindMovieCastByMovieName(){
        return e -> (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.CASTMOVIENAME);
    }

    public static ComposablePredicate isFindMovieKeywordsByMovieName(){
        return e-> (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.KEYWORDSMOVIENAME);
    }

    public static ComposablePredicate isFindMovieKeywordsByMovieId(){
        return e -> (e.getIn().getHeader(TYPE_NAME)).equals(SearchType.KEYWORDSMOVIEID);
    }

}
