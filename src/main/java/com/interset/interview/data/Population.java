package com.interset.interview.data;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
/**
 * contain data of population and show stats
 * @author JamelEddine
 *
 */
public class Population {
	List<Object> records;

	public List<Object> getRecords() {
		return records;
	}

	public void setRecords(List<Object> records) {
		this.records = records;
	}
	
	public int getAvgSiblings()
	{
		return InfosRecord.sum_siblings / records.size();
	}
	
	/**
	 * show stats on Map 
	 * @param map
	 * @param best if best is inf of zero we show all map content
	 * @return
	 */
	public String showMapStats(Map<String, Integer> map, int best)
	{
		int nb = 0;
		String results = "";
		for (Entry<String, Integer> entry : map.entrySet()) {
		    String key = entry.getKey();
		    int value = entry.getValue();
		    if(nb < best || best < 0)
		    {
		    	nb ++;
		    	results += key +" ("+value+"), ";
		    }
		    else
		    {
		    	break;
		    }
		   
		}
		
		return results.substring(0, results.lastIndexOf(","));
	}
	
	public void showStats()
	{
		System.out.println("Average Siblings: " + this.getAvgSiblings());
		System.out.println("Best 3 Foods: " + this.showMapStats(sortByValue(InfosRecord.foods), 3));
		System.out.println("Birth Months: " + this.showMapStats(InfosRecord.bMonthStats, -1));
	}
	
	/**
	 * this code is used to sort hashmap with values 
	 * https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
	 * @param map
	 * @return
	 */
	private static <K, V> Map<K, V> sortByValue(Map<K, V> map) {
	    List<Entry<K, V>> list = new LinkedList<>(map.entrySet());
	    Collections.sort(list, new Comparator<Object>() {
	        @SuppressWarnings("unchecked")
	        public int compare(Object o1, Object o2) {
	            return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue()).compareTo(((Map.Entry<K, V>) (o2)).getValue());
	        }
	    });

	    Map<K, V> result = new LinkedHashMap<>();
	    
	    for(int i = list.size() - 1 ; i >= 0 ; --i)
	    {
	    	Map.Entry<K, V> entry = list.get(i);
	    	result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
	
	
	
}