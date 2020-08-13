import config.ServerConfig;
import factory.WebFactory;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    final String TITLE="Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("CHROME") String browser) {
        browser=browser.toUpperCase();
        driver= WebFactory.create(WebFactory.Browsers.valueOf(browser));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(enabled = true)
    public void openPage() {
        driver.get(cfg.url());
        logger.info("Открыта страница отус");
        Assert.assertEquals(TITLE,driver.getTitle(),"Title страницы не совпадает ");
        logger.info("Title страницы проверен");
    }
    @AfterMethod
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер отключен");
    }
}

