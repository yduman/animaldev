/*
 * QuickselectGenerator.java
 * Yadullah Duman, 2016 for the Animal project at TU Darmstadt.
 * Copying this file for educational purposes is permitted without further authorization.
 */
package generators.searching;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Locale;

import algoanim.primitives.*;
import algoanim.primitives.generators.AnimationType;
import algoanim.primitives.generators.Language;
import algoanim.properties.*;
import algoanim.util.Coordinates;
import algoanim.util.TicksTiming;
import algoanim.util.Timing;
import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.ValidatingGenerator;
import generators.framework.properties.AnimationPropertiesContainer;

/**
 * @author Yadullah Duman
 */
public class QuickselectGenerator implements ValidatingGenerator {
    private Language language;
    private ArrayProperties arrayProperties;
    private ArrayMarkerProperties kSmallestProps, storeIndexProps, loopPointerProps, pivotPointerProps;
    private TextProperties headerProperties, introAndOutroProperties, notificationProperties;
    private SourceCodeProperties scProperties;
    private int[] array;
    private int kSmallest;
    private int pointerCounter = 0;
    private Variables varTable;
    private Text header, info;
    private Text[] introLines, outroLines;
    private final static Timing defaultDuration = new TicksTiming(30);
    private final static Timing swapDuration = new TicksTiming(130);
    private final String LEFT_KEY = "left";
    private final String RIGHT_KEY = "right";
    private final String PIVOT_KEY = "pivot";
    private final String PIVOT_VALUE_KEY = "pivotValue";
    private final String STORE_INDEX_KEY = "storeIndex";
    private final String K_SMALLEST_KEY = "kSmallest";
    private static final String QUICKSELECT_DESCRIPTION = "" +
            "In computer science, quickselect is a selection algorithm " +
            "to find the k-th smallest element in an unordered list. It is related to the quicksort sorting algorithm. " +
            "Like quicksort, it was developed by Tony Hoare, and thus is also known as Hoare's selection algorithm. " +
            "Like quicksort, it is efficient in practice and has good average-case performance, but has poor " +
            "worst-case performance. Quickselect and variants is the selection algorithm most often used in efficient " +
            "real-world implementations.\n" +
            "\n" +
            "Quickselect uses the same overall approach as quicksort, choosing one element as a pivot and " +
            "partitioning the data in two based on the pivot, accordingly as less than or greater than the pivot. " +
            "However, instead of recursing into both sides, as in quicksort, quickselect only recurses into one " +
            "side – the side with the element it is searching for. This reduces the average complexity " +
            "from O(n log n) to O(n).\n" +
            "\n" +
            "As with quicksort, quickselect is generally implemented as an in-place algorithm, and beyond selecting " +
            "the k'th element, it also partially sorts the data. See selection algorithm for further discussion " +
            "of the connection with sorting." +
            "\n" +
            "Worst case performance: O(n^2)\n" +
            "Best case performance: O(n)\n" +
            "Average case performance: O(n)\n" +
            "\n" +
            "source: https://en.wikipedia.org/wiki/Quickselect";

    private static final String QUICKSELECT_SOURCE_CODE = ""
            + "// kSmallest = 0 equals 1st smallest\n"
            + "public int quickselect (int[] array, int left, int right, int kSmallest) {\n" +
            "    if (left == right) \n" +
            "        return array[left]; \n" +
            "\n" +
            "    for (;;) { \n" +
            "        int pivot = randomPivot(left, right); \n" +
            "        pivot = partition(array, left, right, pivot); \n" +
            "\n" +
            "        if (kSmallest == pivot) \n" +
            "            return array[kSmallest]; \n" +
            "        else if (kSmallest < pivot) \n" +
            "            right = pivot - 1; \n" +
            "        else \n" +
            "            left = pivot + 1; \n" +
            "    } \n" +
            "} \n" +
            " \n" +
            "public int partition(int[] array, int left, int right, int pivot) { \n" +
            "    int pivotValue = array[pivot]; \n" +
            "    swap(array, pivot, right); \n" +
            "    int storeIndex = left; \n" +
            "\n" +
            "    for (int i = left; i < right; i++) { \n" +
            "        if (array[i] < pivotValue) { \n" +
            "            swap(array, storeIndex, i); \n" +
            "            storeIndex++; \n" +
            "        } \n" +
            "    } \n" +
            "    swap(array, right, storeIndex); \n" +
            "    return storeIndex; \n" +
            "} \n" +
            "\n" +
            "public void swap(int[] array, int a, int b) { \n" +
            "    int tmp = array[a]; \n" +
            "    array[a] = array[b]; \n" +
            "    array[b] = tmp; \n" +
            "}\n" +
            "\n" +
            "public int randomPivot(int left, int right) { \n" +
            "    return return left + (int) Math.floor(Math.random() * (right - left + 1));\n" +
            "}";

    private String[] descriptionLines = {
            "In computer science, quickselect is a selection algorithm",
            "to find the k-th smallest element in an unordered list. It is related to the quicksort sorting algorithm.",
            "Like quicksort, it was developed by Tony Hoare, and thus is also known as Hoare's selection algorithm.",
            "Like quicksort, it is efficient in practice and has good average-case performance, but has poor",
            "worst-case performance. Quickselect and variants is the selection algorithm most often used in efficient",
            "real-world implementations.",
            "Quickselect uses the same overall approach as quicksort, choosing one element as a pivot and",
            "partitioning the data in two based on the pivot, accordingly as less than or greater than the pivot.",
            "However, instead of recursing into both sides, as in quicksort, quickselect only recurses into one",
            "side – the side with the element it is searching for. This reduces the average complexity",
            "from O(n log n) to O(n).",
            "As with quicksort, quickselect is generally implemented as an in-place algorithm, and beyond selecting",
            "the k'th element, it also partially sorts the data. See selection algorithm for further discussion",
            "of the connection with sorting.",
            "source: https://en.wikipedia.org/wiki/Quickselect"
    };

    private String[] summaryLines = {
            "The complexity of Quickselect:",
            "Worst case performance: O(n^2)",
            "Best case performance: O(n)",
            "Average case performance: O(n)"
    };

    public QuickselectGenerator() {
    }

    public QuickselectGenerator(Language language) {
        this.language = language;
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

    private void start(int[] array) {
        headerProperties = new TextProperties();
        headerProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 14));
        headerProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLUE);

        introAndOutroProperties = new TextProperties();
        introAndOutroProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.PLAIN, 14));

        notificationProperties = new TextProperties();
        notificationProperties.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("SansSerif", Font.BOLD, 20));
        notificationProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.RED);

        header = language.newText(new Coordinates(20, 30), "Quickselect", "header", null, headerProperties);
        introLines = this.getIntroOutroText(descriptionLines, new Coordinates(20, 80), introAndOutroProperties, 20);
        language.nextStep("intro");

        language.nextStep();
        for (Text intro : introLines) {
            intro.hide();
        }

        this.varTable = language.newVariables();

        this.varTable.declare("int", LEFT_KEY);
        this.varTable.declare("int", RIGHT_KEY);
        this.varTable.declare("int", PIVOT_KEY);
        this.varTable.declare("int", K_SMALLEST_KEY);

        IntArray iArray = language.newIntArray(new Coordinates(40, 130), array, "intArray", null, arrayProperties);
        SourceCode sourceCode = language.newSourceCode(new Coordinates(40, 160), "sourceCode", null, scProperties);

        sourceCode.addCodeLine("public int quickSelect(int[] array, int left, int right, int kSmallest) {", null, 0, null); // 0
        sourceCode.addCodeLine("if (left == right)", null, 1, null); // 1
        sourceCode.addCodeLine("return array[left];", null, 2, null); // 2
        sourceCode.addCodeLine("for (;;) {", null, 1, null); // 3
        sourceCode.addCodeLine("int pivot = randomPivot(left, right);", null, 2, null); // 4
        sourceCode.addCodeLine("pivot = partition(array, left, right, pivot);", null, 2, null); // 5
        sourceCode.addCodeLine("if (kSmallest == pivot)", null, 2, null); // 6
        sourceCode.addCodeLine("return array[kSmallest];", null, 3, null); // 7
        sourceCode.addCodeLine("else if (kSmallest < pivot)", null, 2, null); // 8
        sourceCode.addCodeLine("right = pivot - 1;", null, 3, null); // 9
        sourceCode.addCodeLine("else", null, 2, null); // 10
        sourceCode.addCodeLine("left = pivot + 1;", null, 3, null); // 11
        sourceCode.addCodeLine("}", null, 1, null); // 12
        sourceCode.addCodeLine("}", null, 0, null); // 13
        sourceCode.addCodeLine("public int partition(int[] array, int left, int right, int pivot) {", null, 0, null); // 14
        sourceCode.addCodeLine("int pivotValue = array[pivot];", null, 1, null); // 15
        sourceCode.addCodeLine("swap(array, pivot, right);", null, 1, null); // 16
        sourceCode.addCodeLine("int storeIndex = left;", null, 1, null); // 17
        sourceCode.addCodeLine("for (int i = left; i < right; i++) {", null, 1, null); // 18
        sourceCode.addCodeLine("if (array[i] < pivotValue) {", null, 2, null); // 19
        sourceCode.addCodeLine("swap(array, storeIndex, i);", null, 3, null); // 20
        sourceCode.addCodeLine("storeIndex++;", null, 3, null); // 21
        sourceCode.addCodeLine("}", null, 2, null); // 22
        sourceCode.addCodeLine("}", null, 1, null); // 23
        sourceCode.addCodeLine("swap(array, right, storeIndex);", null, 1, null); // 24
        sourceCode.addCodeLine("return storeIndex;", null, 1, null); // 25
        sourceCode.addCodeLine("}", null, 0, null); // 26

        iArray.highlightCell(0, iArray.getLength() - 1, null, null);

        quickSelect(iArray, sourceCode, 0, (iArray.getLength() - 1), this.kSmallest);

        language.nextStep();
        sourceCode.unhighlight(7);
        language.hideAllPrimitives();
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

    private int quickSelect(IntArray array, SourceCode code, int left, int right, int kSmallest) {
        kSmallest -= 1;

        language.nextStep();
        info = language.newText(new Coordinates(400, 100), "Looking for " + String.valueOf(kSmallest + 1) + ". smallest",
                "kSmallestInformation", null, notificationProperties);
        this.varTable.set(K_SMALLEST_KEY, String.valueOf(kSmallest));

        language.nextStep();
        code.highlight(0);

        language.nextStep();
        code.unhighlight(0);
        code.highlight(1);

        if (left == right) {
            return array.getData(left);
        }

        language.nextStep();
        code.unhighlight(1);

        for (; ; ) {
            language.nextStep();
            code.highlight(3);

            language.nextStep();
            code.unhighlight(3);
            code.highlight(4);

            int pivot = randomPivot(left, right);
            this.varTable.set(PIVOT_KEY, String.valueOf(pivot));

            ArrayMarker pivotMarker = language.newArrayMarker(array, pivot, "pivot" + pointerCounter, null, pivotPointerProps);
            pivotMarker.move(pivot, null, defaultDuration);

            language.nextStep();
            code.unhighlight(4);
            code.highlight(5);

            pivot = partition(array, left, right, pivot, code);
            this.varTable.discard(STORE_INDEX_KEY);
            this.varTable.discard(PIVOT_VALUE_KEY);
            this.varTable.set(PIVOT_KEY, String.valueOf(pivot));

            language.nextStep();
            pivotMarker.move(pivot, null, defaultDuration);

            ArrayMarker kSmallestMarker = language.newArrayMarker(array, pivot, "kSmallest" + pointerCounter, null, kSmallestProps);
            kSmallestMarker.hide();

            language.nextStep();
            code.highlight(6);

            if (kSmallest == pivot) {
                pivotMarker.hide();
                info.hide();
                kSmallestMarker.move(pivot, null, defaultDuration);
                kSmallestMarker.show();
                int kSmallestValue = array.getData(kSmallest);

                language.nextStep();
                language.newText(new Coordinates(350, 60),
                        "kSmallest is equal to the pivot!",
                        "kSmallestNotification1", null, notificationProperties);
                language.newText(new Coordinates(350, 80),
                        "We are finished and found our kSmallest!",
                        "kSmallestNotification2", null, notificationProperties);
                language.newText(new Coordinates(350, 100),
                        String.valueOf(kSmallest + 1) + ". smallest value is " + String.valueOf(kSmallestValue),
                        "kSmallestNotification3", null, notificationProperties);
            }

            if (kSmallest == pivot) {
                language.nextStep();
                pivotMarker.hide();
                code.unhighlight(6);
                code.highlight(7);
                kSmallestMarker.hide();
                return array.getData(kSmallest);

            } else if (kSmallest < pivot) {
                language.nextStep();
                code.unhighlight(6);
                code.highlight(8);

                language.nextStep();
                code.unhighlight(8);
                code.highlight(9);

                right = pivot - 1;
                this.varTable.set(RIGHT_KEY, String.valueOf(right));

                language.nextStep();
                code.unhighlight(9);
            } else {
                language.nextStep();
                code.unhighlight(6);
                code.highlight(10);

                language.nextStep();
                code.unhighlight(10);
                code.highlight(11);

                left = pivot + 1;
                this.varTable.set(LEFT_KEY, String.valueOf(left));

                language.nextStep();
                code.unhighlight(11);
            }
            language.nextStep();
            array.unhighlightCell(0, array.getLength() - 1, null, null);
            array.highlightCell(left, right, null, null);
            pivotMarker.hide();
        }
    }

    private int partition(IntArray array, int left, int right, int pivot, SourceCode code) {
        language.nextStep();
        code.unhighlight(5);
        code.highlight(14);

        language.nextStep();
        code.unhighlight(14);
        code.highlight(15);

        int pivotValue = array.getData(pivot);
        this.varTable.declare("int", PIVOT_VALUE_KEY);
        this.varTable.set(PIVOT_VALUE_KEY, String.valueOf(pivotValue));

        language.nextStep();
        code.unhighlight(15);
        code.highlight(16);

        swap(array, pivot, right);

        language.nextStep();
        code.unhighlight(16);
        code.highlight(17);

        int storeIndex = left;
        this.varTable.declare("int", STORE_INDEX_KEY);
        this.varTable.set(STORE_INDEX_KEY, String.valueOf(storeIndex));

        ArrayMarker storeIndexMarker = language.newArrayMarker(array, storeIndex, "storeIndex" + pointerCounter, null, storeIndexProps);
        storeIndexMarker.move(storeIndex, null, defaultDuration);

        ArrayMarker loopMarker = language.newArrayMarker(array, left, "i" + pointerCounter, null, loopPointerProps);
        loopMarker.hide();

        language.nextStep();
        code.unhighlight(17);

        for (int i = left; i < right; i++) {
            language.nextStep();
            code.highlight(18);
            loopMarker.show();
            loopMarker.move(i, null, defaultDuration);

            language.nextStep();
            code.unhighlight(18);
            code.highlight(19);

            if (array.getData(i) >= pivotValue)
                code.unhighlight(19);

            if (array.getData(i) < pivotValue) {
                language.nextStep();
                code.unhighlight(19);
                code.highlight(20);

                swap(array, storeIndex, i);

                language.nextStep();
                code.unhighlight(20);
                code.highlight(21);

                storeIndex++;
                this.varTable.set(STORE_INDEX_KEY, String.valueOf(storeIndex));

                language.nextStep();
                storeIndexMarker.move(storeIndex, null, defaultDuration);

                language.nextStep();
                code.unhighlight(21);
            }
            loopMarker.hide();
        }
        language.nextStep();
        loopMarker.hide();
        code.highlight(24);

        swap(array, right, storeIndex);
        storeIndexMarker.hide();

        language.nextStep();
        code.unhighlight(24);

        return storeIndex;
    }

    private void swap(IntArray array, int a, int b) {
        language.nextStep();
        array.swap(a, b, null, swapDuration);
    }

    private int randomPivot(int left, int right) {
        return left + (int) Math.floor(Math.random() * (right - left + 1));
    }


//    public static void main(String[] args) {
//        Language language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, "Quickselect", "Yadullah Duman", 800, 600);
//        QuickselectGenerator qs = new QuickselectGenerator(language);
//        int[] array = {100, 90, 80, 70, 10, 60, 50, 40, 30, 20};
//        kSmallest = 3;
//        qs.start(array);
//        System.out.println(language);
//    }

    public void init() {
        language = Language.getLanguageInstance(AnimationType.ANIMALSCRIPT, this.getAlgorithmName(), this.getAnimationAuthor(), 800, 600);
        language.setStepMode(true);
    }

    public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {
        kSmallestProps = (ArrayMarkerProperties) props.getPropertiesByName("kSmallestProps");
        storeIndexProps = (ArrayMarkerProperties) props.getPropertiesByName("storeIndexProps");
        loopPointerProps = (ArrayMarkerProperties) props.getPropertiesByName("loopPointerProps");
        arrayProperties = (ArrayProperties) props.getPropertiesByName("arrayProperties");
        pivotPointerProps = (ArrayMarkerProperties) props.getPropertiesByName("pivotPointerProps");
        scProperties = (SourceCodeProperties) props.getPropertiesByName("scProperties");
        array = (int[]) primitives.get("array");
        kSmallest = (int) primitives.get("kSmallest");

        init();
        start(array);

        return language.toString();
    }

    public boolean validateInput(AnimationPropertiesContainer props, Hashtable<String, Object> primitives)
            throws IllegalArgumentException {
        kSmallest = (int) primitives.get("kSmallest");

        if (kSmallest == 0) {
            throw new IllegalArgumentException("" +
                    "Your kSmallest is invalid!\n" +
                    "Please pick a value >= 1 and <= array-length\n" +
                    "Imagine saying \"I want the third smallest element\", then pick 3 as kSmallest.");
        }
        return true;
    }

    public String getAlgorithmName() {
        return "Quickselect";
    }

    public String getAnimationAuthor() {
        return "Yadullah Duman";
    }

    public String getCodeExample() {
        return QUICKSELECT_SOURCE_CODE;
    }

    public Locale getContentLocale() {
        return Locale.US;
    }

    public String getDescription() {
        return QUICKSELECT_DESCRIPTION;
    }

    public String getFileExtension() {
        return "asu";
    }

    public GeneratorType getGeneratorType() {
        return new GeneratorType(GeneratorType.GENERATOR_TYPE_SEARCH);
    }

    public String getName() {
        return "Quickselect";
    }

    public String getOutputLanguage() {
        return Generator.JAVA_OUTPUT;
    }
}
