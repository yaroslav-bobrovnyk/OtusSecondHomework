import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    final String TITLE="Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    enum Browsers{
        CHROME,
        FIREFOX,
        SAFARI,
        IE
    }

    public static WebDriver create(Browsers browsers){
        switch (browsers){
            case IE: {
                WebDriverManager.getInstance(InternetExplorerDriver.class).setup();
                logger.info("Драйвер IE поднят");
                return new InternetExplorerDriver();
            }
            case CHROME: {
                WebDriverManager.getInstance(ChromeDriver.class).setup();
                logger.info("Драйвер Chrome поднят");
                return new ChromeDriver();
            }
            case SAFARI: {
                WebDriverManager.getInstance(SafariDriver.class).setup();
                logger.info("Драйвер Safari поднят");
                return new SafariDriver();
            }
            case FIREFOX: {
                WebDriverManager.getInstance(FirefoxDriver.class).setup();
                logger.info("Драйвер Firefox поднят");
                return new FirefoxDriver();
            }
            default:
                return null;
        }
    }
    @Before
    public void setUp() {
        driver=create(Browsers.CHROME);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void openPage() {
        driver.get(cfg.url());
        logger.info("Открыта страница отус");
        Assert.assertEquals("Title страницы не совпадает ",TITLE,driver.getTitle());
        logger.info("Title страницы проверен");
    }
    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер отключен");
    }
}

