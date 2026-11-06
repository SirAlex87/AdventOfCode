package com.borzacchiello.advent_of_code.strategy;

import com.borzacchiello.advent_of_code.model.TaskKey;
/**
 * Definisce il contratto comune per tutte le strategie di risoluzione
 * dei task dell'applicazione.
 *
 * <p>Ogni implementazione rappresenta una specifica combinazione
 * Task/Parte e contiene la logica necessaria per trasformare l'input
 * testuale nel modello richiesto dall'algoritmo ed eseguire
 * l'elaborazione prevista.</p>
 *
 * <p>L'interfaccia rappresenta il componente Strategy dello
 * Strategy Pattern. Il servizio applicativo non conosce
 * l'implementazione concreta, ma delega la selezione della strategia
 * alla {@link TaskStrategyFactory}.</p>
 */

/**
 * Restituisce la chiave che identifica univocamente la strategia.
 *
 * <p>La chiave è composta dal numero del task e dal numero
 * della parte.</p>
 *
 * @return chiave identificativa della strategia
 */

/**
 * Esegue l'elaborazione prevista dalla strategia.
 *
 * <p>L'input corrisponde alle righe del file TXT già lette dal
 * servizio. Ogni implementazione è responsabile dell'eventuale
 * parsing verso il proprio modello specifico e dell'esecuzione
 * dell'algoritmo.</p>
 *
 * @param input righe contenute nel file di input
 * @return risultato prodotto dall'algoritmo
 */
public interface TaskStrategy<I, O> {
	TaskKey getKey();

	O execute(I input);

}
