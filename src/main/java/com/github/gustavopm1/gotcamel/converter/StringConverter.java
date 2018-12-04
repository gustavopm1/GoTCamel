package com.github.gustavopm1.gotcamel.converter;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Component;

@Component
public class StringConverter {

    public String convert(Object param) {

        if (param instanceof String) {
            return String.valueOf(param);
        }
        if (param instanceof Enum) {
            return convert((Enum) param);
        }
        if (param instanceof Integer) {
            return convert((Integer) param);
        }
        if (param instanceof Long) {
            return convert((Long) param);
        }
        if (param instanceof Boolean) {
            return convert((Boolean) param);
        }
        if (param instanceof ActiveMQQueue) {
            return convert((ActiveMQQueue) param);
        }

        return null;
    }

    public String convert(Enum param) {
        return param.name();
    }

    public String convert(Integer param) {
        return String.valueOf(param);
    }

    public String convert(Long param) {
        return String.valueOf(param);
    }

    public String convert(Boolean param) {
        return String.valueOf(param);
    }

    public String convert(ActiveMQQueue param) {
        return param.toString();
    }
}
