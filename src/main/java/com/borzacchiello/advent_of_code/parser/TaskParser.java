package com.borzacchiello.advent_of_code.parser;

import java.util.List;


public interface TaskParser<I> {

    int getTaskNumber();

    I parse(List<String> lines);
}