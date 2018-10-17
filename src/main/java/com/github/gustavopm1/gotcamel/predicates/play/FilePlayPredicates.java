package com.github.gustavopm1.gotcamel.predicates.play;

import com.github.gustavopm1.gotcamel.predicates.ComposablePredicate;

public class FilePlayPredicates {

    public static ComposablePredicate isFilePlayHello(){
        return e ->((String)e.getIn().getBody()).startsWith("Hello");
    }

    public static ComposablePredicate isFilePlayGreetName(){
        return e ->((String)e.getIn().getBody()).startsWith("Gustavo");
    }

    public static ComposablePredicate isFilePlayCatMeow(){
        return e ->((String)e.getIn().getBody()).startsWith("cat");
    }

    public static ComposablePredicate isStatsTxtFile(){
        return e ->{
            System.out.println(e.getIn().getHeaders().keySet());
            if (e.getIn().getHeaders().containsKey("CamelFileName")&& e.getIn().getHeaders().get("CamelFileName")!=null){
                return ((String)e.getIn().getHeaders().get("CamelFileName")).equalsIgnoreCase("stats.txt");
            }
            return false;
        };
    }
}
