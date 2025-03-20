package guru.qa.country.service;


import guru.qa.country.domain.Country;

import java.util.List;

public interface CountryService {

    List<Country> allCountries();

    Country countryByCode(String code);

    Country addCountry(String name, String code);

    Country editCountryByCode(String code, String name);
}
