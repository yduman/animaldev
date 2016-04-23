package Algorithms;

public class AmericanFlagSort {

	public static void sort(int[] array) {
		final int RADIX = 4;

		int[] counts = new int[RADIX];
		for (int num : array)
			counts[num % RADIX]++;

		int[] offsets = new int[RADIX];
		for (int i = 1; i < RADIX; i++)
			offsets[i] = offsets[i - 1] + counts[i - 1];

		for (int i = 0; i < RADIX; i++) {
			while (counts[i] > 0) {
				int origin = offsets[i];
				int from = origin;
				int num = array[from];
				array[from] = -1;

				do {
					int to = offsets[num % RADIX]++;
					counts[num % RADIX]--;
					int tmp = array[to];
					array[to] = num;
					num = tmp;
					from = to;
				} while (from != origin);
			}
		}
		System.out.println(java.util.Arrays.toString(array));
	}
}
