package guru.qa.country.domain.graphql;

import java.util.UUID;

public record CountryGql(UUID id,
                         String name,
                         String code) {
}
