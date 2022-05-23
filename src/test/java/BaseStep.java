import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BaseStep extends BaseTest {

    FluentWait<WebDriver> wait;

    public BaseStep() {
        wait = new FluentWait<>(appiumDriver);
        wait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
    }

    @Step("<second> kadar bekle")
    public void waitForsecond(int second) throws InterruptedException {
        Thread.sleep(1000 * second);
        logger.info(second+" Saniye Kadar Beklendi.");
    }

    @Step("<id> ID'li elemente tıkla")
    public void clickElementByid(String id) throws InterruptedException {
        appiumDriver.findElement(By.id(id)).click();
        waitForsecond(2);
        logger.info(id+" ID'li Elemente Tıklandı.");
    }

    @Step("<id> ID'li elemente <keyword> değerini yaz")
    public void sendKeyByID(String Key, String keyword) throws InterruptedException {
        appiumDriver.findElement(By.id(Key)).sendKeys(keyword);
        waitForsecond(2);
        logger.info(Key+" ID'li elementin içine "+keyword+" yazıldı.");

    }

    @Step("<xpath> xpath'li elemente tıkla.")
    public void clickElementByxpath(String xpath) throws InterruptedException {
        appiumDriver.findElement(By.xpath(xpath)).click();
        waitForsecond(2);
        logger.info(xpath+" Xpath'li Elemente Tıklandı.");
    }


    @Step("Sayfayı yukarı kaydır")
    public void swipeUpI() {
        Dimension dims = appiumDriver.manage().window().getSize();
        PointOption pointOptionStart, pointOptionEnd;
        int edgeBorder = 10;
        final int PRESS_TIME = 200; // ms
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        System.out.println("pointOptionStart " + pointOptionStart);
        pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
        new TouchAction(appiumDriver)
                .press(pointOptionStart)
                // a bit more reliable when we add small wait
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                .moveTo(pointOptionEnd)
                .release().perform();
        logger.info("Sayfa Yukarı Kaydırıldı.");
    }


    @Step("<id> bul ve <keyword> değeri var mı kontrol et.")
    public void textControlByID(String id, String keyword) throws InterruptedException {
        waitForsecond(3);
        Assert.assertTrue("Text değeri bulunmamadı ", appiumDriver.findElement(By.id(id)).getText().equals(keyword));
        logger.info(id+" ID'li elementin "+keyword+ " değeri içerip içermediği kontrol edildi.");
    }

    @Step("<xpath> Xpath'li element visible mı kontrol et.")
    public void isElementVisibleByXpath(String xpath) throws InterruptedException {
        Assert.assertTrue(xpath + " xpath'li böyle bir element bulunamadı.", isElementVisible(By.xpath(xpath)));
        logger.info(xpath+" Xpath'li elementin görünür olduğu kontrol edildi.");
        waitForsecond(3);
    }

    @Step("<id> ID'li ürün visible mı kontrol et.")
    public void isElementVisibleByID(String id) throws InterruptedException {
        Assert.assertTrue(id + " ID'li böyle bir element bulunamadı.", isElementVisible(By.id(id)));
        logger.info(id+" ID'li elementin görünür olduğu kontrol edildi.");
        waitForsecond(3);
    }

    @Step("<xpath> ile rastgele element seç.")
    public void randomPicker(String xpath) throws InterruptedException {
        List<MobileElement> elementList = appiumDriver.findElements(By.xpath(xpath));
        Random random = new Random();
        int randomItemPicked = random.nextInt(elementList.size());
        elementList.get(randomItemPicked).click();
        logger.info("Rastgele Bir element Seçildi.");
        waitForsecond(2);
    }

    @Step("<number> kere geri sayfaya git")
    public void goBack(int number) throws InterruptedException {

        for (int i = 1; i <= number; i++) {
            appiumDriver.navigate().back();
            logger.info(i+" kere geri butonuna tıklandı.");
            waitForsecond(1);
        }

    }


    private boolean isElementVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
