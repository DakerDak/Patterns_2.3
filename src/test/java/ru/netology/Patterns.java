package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class Patterns {

    @BeforeEach
    public void openForm() {
        open("http://localhost:9999");
    }
    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=\"city\"] .input__control").setValue(validUser.getCity());
        $("[data-test-id=\"date\"] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=\"date\"] [placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[data-test-id=\"name\"] .input__control").setValue(validUser.getName());
        $("[data-test-id='phone'] .input__control").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button_size_m ").click();
        $x("//*[text()=\"Успешно!\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"Встреча успешно запланирована на \"]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15));

        $("[data-test-id=\"date\"] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=\"date\"] [placeholder='Дата встречи']").setValue(secondMeetingDate);
        $(".button_size_m ").click();
        $x("//*[text()=\"Необходимо подтверждение\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"У вас уже запланирована встреча на другую дату. Перепланировать?\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"Перепланировать\"]").click();
        $x("//*[text()=\"Успешно!\"]").should(Condition.visible, Duration.ofSeconds(15));
        $x("//*[text()=\"Встреча успешно запланирована на \"]").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15));
    }

//    @Test
//    void test() {
//        Configuration.holdBrowserOpen = true;
//        String city = faker.address().cityName();
//        $("[data-test-id=\"city\"] .input__control").setValue(city);
//        $("[data-test-id=\"date\"] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
//        LocalDate futureDay = today.plusDays(5);
//        $("[data-test-id=\"date\"] [placeholder='Дата встречи']").setValue(futureDay.format(formatter));
//        String FIO = faker.name().firstName() + " " + faker.name().lastName();
//        $("[data-test-id=\"name\"] .input__control").setValue(FIO);
//        String phoneNumber = faker.phoneNumber().phoneNumber();
//        $("[data-test-id='phone'] .input__control").setValue(phoneNumber);
//        $("[data-test-id='agreement']").click();
//        $(".button_size_m ").click();
//        $x("//*[contains(text(),\"Встреча успешно запланирована на\")]").should(Condition.visible, Duration.ofSeconds(2));
//        $(".button_size_m ").click();
//        $x("//*[contains(text(),\"У вас уже запланирована встреча на другую дату\")]").should(Condition.visible, Duration.ofSeconds(2));
//        $(".button_size_s").click();
//
//        $x("//*[contains(text(),\"Встреча успешно запланирована на\")]").should(Condition.visible, Duration.ofSeconds(2));
//    }
}

//java -jar "D:\IDEA\Projects\Patterns\artifacts\app-card-delivery.jar"