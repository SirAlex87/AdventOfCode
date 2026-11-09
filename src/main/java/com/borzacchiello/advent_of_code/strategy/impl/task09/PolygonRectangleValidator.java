package com.borzacchiello.advent_of_code.strategy.impl.task09;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.borzacchiello.advent_of_code.model.task09.OrthogonalPolygon;
import com.borzacchiello.advent_of_code.model.task09.TilePosition;
import com.borzacchiello.advent_of_code.model.task09.TileRectangle;

public final class PolygonRectangleValidator {

    private PolygonRectangleValidator() {
        // Classe di utility non istanziabile.
    }

    public static boolean isInside(
            OrthogonalPolygon polygon,
            TileRectangle rectangle) {

        List<Long> relevantRows =
                findRelevantRows(
                        polygon,
                        rectangle);

        for (long row : relevantRows) {

            List<LongRange> coveredIntervals =
                    findCoveredIntervals(
                            polygon,
                            row);

            if (!isContainedInSingleInterval(
                    rectangle.minX(),
                    rectangle.maxX(),
                    coveredIntervals)) {

                return false;
            }
        }

        return true;
    }

    private static List<Long> findRelevantRows(
            OrthogonalPolygon polygon,
            TileRectangle rectangle) {

        TreeSet<Long> rows =
                new TreeSet<>();

        rows.add(rectangle.minY());
        rows.add(rectangle.maxY());

        for (TilePosition vertex :
                polygon.vertices()) {

            long vertexY = vertex.y();

            if (vertexY < rectangle.minY()
                    || vertexY > rectangle.maxY()) {

                continue;
            }

            rows.add(vertexY);

            // In un poligono ortogonale la copertura può
            // cambiare immediatamente dopo una riga sulla
            // quale è presente un segmento orizzontale.
            if (vertexY < rectangle.maxY()) {
                rows.add(vertexY + 1);
            }
        }

        return List.copyOf(rows);
    }

    private static List<LongRange> findCoveredIntervals(
            OrthogonalPolygon polygon,
            long row) {

        List<LongRange> intervals =
                new ArrayList<>();

        intervals.addAll(
                findHorizontalBoundaryIntervals(
                        polygon,
                        row));

        List<Long> intersections =
                findVerticalIntersections(
                        polygon,
                        row);

        // Le intersezioni verticali ordinate vengono
        // considerate a coppie.
        //
        // Ogni coppia identifica un intervallo interno
        // attraversato dalla scanline.
        for (int index = 0;
             index + 1 < intersections.size();
             index += 2) {

            long start =
                    intersections.get(index);

            long end =
                    intersections.get(index + 1);

            intervals.add(
                    new LongRange(
                            start,
                            end));
        }

        return mergeIntervals(intervals);
    }

    private static List<LongRange>
            findHorizontalBoundaryIntervals(
                    OrthogonalPolygon polygon,
                    long row) {

        List<LongRange> intervals =
                new ArrayList<>();

        List<TilePosition> vertices =
                polygon.vertices();

        for (int index = 0;
             index < vertices.size();
             index++) {

            TilePosition first =
                    vertices.get(index);

            TilePosition second =
                    vertices.get(
                            (index + 1)
                                    % vertices.size());

            // Ci interessano solamente i segmenti
            // orizzontali presenti sulla riga corrente.
            if (first.y() != second.y()
                    || first.y() != row) {

                continue;
            }

            long start =
                    Math.min(
                            first.x(),
                            second.x());

            long end =
                    Math.max(
                            first.x(),
                            second.x());

            intervals.add(
                    new LongRange(
                            start,
                            end));
        }

        return intervals;
    }

    private static List<Long>
            findVerticalIntersections(
                    OrthogonalPolygon polygon,
                    long row) {

        List<Long> intersections =
                new ArrayList<>();

        List<TilePosition> vertices =
                polygon.vertices();

        for (int index = 0;
             index < vertices.size();
             index++) {

            TilePosition first =
                    vertices.get(index);

            TilePosition second =
                    vertices.get(
                            (index + 1)
                                    % vertices.size());

            // Nel ray casting orizzontale consideriamo
            // solamente i segmenti verticali.
            if (first.x() != second.x()) {
                continue;
            }

            long minY =
                    Math.min(
                            first.y(),
                            second.y());

            long maxY =
                    Math.max(
                            first.y(),
                            second.y());

            // Usiamo un intervallo semiaperto.
            //
            // In questo modo un vertice condiviso da due
            // segmenti non viene contato due volte.
            if (row >= minY
                    && row < maxY) {

                intersections.add(
                        first.x());
            }
        }

        intersections.sort(
                Comparator.naturalOrder());

        return intersections;
    }

    private static List<LongRange> mergeIntervals(
            List<LongRange> intervals) {

        if (intervals.isEmpty()) {
            return List.of();
        }

        List<LongRange> sortedIntervals =
                intervals.stream()
                        .sorted(
                                Comparator.comparingLong(
                                        LongRange::start))
                        .toList();

        List<LongRange> mergedIntervals =
                new ArrayList<>();

        LongRange current =
                sortedIntervals.getFirst();

        for (int index = 1;
             index < sortedIntervals.size();
             index++) {

            LongRange next =
                    sortedIntervals.get(index);

            if (overlapsOrIsAdjacent(
                    current,
                    next)) {

                current =
                        new LongRange(
                                current.start(),
                                Math.max(
                                        current.end(),
                                        next.end()));
            } else {

                mergedIntervals.add(
                        current);

                current = next;
            }
        }

        mergedIntervals.add(current);

        return List.copyOf(
                mergedIntervals);
    }

    private static boolean overlapsOrIsAdjacent(
            LongRange current,
            LongRange next) {

        if (next.start()
                <= current.end()) {

            return true;
        }

        // Evita overflow nel caso limite Long.MAX_VALUE.
        if (current.end()
                == Long.MAX_VALUE) {

            return false;
        }

        return next.start()
                == current.end() + 1;
    }

    private static boolean isContainedInSingleInterval(
            long rectangleStart,
            long rectangleEnd,
            List<LongRange> intervals) {

        for (LongRange interval :
                intervals) {

            if (rectangleStart
                    >= interval.start()
                    && rectangleEnd
                    <= interval.end()) {

                return true;
            }
        }

        return false;
    }

    private record LongRange(
            long start,
            long end) {
    }
}