import factory.WebFactory;
import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.AboutYourselfPage;
import pages.LearningPage;
import pages.MainPage;

import java.util.concurrent.TimeUnit;

public class OtusSiteTest {
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(OtusSiteTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);
    private static MainPage mainPage;
    private static LearningPage learningPage;
    private static AboutYourselfPage aboutYourselfPage;


    @Before
    public void setUp() {
        driver = WebFactory.create(WebFactory.Browsers.CHROME);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        mainPage=new MainPage(driver);
    }

    @Test
    public void aboutYourselfFieldTest()  {
        String firstSocialNetworkName="VK";
        String secondSocialNetworkName="Skype";
        String firstSocialNetworkField="VK field test";
        String secondSocialNetworkField="Skype field test";
        driver.get(cfg.url());
        logger.info("Заходим на страницу OTUS");
        logIn();
        logger.info("Авторизируемся на сайте");
        aboutYourselfPageOpen();
        logger.info("Переходим в раздел о себе");
        socialNetworkAdd(firstSocialNetworkName,firstSocialNetworkField);
        socialNetworkAdd(secondSocialNetworkName,secondSocialNetworkField);
        logger.info("Добавляем социальные сети и заполняем поля");
        aboutYourselfPage.submitForm();
        logger.info("Сохраняем заполненые данные");
        driver.manage().deleteAllCookies();
        logger.info("Удаляем куки");
        driver.get(cfg.url());
        logger.info("Открываем OTUS в чистом браузере");
        logIn();
        logger.info("Авторизируемся на сайте");
        aboutYourselfPageOpen();
        logger.info("Переходим в раздел о себе");
        fieldEquals(firstSocialNetworkField,firstSocialNetworkName);
        fieldEquals(secondSocialNetworkField,secondSocialNetworkName);
        logger.info("Проверяем, что в разделе \"О себе\" отображаются указанные ранее данные");
        fieldClearing(firstSocialNetworkName, secondSocialNetworkName);
        logger.info("Чистим заполненые поля");
    }

    public static void logIn(){
        mainPage.signUnButtonClick();
        mainPage.typeMailField("testaccountotus@gmail.com");
        mainPage.typePasswordField("testpassword");
        mainPage.loginButtonClick();
    }

    public static void aboutYourselfPageOpen() {
        mainPage.hooverAvatarMenu();
        learningPage=mainPage.myCabinetButtonClick();
        aboutYourselfPage=learningPage.aboutYourselfClick();
    }

    public static void socialNetworkAdd(String socialNetworkName, String socialNetworkField) {
        aboutYourselfPage.communicationTypeButtonClick();
        aboutYourselfPage.socialNetworkChoose(socialNetworkName);
        aboutYourselfPage.typeSocialNetworkField(socialNetworkName,socialNetworkField);
        aboutYourselfPage.adButtonClick();
    }

    public static void fieldEquals(String socalNetworkField, String socialNetworkName){
        Assert.assertEquals(socalNetworkField,aboutYourselfPage.fieldValue(socialNetworkName));
    }

    public static void fieldClearing(String firstSocialNetworkName, String secondSocialNetworkName){
        aboutYourselfPage.fieldClearing(firstSocialNetworkName,secondSocialNetworkName);
        aboutYourselfPage.submitForm();
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
