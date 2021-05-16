package ee.taltech;


import org.jetbrains.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;

// Набор вспомогательных переменных и методов
public class SeleniumUtils {
	// Кнопка профиля
	static String profileButton = ".tt-profile__img";

	// Кнопка смены языка
	static String languageSwitchButton = ".tt-profile__link:nth-child(1) .btn__text";

	public static void SwitchLanguageIfCurrentNotCorrect(WebDriver driver, String shouldBeLanguageSwitchText) {
		String actualLanguageSwitchText = ClickProfileAndGetLanguageSwitchText(driver);
		if (!actualLanguageSwitchText.equals(shouldBeLanguageSwitchText))
		{
			// Кода намного больше
			driver.findElement(By.cssSelector(languageSwitchButton)).click();
		}
	}

	// Кликаем по профилю и смотрим на какой язык можем переключиться
	@NotNull
	public static String ClickProfileAndGetLanguageSwitchText(WebDriver driver) {
		// Кода намного больше
		driver.findElement(By.cssSelector(profileButton)).click();
		return driver.findElement(By.cssSelector(languageSwitchButton)).getText();
	}

	// Поиск кнопки требует изучения всего html-кода
	@NotNull
	static By byXPATH(String text) {
		return By.xpath("//*[text()='" + text + "']");
	} // FIXME

	// Поиск
	public static WebElement find(WebDriver driver, String language, String text) {

		driver.get("https://student.taltech.ee/");

		switch (language) {
			case "ENGLISH": {
				String shouldBeLanguageSwitchText = "Lülitu eestikeelseks";
				SwitchLanguageIfCurrentNotCorrect(driver, shouldBeLanguageSwitchText);
				// Кода намного больше
				driver.findElement(byXPATH("Study Info")).click();
				break;
			}
			case "ESTONIAN": {
				String shouldBeLanguageSwitchText = "Switch to English";
				SwitchLanguageIfCurrentNotCorrect(driver, shouldBeLanguageSwitchText);
				// Кода намного больше
				driver.findElement(byXPATH("Õppeinfo")).click();
				break;
			}
		}

		// Кода намного больше
		return driver.findElement(By.linkText(text));
	}

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
