import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main1 {
    public static void main(String[] args) {
        System.out.println("=== Модуль обработки геодезических данных ===");

        String inputData = "START_SLOPE#20240101_0.5...noise...angle.20240102_0.8###DEG#20240103_1.1" +
                "___slope.20240104_1.4/--/ANGLE#20240105_1.7***deg.20240106_2.0" +
                "!!!SLOPE#20240107_2.3&&&angle.20240108_2.6%%%DEG#20240109_2.9" +
                "+++slope.20240110_3.2===ANGLE#20240111_3.5_END";

        String regex = "(?i)(SLOPE|ANGLE|DEG)[#\\.]((\\d{4})(\\d{2})(\\d{2}))_(\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputData);

        StringBuilder sb = new StringBuilder();
        String recordBuffer = null;

        while (matcher.find()) {
            if (recordBuffer != null) {
                sb.append(recordBuffer).append("\n");
            }
            recordBuffer = "Угол наклона: [" + matcher.group(6) + "] (Замер: " +
                    matcher.group(5) + "." + matcher.group(4) + "." + matcher.group(3) + ")";
        }

        System.out.println("Результаты (последняя запись исключена):");
        System.out.print(sb.toString());
    }
}