package runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import base_class_API.Common_Methods;
import io.cucumber.core.cli.Main;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "featureFile" }, glue = ("stepDefinitions"), plugin = {
		"json:target/cucumber-report/cucumber.json", "html:target/cucumber-report/cucumber.html",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "listener.Listener" },

		tags = ("@B2Cmakeinvoicesinglelineitem"))
//		tags = ("@zipERPordersinglelineitem"))
//		tags = ("@ZipERPordercancelinopenstate"))

public class Test_Runner extends AbstractTestNGCucumberTests {

	public static String tags;
	static String featurePath = System.getProperty("user.dir") + "//featureFile";
	static String stepdefinitionsPath = "stepDefinitions";
	public static Logger logger = LogManager.getLogger(Test_Runner.class.getName());

	@SuppressWarnings({ "static-access" })
	public static void main(String[] args) throws Throwable {

		for (int i = 0; i <= 100; i++) {
			System.out.println("User must enter the require tags");
			tags = Common_Methods.scan();

			if (tags != null) {
				if (tags.contains("@")) {
					try {
						Main main = new Main();
						main.run(featurePath, "--glue", stepdefinitionsPath, "--tags", tags, "--plugin",
								"json:target/cucumber-report/cucumber.json", "--plugin",
								"html:target/cucumber-report/cucumber.html", "--plugin",
								"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "--plugin",
								"listener.Listener");
					} catch (Exception e) {

						if (e.getMessage() != null) {
							logger.error(e.getMessage());
						}
					}

				} else {

					System.out.println("Tag is must for run the test cases. However, please provide any tags");
					System.out.println("");

				}
			}
			tags = null;
		}
	}
}