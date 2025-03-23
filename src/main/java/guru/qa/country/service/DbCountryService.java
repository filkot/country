package guru.qa.country.service;

import guru.qa.country.data.CountryEntity;
import guru.qa.country.data.CountryJson;
import guru.qa.country.data.CountryRepository;
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
    public List<CountryJson> allCountries() {
        return countryRepository.findAll()
                .stream()
                .map(ce -> {
                            return new CountryJson(
                                    ce.getName(),
                                    ce.getCode());
                        }

                )
                .toList();

    }

    @Override
    public CountryJson countryByCode(String code) {
        return countryRepository.findCountryByCode(code).toJson();
    }

    @Override
    public CountryJson addCountry(String name, String code) {
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
    public CountryJson editCountryByCode(String code, String name) {
        CountryEntity country = countryRepository.findCountryByCode(code);

        return countryRepository.save(
                new CountryEntity(
                        country.getId(),
                        name,
                        code)
        ).toJson();
    }
}
