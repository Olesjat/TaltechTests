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

// Набор вспомогательных переменных и методов
public class SelenideUtils {
	// Кнопка профиля
	static String profileButton = ".tt-profile__img";

	// Кнопка смены языка
	static String languageSwitchButton = ".tt-profile__link:nth-child(1) .btn__text";

	public static void SwitchLanguageIfCurrentNotCorrect(String shouldBeLanguageSwitchText) {
		String actualLanguageSwitchText = ClickProfileAndGetLanguageSwitchText();
		if (!actualLanguageSwitchText.equals(shouldBeLanguageSwitchText))
		{
			$(languageSwitchButton).click();
		}

	}

	// Кликаем по профилю и смотрим на какой язык можем переключиться
	@NotNull
	public static String ClickProfileAndGetLanguageSwitchText() {
		$(profileButton).click();
		return $(languageSwitchButton).getText();
	}

	// Поиск
	public static By find(String language, String text) {
		open("https://student.taltech.ee/");

		switch (language) {
			case "ENGLISH": {
				String shouldBeLanguageSwitchText = "Lülitu eestikeelseks";
				SwitchLanguageIfCurrentNotCorrect(shouldBeLanguageSwitchText);
				$(withText("Study Info")).click();
				break;
			}
			case "ESTONIAN": {
				String shouldBeLanguageSwitchText = "Switch to English";
				SwitchLanguageIfCurrentNotCorrect(shouldBeLanguageSwitchText);
				$(withText("Õppeinfo")).click();
				break;
			}
		}

		return withText(text);
	}

	// https://mbbaig.blog/selenide-webdriverfactory/
	public static class TaltechFactory extends ChromeDriverFactory {
		@Override
		@CheckReturnValue
		@Nonnull
		public MutableCapabilities createCapabilities(Config config, Browser browser, @Nullable Proxy proxy, File browserDownloadsFolder) {
			// Настраиваем работу браузера под специальный профиль тестирования
			return new MergeableCapabilities(getChromeOptions(), createCommonCapabilities(config, browser, proxy));
		}
	}

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
