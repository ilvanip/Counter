import ilvanip.Counter;

import java.util.Iterator;
import java.util.HashMap;


class Main
{
	private static String[]strings="ISE MSE ESE TOT ISE MSE ESE TOT ISE".split(" ");

	private static void constructors()
	{
		//New empty counter.
		System.out.println(new Counter<String>());

		//Counter from array.
		System.out.println(new Counter<String>(Main.strings));

		//Counter from hashmap.
		HashMap<String,Integer>hm=new HashMap<String,Integer>();
		hm.put("ABC",10);
		hm.put("DEF",-20);
		System.out.println(new Counter<String>(hm));

		//Counter from counter.
		System.out.println(new Counter<String>(new Counter<String>(Main.strings)));
	}

	private static void updates()
	{
		Counter<String>c=new Counter<String>(Main.strings);
		System.out.println(c);
		c.add(c);
		System.out.println(c);
		c.subtract(Main.strings);
		System.out.println(c);
	}


	private static void snapshot()
	{
		Counter<String>c=new Counter<String>(Main.strings);
		Iterator ci=c.orderedSnapshot(false);
		while(ci.hasNext())
			System.out.println(ci.next());
	}

	public static void main(String[]args)
	{
//		Main.constructors();
//		Main.updates();
		Main.snapshot();
	}
}

