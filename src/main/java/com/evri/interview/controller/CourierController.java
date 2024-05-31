package com.evri.interview.controller;

import com.evri.interview.model.Courier;
import com.evri.interview.service.CourierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/couriers")
public class CourierController {

    private CourierService courierService;

    @GetMapping()
    public ResponseEntity<List<Courier>> getAllCouriers(@RequestParam(name = "isActive", defaultValue = "false") boolean isActive) {

        return ResponseEntity.ok(courierService.getCouriers(isActive));
    }

    @PutMapping("/{courierId}")
    public ResponseEntity<Courier> updateCourier(@PathVariable Long courierId, @RequestBody Courier courier) {

        return ResponseEntity.ok(courierService.updateCourier(courierId, courier));
    }
}
