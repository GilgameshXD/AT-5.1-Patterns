package Test;

import Data.Data;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class Test {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @org.junit.jupiter.api.Test
    void shouldChangeMeetingDate() {
        Data.UserInfo user = Data.Registration.generateUser("ru");
        int daysFirst = 4;
        String firstMeetingDate = Data.generateDate(daysFirst);
        int daysSecond = 7;
        String secondMeetingDate = Data.generateDate(daysSecond);
        $("[data-test-id='city'] input").sendKeys(user.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input").sendKeys(user.getName());
        $("[data-test-id='phone'] input").sendKeys(user.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(secondMeetingDate);
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(visible, Duration.ofSeconds(15));
    }

}
