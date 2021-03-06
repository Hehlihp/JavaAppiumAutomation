import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.nio.file.WatchEvent;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","AndroidTestDevice");
            //capabilities.setCapability("platformVersion","9");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("app","/Users/autist/Google Drive/Courses/Automation/JavaAppiumAutomation/APKs/org.wikipedia.apk");

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        }
        @After
        public void tearDown()
        {
            driver.quit();
        }
        @Test
        public void functionCreation()
        {
            waitForElementAndClick(

                    By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                    "Cannot find Search Wikipedia input",
                    5

            );
            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    "Java",
                    "cannot find search input",
                    5
            );


            waitForElementPresent(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                    "Cannot find 'Object-oriented programming language topic searching by Java",
                    15
            );
        }
        @Test
        public void testCancelSearch()
        {
            waitForElementAndClick(
                    By.id("org.wikipedia:id/search_container"),
                    "Cannot find 'Search Wikipedia' input",
                    10
            );

            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    "Java",
                    "cannot find search input",
                    5
            );

            waitForElementAndClear(
                    By.id("org.wikipedia:id/search_src_text"),
                    "cannot find search field",
                    5


            );

            waitForElementAndClick(
                    By.id("org.wikipedia:id/search_close_btn"),
                    "Cannot find X to cancel search",
                    15
            );
            waitForElementNotPresent(
                    By.id("org.wikipedia:id/search_close_btn"),
                    "X is stil preseneted on page",
                    5

            );





        }
        @Test
        public void testCompareArticleTitle() {
            waitForElementAndClick(
                    By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                    "Cannot find 'Search Wikipedia' input",
                    5);
            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text, 'Search…')]"),
                    "Java",
                    "Cannot find search input",
                    5);
            waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                    "Cannot find 'Search Wikipedia' input",
                    5);
            WebElement title_element = waitForElementPresent(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "Cannot find article title",
                    15);

            String article_title = title_element.getAttribute("text");
            Assert.assertEquals(
                    "We see unexpected title!",
                    "Java (programming language)",
                    article_title
            );
        }

        @Test
        public void searchTest()
        {
            waitForElementAndClick(
                    By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                    "Cannot find 'Search Wikipedia' input",
                    5);
            WebElement search_field = waitForElementPresent(By.id("org.wikipedia:id/search_src_text"),
                    "Cannot find search field" ,
                    5);
            String search_text = search_field.getAttribute("text");
            Assert.assertEquals(
                    "Cannot find placeholder for searchbox",
                    "Search…",
                    search_text
            );

        }

        @Test
        public void searchTextAndClearTest() {

            waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]") ,
                    "Cannot find Search field",
                    5);


            waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                    "Java",
                    "Cannot find Search",
                    5);


            List<WebElement> search_result = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));

            System.out.println(search_result.size());

            int article_amount = search_result.size();

            Assert.assertTrue("Cannot find any articles", article_amount > 0);
            for (WebElement current_element: search_result){
                System.out.println(current_element.getAttribute("text"));
               Assert.assertTrue("Keyword isn't in every search result", current_element.getText().contains("Java") );
            }



            waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
                    "Cannot find Search",
                    5);


            waitForElementPresent(By.id("org.wikipedia:id/search_empty_image"),
                    "Search result must be empty",
                    5);

        }










        private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
        {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.withMessage(error_message + "\n");
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(by)
            );
        }
        private WebElement waitForElementPresent(By by, String error_message)
        {
            return waitForElementPresent(by, error_message, 5);
        }

        private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
            element.click();
            return element;
        }
         private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
            element.sendKeys(value);
            return element;
        }


        private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
        {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.withMessage(error_message + "\n");
            return wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(by)
            );
        }
        private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
            element.clear();
            return element;
        }






}
