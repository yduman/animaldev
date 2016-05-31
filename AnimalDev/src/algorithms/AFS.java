package algorithms;

public class AFS
{
    public static int[] sort(int[] array, int radix) {
        int digitCount = getDigitCount(array);
        int divisor = (int) Math.pow(10, digitCount);
        americanFlagSort(array, 0, array.length, divisor, radix);
        return array;
    }

    private static void americanFlagSort(int[] array, int start, int length, int divisor, int radix) {

        // First pass - find counts
        int[] counts = new int[radix];
        int[] offsets = new int[radix];

        for (int i = start; i < length; i++) {
            int digit = getDigit(array[i], divisor, radix);
            counts[digit]++;
        }

        offsets[0] = start;
        for (int i = 1; i < radix; i++) {
            offsets[i] = counts[i - 1] + offsets[i - 1];
        }

        // Second pass - move into position
        for (int i = 0; i < radix; i++) {
            while (counts[i] > 0) {
                int origin = offsets[i];
                int from = origin;
                int num = array[from];
                array[from] = -1;

                do {
                    int digit = getDigit(num, divisor, radix);
                    int to = offsets[digit]++;
                    counts[digit]--;
                    int tmp = array[to];
                    array[to] = num;
                    num = tmp;
                    from = to;
                } while (from != origin);
            }
        }

        if (divisor > 1) {
            for (int i = 0; i < radix; i++) {
                int begin = (i > 0) ? offsets[i - 1] : start;
                int end = offsets[i];

                if (end - begin > 1) {
                    americanFlagSort(array, begin, end, divisor / 10, radix);
                }
            }
        }

    }

    private static int getDigit(int elem, int divisor, int radix) {
        return (elem / divisor) % radix;
    }

    private static int getDigitCount(int[] array) {
        int maxDigitCount = Integer.MIN_VALUE;
        for (int number : array) {
            int tmp = (int) Math.log10(number) + 1;
            if (tmp > maxDigitCount) {
                maxDigitCount = tmp;
            }
        }
        return maxDigitCount;
    }

    public static void main(String[] args)
    {
        int[] array = {789, 1555, 9, 132, 8, 47, 6, 5, 4, 13, 12, 1, 0};
        int radix = 10;
        sort(array, radix);
        System.out.println(java.util.Arrays.toString(array));
    }
}
