package model;

import java.util.List;

import org.javatuples.Pair;

public class Distance {
	
	private Double distanceLeftTopCorner;
	private Double distanceRightTopCorner;
	private Double distanceLeftBottomCorner;
	private Double distanceRightBottomCorner;
	public Distance (double distanceLeftTopCorner, double distanceRightTopCorner, double distanceLeftBottomCorner,
			double distanceRightBottomCorner) {
		this.distanceLeftTopCorner=distanceLeftTopCorner;
		this.distanceRightTopCorner=distanceRightTopCorner;
		this.distanceLeftBottomCorner=distanceLeftBottomCorner;
		this.distanceRightBottomCorner=distanceRightBottomCorner;
	}
	public Double getDistanceLeftTopCorner() {
		return distanceLeftTopCorner;
	}
	public Double getDistanceRightTopCorner() {
		return distanceRightTopCorner;
	}
	public Double getDistanceLeftBottomCorner() {
		return distanceLeftBottomCorner;
	}
	public Double getDistanceRightBottomCorner() {
		return distanceRightBottomCorner;
	}
	public void setDistanceLeftTopCorner(Double distanceLeftTopCorner) {
		this.distanceLeftTopCorner = distanceLeftTopCorner;
	}
	public void setDistanceRightTopCorner(Double distanceRightTopCorner) {
		this.distanceRightTopCorner = distanceRightTopCorner;
	}
	public void setDistanceLeftBottomCorner(Double distanceLeftBottomCorner) {
		this.distanceLeftBottomCorner = distanceLeftBottomCorner;
	}
	public void setDistanceRightBottomCorner(Double distanceRightBottomCorner) {
		this.distanceRightBottomCorner = distanceRightBottomCorner;
	}
	
	

}
