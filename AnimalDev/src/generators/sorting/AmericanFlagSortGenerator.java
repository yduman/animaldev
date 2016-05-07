/*
 * AmericanFlagSortGenerator.java
 * Yadullah Duman, 2016 for the Animal project at TU Darmstadt.
 * Copying this file for educational purposes is permitted without further authorization.
 */
package generators.sorting;

import algoanim.primitives.IntArray;
import algoanim.primitives.SourceCode;
import algoanim.primitives.Text;
import algoanim.primitives.Variables;
import algoanim.primitives.generators.AnimationType;
import algoanim.properties.*;
import algoanim.util.Coordinates;
import algoanim.util.TicksTiming;
import algoanim.util.Timing;
import generators.framework.Generator;
import generators.framework.GeneratorType;

import java.awt.*;
import java.util.Locale;

import algoanim.primitives.generators.Language;

import java.util.Hashtable;

import generators.framework.properties.AnimationPropertiesContainer;

/**
 * @author Yadullah Duman
 */
public class AmericanFlagSortGenerator implements Generator {
    private Language language;
    private int radix;
    private int[] array;
    private ArrayProperties arrayProperties;
    private SourceCodeProperties scProperties;
    private TextProperties textProperties;
    private Variables varTable;
    private Text arrayHeader, countsHeader, offsetsHeader, description;
    private final static Timing defaultDuration = new TicksTiming(30);
    private final String ORIGIN_KEY = "origin-key";
    private final String FROM_KEY = "from-key";
    private final String NUM_KEY = "num-key";
    private final String TO_KEY = "to-key";
    private final String TMP_KEY = "tmp-key";

    public AmericanFlagSortGenerator() {
    }

    public AmericanFlagSortGenerator(Language l) {
        this.language = l;
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

    /**
     * initializing all properties, creating all primitives and executing the algorithm
     *
     * @param array - the array which will be manipulated
     * @param radix - the radix value
     */
    private void start(int[] array, int radix) {
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

        textProperties = new TextProperties();
        textProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 12));

        this.varTable = this.language.newVariables();

        this.varTable.declare("int", ORIGIN_KEY);
        this.varTable.declare("int", NUM_KEY);
        this.varTable.declare("int", FROM_KEY);
        this.varTable.declare("int", TO_KEY);
        this.varTable.declare("int", TMP_KEY);

        arrayHeader = language.newText(new Coordinates(20, 80), "array", "arrayHeader", null, textProperties);

        countsHeader = language.newText(new Coordinates(220, 80), "counts", "countsHeader", null, textProperties);
        countsHeader.hide();

        offsetsHeader = language.newText(new Coordinates(420, 80), "offsets", "offsetsHeader", null, textProperties);
        offsetsHeader.hide();

        IntArray iArray = language.newIntArray(new Coordinates(20, 100), array, "intArray", null, arrayProperties);

        IntArray counts = language.newIntArray(new Coordinates(220, 100), new int[radix], "counts", null, arrayProperties);
        counts.hide();

        IntArray offsets = language.newIntArray(new Coordinates(420, 100), new int[radix], "offsets", null, arrayProperties);
        offsets.hide();

        SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);

        sourceCode.addCodeLine("public void americanFlagSort(int[] array, int radix) {", null, 0, null);    // 0
        sourceCode.addCodeLine("int[] counts = new int[radix];", null, 1, null);                            // 1
        sourceCode.addCodeLine("int[] offsets = new int[radix];", null, 1, null);                            // 2
        sourceCode.addCodeLine("for (int num : array)", null, 1, null);                                    // 3
        sourceCode.addCodeLine("counts[num % radix]++;", null, 2, null);                                    // 4
        sourceCode.addCodeLine("for (int i = 1; i < radix; i++)", null, 1, null);                            // 5
        sourceCode.addCodeLine("offsets[i] = offsets[i - 1] + counts[i - 1];", null, 2, null);                // 6
        sourceCode.addCodeLine("for (int i = 0; i < radix; i++) {", null, 1, null);                        // 7
        sourceCode.addCodeLine("while (counts[i] > 0) {", null, 2, null);                                    // 8
        sourceCode.addCodeLine("int origin = offsets[i];", null, 3, null);                                    // 9
        sourceCode.addCodeLine("int from = origin;", null, 3, null);                                        // 10
        sourceCode.addCodeLine("int num = array[from];", null, 3, null);                                    // 11
        sourceCode.addCodeLine("array[from] = -1;", null, 3, null);                                        // 12
        sourceCode.addCodeLine("do {", null, 3, null);                                                        // 13
        sourceCode.addCodeLine("int to = offsets[num % radix]++;", null, 4, null);                            // 14
        sourceCode.addCodeLine("counts[num % radix]--;", null, 4, null);                                    // 15
        sourceCode.addCodeLine("int tmp = array[to];", null, 4, null);                                        // 16
        sourceCode.addCodeLine("array[to] = num;", null, 4, null);                                            // 17
        sourceCode.addCodeLine("num = tmp;", null, 4, null);                                                // 18
        sourceCode.addCodeLine("from = to;", null, 4, null);                                                // 19
        sourceCode.addCodeLine("} while (from != origin);", null, 3, null);                                // 20
        sourceCode.addCodeLine("}", null, 2, null);                                                        // 21
        sourceCode.addCodeLine("}", null, 1, null);                                                        // 22
        sourceCode.addCodeLine("}", null, 0, null);                                                        // 23

        americanFlagSort(iArray, counts, offsets, sourceCode, radix);

        language.nextStep();
        language.hideAllPrimitives();
        arrayHeader.hide();
        language.nextStep();
    }

    /**
     * The American Flag Sort Algorithm wrapped up by the ANIMAL API
     *
     * @param array   - the array which will be manipulated
     * @param counts  - an array of length radix, where the counts are hold
     * @param offsets - an array of length radix, where the offsets are hold
     * @param code    - the source code ANIMAL is working with
     * @param radix   - the radix value
     */
    private void americanFlagSort(IntArray array, IntArray counts, IntArray offsets, SourceCode code, int radix) {
        language.nextStep("start of algorithm");
        arrayHeader.show();
        code.highlight(0);
        code.unhighlight(0);

        language.nextStep();
        code.highlight(1);

        language.nextStep();
        countsHeader.show();
        counts.show();
        code.unhighlight(1);

        language.nextStep();
        code.highlight(2);

        language.nextStep();
        offsetsHeader.show();
        offsets.show();
        code.unhighlight(2);

        language.nextStep("initializing counts array");
        for (int i = 0; i < array.getLength(); i++) {
            code.highlight(3);
            language.nextStep();
            code.unhighlight(3);

            int num = array.getData(i);
            int pos = num % radix;

            language.nextStep();
            code.highlight(4);
            counts.highlightCell(pos, null, defaultDuration);

            counts.put(pos, counts.getData(pos) + 1, null, defaultDuration);

            language.nextStep();
            code.unhighlight(4);
            counts.unhighlightCell(pos, null, defaultDuration);
        }

        language.nextStep("initializing offsets array");
        for (int i = 1; i < radix; i++) {
            code.highlight(5);
            language.nextStep();
            code.unhighlight(5);

            int sum = offsets.getData(i - 1) + counts.getData(i - 1);

            language.nextStep();
            code.highlight(6);
            offsets.highlightCell(i, null, defaultDuration);

            offsets.put(i, sum, null, defaultDuration);

            language.nextStep();
            code.unhighlight(6);
            offsets.unhighlightCell(i, null, defaultDuration);
        }

        language.nextStep("execution of American Flag Sort");
        for (int i = 0; i < radix; i++) {
            language.nextStep();
            code.highlight(7);
            language.nextStep();
            code.unhighlight(7);

            while (counts.getData(i) > 0) {
                language.nextStep();
                code.highlight(8);
                language.nextStep();
                code.unhighlight(8);

                language.nextStep();
                code.highlight(9);

                int origin = offsets.getData(i);
                this.varTable.set(ORIGIN_KEY, String.valueOf(origin));

                language.nextStep();
                code.unhighlight(9);
                code.highlight(10);

                int from = origin;
                this.varTable.set(FROM_KEY, String.valueOf(from));

                language.nextStep();
                code.unhighlight(10);
                code.highlight(11);

                int num = array.getData(from);
                this.varTable.set(NUM_KEY, String.valueOf(num));

                language.nextStep();
                code.unhighlight(11);
                code.highlight(12);

                language.nextStep();
                array.highlightCell(from, null, defaultDuration);
                array.put(from, -1, null, defaultDuration);

                language.nextStep();
                code.unhighlight(12);
                array.unhighlightCell(from, null, defaultDuration);
                code.highlight(13);

                do {
                    language.nextStep();
                    code.unhighlight(13);
                    code.highlight(14);

                    int to = offsets.getData(num % radix);
                    this.varTable.set(TO_KEY, String.valueOf(to));

                    language.nextStep();
                    offsets.highlightCell(num % radix, null, defaultDuration);
                    offsets.put(num % radix, offsets.getData(num % radix) + 1, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(14);
                    offsets.unhighlightCell(num % radix, null, defaultDuration);
                    code.highlight(15);

                    language.nextStep();
                    counts.highlightCell(num % radix, null, defaultDuration);
                    counts.put(num % radix, counts.getData(num % radix) - 1, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(15);
                    counts.unhighlightCell(num % radix, null, defaultDuration);
                    code.highlight(16);

                    int tmp = array.getData(to);
                    this.varTable.set(TMP_KEY, String.valueOf(tmp));

                    language.nextStep();
                    code.unhighlight(16);
                    code.highlight(17);

                    language.nextStep();
                    array.highlightCell(to, null, defaultDuration);
                    array.put(to, num, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(17);
                    array.unhighlightCell(to, null, defaultDuration);
                    code.highlight(18);

                    num = tmp;
                    this.varTable.set(NUM_KEY, String.valueOf(num));

                    language.nextStep();
                    code.unhighlight(18);

                    language.nextStep();
                    code.highlight(19);

                    from = to;
                    this.varTable.set(FROM_KEY, String.valueOf(from));

                    code.unhighlight(19);
                } while (from != origin);
            }
        }
    }

    /**
     * Initializing the language object
     */
    public void init() {
        language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, this.getAlgorithmName(),
                this.getAnimationAuthor(), 800, 600);
        language.setStepMode(true);
    }

    /**
     * executing the algorithm and generating everything for the ANIMAL Generator
     *
     * @param props      - ANIMAL properties
     * @param primitives - ANIMAL primitives
     * @return AnimalScript Code
     */
    public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {
        radix = (Integer) primitives.get("radix");
        array = (int[]) primitives.get("array");
        arrayProperties = (ArrayProperties) props.getPropertiesByName("arrayProperties");
        scProperties = (SourceCodeProperties) props.getPropertiesByName("scProperties");

        init();
        start(array, radix);

        return language.toString();
    }

    public static void main(String[] args) throws Exception {
        Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "AFS", "YD", 800, 600);
        AmericanFlagSortGenerator afs = new AmericanFlagSortGenerator(language);
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int radix = 4;
        afs.start(array, radix);

        System.out.println(language);
    }

    public String getName() {
        return "American Flag Sort";
    }

    public String getAlgorithmName() {
        return "American Flag Sort";
    }

    public String getAnimationAuthor() {
        return "Yadullah Duman";
    }

    public String getDescription() {
        return AFS_DESCRIPTION;
    }

    public String getCodeExample() {
        return AFS_SOURCE_CODE;
    }

    public String getFileExtension() {
        return "asu";
    }

    public Locale getContentLocale() {
        return Locale.ENGLISH;
    }

    public GeneratorType getGeneratorType() {
        return new GeneratorType(GeneratorType.GENERATOR_TYPE_SORT);
    }

    public String getOutputLanguage() {
        return Generator.JAVA_OUTPUT;
    }

}