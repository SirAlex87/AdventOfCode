package com.borzacchiello.advent_of_code.util.manage_files;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;
import org.junit.jupiter.api.Test;

class ClasspathResourceReaderTest {
	private static final String R = "util/sample-resource.txt";

	@Test
	void lines() throws IOException {
		assertEquals(List.of("prima", "seconda", "terza"), ClasspathResourceReader.readLines(R));
	}

	@Test
	void string() throws IOException {
		var s = System.lineSeparator();
		assertEquals("prima" + s + "seconda" + s + "terza" + s, ClasspathResourceReader.readString(R));
	}

	@Test
	void invalid() {
		assertThrows(IOException.class, () -> ClasspathResourceReader.readLines("missing.txt"));
		assertThrows(IllegalArgumentException.class, () -> ClasspathResourceReader.readLines(null));
		assertThrows(IllegalArgumentException.class, () -> ClasspathResourceReader.readLines(" "));
	}
}
