package Algorithms;

public class AlgorithmExecutor {
	public static void main(String[] args) {
		int array1[] = { 4, 6, 5, 1, 3, 2 };
		int array2[] = { 4, 6, 5, 1, 3, 2 };

		System.out.println("**** QUICKSELECT ****");
		System.out.println(Quickselect.quickselect(array1, 0, array1.length - 1, 1));

		System.out.println("**** AMERICAN FLAG SORT ****");
		AmericanFlagSort.sort(array2);
	}
}
