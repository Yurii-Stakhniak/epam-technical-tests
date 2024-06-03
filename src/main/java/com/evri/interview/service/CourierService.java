package com.evri.interview.service;

import com.evri.interview.exception.CourierNotFoundException;
import com.evri.interview.exception.IllegalCourierNameException;
import com.evri.interview.model.Courier;
import com.evri.interview.model.CourierFullName;
import com.evri.interview.repository.CourierEntity;
import com.evri.interview.repository.CourierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CourierService {

    public static final int MIN_WORDS_COUNT_IN_FULL_NAME = 2;

    private CourierTransformer courierTransformer;
    private CourierRepository repository;

    public List<Courier> getCouriers(boolean isActive) {
        return isActive
                ? getActiveCouriers()
                : getAllCouriers();
    }

    private List<Courier> getActiveCouriers() {
        log.debug("Fetching the list of all active couriers");
        return repository.findAllByActive(true)
                .stream()
                .map(courierTransformer::toCourier)
                .collect(Collectors.toList());
    }

    private List<Courier> getAllCouriers() {
        log.debug("Fetching the list of all couriers");
        return repository.findAll()
                .stream()
                .map(courierTransformer::toCourier)
                .collect(Collectors.toList());
    }

    public Courier updateCourier(Long courierId, Courier courier) {
        log.debug("Performing update courier  with id {}", courierId);

        CourierEntity courierEntity = repository.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException("Courier not found with id: " + courierId));

        CourierFullName courierFullName = getFullName(courier.getName());
        courierEntity.setFirstName(courierFullName.getFirstName());
        courierEntity.setLastName(courierFullName.getLastName());
        courierEntity.setActive(courier.isActive());
        return courierTransformer.toCourier(repository.save(courierEntity));
    }

    private CourierFullName getFullName(String name) {

        if (name == null) {
            throw new IllegalCourierNameException("Name can not be null");
        }
        String[] nameParts = name.split("\\s+", MIN_WORDS_COUNT_IN_FULL_NAME);

        if (name.isEmpty() || nameParts.length < MIN_WORDS_COUNT_IN_FULL_NAME) {
            throw new IllegalCourierNameException(String.format("Illegal courier full name: %s.\n Name can not be empty or less than two words", name));
        }
        CourierFullName courierFullName = new CourierFullName();
        courierFullName.setFirstName(nameParts[0]);
        courierFullName.setLastName(nameParts[1]);
        log.debug("Courier: first name {}, last name {}", courierFullName.getFirstName(), courierFullName.getLastName());
        return courierFullName;
    }
}
