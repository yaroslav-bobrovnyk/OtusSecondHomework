import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class YandexMarket {
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(YandexMarket.class);

    @Before
    public void setUp() {
        WebDriverManager.getInstance(FirefoxDriver.class).setup();
        driver = new FirefoxDriver();
        logger.info("Драйвер поднят");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void yandexTest() {
        driver.get("https://market.yandex.ru/");
        driver.findElement(By.xpath("//span[text()=\"Электроника\"]")).click();
        logger.info("Переходим в раздел Электроника");
        driver.findElement(By.xpath("//a[contains(text(), \"Мобильные\")]")).click();
        logger.info("Переходим в раздел Мобильные");
        driver.findElement(By.xpath("//span[text()=\"HUAWEI\" and @class=\"NVoaOvqe58\"]")).click();
        driver.findElement(By.xpath("//span[text()=\"Xiaomi\" and @class=\"NVoaOvqe58\"]")).click();
        logger.info("Сортируем по HUAWEI и Xiaomi");
        new WebDriverWait(driver,6).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), \"Найдено\")]")));
        showMoreWait();
        driver.findElement(By.xpath("//button[contains(text(),\"по цене\")]")).click();
        logger.info("Сортируем по цене");
        showMoreWait();
        driver.findElement(By.
                xpath("(//div[contains(text(),\"HUAWEI\")]/ancestor::article//div[@class=\"_3lszCe6vYR _3sRZ6ngZIj\"]//div[@class=\"_1CXljk9rtd\"])[1]")).
                click();
        logger.info("Добавляем в сравнение HUAWEI");
        favoriteMessageCheck();
        driver.findElement(By.
                xpath("(//div[contains(text(),\"Xiaomi\")]/ancestor::article//div[@class=\"_3lszCe6vYR _3sRZ6ngZIj\"]//div[@class=\"_1CXljk9rtd\"])[1]")).
                click();
        logger.info("Добавляем в сравнение Xiaomi");
        favoriteMessageCheck();
        driver.findElement(By.xpath("//span[@class=\"_24DZ8VtEeO _3Vm6nmYXxJ\"]")).click();
        logger.info("Переходим в раздел Сравнение");
        List list =driver.findElements(By.xpath("//div[@class=\"LwwocgVx0q zvRJMhRW-w\"]"));
        logger.info("В списке сравнения "+list.size()+" товара");
        driver.findElement(By.xpath("//button[text()=\"Все характеристики\"]")).click();
        logger.info("Нажимаем на Все характеристики");
        List operationSystem=driver.findElements(By.xpath("//div[text()=\"Операционная система\"]"));
        listSize(operationSystem);
        driver.findElement(By.xpath("//button[text()=\"Различающиеся характеристики\"]")).click();
        logger.info("Нажимаем на Различающиеся характеристики");
        List operationSystemDifferent=driver.findElements(By.xpath("//div[text()=\"Операционная система\"]"));
        listSize(operationSystemDifferent);
    }

    private static void listSize(List operationSystem){
        if (operationSystem.size()>0){
            logger.info("Позиция \"Операционная система\" отображается в списке");
        }
        else
            logger.info("Позиция \"Операционная система\" в списке отсутствует");
    }

    private static void favoriteMessageCheck(){
        if (driver.findElement(By.xpath("//div[@class=\"nMEoEKZaF-\"]")).isDisplayed()){
            logger.info("Плашка появилась");
        }
    }
    private static void showMoreWait(){
        new WebDriverWait(driver,10).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=\"Показать еще\"]")));
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
