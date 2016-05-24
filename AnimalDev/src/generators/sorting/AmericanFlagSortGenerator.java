/*
 * AmericanFlagSortGenerator.java
 * Yadullah Duman, 2016 for the Animal project at TU Darmstadt.
 * Copying this file for educational purposes is permitted without further authorization.
 */
package generators.sorting;

import algoanim.primitives.*;
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

import generators.framework.ValidatingGenerator;
import generators.framework.properties.AnimationPropertiesContainer;

/**
 * @author Yadullah Duman
 */
public class AmericanFlagSortGenerator implements ValidatingGenerator {
    private Language language;
    private int radix;
    private int[] array;
    private ArrayProperties arrayProperties;
    private SourceCodeProperties scProperties;
    private TextProperties textProperties, headerProperties, introAndOutroProperties, notificationProperties;
    private Variables varTable;
    private Text header, arrayHeader, countsHeader, offsetsHeader, info;
    private Text[] introLines, outroLines;
    private final static Timing defaultDuration = new TicksTiming(30);
    private final String ORIGIN_KEY = "origin";
    private final String SOURCE_KEY = "source";
    private final String NUM_KEY = "num";
    private final String DESTINATION_KEY = "destination";
    private final String TMP_KEY = "tmp";

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
            "            int source = origin;\n" +
            "            int num = array[source];\n" +
            "            array[source] = -1;\n" +
            "\n" +
            "            do {\n" +
            "                int destination = offsets[num % RADIX]++;\n" +
            "                counts[num % RADIX]--;\n" +
            "                int tmp = array[destination];\n" +
            "                array[destination] = num;\n" +
            "                num = tmp;\n" +
            "                source = destination;\n" +
            "            } while (source != origin);\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n";

    private String[] descriptionLines = {
            "An American flag sort is an efficient, in-place variant of radix sort that distributes items into buckets.",
            "With some optimizations, it is twice as fast as quicksort.",
            "The name comes by analogy with the Dutch national flag problem in the last step:",
            "efficiently partition the array into many stripes.",
            "American flag sort can only sort integers (or objects that can be interpreted as integers).",
            "In-place sorting algorithms, including American flag sort, run without allocating a significant",
            "amount of memory beyond that used by the original array. This is a significant advantage, both in",
            "memory savings and in time saved copying the array.",
            "American flag sort works by successively dividing a list of objects into buckets based on the first",
            "digit of their base-N representation (the base used is referred to as the radix). When N is 2, each",
            "object can be swapped into the correct bucket by using the Dutch national flag algorithm.",
            "where each bucket should begin and end. American flag sort gets around this problem by making two",
            "passes through the array. The first pass counts the number of objects that belong in each of the N",
            "buckets. The beginning and end of each bucket in the original array is then computed as the sum of",
            "sizes of preceding buckets. The second pass swaps each object into place.",
            "source: https://en.wikipedia.org/wiki/American_flag_sort"
    };

    private String[] summaryLines = {
            "After the execution of American Flag Sort, with radix equal to the highest absolute value of the array,",
            "your array should be sorted in ascending order.",
            "The asymptotic time complexity is O(n log n) for worst, average and best case performance."
    };

    public AmericanFlagSortGenerator() {
    }

    public AmericanFlagSortGenerator(Language l) {
        this.language = l;
        language.setStepMode(true);
    }

    private Text[] getIntroOutroText(String[] descriptionLines, Coordinates coordinates, TextProperties properties, int offset) {
        Text[] text = new Text[descriptionLines.length];

        for (int i = 0; i < descriptionLines.length; i++) {
            text[i] = language.newText(new Coordinates(coordinates.getX(), coordinates.getY() + offset * i),
                    descriptionLines[i], "introOutroLines", null, properties);
        }
        return text;
    }

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

        headerProperties = new TextProperties();
        headerProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 14));
        headerProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLUE);

        introAndOutroProperties = new TextProperties();
        introAndOutroProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.PLAIN, 14));

        notificationProperties = new TextProperties();
        notificationProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 20));
        notificationProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.RED);

        header = language.newText(new Coordinates(20, 30), "American Flag Sort", "header", null, headerProperties);
        introLines = this.getIntroOutroText(descriptionLines, new Coordinates(20, 80), introAndOutroProperties, 20);
        language.nextStep("description of algorithm");

        language.nextStep();
        for (Text intro : introLines) {
            intro.hide();
        }

        this.varTable = this.language.newVariables();

        this.varTable.declare("int", ORIGIN_KEY);
        this.varTable.declare("int", NUM_KEY);
        this.varTable.declare("int", SOURCE_KEY);
        this.varTable.declare("int", DESTINATION_KEY);
        this.varTable.declare("int", TMP_KEY);

        arrayHeader = language.newText(new Coordinates(20, 80), "array", "arrasyHeader", null, textProperties);
        countsHeader = language.newText(new Coordinates(220, 80), "counts", "countsHeader", null, textProperties);
        countsHeader.hide();
        offsetsHeader = language.newText(new Coordinates(420, 80), "offsets", "offsetsHeader", null, textProperties);
        offsetsHeader.hide();

        IntArray iArray = language.newIntArray(new Coordinates(20, 100), array, "intArray", null, arrayProperties);
        IntArray counts = language.newIntArray(new Coordinates(220, 100), new int[radix], "countsArray", null, arrayProperties);
        counts.hide();
        IntArray offsets = language.newIntArray(new Coordinates(420, 100), new int[radix], "offsetsArray", null, arrayProperties);
        offsets.hide();
        SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);

        sourceCode.addCodeLine("public void americanFlagSort(int[] array, int radix) {", null, 0, null);   // 0
        sourceCode.addCodeLine("int[] counts = new int[radix];", null, 1, null);                           // 1
        sourceCode.addCodeLine("int[] offsets = new int[radix];", null, 1, null);                          // 2
        sourceCode.addCodeLine("for (int num : array)", null, 1, null);                                    // 3
        sourceCode.addCodeLine("counts[num % radix]++;", null, 2, null);                                   // 4
        sourceCode.addCodeLine("for (int i = 1; i < radix; i++)", null, 1, null);                          // 5
        sourceCode.addCodeLine("offsets[i] = offsets[i - 1] + counts[i - 1];", null, 2, null);             // 6
        sourceCode.addCodeLine("for (int i = 0; i < radix; i++) {", null, 1, null);                        // 7
        sourceCode.addCodeLine("while (counts[i] > 0) {", null, 2, null);                                  // 8
        sourceCode.addCodeLine("int origin = offsets[i];", null, 3, null);                                 // 9
        sourceCode.addCodeLine("int source = origin;", null, 3, null);                                     // 10
        sourceCode.addCodeLine("int num = array[source];", null, 3, null);                                 // 11
        sourceCode.addCodeLine("array[source] = -1;", null, 3, null);                                      // 12
        sourceCode.addCodeLine("do {", null, 3, null);                                                     // 13
        sourceCode.addCodeLine("int destination = offsets[num % radix]++;", null, 4, null);                // 14
        sourceCode.addCodeLine("counts[num % radix]--;", null, 4, null);                                   // 15
        sourceCode.addCodeLine("int tmp = array[destination];", null, 4, null);                            // 16
        sourceCode.addCodeLine("array[destination] = num;", null, 4, null);                                // 17
        sourceCode.addCodeLine("num = tmp;", null, 4, null);                                               // 18
        sourceCode.addCodeLine("source = destination;", null, 4, null);                                    // 19
        sourceCode.addCodeLine("} while (source != origin);", null, 3, null);                              // 20
        sourceCode.addCodeLine("}", null, 2, null);                                                        // 21
        sourceCode.addCodeLine("}", null, 1, null);                                                        // 22
        sourceCode.addCodeLine("}", null, 0, null);                                                        // 23

        americanFlagSort(iArray, counts, offsets, sourceCode, radix);

        language.nextStep();
        language.hideAllPrimitives();
        arrayHeader.hide();
        header.show();

        outroLines = this.getIntroOutroText(summaryLines, new Coordinates(20, 80), introAndOutroProperties, 20);
        language.nextStep("outro");

        language.nextStep();
        for (Text outro : outroLines) {
            outro.hide();
        }

        language.hideAllPrimitives();
        language.nextStep();
    }

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
        info = language.newText(new Coordinates(500, 250), "initializing counts array",
                "countsInit", null, notificationProperties);
        for (int i = 0; i < array.getLength(); i++) {
            code.highlight(3);
            array.highlightCell(i, null, defaultDuration);
            language.nextStep();
            code.unhighlight(3);

            int num = array.getData(i);
            int pos = num % radix;

            language.nextStep();
            code.highlight(4);
            array.unhighlightCell(i, null, defaultDuration);
            counts.highlightCell(pos, null, defaultDuration);

            counts.put(pos, counts.getData(pos) + 1, null, defaultDuration);

            language.nextStep();
            code.unhighlight(4);
            counts.unhighlightCell(pos, null, defaultDuration);
        }
        info.hide();

        language.nextStep("initializing offsets array");
        info = language.newText(new Coordinates(500, 250), "initializing offsets array",
                "offsetsInit", null, notificationProperties);
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
        info.hide();

        language.nextStep("execution of American Flag Sort");
        info = language.newText(new Coordinates(500, 250), "sort by radix",
                "radixSort", null, notificationProperties);
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

                int source = origin;
                this.varTable.set(SOURCE_KEY, String.valueOf(source));

                language.nextStep();
                code.unhighlight(10);
                code.highlight(11);

                int num = array.getData(source);
                this.varTable.set(NUM_KEY, String.valueOf(num));

                language.nextStep();
                code.unhighlight(11);
                code.highlight(12);

                language.nextStep();
                array.highlightCell(source, null, defaultDuration);
                array.put(source, -1, null, defaultDuration);

                language.nextStep();
                code.unhighlight(12);
                array.unhighlightCell(source, null, defaultDuration);
                code.highlight(13);

                do {
                    language.nextStep();
                    code.unhighlight(13);
                    code.highlight(14);

                    int destination = offsets.getData(num % radix);
                    this.varTable.set(DESTINATION_KEY, String.valueOf(destination));

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

                    int tmp = array.getData(destination);
                    this.varTable.set(TMP_KEY, String.valueOf(tmp));

                    language.nextStep();
                    code.unhighlight(16);
                    code.highlight(17);

                    language.nextStep();
                    array.highlightCell(destination, null, defaultDuration);
                    array.put(destination, num, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(17);
                    array.unhighlightCell(destination, null, defaultDuration);
                    code.highlight(18);

                    num = tmp;
                    this.varTable.set(NUM_KEY, String.valueOf(num));

                    code.unhighlight(18);

                    language.nextStep();
                    code.highlight(19);

                    source = destination;
                    this.varTable.set(SOURCE_KEY, String.valueOf(source));

                    language.nextStep();
                    code.unhighlight(19);
                } while (source != origin);
            }
        }
        info.hide();
        language.nextStep();
        language.newText(new Coordinates(500, 250), "The Array is sorted!", "sortedNotification", null, notificationProperties);
    }

    public void init() {
        language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, this.getAlgorithmName(),
                this.getAnimationAuthor(), 800, 600);
        language.setStepMode(true);
    }

    public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {
        array = (int[]) primitives.get("array");
        radix = (Integer) primitives.get("radix");
        arrayProperties = (ArrayProperties) props.getPropertiesByName("arrayProperties");
        scProperties = (SourceCodeProperties) props.getPropertiesByName("scProperties");

        init();
        start(array, radix);

        return language.toString();
    }

    public boolean validateInput(AnimationPropertiesContainer props, Hashtable<String, Object> primitives)
            throws IllegalArgumentException {
        radix = (Integer) primitives.get("radix");
        array = (int[]) primitives.get("array");

        if (radix > 14) {
            throw new IllegalArgumentException("" +
                    "Your radix is too big to visualize! " +
                    "Please pick a radix less than 15.");
        }

        if (radix <= 0) {
            throw new IllegalArgumentException("" +
                    "Your radix is inappropriate for visualization!\n" +
                    "For appropriate visualization the value should be:\n" +
                    "10 <= radix < 15");
        }

        for (int num : array) {
            if (Math.abs(num) >= radix) {
                throw new IllegalArgumentException("" +
                        "Please select array-elements, " +
                        "where its absolute value is less than radix, " +
                        "in order guarantee a sort in ascending order!");
            }
        }

        return true;
    }

//    public static void main(String[] args) throws Exception {
//        Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "American Flag Sort", "Yadullah Duman", 800, 600);
//        AmericanFlagSortGenerator afs = new AmericanFlagSortGenerator(language);
//        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
//        int radix = 11;
//        afs.start(array, radix);
//        System.out.println(language);
//    }

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