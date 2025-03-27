package guru.qa.country.service;

import guru.qa.country.data.CountryEntity;
import guru.qa.country.data.CountryJson;
import guru.qa.country.data.CountryRepository;
import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import guru.qa.country.ex.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
                .map(ce -> new CountryJson(
                        ce.getName(),
                        ce.getCode())

                )
                .toList();

    }

    @Override
    public Page<CountryGql> allGqlCountries(Pageable pageable) {
        return countryRepository.findAll(pageable)
                .map(fe -> new CountryGql(
                        fe.getId(),
                        fe.getName(),
                        fe.getCode())
                );
    }

    @Override
    public CountryJson countryById(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .orElseThrow()
                .toJson();
    }

    @Override
    public CountryGql countryGqlById(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .map(ce -> new CountryGql(
                        ce.getId(),
                        ce.getName(),
                        ce.getCode()
                ))
                .orElseThrow(CountryNotFoundException::new);
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
    public CountryGql addCountryGql(CountryInputGql countryGql) {
        CountryEntity pe = new CountryEntity();
        pe.setName(countryGql.name());
        pe.setCode(countryGql.code());
        CountryEntity saved = countryRepository.save(pe);
        return new CountryGql(
                saved.getId(),
                saved.getName(),
                saved.getCode()
        );
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
