import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignupTests{

    private final ChromeDriver driver = new ChromeDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private static final Logger log = LogManager.getLogger(SignupTests.class);
    @BeforeAll
    public void setupClass() {
        Configurator.setLevel(LogManager.getLogger(SignupTests.class).getName(), Level.INFO);

        String log4jConfPath = "./src/main/properties/log4j2.properties";
        PropertyConfigurator.configure(log4jConfPath);

        //Pls change this to match your own server's ip or port number if you're running your code from a container.
        driver.get("http://localhost:3000");    //default as docker port

        //Getting to the Register page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Close Welcome Banner']")));
        driver.findElement(By.xpath("//button[@aria-label='Close Welcome Banner']")).click();
        driver.findElement(By.xpath("//button[@id='navbarAccount']")).click();
        driver.findElement(By.xpath("//button[@routerLink='/login']")).click();
        driver.findElement(By.xpath("//a[@routerlink='/register']")).click();
    }

    @AfterEach
    public void refreshPage() throws Exception{
        System.out.println("refresh once...");
        driver.navigate().refresh();
        sleep(1000);  //Prevent page reloading too fast
    }

    @Test
    public void testEmptyEmail() {
        log.info("TEST CASE : Empty email input");
        fillEmail("");

        log.log(Level.INFO,"EXPECTED : prompt shows Please provide an email address");
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Please provide an email address')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testInvalidEmail() {
        log.info("TEST CASE : Invalid email input");
        fillEmail("admin@");

        log.info("EXPECTED : prompt shows Please provide an email address");
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Email address is not valid')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testEmptyPassword() {
        log.info("TEST CASE : Invalid email input");
        fillPassword("");

        log.info("EXPECTED : prompt shows Please provide a password");
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Please provide a password')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testInvalidPassword() {
        log.info("TEST CASE : Invalid password input");
        fillPassword("1a34");

        log.info("EXPECTED : prompt shows Password must be 5-40 characters long");
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Password must be 5-40 characters long')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testEmptyRepeatPassword() {
        log.info("TEST CASE : Empty repeatPassword input");
        fillRepeatPassword("");

        log.info("EXPECTED : prompt shows Please repeat your password");
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Please repeat your password')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testUnmatchedRepeatPassword() {
        log.info("TEST CASE : Unmatched repeatPassword input");
        fillPassword("1a345");
        fillRepeatPassword("45678");

        log.info("EXPECTED : prompt shows Passwords do not match");
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Passwords do not match')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testUnselectedSecurityQuestion() {
        log.info("TEST CASE : Unselected security question");
        WebElement element = driver.findElement(By.xpath("//mat-select[@aria-label='Selection list for the security question']"));
        element.click();

        log.info("EXPECTED : prompt shows Please select a security question");
        try {
            element.sendKeys(Keys.ESCAPE);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Please select a security question')]")));
            driver.findElement(By.xpath("//*[contains(text(),'Please select a security question')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testEmptySecurityQuestion() {
        log.info("TEST CASE : Empty security question");
        fillSecurityQuestion("");

        log.info("EXPECTED : prompt shows Please provide an answer to your security question");
        try {
            driver.findElement(By.xpath("//*[contains(text(),'Please provide an answer to your security question')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testCorrectEmail() {
        log.info("TEST CASE : Correct email input");
        fillEmail("admin@gmail");

        log.info("Expected : Pass email input validation");
        try {
            driver.findElement(By.xpath("//input[@id='emailControl' and @aria-invalid='false']"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testCorrectPassword() {
        log.info("TEST CASE : Correct password input");
        fillPassword("1a345");

        log.info("Expected : Pass password input validation");
        try {
            driver.findElement(By.xpath("//input[@id='passwordControl' and @aria-invalid='false']"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testCorrectRepeatPassword() {
        log.info("TEST CASE : Correct repeatPassword input");
        fillPassword("1a345");
        fillRepeatPassword("1a345");

        log.info("EXPECTED : Pass repeat password input validation");
        try {
            driver.findElement(By.xpath("//input[@id='repeatPasswordControl' and @aria-invalid='false']"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testSelectedSecurityQuestion() {
        log.info("TEST CASE : Selected security question");

        log.info("EXPECTED : Able to select the first option in the dropdown box");
        try {
            clickNthOptionDropDown(0);

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testFilledSecurityQuestion() {
        log.info("TEST CASE : Filled security question");
        fillSecurityQuestion("-");

        log.info("EXPECTED : Pass security question validation");
        try {
            driver.findElement(By.xpath("//input[@id='securityAnswerControl' and @aria-invalid='false']"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    @Test
    public void testCreateUser() throws Exception{
        String randomEmail = generateRandomEmail();

        log.info("TEST CASE : Create user with a used email");
        fillEmail(randomEmail);
        fillPassword("1a345");
        fillRepeatPassword("1a345");
        fillSecurityQuestion("-");
        clickNthOptionDropDown(0);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='registerButton']")));
        driver.findElement(By.xpath("//button[@id='registerButton']")).click();

        //Due to test case "testCreateuser" returns us to the Login page, we need to press register again
        sleep(1000);    //Prevent page reloading too fast
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@routerlink='/register']")));
        driver.findElement(By.xpath("//a[@routerlink='/register']")).click();

        sleep(1000);    //Prevent page reloading too fast

        log.info("EXPECTED : prompt shows Email must be unique");
        try {
            fillEmail(randomEmail);
            fillPassword("3456789");
            fillRepeatPassword("3456789");
            fillSecurityQuestion("&");
            clickNthOptionDropDown(1);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='registerButton']")));
            driver.findElement(By.xpath("//button[@id='registerButton']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Email must be unique')]")));
            driver.findElement(By.xpath("//*[contains(text(),'Email must be unique')]"));

            log.info("PASS");
        }catch(Exception e){
            log.info("FAIL");
            log.error(e);
        }
    }

    public void fillEmail(String mail){
        WebElement element = driver.findElement(By.xpath("//input[@id='emailControl']"));
        element.sendKeys(mail);
        element.sendKeys(Keys.TAB);
    }
    public void fillPassword(String password){
        WebElement element = driver.findElement(By.xpath("//input[@id='passwordControl']"));
        element.sendKeys(password);
        element.sendKeys(Keys.TAB);
    }
    public void fillRepeatPassword(String repeatPass){
        WebElement element = driver.findElement(By.xpath("//input[@id='repeatPasswordControl']"));
        element.sendKeys(repeatPass);
        element.sendKeys(Keys.TAB);
    }
    public void clickNthOptionDropDown(int n){
        WebElement element = driver.findElement(By.xpath("//mat-select[@aria-label='Selection list for the security question']"));
        element.click();
        List<WebElement> elementList = driver.findElements(By.xpath("//mat-option[@role='option']"));
        elementList.get(n).click();
    }
    public void fillSecurityQuestion(String securityQuestion){
        WebElement element = driver.findElement(By.xpath("//input[@id='securityAnswerControl']"));
        element.sendKeys(securityQuestion);
        element.sendKeys(Keys.TAB);
    }
    public String generateRandomEmail() {
        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
        final java.util.Random rand = new java.util.Random();

        StringBuilder builder = new StringBuilder();

        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
        }
        return builder.toString().concat("@gmail.com");
    }
}

//            //test login
//            driver.findElement(By.xpath("//input[@aria-label='Text field for the login email']")).sendKeys("admin@gmail.com");
//            WebElement passwordElement = driver.findElement(By.xpath("//input[@aria-label='Text field for the login password']"));
//            passwordElement.sendKeys("password");
//            passwordElement.sendKeys(Keys.ENTER);
//
//            //Test search for products
//            driver.findElement(By.xpath("//mat-search-bar[@aria-label='Click to search']")).click();
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//            WebElement element = driver.findElement(By.xpath("//input[@id='mat-input-0']"));
//            element.sendKeys("banana");
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//            element.sendKeys(Keys.ENTER);
//
//            //place first item in window into basket.
//            //mat-grid-tile[@rowspan='1']//button[@aria-label='Add to Basket']
//            driver.findElement(By.xpath("//mat-grid-tile[@rowspan='1']//button[@aria-label='Add to Basket']")).click();
