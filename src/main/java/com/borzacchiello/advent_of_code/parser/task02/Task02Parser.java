package com.borzacchiello.advent_of_code.parser.task02;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task02.ProductIdRange;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task02Parser implements TaskParser<List<ProductIdRange>> {

    private static final int TASK_NUMBER = 2;

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public List<ProductIdRange> parse(List<String> lines) {

        String input = String.join("", lines);

        return Arrays.stream(input.split(","))
                .map(String::strip)
                .filter(range -> !range.isBlank())
                .map(this::parseRange)
                .toList();
    }

    private ProductIdRange parseRange(String range) {

        String[] values = range.split("-");
        
        if (values.length != 2) {
            throw new InvalidTaskInputException(
                    "Invalid product ID range: " + range);
        }

        try {

            BigInteger start = new BigInteger(values[0].strip());
            BigInteger end = new BigInteger(values[1].strip());

            if (start.compareTo(end) > 0) {
                throw new InvalidTaskInputException(
                        "Invalid product ID range: start is greater than end");
            }

            return new ProductIdRange(start, end);

        } catch (NumberFormatException e) {

            throw new InvalidTaskInputException(
                    "Invalid product ID range: " + range);
        }
    }
}
