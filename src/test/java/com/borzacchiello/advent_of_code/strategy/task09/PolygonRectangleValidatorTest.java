package com.borzacchiello.advent_of_code.strategy.task09;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.task09.OrthogonalPolygon;
import com.borzacchiello.advent_of_code.model.task09.TilePosition;
import com.borzacchiello.advent_of_code.model.task09.TileRectangle;
import com.borzacchiello.advent_of_code.strategy.impl.task09.PolygonRectangleValidator;

class PolygonRectangleValidatorTest {

    private OrthogonalPolygon rectanglePolygon;
    private OrthogonalPolygon concavePolygon;

    @BeforeEach
    void setUp() {

        rectanglePolygon =
                new OrthogonalPolygon(
                        List.of(
                                tile(2, 1),
                                tile(8, 1),
                                tile(8, 5),
                                tile(2, 5)));

        // Poligono concavo con una rientranza centrale.
        //
        // Forma indicativa:
        //
        // 1,1 ------- 7,1
        //  |           |
        //  |           |
        //  |   3---5   |
        //  |   |   |   |
        //  |   |   |   |
        // 1,7--3   5--7,7
        //
        // La zona compresa tra:
        //
        // x = 3..5
        // y = 3..7
        //
        // rappresenta una rientranza e non appartiene
        // all'interno del poligono.

        concavePolygon =
                new OrthogonalPolygon(
                        List.of(
                                tile(1, 1),
                                tile(7, 1),
                                tile(7, 7),
                                tile(5, 7),
                                tile(5, 3),
                                tile(3, 3),
                                tile(3, 7),
                                tile(1, 7)));
    }

    @Test
    void shouldAcceptRectangleInsidePolygon() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(3, 2),
                        tile(7, 4));

        assertTrue(
                PolygonRectangleValidator.isInside(
                        rectanglePolygon,
                        rectangle));
    }

    @Test
    void shouldAcceptRectangleOnPolygonBoundary() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(2, 1),
                        tile(8, 5));

        assertTrue(
                PolygonRectangleValidator.isInside(
                        rectanglePolygon,
                        rectangle));
    }

    @Test
    void shouldRejectRectangleOutsidePolygon() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(1, 1),
                        tile(8, 5));

        assertFalse(
                PolygonRectangleValidator.isInside(
                        rectanglePolygon,
                        rectangle));
    }

    @Test
    void shouldRejectRectangleCrossingPolygonBoundary() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(4, 3),
                        tile(10, 4));

        assertFalse(
                PolygonRectangleValidator.isInside(
                        rectanglePolygon,
                        rectangle));
    }

    @Test
    void shouldRejectRectangleCrossingConcaveIndentation() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(1, 1),
                        tile(7, 7));

        // Gli estremi appartengono al poligono,
        // ma il rettangolo comprende anche la
        // rientranza centrale esterna alla regione.
        assertFalse(
                PolygonRectangleValidator.isInside(
                        concavePolygon,
                        rectangle));
    }

    @Test
    void shouldRejectRectanglePartiallyCrossingConcaveIndentation() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(2, 2),
                        tile(6, 5));

        // Il rettangolo non comprende tutto il bounding box,
        // ma attraversa comunque la rientranza.
        assertFalse(
                PolygonRectangleValidator.isInside(
                        concavePolygon,
                        rectangle));
    }

    @Test
    void shouldAcceptRectangleAboveConcaveIndentation() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(2, 1),
                        tile(6, 2));

        // Il rettangolo si trova completamente sopra
        // l'inizio della rientranza.
        assertTrue(
                PolygonRectangleValidator.isInside(
                        concavePolygon,
                        rectangle));
    }

    @Test
    void shouldAcceptRectangleOnLeftSideOfConcaveIndentation() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(1, 3),
                        tile(3, 7));

        // Il rettangolo occupa solamente la parte sinistra
        // della regione e non attraversa la rientranza.
        assertTrue(
                PolygonRectangleValidator.isInside(
                        concavePolygon,
                        rectangle));
    }

    @Test
    void shouldAcceptRectangleOnRightSideOfConcaveIndentation() {

        TileRectangle rectangle =
                new TileRectangle(
                        tile(5, 3),
                        tile(7, 7));

        // Caso simmetrico sul lato destro.
        assertTrue(
                PolygonRectangleValidator.isInside(
                        concavePolygon,
                        rectangle));
    }

    private static TilePosition tile(
            long x,
            long y) {

        return new TilePosition(
                x,
                y);
    }
}