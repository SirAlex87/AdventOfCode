package com.borzacchiello.advent_of_code.service;

import org.springframework.web.multipart.MultipartFile;

import com.borzacchiello.advent_of_code.exception.InvalidFileException;

/**
 * Definisce il contratto per la validazione dei file ricevuti
 * dall'applicazione.
 *
 * <p>La validazione viene eseguita prima della lettura e prima
 * dell'invocazione della Strategy, impedendo agli algoritmi di
 * ricevere file non conformi ai requisiti applicativi.</p>
 */
public interface FileValidator {
	
	/**
     * Verifica che il file ricevuto soddisfi i requisiti applicativi.
     *
     * @param file file da validare
     * @throws InvalidFileException se il file è nullo, vuoto,
     *         di tipo non supportato o supera eventuali limiti
     */
	void validate(MultipartFile file) throws InvalidFileException;

}
