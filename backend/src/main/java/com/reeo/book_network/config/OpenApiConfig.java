package com.reeo.book_network.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Muhammad Rio",
            email = "muhrio.perm@gmail.com",
            url = "https://twitter.com/RyoPermana33"
        ),
        description = "OpenApi documentation for spring security",
        title = "OpenAPI specification - Rio",
        version = "1.0.0",
        license = @License(
            name = "MIT-License",
            url = "https://www.mit.edu/~amini/LICENSE.md"
        ),
        termsOfService = "Term of service"
    ),
    servers = {
        @Server(
            description = "Local ENV",
            url = "http://localhost:8080/api/v1"
        ),
        @Server(
            description = "Prod ENV",
            url = "https://example.com/"
        )
    }
)
@SecurityScheme(
    name = "BearerAuth",
    description = "JWT auth security",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER,
    bearerFormat = "JWT"
)
public class OpenApiConfig {
}
