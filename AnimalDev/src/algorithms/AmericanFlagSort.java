package algorithms;

public class AmericanFlagSort {
	public static void sort(int[] array, int radix) {
		int[] counts = new int[radix];
		int[] offsets = new int[radix];
		
		for (int num : array) {
			counts[num % radix]++; // FIXME here
		}
		
		for (int i = 1; i < radix; i++) {
			offsets[i] = offsets[i - 1] + counts[i - 1];
		}

		for (int i = 0; i < radix; i++) 
		{
			while (counts[i] > 0) 
			{
				int origin = offsets[i];
				int from = origin;
				int num = array[from];
				array[from] = -1;

				do {
					int to = offsets[num % radix]++; // FIXME here
					counts[num % radix]--;
					int tmp = array[to];
					array[to] = num;
					num = tmp;
					from = to;
				} while (from != origin);
			}
		}
		// FIXME here
	}
	
	public static void main(String[] args) {
		int[] a = {23, 22, 21, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		sort(a, 10);
		System.out.println(java.util.Arrays.toString(a));
	}
}
