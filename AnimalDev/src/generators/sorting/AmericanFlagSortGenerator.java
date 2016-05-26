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
        // TODO: delete properties which are set by XML later
        // TODO: update varTable
        // TODO: update source code
        // TODO: highlight & unhighlight code from getDigit() and getDigitCount()
        // TODO: fix counts and offsets initialization

        // properties for the array
        arrayProperties = new ArrayProperties();
        arrayProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
        arrayProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
        arrayProperties.set(AnimationPropertiesKeys.FILLED_PROPERTY, Boolean.TRUE);
        arrayProperties.set(AnimationPropertiesKeys.ELEMENTCOLOR_PROPERTY, Color.BLACK);
        arrayProperties.set(AnimationPropertiesKeys.ELEMHIGHLIGHT_PROPERTY, Color.RED);
        arrayProperties.set(AnimationPropertiesKeys.CELLHIGHLIGHT_PROPERTY, Color.YELLOW);

        // properties for the source code
        scProperties = new SourceCodeProperties();
        scProperties.set(AnimationPropertiesKeys.CONTEXTCOLOR_PROPERTY, Color.BLUE);
        scProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 12));
        scProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, Color.RED);
        scProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);

        // properties for text
        textProperties = new TextProperties();
        textProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 12));

        // properties for the header
        headerProperties = new TextProperties();
        headerProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 14));
        headerProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLUE);

        // properties for intro and outro
        introAndOutroProperties = new TextProperties();
        introAndOutroProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.PLAIN, 14));

        // properties for notifications
        notificationProperties = new TextProperties();
        notificationProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 20));
        notificationProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.RED);

        // set up header and show intro
        header = language.newText(new Coordinates(20, 30), "American Flag Sort", "header", null, headerProperties);
        introLines = this.getIntroOutroText(descriptionLines, new Coordinates(20, 80), introAndOutroProperties, 20);
        language.nextStep("intro");

        language.nextStep();
        for (Text intro : introLines) {
            intro.hide();
        }

        // initialize variable table
        this.varTable = this.language.newVariables();
        this.varTable.declare("int", ORIGIN_KEY);
        this.varTable.declare("int", NUM_KEY);
        this.varTable.declare("int", SOURCE_KEY);
        this.varTable.declare("int", DESTINATION_KEY);
        this.varTable.declare("int", TMP_KEY);

        // initialize the heades for each array
        arrayHeader = language.newText(new Coordinates(20, 80), "array", "arrasyHeader", null, textProperties);
        countsHeader = language.newText(new Coordinates(220, 80), "counts", "countsHeader", null, textProperties);
        countsHeader.hide();
        offsetsHeader = language.newText(new Coordinates(420, 80), "offsets", "offsetsHeader", null, textProperties);
        offsetsHeader.hide();

        // initialize array and source code
        IntArray iArray = language.newIntArray(new Coordinates(20, 100), array, "intArray", null, arrayProperties);
        SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 140), "sourceCode", null, scProperties);

        // sort()
        sourceCode.addCodeLine("public int[] sort(int[] array, int radix) {", null, 0, null);                   // 0
        sourceCode.addCodeLine("int digitCount = getDigitCount(array);", null, 1, null);                        // 1
        sourceCode.addCodeLine("int divisor = (int) Math.pow(10, digitCount);", null, 1, null);                 // 2
        sourceCode.addCodeLine("americanFlagSort(array, 0, array.length, divisor, radix);", null, 1, null);     // 3
        sourceCode.addCodeLine("return array;", null, 1, null);                                                 // 4
        sourceCode.addCodeLine("}", null, 0, null);                                                             // 5

        // americanFlagSort()
        sourceCode.addCodeLine("private void americanFlagSort" +
                "(int[] array, int start, int length, int divisor, int radix) {", null, 0, null);               // 6
        sourceCode.addCodeLine("int[] counts = new int[radix];", null, 1, null);                                // 7
        sourceCode.addCodeLine("int[] offsets = new int[radix];", null, 1, null);                               // 8
        sourceCode.addCodeLine("for (int i = start; i < length; i++) {", null, 1, null);                        // 9
        sourceCode.addCodeLine("int digit = getDigit(array[i], divisor, radix);", null, 2, null);               // 10
        sourceCode.addCodeLine("counts[digit]++;", null, 2, null);                                              // 11
        sourceCode.addCodeLine("}", null, 1, null);                                                             // 12
        sourceCode.addCodeLine("offsets[0] = start;", null, 1, null);                                           // 13
        sourceCode.addCodeLine("for (int i = 1; i < radix; i++) {", null, 1, null);                             // 14
        sourceCode.addCodeLine("offsets[i] = counts[i - 1] + offsets[i - 1];", null, 2, null);                  // 15
        sourceCode.addCodeLine("}", null, 1, null);                                                             // 16
        sourceCode.addCodeLine("for (int i = 0; i < radix; i++) {", null, 1, null);                             // 17
        sourceCode.addCodeLine("while (counts[i] > 0) {", null, 2, null);                                       // 18
        sourceCode.addCodeLine("int origin = offsets[i];", null, 3, null);                                      // 19
        sourceCode.addCodeLine("int source = origin;", null, 3, null);                                          // 20
        sourceCode.addCodeLine("int num = array[source];", null, 3, null);                                      // 21
        sourceCode.addCodeLine("array[source] = -1;", null, 3, null);                                           // 22
        sourceCode.addCodeLine("do {", null, 3, null);                                                          // 23
        sourceCode.addCodeLine("int digit = getDigit(num, divisor, radix);", null, 4, null);                    // 24
        sourceCode.addCodeLine("int destination = offsets[digit]++;", null, 4, null);                           // 25
        sourceCode.addCodeLine("counts[digit]--;", null, 4, null);                                              // 26
        sourceCode.addCodeLine("int tmp = array[destination];", null, 4, null);                                 // 27
        sourceCode.addCodeLine("array[destination] = num;", null, 4, null);                                     // 28
        sourceCode.addCodeLine("num = tmp;", null, 4, null);                                                    // 29
        sourceCode.addCodeLine("source = destination;", null, 4, null);                                         // 30
        sourceCode.addCodeLine("} while (from != origin);", null, 3, null);                                     // 31
        sourceCode.addCodeLine("}", null, 2, null);                                                             // 32
        sourceCode.addCodeLine("}", null, 1, null);                                                             // 33
        sourceCode.addCodeLine("if (divisor > 1) {", null, 1, null);                                            // 34
        sourceCode.addCodeLine("for (int i = 0; i < radix; i++) {", null, 2, null);                             // 35
        sourceCode.addCodeLine("int begin = (i > 0) ? offsets[i - 1] : start;", null, 3, null);                 // 36
        sourceCode.addCodeLine("int end = offsets[i];", null, 3, null);                                         // 37
        sourceCode.addCodeLine("if (end - begin > 1) {", null, 3, null);                                        // 38
        sourceCode.addCodeLine("americanFlagSort(array, begin, end, divisor / 10, radix);", null, 4, null);     // 39
        sourceCode.addCodeLine("}", null, 3, null);                                                             // 40
        sourceCode.addCodeLine("}", null, 2, null);                                                             // 41
        sourceCode.addCodeLine("}", null, 1, null);                                                             // 42
        sourceCode.addCodeLine("}", null, 0, null);                                                             // 43

        // getDigit()
        sourceCode.addCodeLine("private int getDigit(int elem, int divisor, int radix) {", null, 0, null);      // 44
        sourceCode.addCodeLine("return (elem / divisor) % radix;", null, 1, null);                              // 45
        sourceCode.addCodeLine("}", null, 0, null);                                                             // 46

        // getDigitCount()
        sourceCode.addCodeLine("private int getDigitCount(int[] array) {", null, 0, null);                      // 47
        sourceCode.addCodeLine("int maxDigitCount = Integer.MIN_VALUE;", null, 1, null);                        // 48
        sourceCode.addCodeLine("for (int number : array) {", null, 1, null);                                    // 49
        sourceCode.addCodeLine("int tmp = (int) Math.log10(number) + 1;", null, 2, null);                       // 50
        sourceCode.addCodeLine("if (tmp > maxDigitCount) {", null, 2, null);                                    // 51
        sourceCode.addCodeLine("maxDigitCount = tmp;", null, 3, null);                                          // 52
        sourceCode.addCodeLine("}", null, 2, null);                                                             // 53
        sourceCode.addCodeLine("}", null, 1, null);                                                             // 54
        sourceCode.addCodeLine("return maxDigitCount;", null, 1, null);                                         // 55
        sourceCode.addCodeLine("}", null, 0, null);                                                             // 56

        // start algorithm
        sort(iArray, sourceCode, radix);

        language.nextStep();
        language.hideAllPrimitives();
        arrayHeader.hide();
        countsHeader.hide();
        offsetsHeader.hide();
        header.show();

        // show outro
        outroLines = this.getIntroOutroText(summaryLines, new Coordinates(20, 80), introAndOutroProperties, 20);
        language.nextStep("outro");

        language.nextStep();
        for (Text outro : outroLines) {
            outro.hide();
        }

        language.hideAllPrimitives();
        language.nextStep();
    }

    private void sort(IntArray array, SourceCode code, int radix) {
        language.nextStep();
        arrayHeader.show();
        code.highlight(0);
        code.unhighlight(0);

        language.nextStep("sort()");
        code.highlight(1);

        language.nextStep();
        int digitCount = getDigitCount(array);

        language.nextStep();
        code.unhighlight(1);
        code.highlight(2);
        int divisor = (int) Math.pow(10, digitCount);

        language.nextStep();
        code.unhighlight(2);
        code.highlight(3);
        americanFlagSort(array, 0, array.getLength(), divisor, radix, code);

        language.nextStep();
        code.unhighlight(3);
        code.highlight(4);

        language.nextStep();
        code.unhighlight(4);
    }

    private void americanFlagSort(IntArray array, int start, int length, int divisor, int radix, SourceCode code) {
        language.nextStep();
        code.highlight(6);
        code.unhighlight(6);

        language.nextStep("start of american flag sort");
        code.highlight(7);

        language.nextStep();
        countsHeader.show();
        IntArray counts = language.newIntArray(new Coordinates(220, 100), new int[radix], "countsArray", null, arrayProperties);
        code.unhighlight(7);

        language.nextStep();
        code.highlight(8);

        language.nextStep();
        offsetsHeader.show();
        IntArray offsets = language.newIntArray(new Coordinates(420, 100), new int[radix], "offsetsArray", null, arrayProperties);
        code.unhighlight(8);

        language.nextStep("initializing counts array");
        info = language.newText(new Coordinates(500, 250), "initializing counts array", "countsInit", null, notificationProperties);

        for (int i = start; i < length; i++)
        {
            code.highlight(9);
            array.highlightCell(i, null, defaultDuration);
            language.nextStep();
            code.unhighlight(9);

            language.nextStep();
            code.highlight(10);
            int digit = getDigit(array.getData(i), divisor, radix);

            language.nextStep();
            code.unhighlight(10);
            code.highlight(11);
            array.unhighlightCell(i, null, defaultDuration);
            counts.highlightCell(digit, null, defaultDuration);

            counts.put(digit, counts.getData(digit) + 1, null, defaultDuration);

            language.nextStep();
            code.unhighlight(11);
            counts.unhighlightCell(digit, null, defaultDuration);
        }
        info.hide();

        language.nextStep("initializing offsets array");
        info = language.newText(new Coordinates(500, 250), "initializing offsets array", "offsetsInit", null, notificationProperties);
        code.highlight(13);
        offsets.put(0, start, null, defaultDuration);

        language.nextStep();
        code.unhighlight(13);
        for (int i = 1; i < radix; i++) {
            code.highlight(14);
            language.nextStep();
            code.unhighlight(14);

            int sum = offsets.getData(i - 1) + counts.getData(i - 1);

            language.nextStep();
            code.highlight(15);
            offsets.highlightCell(i, null, defaultDuration);

            offsets.put(i, sum, null, defaultDuration);

            language.nextStep();
            code.unhighlight(15);
            offsets.unhighlightCell(i, null, defaultDuration);
        }
        info.hide();

        language.nextStep();
        info = language.newText(new Coordinates(500, 250), "sort by radix", "radixSort", null, notificationProperties);
        language.nextStep();
        for (int i = 0; i < radix; i++) {
            language.nextStep();
            code.highlight(17);
            language.nextStep();
            code.unhighlight(17);

            while (counts.getData(i) > 0) {
                language.nextStep();
                code.highlight(18);
                language.nextStep();
                code.unhighlight(18);

                language.nextStep();
                code.highlight(19);

                int origin = offsets.getData(i);
                this.varTable.set(ORIGIN_KEY, String.valueOf(origin));

                language.nextStep();
                code.unhighlight(19);
                code.highlight(20);

                int source = origin;
                this.varTable.set(SOURCE_KEY, String.valueOf(source));

                language.nextStep();
                code.unhighlight(20);
                code.highlight(21);

                int num = array.getData(source);
                this.varTable.set(NUM_KEY, String.valueOf(num));

                language.nextStep();
                code.unhighlight(21);
                code.highlight(22);

                language.nextStep();
                array.highlightCell(source, null, defaultDuration);
                array.put(source, -1, null, defaultDuration);

                language.nextStep();
                code.unhighlight(22);
                array.unhighlightCell(source, null, defaultDuration);
                code.highlight(23);

                do {
                    language.nextStep();
                    code.unhighlight(23);
                    code.highlight(24);

                    int digit = getDigit(num, divisor, radix);
                    int destination = offsets.getData(digit);
                    this.varTable.set(DESTINATION_KEY, String.valueOf(destination));

                    language.nextStep();
                    code.unhighlight(24);
                    code.highlight(25);
                    offsets.highlightCell(digit, null, defaultDuration);
                    offsets.put(digit, offsets.getData(digit) + 1, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(25);
                    offsets.unhighlightCell(digit, null, defaultDuration);
                    code.highlight(26);

                    language.nextStep();
                    counts.highlightCell(digit, null, defaultDuration);
                    counts.put(digit, counts.getData(digit) - 1, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(26);
                    counts.unhighlightCell(digit, null, defaultDuration);
                    code.highlight(27);

                    int tmp = array.getData(destination);
                    this.varTable.set(TMP_KEY, String.valueOf(tmp));

                    language.nextStep();
                    code.unhighlight(27);
                    code.highlight(28);

                    language.nextStep();
                    array.highlightCell(destination, null, defaultDuration);
                    array.put(destination, num, null, defaultDuration);

                    language.nextStep();
                    code.unhighlight(28);
                    array.unhighlightCell(destination, null, defaultDuration);
                    code.highlight(29);

                    num = tmp;
                    this.varTable.set(NUM_KEY, String.valueOf(num));

                    code.unhighlight(29);

                    language.nextStep();
                    code.highlight(30);

                    source = destination;
                    this.varTable.set(SOURCE_KEY, String.valueOf(source));

                    language.nextStep();
                    code.unhighlight(30);
                } while (source != origin);
            }
        }
        info.hide();

        if (divisor > 1) {
            language.nextStep();
            code.highlight(34);

            language.nextStep();
            code.unhighlight(34);
            for (int i = 0; i < radix; i++) {
                code.highlight(35);

                language.nextStep();
                code.unhighlight(35);
                code.highlight(36);
                int begin = (i > 0) ? offsets.getData(i - 1) : start;

                language.nextStep();
                code.unhighlight(36);
                code.highlight(37);
                int end = offsets.getData(i);

                language.nextStep();
                code.unhighlight(37);
                if (end - begin > 1) {
                    code.highlight(38);

                    language.nextStep();
                    code.unhighlight(38);
                    code.highlight(39);

                    language.nextStep();
                    code.unhighlight(39);
                    americanFlagSort(array, begin, end, divisor / 10, radix, code);
                }
            }
        }
    }

    private int getDigit(int data, int divisor, int radix) {
        return (data / divisor) % radix;
    }

    private int getDigitCount(IntArray array) {
        int maxDigitCount = Integer.MIN_VALUE;
        for (int i = 0; i < array.getLength(); i++) {
            int tmp = (int) Math.log10(array.getData(i));
            if (tmp > maxDigitCount) {
                maxDigitCount = tmp;
            }
        }
        return maxDigitCount;
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

        for (int number : array) {
            if (number < 0) {
                throw new IllegalArgumentException("Please work with positive numbers!");
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "American Flag Sort", "Yadullah Duman", 800, 600);
        AmericanFlagSortGenerator afs = new AmericanFlagSortGenerator(language);
        int[] array = {9, 8, 47, 6, 5, 4, 13, 12, 1, 0};
        int radix = 10;
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