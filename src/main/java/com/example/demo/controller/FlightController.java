package com.example.demo.controller;

import com.example.demo.dto.FilterDTO;
import com.example.demo.dto.FlightDTO;
import com.example.demo.model.Flight;
import com.example.demo.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    @GetMapping
    public List<FlightDTO> getFlights(FilterDTO filterDTO) {
        return flightService.getFlightsFiltered(filterDTO);
    }
        @GetMapping("/{number}")
        public Flight getFlight(@PathVariable String number) {
            return flightService.getFlightById(number);
        }
    }