package runner;

import java.util.List;

import org.junit.Test;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import model.TestCase;
import utils.ExcelReader;
import utils.ReportWriter;

public class APITestRunnerTest {

    @Test
    public void runAllTests() throws Exception {

        List<TestCase> testCases = ExcelReader.read("testdata/test.xlsx");

        int passed = 0;
        int failed = 0;
        int total = testCases.size();

        StringBuilder detail = new StringBuilder();
        detail.append("\n## Chi tiết\n");

        for (TestCase tc : testCases) {

            if (tc.id == null || tc.id.trim().isEmpty()) continue;

            System.out.println("Running: " + tc.id);

            try {

                Response response;

                if (tc.method.equalsIgnoreCase("POST")) {

                    response = given()
                            .contentType("application/json")
                            .body(tc.body)
                            .when()
                            .post(tc.url);

                } else {

                    response = given()
                            .when()
                            .get(tc.url);
                }

                int actualStatus = response.getStatusCode();

                response.then().log().all();

                if (actualStatus == tc.expectedStatus) {

                    System.out.println("Status: PASS");
                    passed++;
                    detail.append("- ").append(tc.id).append(": PASS\n");

                } else {

                    System.out.println("Status: FAIL");
                    System.out.println("Expected: " + tc.expectedStatus);
                    System.out.println("Actual: " + actualStatus);

                    failed++;
                    detail.append("- ").append(tc.id).append(": FAIL\n");
                }

            } catch (Exception e) {

                System.out.println("Status: ERROR");
                System.out.println("Reason: " + e.getMessage());

                failed++;
                detail.append("- ").append(tc.id).append(": ERROR\n");
            }

            System.out.println("----------------------");
        }
        ReportWriter.writeReport(total, passed, failed, detail.toString());
    }
}