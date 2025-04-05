package guru.qa.country.service;

import guru.qa.country.domain.graphql.CountryGql;
import guru.qa.country.domain.graphql.CountryInputGql;
import guru.qa.grpc.country.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GrpcCountryService extends CountryServiceGrpc.CountryServiceImplBase {

    private static final Random RANDOM = new Random();
    private final CountryService countryService;

    public GrpcCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void country(IdRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql countryGql = countryService.countryGqlById(request.getId());

        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(countryGql.id().toString())
                        .setName(countryGql.name())
                        .setCode(countryGql.code())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void addCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        CountryGql countryGql = countryService.addCountryGql(
                new CountryInputGql(
                        request.getName(),
                        request.getCode()
                )
        );
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(countryGql.id().toString())
                        .setName(countryGql.name())
                        .setCode(countryGql.code())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> addCountries(StreamObserver<CountResponse> responseObserver) {
        AtomicInteger counter = new AtomicInteger(0);

        return new StreamObserver<CountryRequest>() {
            @Override
            public void onNext(CountryRequest request) {
                // Сохраняем страну в базу
                CountryInputGql country = new CountryInputGql(
                        request.getName(),
                        request.getCode());
                countryService.addCountryGql(country);

                // Увеличиваем счетчик
                counter.incrementAndGet();
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                // Отправляем итоговое количество добавленных стран
                responseObserver.onNext(
                        CountResponse.newBuilder()
                                .setCount(counter.get())
                                .build()
                );
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void randomCountry(CountRequest request, StreamObserver<CountryResponse> responseObserver) {
        List<CountryGql> countries = countryService.allGqlCountries();
        for (int i = 0; i < request.getCount(); i++) {
            CountryGql randomCountry = countries.get(RANDOM.nextInt(countries.size()));
            responseObserver.onNext(
                    CountryResponse.newBuilder()
                            .setId(randomCountry.id().toString())
                            .setName(randomCountry.name())
                            .setCode(randomCountry.code())
                            .build()
            );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }
}
