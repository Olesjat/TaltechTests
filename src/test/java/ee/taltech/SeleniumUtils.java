package ee.taltech;


import org.jetbrains.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * The type Selenium utils.
 */
public class SeleniumUtils {
	/**
	 * The constant profileButton.
	 */
	static String profileButton = ".tt-profile__img";

	/**
	 * The constant languageSwitchButton.
	 */
	static String languageSwitchButton = ".tt-profile__link:nth-child(1) .btn__text";

	/**
	 * Switch language if current not correct.
	 *
	 * @param driver                     the driver
	 * @param shouldBeLanguageSwitchText the should be language switch text
	 */
	public static void SwitchLanguageIfCurrentNotCorrect(WebDriver driver, String shouldBeLanguageSwitchText) {
		String actualLanguageSwitchText = ClickProfileAndGetLanguageSwitchText(driver);
		if (!actualLanguageSwitchText.equals(shouldBeLanguageSwitchText))
		{
			driver.findElement(By.cssSelector(languageSwitchButton)).click();
		}
	}




	@NotNull
	public static String ClickProfileAndGetLanguageSwitchText(WebDriver driver) {
		driver.findElement(By.cssSelector(profileButton)).click();
		return driver.findElement(By.cssSelector(languageSwitchButton)).getText();
	}


	@NotNull
	static By byXPATH(String text) {
		return By.xpath("//*[text()='" + text + "']");
	}

	/**
	 * Find web element.
	 *
	 * @param driver
	 * @param language the language
	 * @param text     the text
	 * @return the web element
	 */

	public static WebElement find(WebDriver driver, String language, String text) {

		driver.get("https://student.taltech.ee/");

		switch (language) {
			case "ENGLISH": {
				String shouldBeLanguageSwitchText = "Lülitu eestikeelseks";
				SwitchLanguageIfCurrentNotCorrect(driver, shouldBeLanguageSwitchText);
				driver.findElement(byXPATH("Study Info")).click();
				break;
			}
			case "ESTONIAN": {
				String shouldBeLanguageSwitchText = "Switch to English";
				SwitchLanguageIfCurrentNotCorrect(driver, shouldBeLanguageSwitchText);
				driver.findElement(byXPATH("Õppeinfo")).click();
				break;
			}
		}
		return driver.findElement(By.linkText(text));
	}

	/**
	 * Gets chrome options.
	 *
	 * @return the chrome options
	 */
	@NotNull
	static ChromeOptions getChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--allow-profiles-outside-user-dir");
		options.addArguments("--user-data-dir=C:\\Autotests\\Selenide\\Students");
		options.addArguments("--profile-directory=TestProfile");
		return options;
	}
}
