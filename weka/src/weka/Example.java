package weka;


public class Example {
	public static void sort(Comparable[] a)
	{
		int N = a.length;
		for (int i = 1; i< N; i++)
		{
			for (int j = i; j>0 && less(a[j],a[j-1]); j--)
				exch(a, j, j-1);
		}
	}
	private static boolean less (Comparable<Comparable> v, Comparable w)
	{	return v.compareTo(w) < 0;	}
	private static void exch (Comparable[] a, int i, int j)
	{
		Comparable t=a[i]; a[i] = a[j]; a[j] = t;
	}
	private static void show(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + "");
			System.out.println();
		}	
	}
	public static boolean isSorted(Comparable[] a)
	{
		for (int i=1; i< a.length; i++)
			if (less(a[i], a[i-1]))	  return false;
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	String[] a = In.readStrings();
		Comparable [] a = {3,6,2,8,4,1};
		sort(a);
	//	assert isSorted(a);
		show(a);
	}

}
