package guru.qa.country.service.soap;


import guru.qa.country.config.AppConfig;
import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import guru.qa.country.service.CountryService;
import guru.qa.xml.country.*;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {

    private final CountryService countryService;

    public CountryEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "idRequest")
    @ResponsePayload
    public CountryResponse country(@RequestPayload IdRequest request) {
        CountryGql country = countryService.countryGqlById(request.getId());
        CountryResponse response = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(country.id().toString());
        xmlCountry.setName(country.name());
        xmlCountry.setCode(country.code());
        response.setCountry(xmlCountry);

        return response;
    }

    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "pageRequest")
    @ResponsePayload
    public CountriesResponse allCountriesPage(@RequestPayload PageRequest request) {
        Page<CountryGql> countries = countryService.allGqlCountries(
                org.springframework.data.domain.PageRequest.of(
                        request.getPage(),
                        request.getSize()
                )
        );
        CountriesResponse response = new CountriesResponse();
        response.setTotalPages(countries.getTotalPages());
        response.setTotalElements(countries.getTotalElements());
        response.getCountries().addAll(
                countries.getContent().stream()
                        .map(countryGql -> {
                            Country xmlCountry = new Country();
                            xmlCountry.setId(countryGql.id().toString());
                            xmlCountry.setName(countryGql.name());
                            xmlCountry.setCode(countryGql.code());
                            return xmlCountry;
                        }).toList()
        );

        return response;
    }

    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "countryInputRequest")
    @ResponsePayload
    public CountryResponse addCountry(@RequestPayload CountryInputRequest request) {
        CountryGql country = countryService.addCountryGql(
                new CountryInputGql(request.getName(), request.getCode())
        );
        CountryResponse response = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(country.id().toString());
        xmlCountry.setName(country.name());
        xmlCountry.setCode(country.code());
        response.setCountry(xmlCountry);

        return response;
    }
}
