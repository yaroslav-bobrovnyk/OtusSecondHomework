package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AboutYourselfPage {
    private WebDriver driver;

    public AboutYourselfPage(WebDriver driver) {
        this.driver = driver;
    }

    By addButton = By.xpath("//button[text()=\"Добавить\"]");
    By communicationTypeButton = By.xpath("//span[contains(text(),\"Способ связи\")]");
    String socailNetworkButton ="(//button[@title=\"%s\"])[last()]";
    String socialNetworkField = "//div[contains(text(),\"%s\")]/ancestor::div[@class=\"container__col container__col_12 container__col_middle\"]//input[@type=\"text\"]";
    By saveButton = By.name("continue");

    public AboutYourselfPage adButtonClick(){
        driver.findElement(addButton).click();
        return this;
    }

    public AboutYourselfPage communicationTypeButtonClick(){
        driver.findElement(communicationTypeButton).click();
        return this;
    }

    public AboutYourselfPage socialNetworkChoose(String networkName){
        driver.findElement(By.xpath(String.format(socailNetworkButton,networkName))).click();
        return this;
    }

    public AboutYourselfPage typeSocialNetworkField(String fieldName, String networkField){
        driver.findElement(By.xpath(String.format(socialNetworkField,fieldName))).sendKeys(networkField);
        return this;
    }

    public AboutYourselfPage submitForm(){
        driver.findElement(saveButton).click();
        return this;
    }

    public String fieldValue(String fieldName){
        return driver.findElement(By.xpath(String.format(socialNetworkField,fieldName))).getAttribute("value");
    }

    public AboutYourselfPage fieldClearing(String fieldName1,String fieldName2){
        driver.findElement(By.xpath(String.format(socialNetworkField,fieldName1))).clear();
        driver.findElement(By.xpath(String.format(socialNetworkField,fieldName2))).clear();
        return this;
    }
}
