package ru.netology.delivery.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.TestDataGenerator;
import ru.netology.delivery.data.UserEntry;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTestsPositive {

    private static Faker faker;

    @BeforeAll
    static void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Positive: short path")
    public void shdTestPositiveShortPath() {

        UserEntry newUser = TestDataGenerator.generateNewUser();

        $(By.xpath("//span[@data-test-id='city']/descendant::input[@placeholder='�����']")).setValue(newUser.getDeliveryCity());
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // �������������� ������� �������� �� ��������� �������� ���������� ������ "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).setValue(TestDataGenerator.generateDateForInput(3));
        // ������ ����� ���� �� 3 ��� ����� ������� ����
        $(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).setValue(newUser.getNameSurname());
        $(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).setValue(newUser.getPhoneNumber());
        $(By.xpath("//label[@data-test-id='agreement']")).click();
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"�������������\"]")).click();
        $(By.xpath("//div[@data-test-id='success-notification']/descendant::div[text()=\"�������!\"]")).should(Condition.appear);
        // ��������� ������� ����������� � ������ �������
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("������� ������� ������������� �� " + TestDataGenerator.generateDateForInput(3))); // � ��� � ������ �������� ����� �����������

        // ����������, ������ ���������, ������ ����� �������� ����
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // �������������� ������� �������� ����� �������� ���������� ������ "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).setValue(TestDataGenerator.generateDateForInput(7));
        // ������ ����� ���� �� 7 ���� ����� ������� ����
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"�������������\"]")).click(); // ����� ��� �� ������ ��� ����� ������������
        $x("//div[@data-test-id='replan-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("� ��� ��� ������������� ������� �� ������ ����. ���������������?")); // ������ ���������� ������� ������� ����������� � ����� �������
        $x("//div[@data-test-id='replan-notification']/descendant::button[@role='button']").click();
        // �������� �� ������� ������� ������
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("������� ������� ������������� �� " + TestDataGenerator.generateDateForInput(7))); // ����� ���� ������ �������
        // ���������� �����������, �� � ����� �����, ���� ������� �� � ���������
    }

    @Test
    @DisplayName("Positive: same date twice")
    public void shdTestPositiveShortPathWithSameDate() {

        UserEntry newUser = TestDataGenerator.generateNewUser();

        $(By.xpath("//span[@data-test-id='city']/descendant::input[@placeholder='�����']")).setValue(newUser.getDeliveryCity());
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // �������������� ������� �������� �� ��������� �������� ���������� ������ "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).setValue(TestDataGenerator.generateDateForInput(3));
        // ������ ����� ���� �� 3 ��� ����� ������� ����
        $(By.xpath("//span[@data-test-id='name']/descendant::input[@name='name']")).setValue(newUser.getNameSurname());
        $(By.xpath("//span[@data-test-id='phone']/descendant::input[@name='phone']")).setValue(newUser.getPhoneNumber());
        $(By.xpath("//label[@data-test-id='agreement']")).click();
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"�������������\"]")).click();
        $(By.xpath("//div[@data-test-id='success-notification']/descendant::div[text()=\"�������!\"]")).should(Condition.appear);
        // ��������� ������� ����������� � ������ �������
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("������� ������� ������������� �� " + TestDataGenerator.generateDateForInput(3))); // � ��� � ������ �������� ����� �����������

        // �������� ������ ����
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).sendKeys(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE));
        // �������������� ������� �������� ����� �������� ���������� ������ "Ctrl + Backspace"
        $(By.xpath("//span[@data-test-id='date']/descendant::input[@placeholder='���� �������']")).setValue(TestDataGenerator.generateDateForInput(3));
        // ����� �� �� ����
        $(By.xpath("//button[@role='button']/descendant::span[text()=\"�������������\"]")).click(); // ����� ��� �� ������ ��� ����� ������������
        $x("//div[@data-test-id='replan-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("� ��� ��� ������������� ������� �� ������ ����. ���������������?")); // ������ ���������� ������� ������� ����������� � ����� �������
        $x("//div[@data-test-id='replan-notification']/descendant::button[@role='button']").click();
        // �������� �� ������� ������� ������
        $x("//div[@data-test-id='success-notification']/descendant::div[@class='notification__content']")
                .shouldHave(Condition.text("������� ������� ������������� �� " + TestDataGenerator.generateDateForInput(3)));
        // ����� �� ����������� ������ ���� ��� ��
    }
}
