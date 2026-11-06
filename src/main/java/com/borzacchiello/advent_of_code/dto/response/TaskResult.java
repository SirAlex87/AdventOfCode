package com.borzacchiello.advent_of_code.dto.response;

/**
 * Risposta restituita dopo l'esecuzione di una Strategy.
 *
 * @param task numero del task eseguito
 * @param part parte del task eseguita
 * @param result risultato prodotto dalla Strategy
 */
public record TaskResult(
        int task,
        int part,
        Object result
) {
}