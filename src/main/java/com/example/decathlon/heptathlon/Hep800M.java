package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;

public class Hep800M {

	private int score;
	private double A = 0.11193;
	private double B = 254;
	private double C = 1.88;

	CalcTrackAndField calc = new CalcTrackAndField();


	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) {

		score = calc.calculateTrack(A, B, C, runningTime);

		System.out.println("The result is " + score);
		return score;
	}

}
