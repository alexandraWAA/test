package com.example.demo.specification;

import com.example.demo.model.Airline;
import com.example.demo.model.Airport;
import com.example.demo.model.Flight;
import liquibase.repackaged.org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
public class FlightSpecification {
    public static Specification<Flight> byAirline(String airlineName){
        return (root, query, criteriaBuilder) -> {
            if (airlineName == null || airlineName.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            Join<Flight, Airline> airline = root.join("airline");
            return criteriaBuilder.equal(airline.get("name"), airlineName);
        };
    }
    public static Specification<Flight> byAirportName(String airportName){
        return (root, query, criteriaBuilder) -> {
            if(!StringUtils.hasText(airportName)){
                return criteriaBuilder.conjunction();
            }
            Join<Flight, Airport> departAirport = root.join("departureAirport");
            Join<Flight, Airline> arrivalAirport = root.join("arrivalAirport");
            Predicate predicateDepart = criteriaBuilder.equal(departAirport.get("name"), airportName);
            Predicate predicateArrival = criteriaBuilder.equal(arrivalAirport.get("name"), airportName);
            return criteriaBuilder.or(predicateDepart, predicateArrival);
        };
    }
    public static Specification<Flight> byCityName(String cityName){
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(cityName)) {
                return criteriaBuilder.conjunction();
            }
            Join<Flight, Airport> departAirport = root.join("departureAirport");
            Join<Flight, Airport> arrivalAirport = root.join("arrivalAirport");
            Predicate predicateDepart = criteriaBuilder.equal(departAirport.get("city"), cityName);
            Predicate predicateArrival = criteriaBuilder.equal(arrivalAirport.get("city"), cityName);
            return criteriaBuilder.or(predicateDepart, predicateArrival);
        };
    }



    private static Pair<Instant, Instant> toPairDateRange(LocalDate localDate) {
        Instant startOfDay = localDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant endOfDay = localDate.atTime(23,59,59).atZone(ZoneId.of("UTC")).toInstant();
        return Pair.of(startOfDay,endOfDay);
    }
    public static Specification<Flight> byDepartureDate(LocalDate departureDate) {
        return (root, query, criteriaBuilder) -> {
            if (departureDate == null) {
                return criteriaBuilder.conjunction();
            }
            Pair<Instant, Instant> dateRange = toPairDateRange(departureDate);
            return criteriaBuilder.between(root.get("departureDate"), dateRange.getLeft(), dateRange.getRight());
        };
    }
    public static Specification<Flight> byArrivalDate(LocalDate arrivalDate) {
        return (root, query, criteriaBuilder) -> {
            if (arrivalDate == null) {
                return criteriaBuilder.conjunction();
            }
            Pair<Instant, Instant> dateRange = toPairDateRange(arrivalDate);
            return criteriaBuilder.between(root.get("arrivalDate"), dateRange.getLeft(), dateRange.getRight());
        };
    }
        }
