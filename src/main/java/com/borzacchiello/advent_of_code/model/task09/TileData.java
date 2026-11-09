package com.borzacchiello.advent_of_code.model.task09;

import java.util.List;

public record TileData(
        List<TilePosition> redTiles) {

    public TileData {
        redTiles = List.copyOf(redTiles);
    }
}
