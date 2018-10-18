package com.github.gustavopm1.gotcamel.configuration;

import lombok.Data;

@Data
public class GotCamelConfigurationRoute {
    GotCamelIdentifications inbound;
    GotCamelIdentifications outbound;

}
