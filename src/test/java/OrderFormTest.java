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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderFormTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp(){
        //driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown(){
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestPositiveAllFields(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Сту-дент Нетол огии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }

    @Test
    void shouldTestWithSymbolsInName(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("(Студент.,? Нетологии;");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void shouldTestWithLatinLettersInName(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Sтудент Nетоlогии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void shouldTestWithNumbersInName(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Сту111дент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void shouldTestWithEmptyName(){
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    void shouldTestWithToShotPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+7987654321");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }
    @Test
    void shouldTestWithToLongPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+798765432111");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }


    @Test
    void shouldTestWithLettersInPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+7987654321f");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid.input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void shouldTestWithSymbolsInPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+7-987+654*3210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void shouldTestWithoutPlusInPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("8987654321");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void shouldTestWithPlusOnAnotherPositionInPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("79876+54321");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    void shouldTestWithEmptyPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector(".input_type_tel.input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    void shouldTestWithoutSubmittedCheckbox(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        assertTrue(driver.findElement(By.cssSelector(".checkbox.input_invalid")).isDisplayed());
    }

    @Test
    void shouldTestWithEmptyNameAndPhone(){
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText().trim());
    }

    @Test
    void shouldTestWithAllFieldsEmpty(){
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText().trim());
    }

    @Test
    void shouldTestWithNameAndPhoneNotCorrectWithSubmittedCheckbox(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("(Студент.,? Нетологии;");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+7987---6543210");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void shouldTestWithNameAndPhoneNotCorrectWithoutSubmittedCheckbox(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("(Студент.,? Нетологии;");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+7987---6543210");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input_ _sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void shouldTestWithNameNotCorrectWithoutSubmittedCheckbox(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("(Студент.,? Нетологии;");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    void shouldTestWithPhoneNotCorrectWithoutSubmittedCheckbox(){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__control")).sendKeys("Студент Нетологии");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__control")).sendKeys("+7987---6543210");
        driver.findElement(By.cssSelector("[type=\"button\"]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }
}



