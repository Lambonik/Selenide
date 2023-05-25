package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormTest {
    String generateDate(int dayToAdd, String pattern) {
        return LocalDate.now().plusDays(dayToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldTestFormSending() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79110001122");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void TestFormSendingSelectedCityInList() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ка").click();
        $$(".menu-item").find(exactText("Казань")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79110001122");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void TestFormSendingSelectedDateInFormCalendarIfMeetingInNextMonth() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] .icon-button").click();
        String meetingDay = generateDate(7, "d");
        int meetingMonth = Integer.parseInt(generateDate(7, "M"));
        int nowMonth = Integer.parseInt(generateDate(0, "M"));
        if (meetingMonth > nowMonth) {
            $("[data-step='1'].calendar__arrow_direction_right").click();
            $$(".calendar__day").find(exactText(meetingDay)).click();
        } else {
            $$(".calendar__day").find(exactText(meetingDay)).click();
        }
        ;
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79110001122");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void TestFormSendingSelectedDateInFormCalendarIfMeetingInThisMonth() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] .icon-button").click();
        String meetingDay = generateDate(4, "d");
        int meetingMonth = Integer.parseInt(generateDate(4, "M"));
        int nowMonth = Integer.parseInt(generateDate(0, "M"));
        if (meetingMonth > nowMonth) {
            $("[data-step='1'].calendar__arrow_direction_right").click();
            $$(".calendar__day").find(exactText(meetingDay)).click();
        } else {
            $$(".calendar__day").find(exactText(meetingDay)).click();
        }
        ;
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79110001122");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
