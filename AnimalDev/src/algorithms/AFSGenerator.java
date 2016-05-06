package algorithms;

import algoanim.primitives.IntArray;
import algoanim.primitives.SourceCode;
import algoanim.primitives.generators.AnimationType;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.ArrayProperties;
import algoanim.properties.SourceCodeProperties;
import algoanim.util.Coordinates;
import algoanim.util.TicksTiming;
import algoanim.util.Timing;

import java.awt.*;

/**
 *
 * @author Yadullah Duman <yadullah.duman@gmail.com>
 *
 */
public class AFSGenerator {
    private Language language;
    private ArrayProperties arrayProperties;
    private SourceCodeProperties scProperties;
    private final static Timing defaultDuration = new TicksTiming(30);

    public AFSGenerator(Language l) {
        language = l;
        language.setStepMode(true);
    }

    private static final String AFS_DESCRIPTION = ""
    		+ "An American flag sort is an efficient, in-place variant of radix " +
            "sort that distributes items into hundreds of buckets. [...] With some optimizations, it is twice as fast " +
            "as quicksort. [...] The name comes by analogy with the Dutch national flag problem in the last step: " +
            "efficiently partition the array into many \"stripes\"." +
            "\n" +
            "American flag sort can only sort integers (or objects that can be interpreted as integers). " +
            "In-place sorting algorithms, including American flag sort, run without allocating a significant " +
            "amount of memory beyond that used by the original array. This is a significant advantage, both in " +
            "memory savings and in time saved copying the array." +
            "\n" +
            "American flag sort works by successively dividing a list of objects into buckets based on the first " +
            "digit of their base-N representation (the base used is referred to as the radix). When N is 2, each " +
            "object can be swapped into the correct bucket by using the Dutch national flag algorithm. " +
            "When N is larger, however, objects cannot be immediately swapped into place, because it is unknown " +
            "where each bucket should begin and end. American flag sort gets around this problem by making two " +
            "passes through the array. The first pass counts the number of objects that belong in each of the N " +
            "buckets. The beginning and end of each bucket in the original array is then computed as the sum of " +
            "sizes of preceding buckets. The second pass swaps each object into place.\n" +
            "\n" +
            "source: https://en.wikipedia.org/wiki/American_flag_sort";

    private static final String AFS_SOURCE_CODE = "" +
            "public void sort(int[] array) {\n" +
            "    final int RADIX = 4;\n" +
            "\n" +
            "    int[] counts = new int[RADIX];\n" +
            "    for (int num : array)\n" +
            "        counts[num % RADIX]++;\n" +
            "\n" +
            "    int[] offsets = new int[RADIX];\n" +
            "    for (int i = 1; i < RADIX; i++)\n" +
            "        offsets[i] = offsets[i - 1] + counts[i - 1];\n" +
            "\n" +
            "    for (int i = 0; i < RADIX; i++) {\n" +
            "        while (counts[i] > 0) {\n" +
            "            int origin = offsets[i];\n" +
            "            int from = origin;\n" +
            "            int num = array[from];\n" +
            "            array[from] = -1;\n" +
            "\n" +
            "            do {\n" +
            "                int to = offsets[num % RADIX]++;\n" +
            "                counts[num % RADIX]--;\n" +
            "                swap(array, to, num);\n" +
            "                from = to;\n" +
            "            } while (from != origin);\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "public void swap(int[] array, int a, int b) {\n" +
            "    int tmp = array[a];\n" +
            "    array[a] = array[b];\n" +
            "    array[b] = tmp;\n" +
            "}";

    private void sort(int[] array, int radix) 
    {
        arrayProperties = new ArrayProperties();
        arrayProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
        arrayProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
        arrayProperties.set(AnimationPropertiesKeys.FILLED_PROPERTY, Boolean.TRUE);
        arrayProperties.set(AnimationPropertiesKeys.ELEMENTCOLOR_PROPERTY, Color.BLACK);
        arrayProperties.set(AnimationPropertiesKeys.ELEMHIGHLIGHT_PROPERTY, Color.RED);
        arrayProperties.set(AnimationPropertiesKeys.CELLHIGHLIGHT_PROPERTY, Color.YELLOW);
        
        scProperties = new SourceCodeProperties();
        scProperties.set(AnimationPropertiesKeys.CONTEXTCOLOR_PROPERTY, Color.BLUE);
        scProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 12));
        scProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, Color.RED);
        scProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);

        IntArray iArray = language.newIntArray(new Coordinates(20, 100), array, "intArray", null, arrayProperties);

        IntArray counts = language.newIntArray(new Coordinates(220, 100), new int[radix], "counts", null, arrayProperties);
        counts.hide();

        IntArray offsets = language.newIntArray(new Coordinates(420, 100), new int[radix], "offsets", null, arrayProperties);
        offsets.hide();

        SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);

        sourceCode.addCodeLine("public void americanFlagSort(int[] array, int radix) {", null, 0, null); 	// 0
        sourceCode.addCodeLine("int[] counts = new int[radix];", null, 1, null); 							// 1
        sourceCode.addCodeLine("int[] offsets = new int[radix];", null, 1, null); 							// 2
        sourceCode.addCodeLine("for (int num : array)", null, 1, null); 									// 3
        sourceCode.addCodeLine("counts[num % radix]++;", null, 2, null); 									// 4
        sourceCode.addCodeLine("for (int i = 1; i < radix; i++)", null, 1, null); 							// 5
        sourceCode.addCodeLine("offsets[i] = offsets[i - 1] + counts[i - 1];", null, 2, null); 				// 6
        sourceCode.addCodeLine("for (int i = 0; i < radix; i++) {", null, 1, null); 						// 7
        sourceCode.addCodeLine("while (counts[i] > 0) {", null, 2, null); 									// 8
        sourceCode.addCodeLine("int origin = offsets[i];", null, 3, null); 									// 9
        sourceCode.addCodeLine("int from = origin;", null, 3, null); 										// 10
        sourceCode.addCodeLine("int num = array[from];", null, 3, null); 									// 11
        sourceCode.addCodeLine("array[from] = -1;", null, 3, null); 										// 12
        sourceCode.addCodeLine("do {", null, 3, null); 														// 13
        sourceCode.addCodeLine("int to = offsets[num % radix]++;", null, 4, null); 							// 14
        sourceCode.addCodeLine("counts[num % radix]--;", null, 4, null); 									// 15
        sourceCode.addCodeLine("int tmp = array[to];", null, 4, null); 										// 16
        sourceCode.addCodeLine("array[to] = num;", null, 4, null); 											// 17
        sourceCode.addCodeLine("num = tmp;", null, 4, null); 												// 18
        sourceCode.addCodeLine("from = to;", null, 4, null); 												// 19
        sourceCode.addCodeLine("} while (from != origin);", null, 3, null); 								// 20
        sourceCode.addCodeLine("}", null, 2, null); 														// 21
        sourceCode.addCodeLine("}", null, 1, null); 														// 22
        sourceCode.addCodeLine("}", null, 0, null); 														// 23

        // run algo
        americanFlagSort(iArray, counts, offsets, sourceCode, radix);

        language.nextStep();
        iArray.hide();
        counts.hide();
        offsets.hide();
        sourceCode.hide();
        
        language.nextStep();
    }

    private void americanFlagSort(IntArray array, IntArray counts, IntArray offsets, SourceCode code, int radix)
    {
        language.nextStep();
        code.highlight(0);
        code.unhighlight(0);

        language.nextStep();
        code.highlight(1);

        language.nextStep();
        counts.show();
        code.unhighlight(1);

        language.nextStep();
        code.highlight(2);

        language.nextStep();
        offsets.show();
        code.unhighlight(2);

        language.nextStep();
        code.highlight(3);

        for (int i = 0; i < array.getLength(); i++)
        {
            code.unhighlight(3);

            int num = array.getData(i);
            int pos = num % radix;

            language.nextStep();
            code.highlight(4);

            // fill counts array
            counts.highlightCell(pos, null, defaultDuration);
            counts.put(pos, counts.getData(pos) + 1, null, defaultDuration);
            counts.unhighlightCell(pos, null, defaultDuration);

            language.nextStep();
            code.unhighlight(4);
        }

        language.nextStep();
        code.unhighlight(3);
        code.highlight(5);

        for (int i = 1; i < radix; i++)
        {
            code.unhighlight(5);

            int sum = offsets.getData(i - 1) + counts.getData(i - 1);

            code.unhighlight(5);

            language.nextStep();
            code.highlight(6);

            // fill offsets array
            offsets.highlightCell(i, null, defaultDuration);
            offsets.put(i, sum, null, defaultDuration);
            offsets.unhighlightCell(i, null, defaultDuration);

            language.nextStep();
            code.unhighlight(6);
        }

        language.nextStep();
        code.highlight(7);

        for (int i = 0; i < radix; i++)
        {
            language.nextStep();
            code.unhighlight(7);
            code.highlight(8);

            while (counts.getData(i) > 0)
            {
                language.nextStep();
                code.unhighlight(8);
                code.highlight(9);

                int origin = offsets.getData(i);

                language.nextStep();
                code.unhighlight(9);
                code.highlight(10);

                int from = origin;

                language.nextStep();
                code.unhighlight(10);
                code.highlight(11);

                int num = array.getData(from);

                language.nextStep();
                code.unhighlight(11);
                code.highlight(12);

                language.nextStep();
                array.highlightCell(from, null, defaultDuration);
                array.put(from, -1, null, defaultDuration);
                array.unhighlightCell(from, null, defaultDuration);

                language.nextStep();
                code.unhighlight(12);
                code.highlight(13);

                do
                {
                    language.nextStep();
                    code.unhighlight(13);
                    code.highlight(14);

                    int to = offsets.getData(num % radix);

                    language.nextStep();
                    offsets.highlightCell(num % radix, null, defaultDuration);
                    offsets.put(num % radix, offsets.getData(num % radix) + 1, null, defaultDuration);
                    offsets.unhighlightCell(num % radix, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(14);
                    code.highlight(15);

                    language.nextStep();
                    counts.highlightCell(num % radix, null, defaultDuration);
                    counts.put(num % radix, counts.getData(num % radix) - 1, null, defaultDuration);
                    counts.unhighlightCell(num % radix, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(15);
                    code.highlight(16);

                    int tmp = array.getData(to);

                    language.nextStep();
                    code.unhighlight(16);
                    code.highlight(17);

                    language.nextStep();
                    array.highlightCell(to, null, defaultDuration);
                    array.put(to, num, null, defaultDuration);
                    array.unhighlightCell(to, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(17);
                    code.highlight(18);

                    num = tmp;

                    language.nextStep();
                    code.unhighlight(18);
                    code.highlight(19);

                    from = to;

                    code.unhighlight(19);
                } while (from != origin);
            }
        }
    }

    public static void main(String[] args) 
    {
        Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "American Flag Sort", "Yadullah Duman", 800, 600);
        AFSGenerator americanFlagSort = new AFSGenerator(language);
        
        int[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
        int radix = 10;
        
        americanFlagSort.sort(array, radix);
        System.out.println(language);
    }
}