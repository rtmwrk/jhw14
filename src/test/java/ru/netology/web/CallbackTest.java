package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestWithFindElementsByClassName() {
        // Получаем массив элементов формы с именем класса "input__control"
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        // 1-й такой элемент - поле ввода фамилии и имени пользователя, заполняем его
        elements.get(0).sendKeys("Иванов Петр");
        // 2-й элемент - поле ввода номера телефона
        elements.get(1).sendKeys("+79181234567");
        // 3-й элемент - чекбокс соглашения с условиями, ставим галочку
        //driver.findElement(By.className("checkbox__box")).click();
        // 4-й элемент - кнопка отправки формы, нажимаем ее
        driver.findElement(By.className("button")).click();
        // Читаем страницу - ответ, ищем строку, подтверждающую успешную отправку данных формы
        String textActual = driver.findElement(By.className("paragraph")).getText();
        // Формируем ожидаемое значение строки
        String textExpected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        // Сравниваем значения
        assertEquals(textExpected, textActual.trim());
    }

    @Test
    void shouldTestWithFindElementsByCssSelector() {
        // К сожалению, элемент "form" html-страницы запроса данных клиента для оформления дебетовой карты
        // не имеет аттрибута "data-test-id" либо чего-то подобного. Поэтому найдем элемент "form" по
        // классу, а поля ввода по аттрибуту "data-test-id"
        //WebElement form = driver.findElement(By.cssSelector("[data-test-id=callback-form]"));
        WebElement form = driver.findElement(By.className("form"));
        // 1-й такой элемент - поле ввода фамилии и имени пользователя, заполняем его
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Петр");
        // 2-й элемент - поле ввода номера телефона
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79181234567");
        // 3-й элемент - чекбокс соглашения с условиями, ставим галочку
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        // 4-й элемент - кнопка отправки формы, нажимаем ее
        // И для кнопки отсутствует отдельный аттрибут типа "data-test-id" Отработаем элемент по классу
        driver.findElement(By.className("button")).click();
        // Читаем страницу - ответ, ищем строку, подтверждающую успешную отправку данных формы
        String textActual = driver.findElement(By.className("paragraph")).getText();
        // Формируем ожидаемое значение строки
        String textExpected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        // Сравниваем значения
        assertEquals(textExpected, textActual.trim());
    }
}

