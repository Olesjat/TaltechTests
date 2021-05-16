package ee.taltech;


import org.jetbrains.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * The type Selenium utils.
 */
// Набор вспомогательных переменных и методов
public class SeleniumUtils {
	/**
	 * The constant profileButton.
	 */
// Кнопка профиля
	static String profileButton = ".tt-profile__img";

	/**
	 * The constant languageSwitchButton.
	 */
// Кнопка смены языка
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
			// Кода намного больше
			driver.findElement(By.cssSelector(languageSwitchButton)).click();
		}
	}

	/**
	 * Click profile and get language switch text string.
	 *
	 * @param driver the driver
	 * @return the string
	 */
// Кликаем по профилю и смотрим на какой язык можем переключиться
	@NotNull
	public static String ClickProfileAndGetLanguageSwitchText(WebDriver driver) {
		// Кода намного больше
		driver.findElement(By.cssSelector(profileButton)).click();
		return driver.findElement(By.cssSelector(languageSwitchButton)).getText();
	}

	/**
	 * By xpath by.
	 * Метод для поиска элемента сайта на основе html-кода.
	 * Selenium не имеет встроенного инструмента для поиска элемента сайта
	 * по тексту, поэтому данный метод пришлось написать вручную.
	 * @param text текст, который ищется внутри html-кода
	 * @return возвращаем элемент сайта
	 */

	@NotNull
	static By byXPATH(String text) {
		// Внутренний код * обозначет поиск текста в любых html+блоках
		return By.xpath("//*[text()='" + text + "']");
	}

	/**
	 * Find web element.
	 *
	 * @param driver   Для поиска элемента на сайте необходим входной параметр в виде драйвера
	 * @param language the language
	 * @param text     the text
	 * @return the web element
	 */
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
