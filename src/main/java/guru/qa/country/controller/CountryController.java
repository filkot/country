package guru.qa.country.controller;

import guru.qa.country.domain.Country;
import guru.qa.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<Country> all() {
        return countryService.allCountries();
    }

    @PostMapping("/add")
    public Country add(@RequestBody Country country) {
        return countryService.addCountry(country.name(), country.code());
    }

    @PatchMapping("/edit")
    public Country edit(@RequestBody Country country) {
        return countryService.editCountryByCode(country.code(), country.name());
    }
}
