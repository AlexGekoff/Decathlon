package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;

public class HeptLongJump {

	private int score;
	private double A = 0.1888807;
	private double B = 210;
	private double C = 1.41;

	CalcTrackAndField calc = new CalcTrackAndField();


	// Calculate the score based on distance and height. Measured in meters.
	public int calculateResult(double distance) {

						score = calc.calculateField(A, B, C, distance);

		System.out.println("The result is: " + score);
		return score;
	}

}
