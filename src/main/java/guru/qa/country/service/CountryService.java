package guru.qa.country.service;


import guru.qa.country.data.CountryJson;

import java.util.List;

public interface CountryService {

    List<CountryJson> allCountries();

    CountryJson countryByCode(String code);

    CountryJson addCountry(String name, String code);

    CountryJson editCountryByCode(String code, String name);
}
