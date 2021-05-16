package ee.taltech;

import com.codeborne.selenide.*;
import com.codeborne.selenide.Selectors.*;
import com.codeborne.selenide.webdriver.*;
import org.jetbrains.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import javax.annotation.*;
import javax.annotation.Nullable;
import java.io.*;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * The type Selenide utils.
 */
// Набор вспомогательных переменных и методов
public class SelenideUtils {
	/**
	 * The constant profileButton.
	 * Кнопка профиля описывается css-селектором
	 */

	static String profileButton = ".tt-profile__img";

	/**
	 * The constant languageSwitchButton.
	 * Кнопка смены языка описывается css-селектором
	 */

	static String languageSwitchButton = ".tt-profile__link:nth-child(1) .btn__text";

	/**
	 * Switch language if current not correct.
	 *
	 * @param shouldBeLanguageSwitchText - текст, который должен быть написан на кнопке переключения языка
	 */
	public static void SwitchLanguageIfCurrentNotCorrect(String shouldBeLanguageSwitchText) {
		// Кликаем по профилю и зачитываем текст на кнопке переключения языка
		String actualLanguageSwitchText = ClickProfileAndGetLanguageSwitchText();

		// Если ожидаемый текст и текущий не равны, значит мы не в том языке
		if (!actualLanguageSwitchText.equals(shouldBeLanguageSwitchText))
		{
			// Кликаем по кнопке переключения языка
			$(languageSwitchButton).click();
		}

	}

	/**
	 * Click profile and get language switch text string.
	 *
	 * @return the string
	 */
	@NotNull
	public static String ClickProfileAndGetLanguageSwitchText() {
		// Кликаем по кнопке профиля
		$(profileButton).click();

		// Вытаскиваем текст из кнопки переключения языка
		return $(languageSwitchButton).getText();
	}

	/**
	 * Поиск элемента на сайте.
	 *
	 * @param language язык, на котором мы должны находиться
	 * @param text     текст элемента, который мы ищем
	 * @return возвращаем элемент сайта
	 */
	public static By find(String language, String text) {
		// Открываем ссылку на основную страницу сайта
		open("https://student.taltech.ee/");


		switch (language) {
			case "ENGLISH": {
				// На основе выбранного языка проводим проверку, что мы находимся в режиме этого языка,
				// используя ожидаемый текст на кнопке переключения языка
				String shouldBeLanguageSwitchText = "Lülitu eestikeelseks";
				// Проверяем язык и по необходимости переключаем его
				SwitchLanguageIfCurrentNotCorrect(shouldBeLanguageSwitchText);

				// Осталось кликнуть по основному тестируемому меню информации для студентов
				$(withText("Study Info")).click();
				break;
			}
			case "ESTONIAN": {
				// Аналогично предыдущему кейсу
				String shouldBeLanguageSwitchText = "Switch to English";
				SwitchLanguageIfCurrentNotCorrect(shouldBeLanguageSwitchText);
				$(withText("Õppeinfo")).click();
				break;
			}
		}

		// После открытия основного информационного меню студента надо найти элемент в подменю
		// и вернуть его
		return withText(text);
	}

	/**
	 * The type Taltech factory.
	 * Класс для настройки конфигурации браузера в автоматическом режиме
	 * Пример использования: https://mbbaig.blog/selenide-webdriverfactory/
	 */

	public static class TaltechFactory extends ChromeDriverFactory {
		@Override
		@CheckReturnValue
		@Nonnull
		public MutableCapabilities createCapabilities(Config config, Browser browser, @Nullable Proxy proxy, File browserDownloadsFolder) {
			// Настраиваем работу браузера под специальный профиль тестирования
			return new MergeableCapabilities(getChromeOptions(), createCommonCapabilities(config, browser, proxy));
		}
	}

	// Метод для возврата опций браузера Chrome
	@NotNull
	private static ChromeOptions getChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--allow-profiles-outside-user-dir");
		options.addArguments("--user-data-dir=C:\\Autotests\\Selenide\\Students");
		options.addArguments("--profile-directory=TestProfile");
		return options;
	}
}
