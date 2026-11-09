package com.borzacchiello.advent_of_code.model.task11;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record DeviceGraph(
        Map<String, List<String>> connections) {

    public DeviceGraph {

        connections = connections.entrySet()
                .stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                entry -> List.copyOf(
                                        entry.getValue())));
    }

    public List<String> destinations(
            String device) {

        return connections.getOrDefault(
                device,
                List.of());
    }

    public boolean containsDevice(
            String device) {

        return connections.containsKey(device)
                || connections.values()
                        .stream()
                        .anyMatch(destinations ->
                                destinations.contains(device));
    }
}