package guru.qa.country.service;

import guru.qa.country.data.CountryEntity;
import guru.qa.country.data.CountryRepository;
import guru.qa.country.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbCountryService implements CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> allCountries() {
        return countryRepository.findAll()
                .stream()
                .map(ce -> {
                            return new Country(
                                    ce.getName(),
                                    ce.getCode());
                        }

                )
                .toList();

    }

    @Override
    public Country countryByCode(String code) {
        return null;
    }

    @Override
    public Country addCountry(String name, String code) {
        CountryEntity countryEntity = countryRepository.save(
                new CountryEntity(
                        null,
                        name,
                        code
                )
        );

        return countryEntity.toJson();
    }

    @Override
    public Country editCountryByCode(String code, String name) {
        CountryEntity country = countryRepository.findCountryByCode(code);

        return countryRepository.save(
                new CountryEntity(
                        country.getId(),
                        name,
                        code)
        ).toJson();
    }
}
