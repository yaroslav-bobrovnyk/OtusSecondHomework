package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LearningPage {
    private WebDriver driver;

    public LearningPage(WebDriver driver) {
        this.driver = driver;
    }

    By aboutYourselfButton = By.xpath("(//div[@class=\"nav__items\"]//a[@title=\"О себе\"])[1]");

    public AboutYourselfPage aboutYourselfClick(){
        driver.findElement(aboutYourselfButton).click();
        return new AboutYourselfPage(driver);
    }
}
