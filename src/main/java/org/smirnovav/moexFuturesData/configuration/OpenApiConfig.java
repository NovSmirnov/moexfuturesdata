package org.smirnovav.moexFuturesData.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "MOEX Futures information Api",
                description = "API информационной системы, описывающей фьючерсы, торгуемые на Московской бирже",
                version = "0.0.1-SNAPSHOT",
                contact = @Contact(
                        name = "Alexandr Smirnov",
                        email = "noMail@nomail.ru",
                        url = "noUrl"
                )
        ))
public class OpenApiConfig {
}
