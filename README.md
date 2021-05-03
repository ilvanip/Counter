# Java Counter
> The Python collections modules' counter in Java

#### This is an implementation of an item counter in Java. Effort has been made to mirror the functionality of the existing counter in the Python collections module.

#### The code for the Python collections counter has been given for reference (**Note**: This won't run as all dependencies have been stripped off!).

# Features supported by this counter:
*   #### Generic counter class can count items of any data type.
    *   (**Note**: This is **\*NOT\*** the same as counting items of different types in the same counter. One counter instance can count items of only 1 type. But that type can be whatever you want.)
*   #### Initialization from arrays and iterables(Lists and Sets).
    *   The Python counter supports this type of initialization so why shouldn't we.
*   #### Initialization from a Map of values.
    *   The Python counter supports initialization from a dictionary where keys are items and values are counts.
    *   So we have an overload that accepts a Map<I,Integer>, where I is the type of the items being counted. The values are the counts of those items.
*   #### Initialization from another counter.
*   #### Finding the intersection and union of two counters counting the same type of item.
    *   These methods conform to the reference Python implementation given in the repo.
*   #### Data extraction as a HashMap.
    *   There is a method for getting the data of the counter as a HashMap of item-frequency pairs.
    *   This can be useful if you have existing code that deals with Maps.
*   #### Iteration.
    *   This implementation provides an iterator for iterating over the items of the counter. The order of the items can be set accordingly.
    *   The iterator is read-only and hence cannot be used to modify the counter.
*   #### Printing.
    *   An override for 'toString()' has been provided for convenience.

# Example usage:

#### Examples have been provided in the file named 'test.java'. One sample is provided here for reference.
```java
import ilvanip.Counter;
String[]strings="ISE MSE ESE TOT ISE MSE ESE TOT ISE".split(" ");
//Initialzing a counter of strings from an array of strings.
Counter<String>c=new Counter<String>(strings);
System.out.println(c);
```

# Limitations and workarounds:
*	#### Statistical operations are unavailable on this Counter.
    *   As iterators have been provided, you can implement your own.
*	#### A counter is essentially a Map of sorts. But this implementation is not fully compatible with the Collections Framework as it is not a Map(it doesn't extend AbstractMap). As a result, it cannot be used as an argument to functions that expect a Map as an argument.
    *   You can take the Map view of the counter and work on it from there.
    *   Once done, you can create a new counter from the modified Map.





