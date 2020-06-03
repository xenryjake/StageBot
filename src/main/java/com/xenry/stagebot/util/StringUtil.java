package com.xenry.stagebot.util;
import java.util.ArrayList;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/23/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class StringUtil {
	
	private StringUtil(){}
	
	/*//TEST JUST THIS STRING THING
	public static void main(String[] args){
		System.out.println(stringSimilarityScore("martingaricks", "martingarrix"));
	}*/
	
	public static double stringSimilarityScore(String a, String b){
		return stringSimilarityScore(bigram(a), bigram(b));
	}
	
	public static double stringSimilarityScore(List<char[]> bigram1, List<char[]> bigram2) {
		List<char[]> copy = new ArrayList<>(bigram2);
		int matches = 0;
		for (int i = bigram1.size(); --i >= 0;) {
			char[] bigram = bigram1.get(i);
			for (int j = copy.size(); --j >= 0;) {
				char[] toMatch = copy.get(j);
				if (bigram[0] == toMatch[0] && bigram[1] == toMatch[1]) {
					copy.remove(j);
					matches += 2;
					break;
				}
			}
		}
		return (double) matches / (bigram1.size() + bigram2.size());
	}
	
	public static List<char[]> bigram(String input) {
		ArrayList<char[]> bigram = new ArrayList<>();
		for (int i = 0; i < input.length() - 1; i++) {
			char[] chars = new char[2];
			chars[0] = input.charAt(i);
			chars[1] = input.charAt(i+1);
			bigram.add(chars);
		}
		return bigram;
	}
	
}
