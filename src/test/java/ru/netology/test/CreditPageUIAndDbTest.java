package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Card;
import ru.netology.data.DbUtils;
import ru.netology.page.CreditPage;
import ru.netology.page.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;

public class CreditPageUIAndDbTest {
    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\Temp\\chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        DbUtils.clearTables();
        String url = System.getProperty("sut.url");
        open(url);

    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }


    @Test
    @Order(1)
    void shouldBuyInCreditGate() throws SQLException {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getCreditStatus());
    }

    @Test
    @Order(2)
    void shouldBuyInCreditGateWithNameInLatinLetters() throws SQLException {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidNameInLatinLetters(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getCreditStatus());
    }

    @Test
    @Order(3)
    void shouldNotBuyInCreditGateWithDeclinedCardNumber() throws SQLException {
        Card card = new Card(getDeclinedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkDeclineNotification();

    }

    @Test
    @Order(1)
    void shouldNotBuyInCreditGateWithInvalidCardNumber() throws SQLException {
        Card card = new Card(getInvalidCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkDeclineNotification();

    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithShortCardNumber() {
        Card card = new Card(getShortCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    @Order(3)
    void shouldNotBuyInCreditGateWithEmptyCardNumber() {
        Card card = new Card(null, getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkRequiredField();
    }

    @Test
    @Order(1)
    void shouldNotBuyInCreditGateWithInvalidMonth() {
        Card card = new Card(getApprovedNumber(), "00", getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidDate();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithNonExistingMonth() {
        Card card = new Card(getApprovedNumber(), "13", getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidDate();

    }

    @Test
    @Order(3)
    void shouldNotBuyInCreditGateWithExpiredMonth() {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getCurrentYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkExpiredDate();
    }

    @Test
    @Order(4)
    void shouldNotBuyInCreditGateWithEmptyMonth() {
        Card card = new Card(getApprovedNumber(), null, getNextYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkRequiredField();
    }

    @Test
    @Order(1)
    void shouldNotBuyInCreditGateWithExpiredYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getLastYear(), getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkExpiredDate();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithEmptyYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), null, getValidName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkRequiredField();
    }


    @Test
    @Order(1)
    void shouldNotBuyInCreditGateWithOnlyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidName();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithOnlyNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyNameInLatinLetters(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidName();
    }

    @Test
    @Order(3)
    void shouldNotBuyInCreditGateWithOnlySurname() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurname(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidName();
    }


    @Test
    @Order(4)
    void shouldNotBuyInCreditGateWithOnlySurnameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurnameInLatinLetters(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidName();
    }

    @Test
    @Order(5)
    void shouldNotBuyInCreditGateWithNameAndSurnameWithDash() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), "Иван-Иванов", getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidFormat();
    }

    @Test
    @Order(6)
    void shouldNotBuyInCreditGateWithTooLongName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getTooLongName(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkLongName();
    }

    @Test
    @Order(7)
    void shouldNotBuyInCreditGateWithDigitsInName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithNumbers(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidDataName();
    }

    @Test
    @Order(8)
    void shouldNotBuyInCreditGateWithTooShortName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithOneLetter(), getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkShortName();
    }

    @Test
    @Order(9)
    void shouldNotBuyInCreditGateWithEmptyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), null, getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkRequiredField();
    }

    @Test
    @Order(10)
    void shouldNotBuyInCreditGateWithSpaceInsteadOfName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), " ", getValidCvc());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidDataName();
    }

    @Test
    @Order(1)
    void shouldNotBuyInCreditGateWithOneDigitInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithOneDigit());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidCvc();
    }

    @Test
    @Order(2)
    void shouldNotBuyInCreditGateWithTwoDigitsInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithTwoDigits());
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkInvalidCvc();
    }


    @Test
    @Order(3)
    void shouldNotBuyInCreditGateWithEmptyCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), null);
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkRequiredField();
    }



    @Test
    @Order(1)
    void shouldNotBuyInCreditGateWithAllEmptyFields() {
        Card card = new Card(null, null, null, null, null);
        val startPage = new StartPage();
        startPage.buyInCredit();
        val creditPage = new CreditPage();
        creditPage.fulfillData(card);
        creditPage.checkAllFieldsAreRequired();

    }
}