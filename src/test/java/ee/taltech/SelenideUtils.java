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

public class SelenideUtils {
	/**
	 * The constant profileButton.

	 */

	static String profileButton = ".tt-profile__img";
	static String languageSwitchButton = ".tt-profile__link:nth-child(1) .btn__text";

	/**
	 * Switch language if current not correct.
	 *
	 * @param shouldBeLanguageSwitchText -
	 */
	public static void SwitchLanguageIfCurrentNotCorrect(String shouldBeLanguageSwitchText) {

		String actualLanguageSwitchText = ClickProfileAndGetLanguageSwitchText();
		if (!actualLanguageSwitchText.equals(shouldBeLanguageSwitchText))
		{
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

		$(profileButton).click();
		return $(languageSwitchButton).getText();
	}

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

	// code example https://mbbaig.blog/selenide-webdriverfactory/
	public static class TaltechFactory extends ChromeDriverFactory {
		@Override
		@CheckReturnValue
		@Nonnull
		public MutableCapabilities createCapabilities(Config config, Browser browser, @Nullable Proxy proxy, File browserDownloadsFolder) {

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
