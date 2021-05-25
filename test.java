import ilvanip.Counter;

import java.util.Iterator;
import java.util.HashMap;


class Main
{
	private static String[]strings="ISE MSE ESE TOT ISE MSE ESE TOT ISE".split(" ");

	//Test for constructors.
	private static void constructors()
	{
		System.out.println("Testing constructors:");
		//New empty counter.
		System.out.println("Empty:"+new Counter<String>());
		//Counter from array.
		System.out.println("From Array:"+new Counter<String>(Main.strings));
		//Counter from hashmap.
		HashMap<String,Integer>hm=new HashMap<String,Integer>();
		hm.put("ABC",10);
		hm.put("DEF",-20);
		System.out.println("From HashMap:"+new Counter<String>(hm));
		//Counter from counter.
		System.out.println("From Counter:"+new Counter<String>(new Counter<String>(Main.strings)));
	}

	//Test for updates.
	private static void updates()
	{
		System.out.println("Testing updates");
		Counter<String>c=new Counter<String>(Main.strings);
		System.out.println(c);
		c.add(c);
		System.out.println(c);
		c.subtract(Main.strings);
		System.out.println(c);
	}

	//Test for iteration.
	private static void snapshot()
	{
		System.out.println("Testing iteration");
		Counter<String>c=new Counter<String>(Main.strings);
		System.out.println("Frequent Last");
		Iterator ci=c.orderedSnapshot(false);
		while(ci.hasNext())
			System.out.println(ci.next());
		System.out.println("Frequent First");
		ci=c.orderedSnapshot(true);
		while(ci.hasNext())
			System.out.println(ci.next());
	}

	//Test for intersection and union.
	private static void setOperations()
	{
		System.out.println("Testing set operations.");
		Integer[]i1={1,2,3,4,5,4,3,2,1,1};
		Integer[]i2={3,4,5,4,9,2,5,6};
		Counter<Integer>c1=new Counter<Integer>(i1);
		Counter<Integer>c2=new Counter<Integer>(i2);
		System.out.println("C1: "+c1);
		System.out.println("C2: "+c2);
		System.out.println("Intersection:");
		System.out.println(c1.intersection(c2));
		System.out.println("Union:");
		System.out.println(c1.union(c2));
		
	}

	public static void main(String[]args)
	{
		Main.constructors();
		Main.updates();
		Main.snapshot();
		Main.setOperations();
	}
}

