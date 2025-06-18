package org.example.phonebook.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;



/**
 * OpenAPI configuration class for non-production environments.
 *
 * <p>This configuration registers a security scheme for JWT-based authentication,
 * enabling proper authorization support in Swagger UI and other OpenAPI tools.
 *
 * <p>The security scheme is defined as a bearer token passed via the {@code Authorization} HTTP header.
 * It is only active when the {@code prod} Spring profile is <strong>not</strong> active.
 *
 * <p>Security scheme details:
 * <ul>
 *     <li><strong>Type:</strong> HTTP</li>
 *     <li><strong>Scheme:</strong> bearer</li>
 *     <li><strong>Bearer format:</strong> JWT</li>
 *     <li><strong>Header name:</strong> Authorization</li>
 *     <li><strong>Description:</strong> Enter the token as {@code Bearer &lt;your_token&gt;}</li>
 * </ul>
 *
 * @see io.swagger.v3.oas.annotations.security.SecurityScheme
 * @see org.springframework.context.annotation.Profile
 */
@Configuration
@Profile("!prod")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization",
        description = "Enter JWT token in the format: 'Bearer <token>'"
)
public class OpenAPIConfiguration {

}
