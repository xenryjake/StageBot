package com.xenry.stagebot.util;
import java.util.*;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/23/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class MapUtil {
	
	private MapUtil(){}
	
	public static <K,V extends Comparable<? super V>> Map<K,V> sortByValue(Map<K,V> map){
		List<Map.Entry<K,V>> list = new ArrayList<>(map.entrySet());
		list.sort(Map.Entry.comparingByValue());
		Map<K,V> result = new LinkedHashMap<>();
		for(Map.Entry<K,V> entry : list){
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	public static <K,V extends Comparable<? super V>> Map<K,V> sortByValueReverse(Map<K,V> map){
		List<Map.Entry<K,V>> list = new ArrayList<>(map.entrySet());
		list.sort(Map.Entry.comparingByValue());
		Collections.reverse(list);
		Map<K,V> result = new LinkedHashMap<>();
		for(Map.Entry<K,V> entry : list){
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
}
