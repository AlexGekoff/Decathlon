package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;

public class HeptHightJump {

	private int score;
	private double A = 1.84523;
	private double B = 75;
	private double C = 1.348;

	CalcTrackAndField calc = new CalcTrackAndField();


	// Calculate the score based on distance and height. Measured in cenimeters.
	public int calculateResult(double distance) {

							score = calc.calculateField(A, B, C, distance);

		System.out.println("The result is: " + score);
		return score;
	}

}
