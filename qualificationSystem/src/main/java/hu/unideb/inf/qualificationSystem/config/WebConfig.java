package hu.unideb.inf.qualificationSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Web configuration for CORS and resource handling.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /** Max age for CORS preflight cache in seconds. */
    private static final int MAX_AGE = 3600;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200",
                        "http://localhost:8084")
                .allowedMethods("GET", "POST", "PUT", "DELETE",
                        "OPTIONS", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE);
    }

    @Override
    public void addResourceHandlers(
            final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(
                            final String resourcePath,
                            final Resource location) throws IOException {
                        Resource requestedResource =
                                location.createRelative(resourcePath);

                        // If the resource exists, return it
                        if (requestedResource.exists()
                                && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        // Only return index.html for navigation
                        // routes (no file extension)
                        // Don't return index.html for static files
                        // (.css, .js, .ico, etc.)
                        if (!resourcePath.contains(".")) {
                            return new ClassPathResource(
                                    "/static/index.html");
                        }

                        // For missing static files, return null
                        // (will result in 404)
                        return null;
                    }
                });
    }
}
