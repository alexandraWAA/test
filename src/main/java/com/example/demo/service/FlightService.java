package com.example.demo.service;

import com.example.demo.dto.FilterDTO;
import com.example.demo.dto.FlightDTO;
import com.example.demo.exceptions.FlightNotFoundException;
import com.example.demo.model.Flight;
import com.example.demo.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.specification.FlightSpecification.*;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    public List<FlightDTO> getFlightsFiltered(FilterDTO filterDTO) {
        List<Flight> flights = flightRepository.findAll(Specification.where(byAirline(filterDTO.getAirlineName()))
                .and(byAirportName(filterDTO.getAirportName()))
                .and(byCityName(filterDTO.getCityName()))
                .and(byDepartureDate(filterDTO.getDepartureDate()))
                .and(byArrivalDate(filterDTO.getArrivalDate())));
        return flights
                .stream()
                .map(FlightDTO::from)
                .collect(Collectors.toList());
    }
    public Flight getFlightById(String number) {
        return flightRepository.findByFlightNumber(number).orElseThrow(FlightNotFoundException::new);    }
    }
