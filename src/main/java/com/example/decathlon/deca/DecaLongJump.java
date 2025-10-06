package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;

public class DecaLongJump {

	private int score;
	private double A = 0.14354;
	private double B = 220;
	private double C = 1.4;

	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on distance and height. Measured in centimetres.
	public int calculateResult(double distance) {

					score = calc.calculateField(A, B, C, distance);

		System.out.println("The result is: " + score);
		return score;
	}

}
