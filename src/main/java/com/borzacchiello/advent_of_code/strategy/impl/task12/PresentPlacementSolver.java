package com.borzacchiello.advent_of_code.strategy.impl.task12;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.borzacchiello.advent_of_code.model.task12.GridPosition;
import com.borzacchiello.advent_of_code.model.task12.PresentRegion;
import com.borzacchiello.advent_of_code.model.task12.PresentShape;
import com.borzacchiello.advent_of_code.model.task12.ShapeOrientation;

public final class PresentPlacementSolver {

    private PresentPlacementSolver() {
        // Classe di utility non istanziabile.
    }

    public static boolean canFit(
            List<PresentShape> shapes,
            PresentRegion region) {

        // Primo filtro:
        // se l'area richiesta supera quella disponibile,
        // il posizionamento è sicuramente impossibile.
        if (!hasEnoughArea(
                shapes,
                region)) {

            return false;
        }

        // Il controllo dell'area è necessario,
        // ma non garantisce da solo il posizionamento.
        List<PresentInstance> presents =
                createPresentInstances(
                        shapes,
                        region);

        // Nessun regalo richiesto.
        if (presents.isEmpty()) {
            return true;
        }

        // Per ciascun regalo vengono calcolate tutte
        // le collocazioni ottenibili tramite rotazioni
        // e riflessioni.
        List<PlacementGroup> placementGroups =
                createPlacementGroups(
                        presents,
                        region);

        // Se almeno un regalo non ha una collocazione
        // possibile, il fit è impossibile.
        if (placementGroups.stream()
                .anyMatch(group ->
                        group.placements().isEmpty())) {

            return false;
        }

        // Le forme più difficili vengono provate prima.
        // Un minor numero di placement riduce i rami
        // esplorati dal backtracking.
        placementGroups.sort(
                Comparator.comparingInt(
                        group ->
                                group.placements().size()));

        BitSet occupied =
                new BitSet(region.area());

        Set<SearchState> failedStates =
                new HashSet<>();

        return search(
                placementGroups,
                0,
                occupied,
                failedStates);
    }

    private static boolean hasEnoughArea(
            List<PresentShape> shapes,
            PresentRegion region) {

        long requiredArea = 0;
        long availableArea =
                region.area();

        for (int shapeIndex = 0;
             shapeIndex < shapes.size();
             shapeIndex++) {

            long shapeArea =
                    shapes.get(shapeIndex)
                            .area();

            long quantity =
                    region.shapeQuantities()
                            .get(shapeIndex);

            long currentRequiredArea =
                    Math.multiplyExact(
                            shapeArea,
                            quantity);

            requiredArea =
                    Math.addExact(
                            requiredArea,
                            currentRequiredArea);

            // Short-circuit:
            // appena l'area richiesta supera quella
            // disponibile non è necessario proseguire.
            if (requiredArea > availableArea) {
                return false;
            }
        }

        return true;
    }

    private static List<PresentInstance>
            createPresentInstances(
                    List<PresentShape> shapes,
                    PresentRegion region) {

        List<PresentInstance> presents =
                new ArrayList<>();

        for (int shapeIndex = 0;
             shapeIndex < shapes.size();
             shapeIndex++) {

            PresentShape shape =
                    shapes.get(shapeIndex);

            int quantity =
                    region.shapeQuantities()
                            .get(shapeIndex);

            for (int instance = 0;
                 instance < quantity;
                 instance++) {

                presents.add(
                        new PresentInstance(
                                shapeIndex,
                                shape));
            }
        }

        return presents;
    }

    private static List<PlacementGroup>
            createPlacementGroups(
                    List<PresentInstance> presents,
                    PresentRegion region) {

        Map<Integer, List<BitSet>>
                placementsByShape =
                new HashMap<>();

        List<PlacementGroup> groups =
                new ArrayList<>();

        for (PresentInstance present :
                presents) {

            List<BitSet> placements =
                    placementsByShape
                            .computeIfAbsent(
                                    present.shapeIndex(),
                                    ignored ->
                                            createPlacements(
                                                    present.shape(),
                                                    region));

            groups.add(
                    new PlacementGroup(
                            placements));
        }

        return groups;
    }

    private static List<BitSet> createPlacements(
            PresentShape shape,
            PresentRegion region) {

        List<ShapeOrientation> orientations =
                ShapeOrientationGenerator.generate(
                        shape);

        List<BitSet> placements =
                new ArrayList<>();

        Set<BitSet> uniquePlacements =
                new HashSet<>();

        for (ShapeOrientation orientation :
                orientations) {

            int maximumRow =
                    region.height()
                            - orientation.height();

            int maximumColumn =
                    region.width()
                            - orientation.width();

            if (maximumRow < 0
                    || maximumColumn < 0) {

                continue;
            }

            for (int row = 0;
                 row <= maximumRow;
                 row++) {

                for (int column = 0;
                     column <= maximumColumn;
                     column++) {

                    BitSet placement =
                            createPlacement(
                                    orientation,
                                    row,
                                    column,
                                    region.width());

                    if (uniquePlacements.add(
                            placement)) {

                        placements.add(placement);
                    }
                }
            }
        }

        return List.copyOf(placements);
    }

    private static BitSet createPlacement(
            ShapeOrientation orientation,
            int startRow,
            int startColumn,
            int regionWidth) {

        BitSet placement =
                new BitSet();

        for (GridPosition cell :
                orientation.cells()) {

            int row =
                    startRow + cell.row();

            int column =
                    startColumn
                            + cell.column();

            int bitIndex =
                    row * regionWidth
                            + column;

            placement.set(bitIndex);
        }

        return placement;
    }

    private static boolean search(
            List<PlacementGroup> groups,
            int presentIndex,
            BitSet occupied,
            Set<SearchState> failedStates) {

        if (presentIndex
                == groups.size()) {

            return true;
        }

        SearchState state =
                new SearchState(
                        presentIndex,
                        occupied);

        if (failedStates.contains(state)) {
            return false;
        }

        PlacementGroup group =
                groups.get(presentIndex);

        for (BitSet placement :
                group.placements()) {

            if (occupied.intersects(
                    placement)) {

                continue;
            }

            BitSet nextOccupied =
                    (BitSet) occupied.clone();

            nextOccupied.or(placement);

            if (search(
                    groups,
                    presentIndex + 1,
                    nextOccupied,
                    failedStates)) {

                return true;
            }
        }

        failedStates.add(state);

        return false;
    }

    private record PresentInstance(
            int shapeIndex,
            PresentShape shape) {
    }

    private record PlacementGroup(
            List<BitSet> placements) {

        PlacementGroup {
            placements =
                    List.copyOf(placements);
        }
    }

    private record SearchState(
            int presentIndex,
            BitSet occupied) {

        SearchState {
            occupied =
                    (BitSet) occupied.clone();
        }
    }
}