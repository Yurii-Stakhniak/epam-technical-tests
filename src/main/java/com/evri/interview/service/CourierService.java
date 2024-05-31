package com.evri.interview.service;

import com.evri.interview.exception.CourierNotFoundException;
import com.evri.interview.model.Courier;
import com.evri.interview.model.CourierFullName;
import com.evri.interview.repository.CourierEntity;
import com.evri.interview.repository.CourierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.evri.interview.validator.FullNameValidator.MIN_WORDS_COUNT_IN_FULL_NAME;

@Service
@AllArgsConstructor
public class CourierService {

    private CourierTransformer courierTransformer;
    private CourierRepository repository;

    public List<Courier> getCouriers(boolean isActive) {
        return isActive
                ? getActiveCouriers()
                : getAllCouriers();
    }

    private List<Courier> getActiveCouriers() {
        return repository.findAllByActive(true)
                .stream()
                .map(courierTransformer::toCourier)
                .collect(Collectors.toList());
    }

    private List<Courier> getAllCouriers() {
        return repository.findAll()
                .stream()
                .map(courierTransformer::toCourier)
                .collect(Collectors.toList());
    }

    public Courier updateCourier(Long courierId, Courier courier) {

        CourierEntity courierEntity = repository.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException("Courier not found with id: " + courierId));

        CourierFullName courierFullName = getFullName(courier.getName());
        courierEntity.setFirstName(courierFullName.getFirstName());
        courierEntity.setLastName(courierFullName.getLastName());
        courierEntity.setActive(courier.isActive());
        return courierTransformer.toCourier(repository.save(courierEntity));
    }

    private CourierFullName getFullName(String name) {
        CourierFullName courierFullName = new CourierFullName();
        String[] names = name.split("\\s+", MIN_WORDS_COUNT_IN_FULL_NAME);
        courierFullName.setFirstName(names[0]);
        courierFullName.setLastName(names[1]);
        return courierFullName;
    }
}
