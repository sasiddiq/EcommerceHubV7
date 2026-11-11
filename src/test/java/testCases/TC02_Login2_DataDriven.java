
package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.RetryAnalyzer;

public class TC02_Login2_DataDriven extends BaseClass {

    private static final Logger logger =
            LogManager.getLogger(TC02_Login2_DataDriven.class);

    @Test(
        groups = {"sanity", "regression", "datadriven"},
        dataProvider = "LoginData",
        dataProviderClass = DataProviders.class,
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testLogin(String email, String pwd) {

        logger.info("Starting Data Driven Login Test");
        logger.debug("Testing login for email: {}", email);

        try {
            HomePage hp = new HomePage(getDriver());

            logger.debug("Clicking My Account");
            hp.clickMyAccount();

            logger.debug("Navigating to Login Page");
            hp.goToLogin();

            LoginPage lp = new LoginPage(getDriver());

            logger.debug("Entering email address");
            lp.setEmail(email);

            logger.debug("Entering password");
            lp.setPwd(pwd);

            logger.debug("Clicking Login button");
            lp.clickLogin();

            AccountPage ap = new AccountPage(getDriver());

            logger.debug("Verifying My Account confirmation");

            boolean status =
                ap.getMyAccountConfirmation().isDisplayed();

            try {
                Assert.assertTrue(
                    status,
                    "Login failed for email: " + email
                );

                logger.info("Login successful for: {}", email);

            } catch (AssertionError ae) {

                logger.error(
                    "Login assertion failed for: {}", email, ae
                );

                throw ae;
            }

            logger.debug("Clicking My Account dropdown");
            ap.clickMyAccountDropDown();

            logger.debug("Clicking Logout");
            ap.clickLogout();

            logger.info("Logout successful for: {}", email);

        } catch (AssertionError ae) {

            logger.error(
                "Test failed due to assertion error: {}", email, ae
            );

            throw ae;

        } catch (Exception e) {

            logger.error(
                "Unexpected exception during login: {}", email, e
            );

            Assert.fail(
                "Login test failed due to exception: "
                    + e.getMessage(),
                e
            );

        } finally {

            logger.debug(
                "Finished login test execution for: {}", email
            );
        }
    }
}
