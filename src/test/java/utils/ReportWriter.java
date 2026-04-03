package utils;

import java.io.FileWriter;

public class ReportWriter {

    public static void writeReport(int total, int passed, int failed, String detail) throws Exception {

        int executed = passed + failed;
        int blocked = total - executed;
        double percent = (executed * 100.0) / total;

        String content = "## Thống kê Test Case\n\n" +
                "| Chỉ số | Giá trị |\n" +
                "|---|---|\n" +
                "| Tổng số Test Case | " + total + " |\n" +
                "| Dã thực hiện | " + executed + " |\n" +
                "| Passed | " + passed + " |\n" +
                "| Failed | " + failed + " |\n" +
                "| Blocked | " + blocked + " |\n" +
                "| Tỷ lệ hoàn thành (%) | " + String.format("%.2f", percent) + "% |\n" +
                detail;

        FileWriter writer = new FileWriter("report.md");
        writer.write(content);
        writer.close();
    }
}