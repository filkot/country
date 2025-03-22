package guru.qa.country.controller;

import guru.qa.country.data.CountryJson;
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
    public List<CountryJson> all() {
        return countryService.allCountries();
    }

    @GetMapping("/{code}")
    public CountryJson getCountry(@PathVariable("code") String code) {
        return countryService.countryByCode(code);
    }

    @PostMapping("/add")
    public CountryJson add(@RequestBody CountryJson country) {
        return countryService.addCountry(country.countryName(), country.countryCode());
    }

    @PatchMapping("/edit")
    public CountryJson edit(@RequestBody CountryJson country) {
        return countryService.editCountryByCode(country.countryCode(), country.countryName());
    }
}
