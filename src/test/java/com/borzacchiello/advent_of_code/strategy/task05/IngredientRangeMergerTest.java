package com.borzacchiello.advent_of_code.strategy.task05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task05.IngredientIdRange;
import com.borzacchiello.advent_of_code.strategy.impl.task05.IngredientRangeMerger;

class IngredientRangeMergerTest {

    @ParameterizedTest
    @MethodSource("provideMergeCases")
    void shouldMergeRanges(
            List<IngredientIdRange> input,
            List<IngredientIdRange> expected) {

        List<IngredientIdRange> result =
                IngredientRangeMerger.merge(input);

        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideMergeCases() {

        return Stream.of(

                // Nessun intervallo.
                Arguments.of(
                        List.of(),
                        List.of()),

                // Un singolo intervallo rimane invariato.
                Arguments.of(
                        List.of(
                                range(10, 20)),
                        List.of(
                                range(10, 20))),

                // Intervalli completamente separati.
                //
                // Tra 15 e 20 esiste un intervallo scoperto,
                // quindi i due range devono rimanere separati.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(20, 25)),
                        List.of(
                                range(10, 15),
                                range(20, 25))),

                // Sovrapposizione parziale.
                //
                // 10-15
                //    14-20
                //
                // Risultato: 10-20.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20)),
                        List.of(
                                range(10, 20))),

                // Gli intervalli condividono lo stesso estremo.
                //
                // 10-15
                //     15-20
                //
                // Risultato: 10-20.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(15, 20)),
                        List.of(
                                range(10, 20))),

                // Intervalli adiacenti.
                //
                // 10-15
                //       16-20
                //
                // Non esiste alcun ID scoperto tra 15 e 16,
                // quindi possono essere rappresentati come 10-20.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(16, 20)),
                        List.of(
                                range(10, 20))),

                // Il secondo intervallo è completamente
                // contenuto nel primo.
                //
                // 10----------------30
                //      15-----20
                //
                // Risultato: 10-30.
                Arguments.of(
                        List.of(
                                range(10, 30),
                                range(15, 20)),
                        List.of(
                                range(10, 30))),

                // Intervalli identici.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(10, 20)),
                        List.of(
                                range(10, 20))),

                // Catena di intervalli sovrapposti.
                //
                // 10-15
                //    14-20
                //        18-------30
                //
                // Risultato: 10-30.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(18, 30)),
                        List.of(
                                range(10, 30))),

                // Catena di intervalli adiacenti.
                //
                // 10-15
                //       16-20
                //             21-30
                //
                // Risultato: 10-30.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(16, 20),
                                range(21, 30)),
                        List.of(
                                range(10, 30))),

                // Sovrapposizione seguita da adiacenza.
                //
                // 10-15
                //    14-20
                //          21-25
                //
                // I primi due producono 10-20.
                // Il terzo è quindi adiacente al risultato.
                //
                // Risultato finale: 10-25.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(21, 25)),
                        List.of(
                                range(10, 25))),

                // Due gruppi indipendenti.
                //
                // Primo gruppo: 10-25.
                // Secondo gruppo: 50-70.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(21, 25),
                                range(50, 60),
                                range(55, 70)),
                        List.of(
                                range(10, 25),
                                range(50, 70))),

                // Gli intervalli non sono ordinati.
                //
                // Il merger deve prima ordinarli per estremo
                // iniziale e successivamente applicare il merge.
                Arguments.of(
                        List.of(
                                range(50, 60),
                                range(10, 15),
                                range(16, 20),
                                range(55, 70)),
                        List.of(
                                range(10, 20),
                                range(50, 70))),

                // Intervallo più grande fornito dopo
                // un intervallo contenuto.
                //
                // Serve a verificare che l'ordine iniziale
                // dell'input non influenzi il comportamento.
                Arguments.of(
                        List.of(
                                range(15, 20),
                                range(10, 30)),
                        List.of(
                                range(10, 30)))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCountCases")
    void shouldCountCoveredIds(
            List<IngredientIdRange> ranges,
            String expected) {

        BigInteger result =
                IngredientRangeMerger.countCoveredIds(ranges);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments> provideCountCases() {

        return Stream.of(

                // Nessun intervallo.
                Arguments.of(
                        List.of(),
                        "0"),

                // Intervallo costituito da un solo ID.
                Arguments.of(
                        List.of(
                                range(10, 10)),
                        "1"),

                // Gli estremi sono inclusivi.
                //
                // 10, 11, 12, 13, 14, 15.
                //
                // Totale: 6.
                Arguments.of(
                        List.of(
                                range(10, 15)),
                        "6"),

                // Sovrapposizione.
                //
                // 10-15
                // 14-20
                //
                // Unione: 10-20.
                //
                // Totale:
                // 20 - 10 + 1 = 11.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20)),
                        "11"),

                // Adiacenza.
                //
                // 10-15 contiene 6 ID.
                // 16-20 contiene 5 ID.
                //
                // Unione: 10-20.
                // Totale: 11.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(16, 20)),
                        "11"),

                // Intervallo contenuto.
                //
                // 15-20 è già completamente compreso
                // dentro 10-30.
                //
                // Totale:
                // 30 - 10 + 1 = 21.
                Arguments.of(
                        List.of(
                                range(10, 30),
                                range(15, 20)),
                        "21"),

                // Intervalli separati.
                //
                // 10-15 = 6 ID.
                // 20-25 = 6 ID.
                //
                // Totale: 12.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(20, 25)),
                        "12"),

                // Catena di sovrapposizioni.
                //
                // 10-15
                // 14-20
                // 18-30
                //
                // Unione: 10-30.
                // Totale: 21.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(18, 30)),
                        "21"),

                // Sovrapposizione seguita da adiacenza.
                //
                // 10-15
                // 14-20
                // 21-25
                //
                // Unione: 10-25.
                // Totale: 16.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(21, 25)),
                        "16"),

                // Due gruppi differenti.
                //
                // 10-15 + 16-20 = 10-20 = 11 ID.
                // 50-60 + 55-70 = 50-70 = 21 ID.
                //
                // Totale: 32.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(16, 20),
                                range(50, 60),
                                range(55, 70)),
                        "32"),

                // Due gruppi con sovrapposizioni.
                //
                // 10-20 + 15-25 = 10-25 = 16 ID.
                // 50-60 + 55-70 = 50-70 = 21 ID.
                //
                // Totale: 37.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(15, 25),
                                range(50, 60),
                                range(55, 70)),
                        "37"),

                // Intervalli identici.
                //
                // L'intera copertura deve essere conteggiata
                // una sola volta.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(10, 20),
                                range(10, 20)),
                        "11"),

                // Stesso insieme precedente fornito
                // in ordine differente.
                //
                // Il conteggio non deve dipendere
                // dall'ordine dell'input.
                Arguments.of(
                        List.of(
                                range(55, 70),
                                range(10, 20),
                                range(50, 60),
                                range(15, 25)),
                        "37")
        );
    }

    /**
     * Crea rapidamente un intervallo per rendere
     * più leggibili i dati dei test parametrizzati.
     */
    private static IngredientIdRange range(
            long start,
            long end) {

        return new IngredientIdRange(
                BigInteger.valueOf(start),
                BigInteger.valueOf(end));
    }
}