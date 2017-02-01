
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.poi.hssf.util.HSSFColor.BROWN;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import com.qfor.driverscript.Environment;
import com.qfor.interactions.Interactions2;
import com.qfor.testcase.TestCase;
import com.qfor.utils.ScreenShotUtils;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import projectUtils.ProjectEmailUtil;

public class ProjectSpecific {

	// Variable Declaration
	static Interactions2 itr2 = new Interactions2();
	public static WebElement gridElement = null;
	public static List<String> dealGroupRandomList = new ArrayList<String>();
	public static int implicitWaitInterim = 2;
	public static String result = null;
	public static String month = "";
	public static String year = "";
	public static String date = "";
	public static String monthandyear = "";
	public static String ddvalue = "";
	public static List<String> dealList = new ArrayList<String>();
	public static String winHandleBefore = null;

	/**
	 * Function to invoke DataBase Connection
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean createDB(WebDriver driver, String screenName, String ObjectName, String value) {
		try {
			// Prepare connection
			String url1 = Constants.iDatabaseName + ObjectName;
			// Load Microsoft SQL Server JDBC driver
			String dbClass = Constants.iDriverName;
			// Create the instance of driver
			Class.forName(dbClass).newInstance();
			// Get connection to DB
			Connection con = DriverManager.getConnection(url1, "membadmin", "password");
			// Create Statement
			Statement stmt = (Statement) con.createStatement();
			stmt.executeUpdate(value);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This method will perform the Action and key items
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean ActionItem(WebDriver driver, String screenName, String ObjectName, String value)
			throws InterruptedException {

		WebElement element = itr2.getWebElement(driver, screenName, ObjectName);
		// creating the object for Action class
		org.openqa.selenium.interactions.Actions act = new org.openqa.selenium.interactions.Actions(driver);

		// Splitting the case and value,if value present
		String[] iCaseandValue = value.split("-");
		String iCase = iCaseandValue[0];
		String iValue = iCaseandValue[1];
		// Performing the operation based on the case and value
		switch (iCase) {
		// case for checkbox
		case "Check":
			act.click(element).build().perform();
			// case for sendkeys
		case "sendKeys":
			act.sendKeys(element, Keys.valueOf(iValue)).perform();
			break;
		// case of double click
		case "doubleClick":
			act.doubleClick(element).perform();
			break;
		// case for move to particular element
		case "moveToElement":
			Thread.sleep(1000);
			act.moveToElement(element).build().perform();
			break;
		// case for move key down
		case "keyDown":
			act.keyDown(element, Keys.valueOf(iValue)).perform();
			break;
		// case for move key up
		case "keyUp":
			act.keyUp(element, Keys.valueOf(iValue)).perform();
			break;
		case "pageUp":
			Thread.sleep(1000);
			element.sendKeys(Keys.PAGE_UP);
			break;
		case "Keys":
			Thread.sleep(1000);
			element.sendKeys(Keys.valueOf(iValue));
			break;
		}
		return true;
	}

	/**
	 * This method will compare the URL and switch the handle to required page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean compareURL(WebDriver driver, String screenName, String ObjectName, String value) {
		WebElement oElement = itr2.getWebElement(driver, screenName, ObjectName);
		oElement.click();
		// Store the current window handle
		String winHandleBefore = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		String firstWinHandle = driver.getWindowHandle();
		handles.remove(firstWinHandle);
		String winHandle = handles.iterator().next();
		if (winHandle != firstWinHandle) {
			// Storing handle of second window handle
			String secondWinHandle = winHandle;
			// Switch control to new window
			driver.switchTo().window(secondWinHandle);
		}
		String currentURL = driver.switchTo().window(winHandle).getCurrentUrl();
		if (currentURL.equals(value)) {
			System.out.println("Navigated to Moody's Web Application");
			TestCase.takeScreenShot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Navigated to Moody's Web Application");
		} else {
			System.out.println("Expected page not found");
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, "Expected page not found");
		}
		driver.close();
		driver.switchTo().window(winHandleBefore);
		driver.getCurrentUrl();
		return true;
	}

	/**
	 * Method to verify the list of elements
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyListElements(WebDriver driver, String screen, String object, String value)
			throws InterruptedException {

		List<String> sElementList = new ArrayList<>();
		List<String> inputList = new ArrayList<>();
		String strArray[] = value.split(",");
		// Condition to check if the page name is HomeButton from input sheet
		if (screen.equals(Constants.sHome)) {
			ActionItem(driver, Constants.sHome, Constants.oHomeButton, Constants.iHomeButtonValue);
		}
		// Condition to check if the page name is NavigationList from input
		// sheet
		if (object.equals(Constants.oNavigationList)) {
			ActionItem(driver, screen, object, Constants.iHomeButtonValue);
		}

		Thread.sleep(6000);
		List<WebElement> listele = itr2.getWebElements(driver, screen, object);

		// Converting WebElements as String
		for (WebElement element : listele) {
			if (element.getAttribute("class").contains("disable-link") || element.getText().equals("")
					|| element.getAttribute("class").contains("disabled")) {
				continue;
			}
			sElementList.add(element.getText().trim());
			System.out.println(element.getText().trim());
		}
		for (int i = 0; i < strArray.length; i++) {
			inputList.add(strArray[i]);
		}
		Set<String> set1 = new HashSet<String>();
		set1.addAll(sElementList);
		Set<String> set2 = new HashSet<String>();
		set2.addAll(inputList);
		if (set1.equals(set2)) {
			System.out.println("**************the navigation List matches**************");
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"**************the navigation List matches**************" + value);

		} else {
			System.out.println("**************the navigation List does not matches************");
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"**************the navigation List does not matches************" + value);
		}
		return true;
	}

	/**
	 * This function to select random value from the list
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean selectListValue(WebDriver driver, String screenName, String ObjectName, String value) {
		try {
			int iValue = Integer.parseInt(value);
			WebElement element = itr2.getWebElement(driver, screenName, ObjectName);
			List<WebElement> oElement = element.findElements(By.tagName(Constants.otr));

			for (int j = 0; j < iValue; j++) {
				try {
					WebElement oListElement = selectRandomListElement(oElement);
					String sValue = oListElement.findElement(By.tagName(Constants.otd)).getText();
					System.out.println(sValue);
					oListElement.findElement(By.tagName(Constants.otd)).findElement(By.tagName(Constants.olabel))
							.click();
					Thread.sleep(2000);
					dealGroupRandomList.add(sValue);
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
							"Deals found to assign in Deal Group");
				} catch (Exception e) {
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
							"No Deals found to assign in Deal Group");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This function is to select random values from WebElement
	 * 
	 * @param List
	 *            of WebElements
	 * @returns WebElement
	 */

	public static WebElement selectRandomListElement(List<WebElement> randomList) {
		WebElement random = null;
		try {
			Random rand = new Random();
			random = randomList.get(rand.nextInt(randomList.size()));

		} catch (Exception e) {
		}
		return random;
	}

	/**
	 * Function will wait till the object disappears
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkifElementNotPresent(WebDriver driver, String screen, String object, String waitTime)
			throws InterruptedException {
		long tStart, tCurrent;
		tStart = System.currentTimeMillis();
		tCurrent = 0;
		WebElement elementStatus = null;
		int waitTim = Integer.parseInt(waitTime);
		do {
			driver.manage().timeouts().implicitlyWait(implicitWaitInterim, TimeUnit.SECONDS);
			try {
				// Checks the element present in the page
				elementStatus = itr2.getWebElement(driver, screen, object);

				if (elementStatus.isDisplayed()) {
					System.out.println("Object map found ");
				} else {
					System.out.println("Object map not found ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			tCurrent = System.currentTimeMillis();
		} while (tCurrent - tStart < waitTim);
		return true;

	}

	/**
	 * Method to get the values from the grid
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean searchDealinGridView(WebDriver driver, String screenName, String ObjectName, String value) {
		// variable declaration
		boolean flag = false;
		int row;
		String row1 = null;
		String otherrow = null;
		WebElement sortList = null;
		WebElement oGrid = driver.findElement(By.id(Constants.otableViewList));
		List<WebElement> oListGrid = oGrid.findElements(By.xpath(Constants.osearchDealinGridView1));
		try {
			// Iterates the row value

			for (row = 1; row < oListGrid.size(); row++) {
				if (flag == false) {
					if (row == 1) {
						row1 = Constants.osearchDealinGridView2;
					} else {
						otherrow = Constants.osearchDealinGridView3 + row + Constants.osearchDealinGridView4;
					}

					for (int col = 1; col < 4; col++) {
						if (row == 1 && col == 1) {
							sortList = driver.findElement(By.xpath(Constants.osearchDealinGridView5 + value
									+ Constants.osearchDealinGridView6 + row1 + Constants.osearchDealinGridView7));
						} else if (row == 1 && col > 1) {
							sortList = driver.findElement(By.xpath(Constants.osearchDealinGridView8 + value
									+ Constants.osearchDealinGridView9 + col + Constants.osearchDealinGridView10));
						} else if (row > 1 && col == 1) {
							sortList = driver.findElement(By.xpath(Constants.osearchDealinGridView8 + value
									+ Constants.osearchDealinGridView6 + otherrow + Constants.osearchDealinGridView7));
						} else if (row > 1 && col > 1) {
							sortList = driver.findElement(By.xpath(Constants.osearchDealinGridView5 + value
									+ Constants.osearchDealinGridView6 + otherrow + Constants.osearchDealinGridView3
									+ col + Constants.osearchDealinGridView10));
						}
						System.out.println(sortList.getText());
						String sElement = sortList.getText();
						if (sElement.equals(Actions.sDealValue)) {
							WebElement oElement = driver.findElement(By.xpath(
									Constants.osearchDealinGridView12 + otherrow + Constants.osearchDealinGridView3
											+ col + Constants.osearchDealinGridView11));
							oElement.click();
							flag = true;
							break;
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This function to check/ Uncheck the element based on Input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkBoxChecked(WebDriver driver, String screenName, String ObjectName, String value) {
		try {
			int j = 2;
			String[] valueArray = value.split(";");
			String value1 = valueArray[0];
			String value4 = valueArray[4];
			String value5 = valueArray[5];
			WebElement oElement = null;
			oElement = Actions.returnTrElement(driver, screenName, ObjectName, value1);

			List<WebElement> oListElement = oElement.findElements(By.tagName(Constants.otd));
			for (int i = 1; i < valueArray.length - 1; i++) {

				if (valueArray[i].equals("Select")) {
					if (oListElement.get(j).findElement(By.tagName(Constants.oinput)).isSelected() == true) {
						System.out.println("The Control " + " is selected");
						com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "The Control is selected");

					} else {
						oListElement.get(j).findElement(By.tagName(Constants.oinput)).click();
						System.out.println("The Control " + " is now selected");
						com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "The Control is now selected");
					}
					System.out.println("Value is already Selected");
				} else if (valueArray[i].equals("UnSelect")) {
					if (oListElement.get(j).findElement(By.tagName(Constants.oinput)).isSelected() == true) {
						oListElement.get(j).findElement(By.tagName(Constants.oinput)).click();
						System.out.println("The Control " + " is now unselected");
						com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
								"The Control is now unselected");
					} else {
						System.out.println("The Control " + " is  unselected");
						com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "The Control is unselected");
					}
				}
				j++;

			}
			Actions.verifySelectStatus(oListElement.get(6).findElement(By.tagName(Constants.oinput)), value4);

			// To select the Report reader access value
			if (screenName.equals("MUOptional")) {
				List<WebElement> oElement1 = oListElement.get(5).findElements(By.tagName("label"));
				for (int i = 0; i < oElement1.size(); i++) {
					if (value5.equals("External")) {
						oElement1.get(i).click();
						System.out.println("User has External Report Reader Access");
						com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
								"User has External Report Reader Access");
					} else {
						oElement1.get(i).click();
						System.out.println("User has Internal Report Reader Access");
						com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
								"User has Internal Report Reader Access");
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the default view of Grid
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkListViewStatus(WebDriver driver, String screenName, String ObjectName, String value) {
		// variable declaration
		WebElement oListElements = null;
		try {
			WebElement oListTable = driver.findElement(By.id(Constants.olistViewList));
			List<WebElement> oListTr = oListTable.findElements((By.tagName(Constants.otr)));
			// Iterating the pagination value
			WebElement oPageLink = driver.findElement(By.xpath(Constants.oPaginationContainer));
			List<WebElement> oLinks = oPageLink.findElements(By.tagName(Constants.olink));
			for (WebElement oNo : oLinks) {
				oNo.click();
				// Iterates the list values
				for (int i = 2; i < oListTr.size(); i++) {
					// Iterates the list
					oListElements = driver.findElement(By.xpath(Constants.oListViewTr + i + Constants.oListViewTd));
					if (oListElements.getText().equals(value)) {
						oListElements.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the save button is enabled/disabled before clicking the
	 * save
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean saveEnableorDisable(WebDriver driver, String screenName, String ObjectName, String value) {
		try {
			WebElement oSave = itr2.getWebElement(driver, screenName, ObjectName);
			oSave.click();
		} catch (Exception e) {
			System.out.println("Save Button is not enabled");
		}
		return true;
	}

	/**
	 * Function to check boundary value of given text from input sheet
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getAttributeOfElement(WebDriver driver, String screen, String object, String value) {
		WebElement element = itr2.getWebElement(driver, screen, object);
		String attribute = element.getAttribute(value);
		System.out.println("GetAttribute ====== " + attribute);
		if (Integer.parseInt(attribute) <= 50) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Maximum length : " + attribute);
		} else if (attribute.equalsIgnoreCase("true")) {
			System.out.println("GetAttribute ====== " + attribute);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Disabled : " + attribute);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, "Maximum length : " + attribute);
		}

		return true;
	}

	/**
	 * Function to check the values in Sortby dropdown
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean sortByOption(WebDriver driver, String screen, String object, String value) {
		try {
			String sElement = null;
			for (int i = 1; i < 6; i++) {
				if (i == 1) {
					WebElement oElement = driver.findElement(By.xpath(Constants.oSortByOption1));
					sElement = oElement.getText();
				} else {
					WebElement oElement = driver
							.findElement(By.xpath(Constants.oSortByOption2 + i + Constants.oSortByOption3));
					sElement = oElement.getText();
				}
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"Options in SortBy Dropdown : " + sElement);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to refresh the webpage
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean webPageRefresh(WebDriver driver, String screenName, String ObjectName, String value) {
		try {
			// Refresh the webpage
			driver.navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to clear the value in textbox
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean textClear(WebDriver driver, String screenName, String ObjectName, String value) {
		try {
			WebElement oClear = itr2.getWebElement(driver, screenName, ObjectName);
			// Clear the textbox
			oClear.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the given text exists
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyTextExist(WebDriver driver, String screen, String object, String value) {
		String element = null;
		try {
			element = itr2.getWebElement(driver, screen, object).getText().trim();
			if (element.contains(value.trim())) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, " Verified : " + element);
				return true;
			}
		} catch (Exception e) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, " Not verified : " + element);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Function the checks for the dropdown elements
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean VerifydropdownElements(String dropdown, WebElement element) {
		try {
			List<String> dropdownList = new ArrayList<String>(Arrays.asList(dropdown.split(",")));
			List<String> expectedDDList = new ArrayList<String>();
			Set<String> set1 = new LinkedHashSet<String>();
			set1.addAll(dropdownList);
			Set<String> set2 = new LinkedHashSet<String>();
			for (WebElement ele : element.findElements(By.tagName(Constants.oOption))) {
				expectedDDList.add(ele.getText());
			}
			set2.addAll(expectedDDList);
			if (set1.equals(set2)) {
				System.out.println("PASS" + " " + expectedDDList);

			} else
				System.out.println("FAIL" + " " + expectedDDList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function for finding the element is selected or not
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean isSelected(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement element = itr2.getWebElement(driver, screen, object);
			if (element.isSelected() == true) {
				System.out.println(object + " is selected");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, object + " is selected");
			} else {
				System.out.println(object + " is not selected");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, object + " is not selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function for check the given control is enabled or not
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean isEnabled(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement element = itr2.getWebElement(driver, screen, object);
			if (element.isEnabled() == true) {
				System.out.println(object + " is Enabled");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, object + " is Enabled");
			} else {
				System.out.println(object + " is not Enabled");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, object + " is not Enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Function to verify the Dates after Filtering
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyDateAfterFiltering(WebDriver driver, String screen, String object, String value)
			throws ParseException {
		List<WebElement> dateList = itr2.getWebElements(driver, screen, object);
		String valueArray[] = value.split(",");
		boolean stepflag = false;

		for (WebElement ele : dateList) {
			String filteredDate = "";
			if (object.equals("ChangedDateList")) {
				filteredDate = ele.getText();
			} else {
				filteredDate = ele.getText().split("\n")[2].split(":")[1].trim();
			}
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.Date1);
			SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Date2);
			Date date = sdf1.parse(filteredDate);
			Date startdate = sdf.parse(valueArray[0]);
			Date enddate = sdf.parse(valueArray[1]);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(enddate);

			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);

			Date enddateofthemonth = calendar.getTime();
			System.out.println(enddateofthemonth);
			boolean flag = compareTwoDate(date, startdate, enddateofthemonth);
			if (flag == true) {
				stepflag = true;
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, "the date " + filteredDate
						+ " is not in between the mentioned range " + valueArray[0] + " to " + valueArray[1]);
				stepflag = false;
			}
		}
		if (stepflag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"All the dates filtered is in between the mention range " + valueArray[0] + " to " + valueArray[1]);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"All the dates filtered is not in between the mention range " + valueArray[0] + " to "
							+ valueArray[1]);
		}
		return true;
	}

	/**
	 * Function to compare the given date between two dates
	 * 
	 * @param actual
	 *            date
	 * @param startDate
	 * @param EndDate
	 * 
	 */

	public static boolean compareTwoDate(Date date, Date dateStart, Date dateEnd) {
		if (date != null && dateStart != null && dateEnd != null) {
			if ((date.compareTo(dateStart) == 0 || date.after(dateStart))
					&& (date.compareTo(dateEnd) == 0 || date.before(dateEnd))) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Function to verify All dates as such as expected format
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyAllDates(WebDriver driver, String screen, String object, String value)
			throws ParseException {
		List<WebElement> dateList = itr2.getWebElements(driver, screen, object);
		boolean stepflag = false;
		String date = "";
		for (WebElement ele : dateList) {
			SimpleDateFormat sdf = new SimpleDateFormat(value);
			if (object.equals("VintageDateList")) {
				date = ele.getText();
			} else {
				date = ele.getText().split("\n")[2].split(":")[1].trim();
				System.out.println(date);
			}
			try {
				sdf.format(sdf.parse(date));

				stepflag = true;

			} catch (Exception e) {
				stepflag = false;

			}
		}
		if (stepflag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"All the dates is filtered as the expected format");
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"All the dates is not filtered as the expected format");
		}

		return true;
	}

	/**
	 * Function to get the text color and verify the given condition is
	 * satisfied or not
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getColorofText(WebDriver driver, String screen, String object, String value) {
		String[] valSplit = value.split(";");
		String value0 = valSplit[0];
		String value1 = valSplit[1];
		WebElement ele = itr2.getWebElement(driver, screen, object);
		// To get the cssvalue of the color
		String color = ele.getCssValue("color");
		String[] colors = color.replace("rgba(", "").replace(")", "").split(",");
		int r = Integer.parseInt(colors[0].trim());
		String hexDecimal = "#" + Integer.toHexString(r);
		switch (value0) {
		// if color matches print the success message
		case "Red":
			// Checks the condition whether the text color is Red
			if (hexDecimal.equals(Constants.hexDecimalRed)) {
				System.out.println(value1 + " is displayed in Red");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, value1 + " is displayed in Red");
				break;
			} else {
				System.out.println(value1 + " is not displayed in Red");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, value1 + " is not displayed in Red");
				break;
			}
		case "Green":
			// Checks the condition whether the text color is Green
			if (hexDecimal.equals(Constants.hexDecimalGreen)) {
				System.out.println(value1 + " is displayed in Green");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, value1 + " is displayed in Green");
				break;
			} else {
				System.out.println(value1 + " is not displayed in Green");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, value1 + " is not displayed in Green");
				break;
			}
		}
		return true;
	}

	/**
	 * Function to clear the value for the given object
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean clearTextKeys(WebDriver driver, String screen, String object, String value) {
		try {
			// Identifies the element
			WebElement ele = itr2.getWebElement(driver, screen, object);
			// Clears the value
			ele.sendKeys(Keys.CONTROL + "a");
			ele.sendKeys(Keys.BACK_SPACE);

			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The filtered values " + value + " got cleared");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to take screenshot using Robot
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean robotScreenshot(WebDriver driver, String screen, String object, String value) {
		try {
			TestCase.takeScreenShotRobot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to remove the special character in a String
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean removeSpecialCharater(WebDriver driver, String screen, String object, String value) {
		result = value.replaceAll("[\\$\\-\\%\\+\\^:,]", "");
		return true;

	}

	/**
	 * Function to verify the Loan Level value from the input sheet
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyLoanLevelValue(WebDriver driver, String screen, String object, String value) {
		WebElement ele = itr2.getWebElement(driver, screen, object);
		String loanValue = ele.getText().split(":")[1];
		if (result.split("\\.")[0].equals(loanValue)) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The Loal level value " + result.split("\\.")[0] + " is matched with " + loanValue);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"The Loal level value " + result.split("\\.")[0] + " is not matched with " + loanValue);
		}
		return true;
	}

	/**
	 * Function to check the save and cancel in Edit Due dates
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean editDueDatesVal(WebDriver driver, String screen, String object, String value) {
		int k = 2;
		List<String> sFileList = new ArrayList<String>();
		List<String> sEList = new ArrayList<String>();

		WebElement oList = null;
		try {
			for (int i = 0; i < 3; i++) {
				Random rand = new Random();
				int randomNum = rand.nextInt((9 - 1) + 1) + 1;
				System.out.println(randomNum);
				String sValue = String.valueOf(randomNum);
				sEList.add(sValue);

				if (k == 2) {
					oList = itr2.getWebElement(driver, screen, object);
				} else {
					oList = driver.findElement(By.xpath(Constants.oDueDates1 + k + Constants.oDueDates2));
				}
				sFileList.add(oList.getAttribute("value"));
				oList.click();
				oList.sendKeys(Keys.BACK_SPACE);
				oList.clear();
				oList.sendKeys(sValue);
				oList.sendKeys(Keys.TAB);
				k++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the existing values in Due Dates
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean editDueDates(WebDriver driver, String screen, String object, String value) {
		List<String> sList = new ArrayList<String>();
		List<String> sFileList = new ArrayList<String>();
		WebElement oElement = null;
		int j = 0;
		try {
			WebElement oEle = driver.findElement(By.xpath("//*[@id='dueDates']"));
			List<WebElement> oListEle = oEle.findElements(By.xpath("//*[@class='row']"));
			for (int i = 1; i < oListEle.size() - 2; i++) {
				if (j == 0) {
					oElement = driver.findElement(By.xpath("//div[@id='dueDates']/div[3]/div/div/div/div[2]/span"));
					j++;
				} else {
					oElement = driver
							.findElement(By.xpath("//div[@id='dueDates']/div[3]/div/div[" + i + "]/div/div[2]/span"));
				}
				sList.add(oElement.getText());
			}
			TestCase.takeScreenShot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			driver.findElement(By.linkText("Edit Due Dates")).click();
			sFileList = Actions.dueDates(driver, screen, object);
			TestCase.takeScreenShot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			if (sFileList.equals(sList)) {
				System.out.println("The List values are equal");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"The values are matches with Due Dates screen");
			} else {
				System.out.println("The List values are not equal");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
						"The values are not matches with Due Dates screen");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function for verifying itemized User in the Table
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyItemizedUser(WebDriver driver, String screen, String object, String value)
			throws InterruptedException {
		// getting the element in the WebElement List
		List<WebElement> pageList = driver.findElements(By.xpath(Constants.oItemizedUser1));
		// iterating the page List
		for (int i = 0; i < pageList.size(); i++) {
			if (i == 0) {
				continue;
			}
			WebElement pageEle = driver.findElement(By.xpath(Constants.oItemizedUser1 + "[" + i + "]"));
			pageEle.findElement(By.tagName("a")).click();
			Thread.sleep(4000);
			List<WebElement> trList = driver.findElements(By.xpath(Constants.oItemizedUser2));
			boolean pageflag = false;
			for (int j = 0; j < trList.size(); j++) {
				if (j == 0 || j == 1) {
					continue;
				}
				List<WebElement> tdList = driver
						.findElements(By.xpath(Constants.oItemizedUser2 + "[" + j + Constants.oItemizedUser3));
				String type = "";
				String user = "";
				boolean flag = false;
				for (int k = 0; k <= tdList.size(); k++) {
					if (k == 0) {
						continue;
					}
					WebElement tdEle = driver.findElement(
							By.xpath(Constants.oItemizedUser2 + "[" + j + Constants.oItemizedUser3 + "[" + k + "]"));
					if (k == 1) {
						type = tdEle.getText();
					}
					if (k == 2) {
						user = tdEle.getText();
					}
					if (type.equals("Itemized")) {
						if (k == 3 || k == 4 || k == 5) {
							try {
								driver.findElement(
										By.xpath(Constants.oItemizedUser2 + "[" + j + Constants.oItemizedUser4))
										.click();
								Thread.sleep(5000);
								System.out.println(
										"Submitter, Reviewer and Approver is not selected for Itemized user " + user);
								com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
										"Submitter, Reviewer and Approver is not selected for Itemized user " + user);
								flag = true;
								break;
							} catch (Exception e) {
								System.out.println(
										"Submitter, Reviewer and Approver is selected for Itemized user " + user);
								com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
										"Submitter, Reviewer and Approver is selected for Itemized user " + user
												+ user);
								flag = true;
								break;
							}
						}
					} else {
						break;
					}

				}
				if (flag == true) {
					pageflag = true;
					break;
				}
			}
			if (pageflag == true) {
				break;
			}
		}

		return true;

	}

	/**
	 * Function to select the specific checkbox by passing the element from
	 * Input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean selectAdmin(WebDriver driver, String screen, String object, String value) {
		try {
			String[] valueArray = value.split(";");
			String value1 = valueArray[0];
			String value4 = valueArray[4];
			WebElement oElement = null;

			oElement = Actions.returnTrElement(driver, screen, object, value1);

			List<WebElement> oListElement = oElement.findElements(By.tagName(Constants.otd));

			Actions.verifySelectStatus(oListElement.get(6).findElement(By.tagName(Constants.oinput)), value4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to find the required value in the grid from Input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean searchValue(WebDriver driver, String screen, String object, String value) {
		try {
			String[] objArray = object.split(";");
			String object0 = objArray[0];
			String object1 = objArray[1];
			String[] valArray = value.split(";");
			String value0 = valArray[0];
			String value1 = valArray[1];
			WebElement oElement = Actions.returnTrElement(driver, screen, object0, value0);
			WebElement oElement1 = oElement.findElement(By.xpath(object1));
			String sElement = oElement1.getText();
			if (sElement.equals(value1)) {
				System.out.println("Expected value is : " + value1 + " and Actual Value is : " + sElement);
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"Expected value is : " + value1 + " and Actual Value is : " + sElement);
			} else {
				System.out.println("Expected value is : " + value1 + " and Actual Value is : " + sElement);
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
						"Expected value is : " + value1 + " and Actual Value is : " + sElement);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to get the count of records in Home Page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getHomeCount(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			String sHomeSubmission = oElement.getText();
			Actions.homeSubmissionCnt = Integer.parseInt(sHomeSubmission);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to get the Count of recrods in Submission page and matching with
	 * Home page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getSubmissionCnt(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			List<WebElement> oListElements = oElement.findElements(By.tagName("tr"));
			if (oListElements.get(0).getText().equals("No submission found.")) {
				System.out.println(" No Submissions Found for " + value);
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, " No Submissions Found for " + value);
			} else {
				int iSize = oListElements.size();
				if (iSize == Actions.homeSubmissionCnt) {
					System.out.println(
							value + " Submission records are matching in both Submissions and Home page : " + iSize);
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
							value + " Submission records are matching in both Submissions and Home page : " + iSize);
				} else {
					System.out.println(value
							+ " Submission records are not matching in both Submissions and Home page : " + iSize);
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, value
							+ " Submission records are not matching in both Submissions and Home page : " + iSize);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to calculate the percentage of submissions
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean calculatePublished(WebDriver driver, String screen, String object, String value) {
		try {
			float val = 0;
			int subValue = 0;
			float submissionCal = 0;
			String[] objArray = object.split(";");
			String object0 = objArray[0];
			String object1 = objArray[1];
			WebElement oElement = itr2.getWebElement(driver, screen, object0);
			String sElement = oElement.getText();
			int leng = sElement.length();
			String sValue = "";
			for (int i = 0; i < leng; i++) {
				Character character = sElement.charAt(i);
				if (Character.isDigit(character)) {
					sValue += character;
					subValue = Integer.parseInt(sValue);
					val = Float.parseFloat(sValue);
					break;
				}
			}
			WebElement oElement1 = itr2.getWebElement(driver, screen, object1);
			String sElement1 = oElement1.getText();
			float sEle = Float.parseFloat(sElement1);
			submissionCal = (val * 100 / sEle);
			int subCal = Math.round(submissionCal);
			if (subCal == subValue) {
				System.out.println(value + " % of Submissions is matching : " + submissionCal + "%");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						value + " % of Submissions is matching : " + +submissionCal + "%");
			} else {
				System.out.println(value + " % of Submissions is not matching : " + submissionCal + "%");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						value + " % of Submissions is not matching : " + +submissionCal + "%");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to enter random value
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean enterRandomValue(WebDriver driver, String screen, String object, String value) {
		try {
			Random rand = new Random();
			String valueArray[] = value.split(",");
			int min = Integer.parseInt(valueArray[0]);
			int max = Integer.parseInt(valueArray[1]);
			int randomNum = rand.nextInt((max - min) + 1) + min;
			System.out.println(randomNum);
			WebElement oEle = itr2.getWebElement(driver, screen, object);
			oEle.clear();
			oEle.sendKeys(String.valueOf(randomNum));
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to get the Text from Element List
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean getTextFromList(WebDriver driver, String screen, String object, String value) {
		List<WebElement> list = itr2.getWebElements(driver, screen, object);
		int i = 1;
		for (WebElement element : list) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Deal " + i + " : " + element.getText());
			i++;
		}
		return true;
	}

	/**
	 * Function to wait until the given element found
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean waitUntilElementFound(WebDriver driver, String screen, String object, String value)
			throws InterruptedException {
		long tStart, tCurrent;
		tStart = System.currentTimeMillis();
		tCurrent = 0;
		@SuppressWarnings("unused")
		String errorMessage = null;
		int iValue = Integer.parseInt(value);
		boolean elementStatus = false;
		do {
			driver.manage().timeouts().implicitlyWait(implicitWaitInterim, TimeUnit.SECONDS);
			try {
				// Checks the element present in the page
				elementStatus = itr2.getWebElement(driver, screen, object).isDisplayed();
				if (elementStatus) {
					System.out.println("Object map not found ");
					return elementStatus;
				}
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
			tCurrent = System.currentTimeMillis();
		} while (tCurrent - tStart < iValue);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if (tCurrent - tStart >= iValue) {
			// System.out.println("Unable to perform " + objectMap
			// + ". Timed out: " + waitTime + " milli seconds");
		}
		return true;
	}

	/**
	 * Function to check the given the user has read only access
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkReadOnlyAccess(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			if (oElement == null) {
				System.out.println("User has read-only access");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "User has read-only access");
			} else {
				System.out.println("User don't have read-only access");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, "User don't have read-only access");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the External Report access permission
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean externalAccess(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			if (oElement.isDisplayed()) {
				System.out.println(value);
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, value);

			} else {
				System.out.println(
						"User has only External Report Reader Access and the action is not expected for the given status");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
						"User has only External Report Reader Access and the action is not expected for the given status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the given link is present in the page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean linkPresent(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			if (oElement == null) {
				System.out.println(value);
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, value);
			} else {
				System.out.println(value);
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, value);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the filtered date in the Submisssion page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean verifyFilterDate(WebDriver driver, String screen, String object, String value)
			throws ParseException, InterruptedException {
		boolean stepflag = false;
		String filteredDate = "";
		Date startdate = null;
		Date enddate = null;
		Date date = null;
		Date enddateofthemonth = null;
		List<String> dateOptionList = new ArrayList<>();
		List<WebElement> dateElements = driver.findElement(By.id("Vintage")).findElements(By.tagName("option"));
		for (WebElement option : dateElements) {
			if (option.getText().equals("Vintage Date")) {
				continue;
			}
			dateOptionList.add(option.getText());
		}
		String endDateOfMonth = selectRandomListValue(dateOptionList);
		itr2.selectValue(driver, "id", "Vintage", endDateOfMonth);
		waitUntilElementFound(driver, screen, object, "20000");
		TestCase.takeScreenShot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(), driver,
				Environment.Path_Screenshots, "", "", true);
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, endDateOfMonth + " Vintage Date is Selected");

		if (object.equals("VintageDateList")) {
			waitUntilElementFound(driver, "AuditHistory", "ChangedDateHeader", value);
		} else {
			waitUntilElementFound(driver, "Published", "SubmissionTable", value);
		}
		List<WebElement> dateList = itr2.getWebElements(driver, screen, object);
		for (WebElement ele : dateList) {
			if (object.equals("VintageDateList")) {
				filteredDate = ele.getText();
			} else {
				filteredDate = ele.getText().split("\n")[2].split(":")[1].trim();
			}

			SimpleDateFormat sdf = new SimpleDateFormat(Constants.Date1);
			SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Date2);
			if (object.equals("VintageDateList")) {
				date = sdf.parse(filteredDate);
			} else {
				date = sdf1.parse(filteredDate);
			}
			startdate = sdf.parse(endDateOfMonth);
			enddate = sdf.parse(endDateOfMonth);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(enddate);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			enddateofthemonth = calendar.getTime();
			System.out.println(enddateofthemonth);
			boolean flag = compareTwoDate(date, startdate, enddateofthemonth);
			if (flag == true) {
				stepflag = true;
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, "the date " + filteredDate
						+ " is not in between the mentioned range " + startdate + " to " + enddateofthemonth);
				stepflag = false;
			}
		}
		if (stepflag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"All the dates filtered is in between the mention range " + startdate + " to " + enddateofthemonth);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"All the dates filtered is not in between the mention range " + startdate + " to "
							+ enddateofthemonth);
		}
		return true;

	}

	/**
	 * Function to select the random value from the List of String values
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static String selectRandomListValue(List<String> randomList) {
		String random = "";
		try {
			Random rand = new Random();
			random = randomList.get(rand.nextInt(randomList.size()));

		} catch (Exception e) {
		}
		return random;
	}

	/**
	 * Function to get the Webelemet List and store it in List of Strings
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static List<String> getTextAndStoreInList(WebDriver driver, String screen, String object, String value) {
		List<String> list = new ArrayList<>();
		List<WebElement> elementlist = itr2.getWebElements(driver, screen, object);
		int i = 1;
		for (WebElement element : elementlist) {
			if (element.getText().equals("")) {
				continue;
			}
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					value + " " + i + " : " + element.getText());
			list.add(element.getText());
			i++;
		}
		return list;
	}

	/**
	 * Function to verify the Asset numbers after linking in to the Loan level
	 * value in Data Level Tab
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyAssetNumberFilter(WebDriver driver, String screen, String object, String value)
			throws InterruptedException {
		List<String> assetList = getTextAndStoreInList(driver, screen, object, value);
		String assetNumber = selectRandomListValue(assetList);
		Thread.sleep(3000);
		driver.findElement(By.id("txtFilter")).click();
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Filter Value : " + assetNumber);
		driver.findElement(By.id("txtFilter")).sendKeys(assetNumber);
		Thread.sleep(3000);
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
				"The Filtered value " + assetNumber + " matches with the " + assetNumber);
		return true;
	}

	/**
	 * Function to get the Filing Date value
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getFilingDate(WebDriver driver, String screen, String object, String value) {
		try {
			Actions.formatter = new SimpleDateFormat("dd-MMM-yyyy");
			WebElement oElement = driver.findElement(By.xpath("//div/div/form/div"));
			String sElement = oElement.getText();
			String[] sList = sElement.split("\n");
			for (int i = 0; i < sList.length; i++) {
				if (sList[i].contains("Filing Date")) {
					String[] sListEle = sList[i].trim().split("-");
					String sListDate = sListEle[0].trim().replace(" ", "-");
					Actions.sFilingDate = Actions.formatter.parse(sListDate);
					System.out.println(Actions.sFilingDate);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the given value from the input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean dueDateVerify(WebDriver driver, String screen, String object, String value) {
		try {
			SimpleDateFormat formatter1 = new SimpleDateFormat("MMM-dd-yyyy");
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			String sElement = oElement.getText();
			Date date = formatter1.parse(sElement);
			if (date.equals(Actions.sFilingDate)) {
				System.out.println("Due Date matches with actual filing Date");
			} else {
				System.out.println("Due Date not matches with actual filing Date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the filter values
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean printUniqueDataFromList(WebDriver driver, String screen, String object, String value)
			throws InterruptedException {

		if (value.contains("Specific")) {
			String valueArray[] = value.split(";");
			String value1 = selectRandomValueFromList(driver, screen, valueArray[1], value);
			value = value1;
			waitUntilElementFound(driver, screen, "ChangedDateHeader", "20000");
		}
		boolean flag = false;
		List<WebElement> elementlist = itr2.getWebElements(driver, screen, object);
		List<String> list = new ArrayList<>();
		for (WebElement ele : elementlist) {
			list.add(ele.getText());
		}
		if (value.equals("All")) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The column " + object + " has All values " + new HashSet<String>(list).toString());
		} else if (value.contains("AutoTest")) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The Deal Group " + value + " has the following Deal " + new HashSet<String>(list).toString());
		} else {
			for (String valueList : list) {
				if (valueList.equals(value)) {
					flag = true;
				} else {
					flag = false;
				}
			}

			if (flag == true) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"The column " + object + " has " + value + " " + new HashSet<String>(list).toString());
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
						"The column " + object + " does not has " + value + " " + new HashSet<String>(list).toString());
			}
		}
		return true;
	}

	/**
	 * Function to select random String value from the list
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static String selectRandomValueFromList(WebDriver driver, String screen, String object, String value) {
		String randvalue = "";
		List<String> list = new ArrayList<>();
		List<WebElement> elements = itr2.getWebElement(driver, screen, object).findElements(By.tagName("option"));
		for (WebElement ele : elements) {
			list.add(ele.getText());
		}
		randvalue = selectRandomListValue(list);
		new Select(itr2.getWebElement(driver, screen, object)).selectByVisibleText(randvalue);
		return randvalue;
	}

	/**
	 * Function to get Current Month
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	@SuppressWarnings("resource")
	public static boolean getCurrentMonth(WebDriver driver, String screen, String object, String value) {
		try {
			int iValue = Integer.parseInt(value);
			Formatter fmt = new Formatter();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, iValue);
			fmt = new Formatter();
			fmt.format("%tB", cal);
			System.out.println(fmt);

			WebElement oElement = itr2.getWebElement(driver, screen, object);
			String sElement = oElement.getText();
			if (sElement.equals(fmt.toString())) {
				System.out.println(sElement + " Header found");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, sElement + " Header found");
			} else {
				System.out.println(sElement + " Header not found");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, sElement + " Header not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to edit the filing Date
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean editFilingDate(WebDriver driver, String screen, String object, String value) {
		try {
			int i = 0;
			int j = 0;
			String sSValue = value.replaceAll("\\s+", "");
			List<String> sList = new ArrayList<String>();
			String objArray[] = object.split(";");
			String object0 = objArray[0];
			String object1 = objArray[1];
			WebElement oElement = itr2.getWebElement(driver, screen, object0);
			String sElement = oElement.getAttribute("value").replaceAll("\\s+", "");
			if (sElement.equals(sSValue)) {
				System.out.println("Edited value matches with Filing Date");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						" Edited value matches with Filing Date : " + sElement);

			} else {
				System.out.println(sElement + " Edited value not matches with Filing Date");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
						"Edited value not matches with Filing Date : " + sElement);
			}

			List<WebElement> oEle = itr2.getWebElements(driver, screen, object1);
			for (WebElement oListEle : oEle) {
				if (i == 0) {
					i++;
					continue;
				}
				sList.add(oListEle.getText());
			}
			for (j = 0; j < sList.size(); j++) {
				String sValue = sElement.substring(0, sElement.length() - 2);
				if (sList.get(j).contains(sValue)) {
					System.out.println("Due Date Grid is updated as per Filing Date");
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
							"Due Date Grid is updated as per Filing Date : " + sList.get(j));
				} else {
					System.out.println("Due Date Grid is not updated as per Filing Date");
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
							"Due Date Grid is not updated as per Filing Date : " + sList.get(j));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to scroll the dropdown elements
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean scrollDropDown(WebDriver driver, String screen, String object, String value) {
		try {
			TestCase.takeScreenShotRobot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			WebElement oEle1 = driver.findElement(By.id("DealNames"));
			List<WebElement> oList = oEle1.findElements(By.tagName("option"));
			for (WebElement oEle : oList) {
				oEle.sendKeys(Keys.DOWN);
				TestCase.takeScreenShotRobot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(),
						driver, Environment.Path_Screenshots, "", "", true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to click the link By Link Text
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean clickLinkText(WebDriver driver, String screen, String object, String value) {

		WebElement element = driver.findElement(By.linkText(value));
		element.click();
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Click the object with value " + value);
		return true;

	}

	/**
	 * Function to convert the date format
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean convertDateFormat(WebDriver driver, String screen, String object, String value)
			throws ParseException {
		String valArray[] = value.split(";");
		String strArray[] = null;
		String reqDate = "";
		String dateEle = itr2.getWebElement(driver, screen, object).getText();
		SimpleDateFormat sdf = new SimpleDateFormat(valArray[0]);
		SimpleDateFormat sdf1 = new SimpleDateFormat(valArray[1]);
		reqDate = sdf.format(sdf1.parse(dateEle));
		try {
			if (Integer.parseInt(dateEle.split("-")[0]) <= 12) {
				System.out.println(reqDate);
				strArray = reqDate.split("-");
				month = getMonthShortName(Integer.parseInt(dateEle.split("-")[0]) - 1);
			}
		} catch (NumberFormatException e) {
			System.out.println(reqDate);
			strArray = reqDate.split("-");
		}
		if (valArray[0].equals("MMM-yyyy")) {
			month = strArray[0];
			year = strArray[1];
			monthandyear = month + "-" + year;
			System.out.println(monthandyear);
		}
		if (valArray[0].equals("MMM-dd-yyyy")) {

			date = strArray[1];
			year = strArray[2];
			monthandyear = month + "-" + date + "-" + year;
		}

		return true;

	}

	/**
	 * Function to select the month and year
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean selectDate(WebDriver driver, String screen, String object, String value)
			throws InterruptedException {
		String objArray[] = object.split(";");
		selectByVisibleText(driver, screen, objArray[0], month);
		Thread.sleep(3000);
		selectByVisibleText(driver, screen, objArray[1], year);
		return true;
	}

	/**
	 * Function to select the option by visible text
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean selectByVisibleText(WebDriver driver, String screen, String object, String value) {
		WebElement dateEle = itr2.getWebElement(driver, screen, object);
		new Select(dateEle).selectByVisibleText(value);
		return true;

	}

	/**
	 * Function to verify the date format
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyDateFormat(WebDriver driver, String screen, String object, String value)
			throws ParseException {
		boolean flag = false;
		List<WebElement> elements = itr2.getWebElements(driver, screen, object);
		String valArray[] = value.split(";");
		for (WebElement ele : elements) {
			String date = ele.getText();
			SimpleDateFormat sdf = new SimpleDateFormat(valArray[0]);
			SimpleDateFormat sdf1 = new SimpleDateFormat(valArray[1]);
			String reqDate = sdf.format(sdf1.parse(date));
			if (reqDate.equals(monthandyear)) {
				flag = true;
			} else {
				flag = false;
			}
		}
		if (flag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The column " + object + " has " + monthandyear);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The column " + object + " does not has " + monthandyear);
		}
		return true;

	}

	/**
	 * Function to select random value from vintage Drop down
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean selectRandomVintageDate(WebDriver driver, String screen, String object, String value) {
		selectRandomValueFromList(driver, screen, object, monthandyear);
		return true;

	}

	/**
	 * Function to get the Deal by Asset class in Deals Page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getAssetClassList(WebDriver driver, String screen, String object, String value) {
		String lastSub = null;
		String asset = null;
		String deal = null;
		List<WebElement> listEle = itr2.getWebElements(driver, screen, object);
		for (WebElement ele : listEle) {
			lastSub = ele.findElement(By.xpath("td[4]/a/span")).getText();
			asset = ele.findElement(By.xpath("td[3]/a/span")).getText();
			if (!lastSub.equals("--") && value.contains(asset)) {
				deal = ele.findElement(By.xpath("td[2]/a/span")).getText();
				dealList.add(deal);
			}
		}
		return true;

	}

	/**
	 * Function to verify the asset class List in Audit Page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyAssetClassList(WebDriver driver, String screen, String object, String value) {
		boolean flag = false;
		List<WebElement> listEle = itr2.getWebElements(driver, screen, object);
		for (WebElement ele : listEle) {
			for (String deal : dealList) {
				if (ele.getText().equals(deal)) {
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
							"The column " + object + " has " + new HashSet<String>(dealList).toString());
					flag = true;
					break;
				}
			}
			if (flag == true) {
				break;
			}
		}

		return true;

	}

	/**
	 * Function to verify the default value which has the class name 'active'
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean checkDefaultValue(WebDriver driver, String screen, String object, String value) {
		WebElement ele = itr2.getWebElement(driver, screen, object);
		if (ele.getAttribute("class").contains("active")) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The " + ele.getText() + " is the default value for " + object);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"The " + ele.getText() + " is not the default value for " + object);
		}
		return true;

	}

	/**
	 * Function to verify the default value for the Drop Down control
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean checkDefaultValueOfDD(WebDriver driver, String screen, String object, String value) {
		WebElement ele = itr2.getWebElement(driver, screen, object);
		String defaultval = new Select(ele).getFirstSelectedOption().getText();
		if (defaultval.equals(value)) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The " + defaultval + " is the default value for " + object);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"The " + defaultval + " is not the default value for " + object);
		}

		return true;

	}

	/**
	 * Function to verify Comments of the Pop Up
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean verifyCommentsInPopUp(WebDriver driver, String screen, String object, String value)
			throws ParseException {
		List<WebElement> commentList = itr2.getWebElements(driver, screen, object);

		int i = 1;
		for (WebElement ele : commentList) {
			if (i == 1) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"Latest " + value + " comments on top followed by other comments");
			}
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, i + " : ");
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Comment UserName : " + ele.getText().split("\n")[0].split(" ")[0]);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Comment Date : " + ele.getText().split("\n")[0].split(" ")[1]);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Comments : " + ele.getText().split("\n")[1]);
			i++;

		}
		return true;

	}

	/**
	 * Function to print value in the Report
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean printValue(WebDriver driver, String screen, String object, String value) {
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, value);
		return true;

	}

	/**
	 * Function to verify due dates with upcoming due dates
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyDue(WebDriver driver, String screen, String object, String value) {
		try {
			String[] objArray = object.split(";");
			String object0 = objArray[0];
			String object1 = objArray[1];
			String object2 = objArray[2];
			// Getting the given date value
			WebElement oElement1 = itr2.getWebElement(driver, screen, object0);
			String sElement1 = oElement1.getText().replaceAll("\\D", "").substring(0, 2);
			int iValue1 = Integer.parseInt(sElement1);

			WebElement oElement2 = itr2.getWebElement(driver, screen, object1);
			String sElement2 = oElement2.getText().replaceAll("\\D", "").substring(0, 2);
			int iValue2 = Integer.parseInt(sElement2);
			int sValue = iValue2 - iValue1;
			if (sValue == Integer.parseInt(value)) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"Upcoming Due dates matches with Due dates for " + object2 + " : " + sValue);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"Upcoming Due dates not matches with Due dates for " + object2 + " : " + sValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the upload section
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyUploadSection(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement oElement = driver.findElement(By.xpath("//div/div/form/div"));
			String[] sElement = oElement.getText().split("\n");
			if (sElement[4].equals(value)) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, value);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to get the size of tr count from grid
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getTrSize(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			List<WebElement> oListElement = oElement.findElements(By.tagName("tr"));
			int iSize = oListElement.size() - 1;
			if (iSize == Integer.parseInt(value)) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"The Expected count are equal : " + iSize);

			} else {
				if (oListElement.get(1).getText().contains("No submission found")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "The Expected count is 0 ");
				} else {

					com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
							"The Expected count are not equal : " + iSize);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the color of the element
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean verifyBGColorOfAnElement(WebDriver driver, String screen, String object, String value) {
		String valueArray[] = value.split(";");
		String status = null;
		String username = null;
		String date = null;
		String round = null;
		boolean flag = false;
		List<WebElement> workflowList = itr2.getWebElements(driver, screen, object);
		int i = 1;
		for (WebElement ele : workflowList) {
			round = ele.findElement(By.tagName("div")).findElement(By.tagName("div")).getCssValue("background-color");
			status = ele.findElement(By.tagName("h4")).getText();

			if (round.equals(valueArray[1]) && status.equals(valueArray[2])) {
				flag = true;
				break;

			} else {
				flag = false;
			}
			i++;
		}
		if (flag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					status + " is displayed in " + valueArray[0]);

			try {
				username = driver.findElement(By.xpath("//div[@id='remDealStatus']/div/div[" + i + "]/div/div[2]/p[1]"))
						.getText();
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						username + " is the Username for the status " + status);
				date = driver.findElement(By.xpath("//div[@id='remDealStatus']/div/div[" + i + "]/div/div[2]/p[2]"))
						.getText();
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						date + " is the Date for the status " + status);
			} catch (Exception e) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						status + " does not have username and date for the status " + status);

			}
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					status + " is not displayed in " + valueArray[0]);
			try {
				username = driver.findElement(By.xpath("//div[@id='remDealStatus']/div/div[" + i + "]/div/div[2]/p[1]"))
						.getText();
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						username + " is the Username for the status " + status);
				date = driver.findElement(By.xpath("//div[@id='remDealStatus']/div/div[" + i + "]/div/div[2]/p[2]"))
						.getText();
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						date + " is the Date for the status " + status);
			} catch (Exception e) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						status + " does not have username and date for the status " + status);

			}
		}

		return true;

	}

	/**
	 * Function to check the number is occurring between two numbers
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean checkNumBetweenTwoNums(WebDriver driver, String screen, String object, String value) {
		String var = null;
		String irv = null;
		String llv = null;
		String varpercent = null;
		double expectedVarPercent;
		double roundVarPercent;
		double value1;
		var = result;
		String objArray[] = object.split(",");
		WebElement obj1 = itr2.getWebElement(driver, screen, objArray[0]);
		removeSpecialCharater(driver, screen, object, obj1.getText());
		irv = result;
		WebElement obj2 = itr2.getWebElement(driver, screen, objArray[1]);
		removeSpecialCharater(driver, screen, object, obj2.getText());
		llv = result;
		if (Float.parseFloat(var) >= Float.parseFloat(llv) && Float.parseFloat(var) <= Float.parseFloat(irv)) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The Variance " + Float.parseFloat(var) + " is between the Investor Report Value "
							+ Float.parseFloat(irv) + " and Loan Level Value " + Float.parseFloat(llv));
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"The Variance " + Float.parseFloat(var) + " is not between the Investor Report Value "
							+ Float.parseFloat(irv) + " and Loan Level Value " + Float.parseFloat(llv));
		}

		WebElement obj3 = itr2.getWebElement(driver, screen, objArray[2]);
		removeSpecialCharater(driver, screen, object, obj3.getText());
		varpercent = result;
		expectedVarPercent = (Double.parseDouble(var) / Double.parseDouble(irv)) * 100;
		String val[] = String.valueOf(expectedVarPercent).split("\\.");
		int length = (int) (Math.log10(Double.parseDouble(val[1])) + 1);
		System.out.println(length);
		if (length > 11) {
			long factor = (long) Math.pow(10, 11);
			value1 = expectedVarPercent * factor;
			long tmp = Math.round(value1);
			roundVarPercent = (double) tmp / factor;

		} else {
			roundVarPercent = expectedVarPercent;
		}
		if (Double.parseDouble(varpercent) == roundVarPercent) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The Variance % " + roundVarPercent + " is between the Investor Report Value "
							+ Float.parseFloat(irv) + " and Loan Level Value " + Float.parseFloat(llv));
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"The Variance % " + roundVarPercent + " is not between the Investor Report Value "
							+ Float.parseFloat(irv) + " and Loan Level Value " + Float.parseFloat(llv));
		}
		return true;
	}

	/**
	 * Function to print the web elements values
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean printWebElementListValues(WebDriver driver, String screen, String object, String value) {
		List<WebElement> list = itr2.getWebElements(driver, screen, object);
		List<String> strlist = new ArrayList<>();
		for (WebElement ele : list) {
			strlist.add(ele.getText().trim());
		}
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, strlist.toString());
		return true;

	}

	/**
	 * Function to check the Deals is occurring on the top in the Recently
	 * published Deals
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean checkRecentDealsOnTop(WebDriver driver, String screen, String object, String value)
			throws ParseException, InterruptedException {
		String objArray[] = object.split(",");
		List<WebElement> list = itr2.getWebElements(driver, screen, objArray[0]);
		List<Date> dateList = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy", Locale.US);
		String strArray[] = null;
		String dealName = null;
		String asset = null;
		String published = null;
		String by = null;
		String viewdetails = null;
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
				"Recently Published Submissions contains the following fields :");
		for (WebElement ele : list) {
			if (ele.getText().equals("")) {
				itr2.getWebElement(driver, screen, objArray[1]).click();
				Thread.sleep(2000);
			}
			strArray = ele.getText().split("\n");
			dealName = strArray[0];
			asset = strArray[1].split(":")[1];
			published = strArray[2].split(":")[1].trim();
			by = strArray[3].split(":")[1];
			viewdetails = strArray[4];
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Deal Name :" + dealName.toUpperCase());
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Asset Class :" + asset);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Published Date :" + published);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Published By :" + by);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, viewdetails);
			dateList.add(sdf.parse(published));

		}
		Actions.checkDescendingDate(dateList);
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, dateList.toString());

		return true;

	}

	/**
	 * Function to get the size of the web elements list
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static int getListSize(WebDriver driver, String screen, String object, String value) {
		List<WebElement> list = itr2.getWebElements(driver, screen, object);
		int size = list.size();
		return size;

	}

	/**
	 * Function to verify two integer values
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean verifyIntegerEquals(WebDriver driver, String screen, String object, String value) {
		int expected = 0;
		String objArray[] = object.split(",");
		int actual = Integer.parseInt(value);
		if (actual == 0) {
			expected = 0;
		} else {
			expected = getListSize(driver, screen, objArray[0], value) - 1;
		}

		if (actual == expected) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, actual + " " + objArray[1]
					+ " on the home page is equal to the " + expected + " tasks on the Your Tasks page.");
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, actual + " " + objArray[1]
					+ " on the home page is not equal to the " + expected + " tasks on the Your Tasks page.");
		}
		return true;
	}

	/**
	 * Function to get the short name of the month
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static String getMonthShortName(int monthNumber) {
		String monthName = "";

		if (monthNumber >= 0 && monthNumber < 12)
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MONTH, monthNumber);

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
				simpleDateFormat.setCalendar(calendar);
				monthName = simpleDateFormat.format(calendar.getTime());
			} catch (Exception e) {
				if (e != null)
					e.printStackTrace();
			}
		return monthName;
	}

	public static boolean verifyDealsAssociatedToDG(WebDriver driver, String screen, String object, String value)
			throws InterruptedException {
		List<WebElement> list = itr2.getWebElements(driver, screen, object);

		for (WebElement ele : list) {
			if (dealGroupRandomList.contains(ele.getText())) {

				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"The Deal Value matches : " + ele.getText());

			} else {

				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
						"The Deal Value does not matches : " + ele.getText());
			}

		}

		return true;
	}

	public static boolean editUpandDown(WebDriver driver, String screen, String object, String value) {
		try {
			String[] sArray = value.split(";");
			String value0 = sArray[0];
			String value1 = sArray[1];
			WebElement oList = itr2.getWebElement(driver, screen, object);
			oList.click();
			oList.sendKeys(Keys.BACK_SPACE);
			oList.clear();
			oList.sendKeys(value0);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Passed the value : " + value0);
			TestCase.takeScreenShot(com.qfor.driverscript.DriverScriptRef.Report, new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			oList.sendKeys(Keys.TAB);
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			String sElement = oElement.getAttribute(value1);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The minimum value of Due Dates : " + sElement);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the given text exists
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyTextNotExist(WebDriver driver, String screen, String object, String value) {
		WebElement element = null;
		String eleValue = null;
		try {
			element = itr2.getWebElement(driver, screen, object);
			eleValue = element.getText();
			if (eleValue == null) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, " Verified : " + eleValue);
				return true;
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, " Verification failed : " + eleValue);
			}
		} catch (Exception e) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, " Not verified : " + eleValue);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Function for check the given control is disabled or not
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean isNotEnabled(WebDriver driver, String screen, String object, String value) {
		try {
			WebElement element = itr2.getWebElement(driver, screen, object);
			System.out.println("Is Enabled ==== " + element.isEnabled());
			if (element.isEnabled() == false) {
				System.out.println(object + " is not Enabled");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, object + " is Not Enabled");
			} else {
				System.out.println(object + " is Enabled");
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, object + " is Enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	// adding methods Jan 19 2017

	/**
	 * Function to perform mouse over action
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean moveToElementAndClick(WebDriver driver, String screenName, String ObjectName, String value) {

		WebElement element = itr2.getWebElement(driver, screenName, ObjectName);
		mouseHoverJScript(element, driver);
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "Mouse Over Action is performed");
		return true;

	}

	/**
	 * java script Function to perform mouse over action
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static void mouseHoverJScript(WebElement HoverElement, WebDriver driver) {
		try {

			String mouseOverScript = "if(document.createEvent)" + "{var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initEvent('mouseover', true, false); " + "arguments[0].dispatchEvent(evObj);} "
					+ "else if(document.createEventObject) " + "{ arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) driver).executeScript(mouseOverScript, HoverElement);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occurred while hovering" + e.getStackTrace());

		}
	}

	/**
	 * This method copies the OTP from the logged in users email and enter it
	 * into the OTP tex box.
	 * 
	 * @param driver
	 * @param screenName
	 * @param ObjectName
	 * @param value
	 * @return
	 */
	public static boolean enterOTPtext(WebDriver driver, String screenName, String ObjectName, String value) {
		boolean successFlag = false;
		ProjectEmailUtil readOTP;

		if (value != null && value != "") {
			String[] credentials = value.split(",");
			String userEmail = credentials[0];
			String password = credentials[1];
			readOTP = new ProjectEmailUtil(userEmail, password);
		} else {
			readOTP = new ProjectEmailUtil();
		}

		try {

			// manage the Alert here.
			String OTPtext = readOTP.getOTP();
			// wait sometime to read the value.
			Thread.sleep(2000);

			if (OTPtext != "") {
				System.out.println(OTPtext);
				WebElement element = itr2.getWebElement(driver, screenName, ObjectName);
				if (isAlertPresent(driver)) {
					driver.switchTo().alert().dismiss();
				}
				element.sendKeys(OTPtext);
				Thread.sleep(1000);
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "OTP has been Entered as expected");
				successFlag = true;

			} else {

				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL, ObjectName
						+ "Unable to get OTP from the Email, either email doesn't have OTP, or its timing issue");
				return successFlag;
			}

		} catch (MessagingException | IOException | InterruptedException | UnhandledAlertException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"Error In retrieving OTP from the OTP email");
			return successFlag;
		}

		return successFlag;
	}

	// checkinf for alerts.

	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
	}

	// lavanya copy Jan 24

	/**
	 * Function to switch to another Window
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean switchToWindowAndMaximize(WebDriver driver, String screenName, String ObjectName,
			String value) {
		try {
			// Store the current window handle
			winHandleBefore = driver.getWindowHandle();
			// Switch to new window opened
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
				driver.manage().window().maximize();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to get Text from the Element List
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static List<String> getTextfromElements(WebDriver driver, String screenName, String ObjectName,
			String value) {
		List<String> textList = new ArrayList<>();
		List<WebElement> list = itr2.getWebElements(driver, screenName, ObjectName);
		try {
			for (WebElement element : list) {
				textList.add(element.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textList;
	}

	/**
	 * Function to get Text from the Element List
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean switchToDefaultWindow(WebDriver driver, String screenName, String ObjectName, String value) {
		// Switch back to original browser (first window)
		driver.switchTo().window(winHandleBefore);
		return true;

	}

	/**
	 * Function to close driver
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean closeDriver(WebDriver driver, String screenName, String ObjectName, String value) {
		driver.close();
		return true;

	}

	/**
	 * Function to press Enter Key
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean pressEnterKey(WebDriver driver, String screenName, String ObjectName, String value) {
		// Press enter Key
		WebElement element = itr2.getWebElement(driver, screenName, ObjectName);
		element.sendKeys(Keys.ENTER);
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "ENTER Key is pressed");
		return true;

	}

	// Jan 30
	public static boolean setAllUserPermissions(WebDriver driver, String screenName, String ObjectName, String value) {

		try {
			//the following line is to be edited later
			//WebElement scrollDiv = driver.findElement(By.xpath("//*[@id='ctl00_MainContentRoot_ASPxUserPermission_DXFixedColumnsDiv']"));
		
				
				
			List<WebElement> tabledataElements = itr2.getWebElements(driver, screenName, ObjectName);
			WebElement element = null;
			int i = 0;
			boolean flag = false;
			String username = "";
			for (WebElement tdEle : tabledataElements) {
				if (i == 0) {
					username = tdEle.getText();
					if (value.equals(username)) {
						com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "UserName : " + username);
					}
				} else {
					element = tdEle.findElement(By.tagName("span")).findElement(By.tagName("span"));
					//itr2.scrollTo(driver, element); //this gives error after the 7th item.
					//if its at 7 scroll the whole div to the end for other elements to be visisble.
					if(!element.isDisplayed()){
						WebElement scrollDiv = driver.findElement(By.xpath("//*[@id='ctl00_MainContentRoot_ASPxUserPermission_DXFixedColumnsDiv']"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollDiv);
						if(!element.isDisplayed()){
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].0", scrollDiv);
						}
					}
					if (element.getAttribute("class").contains("Unchecked")) {
						//itr2.scrollTo(driver, element);
						element.click();
						//checkifElementNotPresent(driver, screenName, "Loading", "2000");

					} else {
						continue;
					}
					flag = true;
				}
				
				//this sleep is to avoid checkbox progress on checkbox click.
				Thread.sleep(3000);
				i++;

			}
			if (flag == true) {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
						"All user permission has been set for user : " + username);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
						"All user permission did not set for user : " + username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	
	//scroll To test method.
	
	public static boolean scrollcheckbox(WebDriver driver, String screenName, String ObjectName, String value) {
		boolean successFlag = false;
		
		//for table body scroll to the end.
		
		//WebElement tableA = driver.findElement(By.xpath("//*[@id='ctl00_MainContentRoot_ASPxUserPermission_DXMainTable']"));
		WebElement scrollDiv = driver.findElement(By.xpath("//*[@id='ctl00_MainContentRoot_ASPxUserPermission_DXFixedColumnsDiv']"));
		//System.out.println(tableA.isDisplayed());
		// Scroll to div's most right:
		System.out.println(scrollDiv.isDisplayed());
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollDiv);
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tableA);
		
		
		WebElement lastTd = driver.findElement(By.id("ctl00_MainContentRoot_ASPxUserPermission_tccell0_16"));
		System.out.println(lastTd.isDisplayed());
		
		
/*		//WebElement checkboxlast = driver.findElement(By.xpath("//*[@id='ctl00_MainContentRoot_ASPxUserPermission_cell0_16_chUpdate Deal URL_28_S_D']"));
		WebElement lastTd = driver.findElement(By.id("ctl00_MainContentRoot_ASPxUserPermission_tccell0_16"));
		
		System.out.println(lastTd.isDisplayed());
		
		JavascriptExecutor js = (JavascriptExecutor)driver; 
		js.executeScript("arguments[0].scrollIntoView()", lastTd);
		
		System.out.println(lastTd.isDisplayed());
		
		System.out.println(lastTd.isDisplayed());*/
		return successFlag;

	}
	public static boolean scrollTotest(WebDriver driver, String screenName, String ObjectName, String value) {
		boolean successFlag = false;
				WebElement promotLink = driver.findElement(By.xpath("//*[@id='ctl00_MainContentRoot_ASPxGridViewManageUsers_cell0_8_lbAdminUser']"));
				System.out.println(promotLink.isDisplayed());
				
				JavascriptExecutor js = (JavascriptExecutor)driver; 
				js.executeScript("arguments[0].scrollIntoView()", promotLink);

				
						
			
		
		return successFlag;

	}
	
	//Jan 31 - 
	
	public static boolean promoteUser(WebDriver driver, String screenName, String ObjectName, String value) {
		boolean successFlag = false;
		List<WebElement> accessTable = itr2.getWebElements(driver, screenName, ObjectName);
		
		System.out.println(accessTable.size());

		WebElement userNameField = null;
		WebElement promoteLink = null;
		
		int i=0;
		
/*		WebElement firstRow = driver.findElement(By.xpath("//table[@id ='ctl00_MainContentRoot_ASPxGridViewManageUsers_DXMainTable']/tbody/tr[4]"));
		
		WebElement firstCol = firstRow.findElement(By.xpath("td[1]"));
		firstCol.getText();
		System.out.println(firstCol.getText());*/
		for (WebElement row : accessTable) {
			if (i < 3) {
				i++;
				continue;
			} else {
				userNameField = row.findElement(By.xpath("td[1]"));
				System.out.println(userNameField.getText());

				if (userNameField.getText().equalsIgnoreCase(value)) {
					promoteLink = row.findElement(By.xpath("td[6]"));
					System.out.println(promoteLink.isDisplayed());

					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].scrollIntoView()", promoteLink);

					System.out.println(promoteLink.isDisplayed());

					System.out.println(promoteLink.getText());
					if (promoteLink.getText().equalsIgnoreCase("Promote")) {
						promoteLink.click();
					} else {
						System.out.println("The user is already promoted.");
					}

					successFlag = true;
					return successFlag;

				} else {
					System.out.println("The search user doesn't exist");

				}

/*				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

				i++;
			}
		}

		return successFlag;

	}
	
	//mulitpbrowser will be deleted later.
	public static boolean mutibrowser(WebDriver driver, String screenName, String ObjectName, String value) {
		boolean successFlag = false;
			
		
		//CHECK BROWSER INSTANCE NEW
		System.setProperty("webdriver.gecko.driver", "TestData/geckodriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("marionette", true);
			capabilities.setCapability(FirefoxDriver.BINARY, "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			//driver = new MarionetteDriver(capabilities); 
			WebDriver browser = new FirefoxDriver(capabilities);
			browser.get("bankofamericastage.lewtan.com");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			browser.close();
			browser.quit();
		return successFlag;

	}
	

	
}