package com.borzacchiello.advent_of_code.parser.task03;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.task03.BatteryBank;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task03Parser implements TaskParser<List<BatteryBank>> {

	 private static final int TASK_NUMBER = 3;
	 private static final Pattern VALID_BANK =
	            Pattern.compile("[1-9]+");

	    @Override
	    public int getTaskNumber() {
	        return TASK_NUMBER;
	    }

	    @Override
	    public List<BatteryBank> parse(List<String> lines) {

	        return lines.stream()
	                .filter(line -> line != null && !line.isBlank())
	                .map(String::strip)
	                .map(this::parseBank)
	                .toList();
	    }

	    private BatteryBank parseBank(String line) {

	        if (!VALID_BANK.matcher(line).matches()) {
	            throw new IllegalArgumentException(
	                    "Invalid battery bank: " + line);
	        }

	        return new BatteryBank(line);
	    }
}