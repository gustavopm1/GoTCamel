package com.github.gustavopm1.gotcamel.predicates;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import java.util.Objects;

@FunctionalInterface
public interface ComposablePredicate extends Predicate, java.util.function.Predicate<Exchange> {

    @Override
    default boolean matches(Exchange exchange) {
        return test(exchange);
    }

    @Override
    default ComposablePredicate and(java.util.function.Predicate<? super Exchange> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }

    @Override
    default ComposablePredicate negate() {
        return (t) -> !test(t);
    }

    @Override
    default ComposablePredicate or(java.util.function.Predicate<? super Exchange> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }
}