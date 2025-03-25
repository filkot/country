package guru.qa.country.service;


import guru.qa.country.data.CountryJson;
import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {

    List<CountryJson> allCountries();

    Page<CountryGql> allGqlCountries(Pageable pageable);

    CountryJson countryById(String id);

    CountryGql countryGqlById(String id);

    CountryJson addCountry(String name, String code);

    CountryGql addCountryGql(CountryInputGql countryGql);

    CountryJson countryByCode(String code);

    CountryJson editCountryByCode(String code, String name);


}
