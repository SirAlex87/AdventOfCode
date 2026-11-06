package com.borzacchiello.advent_of_code.strategy.impl.task12;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task01.Rotation;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task12Part1Strategy implements TaskStrategy<List<Rotation>, Integer> {

	@Override
	public TaskKey getKey() {
		return new TaskKey(12, 1);
	}

	@Override
	public Integer execute(List<Rotation> rotations) {
		// TODO Auto-generated method stub
		return null;
	}
}
