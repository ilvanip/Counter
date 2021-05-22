//A Java implementation of the counter in Pythons collections module.
//I have tried my best to replicate the exact methods wherever possible.

package ilvanip;

import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Iterator;

//Java Set union and intersection:
//	https://stackoverflow.com/questions/51113134/union-or-intersection-of-java-sets

//Counter to count items of type I
public class Counter<I>
{
	//Map for storing the counts of items of type I.
	private HashMap<I,Integer>map=new HashMap<I,Integer>();

	//Comparator for comparing items of type I and their counts.
	private ItemCountComparator<I>comp=new ItemCountComparator<I>();

	/*
	Python provides the following initializers:
		Empty, Iterables, Dictionary{items:counts}, Another counter.
	Consequently I provide the following constructor overloads:
		Empty, Iterables, Arrays, Another counter, Maps.
	*/
	//Default constructor creates an empty counter.
	public Counter(){}

	//Overload for array.
	public Counter(I[]items)
	{
		this.add(items);
	}

	//Overload for Iterables(accepts Lists, Sets, etc.)
	public Counter(Iterable<I>items)
	{
		this.add(items);
	}

	//Create a new counter from another counter.
	public Counter(Counter<I>counter)
	{
		this.add(counter);
	}

	//Pythons counter constructor takes a dict/mapping of
	//	items to frequencies as a constructor parameter.
	//We replicate said constructor by providing a Map overload.
	//Initialize a counter with a Map of items.
	//Keys are items and values are counts.
	//This method exists because Pythons counter
	//	accepts a dictionary as a constructor argument.
	public Counter(Map<I,Integer>item_counts)
	{
		this.add(item_counts);
	}

	/*
	Update the counter values. Values can come from arrays, iterables, maps,
		other counters, or can be directly set.
	The method used is 'add', analogous to the
		'update' counter method provided by Python.
	*/
	//Overload for adding 'n' to the count of 'item'
	public void add(I item,int n)
	{
		this.map.put(item,this.getCount(item)+n);
	}

	//Overload for array.
	public void add(I[]items)
	{
		for(I item:items)
			this.add(item,1);
	}

	//Overload for Iterables(accepts Lists, Sets, etc.)
	public void add(Iterable<I>items)
	{
		for(I item:items)
			this.add(item,1);
	}

	//Overload for accepting updates from a Map of items.
	//Keys are items and values are counts.
	public void add(Map<I,Integer>item_counts)
	{
		for(I item:item_counts.keySet())
			this.add(item,item_counts.get(item));
	}

	//Overload for counter.
	//The new counter must count the same item type as that of the current one.
	public void add(Counter<I>counter)
	{
		this.add(counter.map);
	}

	/*
	The opposite of 'add' is 'subtract'.
	*/
	//Overload for subtracting 'n' from the count of 'item'
	public void subtract(I item,int n)
	{
		this.map.put(item,this.getCount(item)-n);
	}

	//Overload for array.
	public void subtract(I[]items)
	{
		for(I item:items)
			this.subtract(item,1);
	}

	//Overload for Iterables.
	//This overload accepts Lists, Sets, etc.
	public void subtract(Iterable<I>items)
	{
		for(I item:items)
			this.subtract(item,1);
	}

	//Overload for accepting updates from a Map of items.
	//Keys are items and values are counts.
	public void subtract(Map<I,Integer>item_counts)
	{
		for(I item:item_counts.keySet())
			this.subtract(item,item_counts.get(item));
	}

	//Overload for counter.
	//The new counter must count the same item type as that of the current one.
	public void subtract(Counter<I>counter)
	{
		this.subtract(counter.map);
	}

	//Get the count of 'item'.
	//If 'item' doesn't exist, 0 is returned.
	public int getCount(I item)
	{
		//Default value to return.
		int count=0;
		//Try to get the exact count.
		try{count=this.map.get(item);}
		catch(NullPointerException npe){}
		//Return whatever we have.
		return count;
	}

	//Explicitly set the count of 'item' to 'count'
	public void setCount(I i,int count)
	{
		this.map.put(i,count);
	}

	//Clear the counter.
	public void clear()
	{
		this.map.clear();
	}

	//This is an iterator that iterates over the counter items
	//	in the order of frequency.
	//If 'frequent_first' is true, then the most frequent items will appear first.
	//Otherwise, they will appear last.
	public CounterIterator<I>orderedSnapshot(boolean frequent_first)
	{
		//If the frequent items must come first, the iterator must be in descending order.
		//So reverse the comparator.
		//Otherwise, use it as is.
		return new CounterIterator<I>(this,frequent_first?this.comp.reversed():this.comp);
	}

	//Counter intersection is defined as the minimum of corresponding counts.
	//Minima lesser than or equal to 0 are dropped.
	public Counter<I>intersection(Counter<I>other)
	{
		//Create a new counter
		Counter<I>counter=new Counter<I>();
		//Repeat for each item in the opposite counter.
		for(I item:other.map.keySet())
		{
			int min_count=Math.min(this.getCount(item),other.getCount(item));
			if(min_count>0)
				counter.add(item,min_count);
		}
		return counter;
	}

	//Counter union is defined as the maximum of corresponding counts.
	//Maxima lesser than or equal to 0 are dropped.
	public Counter<I>union(Counter<I>other)
	{
		//Get all the items in a set first.
		Set<I>items=this.map.keySet();
		items.addAll(other.map.keySet());
		//Create a new counter
		Counter<I>counter=new Counter<I>();
		//Repeat for each entry.
		for(I item:items)
		{
			int max_count=Math.max(this.getCount(item),other.getCount(item));
			if(max_count>0)
				counter.add(item,max_count);
		}
		return counter;
	}

	//Return the contents of the counter as a map.
	public HashMap<I,Integer>toMap()
	{
		return new HashMap<I,Integer>(this.map);
	}

	//The number of items being counted.
	public int size()
	{
		return this.map.size();
	}

	//Remove 'item' from the counter completely.
	public void remove(I item)
	{
		this.map.remove(item);
	}

	//Override for toString()
	public String toString()
	{
		return "Counter("+this.map+")";
	}

}


//An iterator for the Java counter.
//This iterator cannot be imported outside this package.

//Iterator class to iterate over the items in the counter by order of frequency.
//This class is a 'read-only' iterator, i.e., it supports only the 'hasNext()'
//and 'next()' methods.
class CounterIterator<I>implements Iterator<Entry<I,Integer>>
{
	//Queue to store all the items.
	private PriorityQueue<Entry<I,Integer>>pq;

	//End of iteration.
	public boolean hasNext()
	{
		return !this.pq.isEmpty();
	}

	//The next entry.
	public Entry<I,Integer>next()
	{
		return this.pq.remove();
	}

	//'counter' is the calling counter counting items of type 'I'.
	//If 'frequent_first' is true, most frequent items will appear first.
	//Else they will appear last.
	CounterIterator(Counter<I>counter,Comparator<Entry<I,Integer>>c)
	{
		//Initialize the priority queue with the size of the counter and the comparator.
		this.pq=new PriorityQueue<Entry<I,Integer>>(counter.size(),c);
		//Fill in the priority queue.
		for(Entry<I,Integer>entry:counter.toMap().entrySet())
			this.pq.offer(entry);
	}
}

//Comparator for comparing two Entry<I,Integer>s
//We need this to order the items by frequency.
//Given two Entry<I,Integer>s, the comparison is strictly based on the value
//	held by the Entry, i.e., in this case, the item frequency.
//More frequent items are greater than less frequent items.
class ItemCountComparator<I> implements Comparator<Entry<I,Integer>>
{
	public int compare(Entry<I,Integer>e1,Entry<I,Integer>e2)
	{
		if(e1.getValue()>e2.getValue())
			return 1;
		if(e1.getValue()<e2.getValue())
			return -1;
		return 0;
	}
}

