package guru.qa.country.service;

import guru.qa.country.controller.error.ApiError;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class CountryErrorAttributes extends DefaultErrorAttributes {

    private final String apiVersion;

    public CountryErrorAttributes(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> defaultMap = super.getErrorAttributes(webRequest, options);
        ApiError apiError = ApiError.fromAttributeMap(apiVersion, defaultMap);
        return apiError.toAttributeMap();
    }
}
