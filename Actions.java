
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.qfor.driverscript.Environment;
import com.qfor.interactions.Interactions2;
import com.qfor.testcase.TestCase;
import com.qfor.utils.Log;
import com.qfor.utils.ScreenShotUtils;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.IMAPProtocol;



import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Actions extends ProjectSpecific {

	// Variable declaration
	static Interactions2 itr2 = new Interactions2();
	public String selectedValue;
	public static int implicitWaitInterim = 2;
	public static int implicitWaitEnd = 5;
	public static int timeOut = 7000;
	public static String SmtpHost = null;
	public static String SmtpSSLPort = null;
	public static String SmtpEmailId1 = null;
	public static String SmtpEmailPassword1 = null;
	public static String SmtpEmailId = null;
	public static String SmtpEmailPassword = null;
	public static String EmailContent = null;
	public static String SubmitFileDeal = null;
	public static String SubmitInvalid = null;
	public static String SubmitFileAsset = null;
	public static String DealLevelInvalid = null;
	public static String AssetLevelInvalid = null;
	public static String DealLevelDealA = null;
	public static String AssetLevelDealA = null;
	public static String FilePath = null;
	public static final String stextbox = "text";
	public static final String sselect = "select";
	public static final String sdropdown = "DropDown";
	public static final String sddown = "dropdown";
	public static final String scombo = "bigcombo";
	public static final String slink = "a";
	public static final String sbutton = "button";
	public static final String ssubmit = "submit";
	public static final String spassword = "password";
	public static final String scheckbox = "checkbox";
	public static final String sradio = "radio";
	public static WebElement gridElement = null;
	public static String newPassword = null;
	public static String sRandom = null;
	public static String loanVal = null;
	public static WebElement oGridElement = null;
	public static String sDealValue = null;
	public static String[] fileArr_Autoit = null;
	public static String text_autoit = null;
	public static String image_saveasWindow;
	public static String exportExcelFile;
	public static String excelScreenshot;
	public static String imagepath_Excel;
	public static String NewFileNamePathExportedExcel;
	public static String ExportToExcelScreenshots;
	public static String image_ExportedExcelWindow;
	public static String openwithWindow = "true";
	public static List<String> moveditemList = null;
	public static int iSynchTimeSeconds = 30;
	public static String DesktopPath = null;
	public static String DownloadPath = null;
	public static String result = null;
	public static WebElement userNameElement = null;
	public static int homeSubmissionCnt;
	public static Date sFilingDate;
	public static SimpleDateFormat formatter;
	public static String AssetRelFilePath;
	// Map Declaration
	public static Map<Integer, String> passwordMap = new HashMap<>();
	// List Declaration
	public static List<String> sEList = new ArrayList<String>();

	public List<String> dealListByAsset = new ArrayList<String>();

	/**
	 * This method will get the URL from configurable file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getURL(WebDriver driver, String screenName,
			String ObjectName, String value) {
		Environment environment = new Environment();
		@SuppressWarnings("static-access")
		String url = environment.getURL();
		System.out.println(url);
		// Launch the url
		driver.get(url);
		return true;
	}

	/**
	 * Function to implement static wait
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean sleep(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			// sleep for given period
			Thread.sleep(Integer.parseInt(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to generate Date and TimeStamp
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getDateTimeStamp(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			WebElement ele = itr2.getWebElement(driver, screenName, ObjectName);
			String timeStamp = new SimpleDateFormat(Constants.Date3)
					.format(new java.util.Date());
			newPassword = Constants.iUser + timeStamp;
			// Send the generated new value
			ele.sendKeys(newPassword);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to generate Date and TimeStamp
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getDate(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			WebElement ele = itr2.getWebElement(driver, screenName, ObjectName);
			String timeStamp = new SimpleDateFormat(Constants.Date3)
					.format(new java.util.Date());
			newPassword = timeStamp;
			// Send the generated new value
			ele.sendKeys(newPassword);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Function to generate Date and TimeStamp value based on input value
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getDateTimeStampValue(WebDriver driver,
			String screenName, String ObjectName, String value) {
		try {
			WebElement ele = itr2.getWebElement(driver, screenName, ObjectName);
			String timeStamp = new SimpleDateFormat(Constants.Date3)
					.format(new java.util.Date());
			newPassword = value + timeStamp;
			// Send the generated new value
			ele.sendKeys(newPassword);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to read property file and calls the email method
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public boolean forgotPass(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			// read the property values
			readProperty(driver);
			// Calls the email functions
			emailVerification(driver, SmtpEmailId, SmtpEmailPassword, SmtpHost,
					SmtpSSLPort);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to read the values in property file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean readProperty(WebDriver driver) {
		try {
			// Reads the environment properties file
			FileReader reader = new FileReader(
					"TestData/RREnvironment.properties");
			Properties properties = new Properties();
			properties.load(reader);
			// Get the property value
			SmtpHost = properties.getProperty("SmtpHost");
			SmtpSSLPort = properties.getProperty("SmtpSSLPort");
			SmtpEmailId1 = properties.getProperty("SmtpEmailId1");
			SmtpEmailPassword1 = properties.getProperty("SmtpEmailPassword1");
			SmtpEmailId = properties.getProperty("SmtpEmailId");
			SmtpEmailPassword = properties.getProperty("SmtpEmailPassword");
			EmailContent = properties.getProperty("EmailContent");
			DesktopPath = properties.getProperty("DesktopPath");
			DownloadPath = properties.getProperty("DownloadPath");
			FilePath = properties.getProperty("Filepath");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to read the values in property file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public boolean addnewUser(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			// Read the property file
			readProperty(driver);
			// Calls the email function
			emailVerification(driver, SmtpEmailId1, SmtpEmailPassword1,
					SmtpHost, SmtpSSLPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to invoke Manage Users
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean manageUsers(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			boolean flag = false;
			String[] objSplit = ObjectName.split(";");
			String object0 = objSplit[0];
			String object1 = objSplit[1];
			WebElement oPageLink = itr2.getWebElement(driver, screenName,
					object1);
			List<WebElement> oLinks = oPageLink.findElements(By.tagName("a"));
			// Iterates the list
			for (WebElement oNo : oLinks) {
				String sNo = oNo.getText();
				oNo.click();
				// calls the function and returns the value
				flag = findTextInGridforPagination(driver, screenName, object0,
						value, sNo);
				if (flag == true) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// *****************************************************************************
	// Author:
	// Description: Function to invoke Manage Users
	// Params:
	// Returns:
	// Last Modified:
	// *****************************************************************************

	/**
	 * Function to delete the read messages from inbox using javamail
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public void emailMessageDeletion(WebDriver driver,
			final String emailUserName, final String emailPassword,
			String host, String port) {
		try {
			IMAPFolder folder = null;
			int msg = 0;
			Thread.sleep(50000);
			// Send the parameter values to getFolder Method
			folder = (IMAPFolder) getFolder(emailUserName, SmtpEmailPassword,
					SmtpHost, SmtpSSLPort);
			// Get the unread message count
			msg = folder.getUnreadMessageCount();
			System.out.println("UnreadMessageCount : " + msg);
			Message[] messages = null;
			messages = folder.getMessages();
			for (int i = 0; i < msg; i++) {
				System.out.println("From : "
						+ messages[i].getFrom()[0].toString());
				System.out.println("Subject : " + messages[i].getSubject());
				System.out.println("Subject : " + messages[i].getSubject());
				System.out.println("Sent Date : " + messages[i].getSentDate());
				System.out.println(messages[i].getContent().toString());
				System.out.println(messages[i].getContent().toString());

				// Deletes the read mail
				messages[i].setFlag(Flags.Flag.DELETED, true);
				folder.close(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to verify the required email messages using javamail
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public void emailVerification(WebDriver driver, final String emailUserName,
			final String emailPassword, String host, String port) {
		try {
			int msg = 0;
			boolean isMailFound = false;
			IMAPFolder folder = null;
			String sLaunch = null;
			Thread.sleep(50000);
			// Send the parameter values to getFolder Method
			folder = (IMAPFolder) getFolder(emailUserName, SmtpEmailPassword,
					SmtpHost, SmtpSSLPort);
			startListening(folder);
			// Get the unread message count
			msg = folder.getUnreadMessageCount();
			System.out.println("UnreadMessageCount : " + msg);
			Message[] messages = null;
			Message mails = null;
			messages = folder.getMessages();
			// Search for unread mail
			for (Message mail : messages) {
				if (!mail.isSet(Flags.Flag.SEEN)) {
					mails = mail;
					System.out.println("Message Count is: "
							+ mails.getMessageNumber());
					isMailFound = true;
				}
			}
			if (!isMailFound) {
				System.out.println("Could not find new mail");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "Could not find new mail");
			}
			// Read the content of mail and launch URL
			for (int i = 0; i < msg; i++) {
				if ((messages[i].getSubject()
						.contains(Constants.iPasswordReminder))
						|| (messages[i].getSubject()
								.contains(Constants.iAccountCreated))) {
					String line;
					StringBuffer buffer = new StringBuffer();
					System.out.println(buffer);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(mails.getInputStream()));
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}

					System.out.println("From : "
							+ messages[i].getFrom()[0].toString());
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "From : "
									+ messages[i].getFrom()[0].toString());
					System.out.println("Subject : " + messages[i].getSubject());
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS,
							"Subject : " + messages[i].getSubject());
					System.out.println("Subject : " + messages[i].getSubject());
					System.out.println("Sent Date : "
							+ messages[i].getSentDate());
					System.out.println(messages[i].getContent().toString());
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS,
							"Sent Date : " + messages[i].getSentDate());
					String URL = buffer.toString().split("href")[1];
					// Getting the value from double quotes
					Pattern p = Pattern.compile("\"([^\"]*)\"");
					Matcher m = p.matcher(URL);
					while (m.find()) {
						sLaunch = m.group(1);
						System.out.println(sLaunch);
						break;
					}
					System.out.println(sLaunch);
					// Launch the URL from Email content
					driver.get(sLaunch);
				} else if (messages[i].getSubject().contains(
						"requesting access")) {
					System.out.println("From : "
							+ messages[i].getFrom()[0].toString());
					System.out.println("Subject : " + messages[i].getSubject());
					System.out.println("Subject : " + messages[i].getSubject());
					System.out.println("Sent Date : "
							+ messages[i].getSentDate());
					System.out.println(messages[i].getContent().toString());
					System.out.println(messages[i].getContent().toString());
				}
				// Deletes the read mail
				messages[i].setFlag(Flags.Flag.DELETED, true);
				folder.close(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to get all the required information to open mail
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public Folder getFolder(final String emailUserName,
			final String emailPassword, String host, String port) {
		Folder folder = null;
		try {
			System.out.println("Inside the mail function");
			Properties props = new Properties();
			props.put("mail.imap.starttls.enable", "true");
			// SMTP Host
			props.put("mail.imap.host", host);
			// SSL Port
			props.put("mail.imap.socketFactory.port", port);
			// SSL Factory Class
			props.put("mail.imap.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			// Enabling SMTP Authentication
			props.put("mail.imap.auth", "true");
			// SMTP Port
			props.put("mail.imap.port", port);
			// Setup authentication, get session
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(emailUserName,
									emailPassword);
						}
					});

			// Set the subject and body of the message
			Store store = session.getStore("imaps");
			store.connect(host, emailUserName, emailPassword);
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return folder;
	}

	/**
	 * Reusable function to start the Email thread
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public void startListening(IMAPFolder imapFolder) throws MessagingException {
		// We need to create a new thread to keep alive the connection
		Thread t = new Thread(new KeepAliveRunnable(imapFolder),
				"IdleConnectionKeepAlive");

		t.start();

		// Shutdown keep alive thread
		if (t.isAlive()) {
			t.interrupt();
		}
	}

	/**
	 * Runnable used to keep alive the connection to the IMAP
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	private static class KeepAliveRunnable implements Runnable {
		private static final long KEEP_ALIVE_FREQ = 300000; // 5 minutes
		private IMAPFolder folder;

		public KeepAliveRunnable(IMAPFolder folder) {
			this.folder = folder;
		}

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					Thread.sleep(KEEP_ALIVE_FREQ);
					// Perform a NOOP just to keep alive the connection
					System.out
							.println("Performing a NOOP to keep alvie the connection");
					folder.doCommand(new IMAPFolder.ProtocolCommand() {
						public Object doCommand(IMAPProtocol p)
								throws ProtocolException {
							p.simpleCommand("NOOP", null);
							return null;
						}
					});
				} catch (InterruptedException e) {
					// Ignore, just aborting the thread...
				} catch (MessagingException e) {
					// Shouldn't really happen...
					System.out
							.println("Unexpected exception while keeping alive the IDLE connection");
				}
			}
		}
	}

	/**
	 * Function that invokes account lockout scenario
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean accountLockOut(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			do {
				String[] objSplit = ObjectName.split(";");
				String object0 = objSplit[0];
				String object1 = objSplit[1];
				String object2 = objSplit[2];
				String object3 = objSplit[3];
				WebElement oElement1 = itr2.getWebElement(driver, screenName,
						object0);
				oElement1.click();
				WebElement oElement2 = itr2.getWebElement(driver, screenName,
						object1);
				oElement2.sendKeys(value);
				WebElement oElement3 = itr2.getWebElement(driver, screenName,
						object2);
				oElement3.sendKeys(Constants.iAccountPassword);
				WebElement oElement4 = itr2.getWebElement(driver, screenName,
						object3);
				oElement4.click();
			} while (itr2.verifyExists(driver, "cssSelector",
					Constants.oAccountLock));
			System.out.println("out of do while loop");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to get the values from the grid and check the sorting
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean gridViewSortforDeal(WebDriver driver,
			String screenName, String ObjectName, String value) {
		String row1 = null;
		String otherrow = null;
		WebElement sortList = null;
		List<String> stringSortList = new ArrayList<String>();
		WebElement oGrid = driver.findElement(By.id(value));
		List<WebElement> oListGrid = oGrid.findElements(By
				.xpath(Constants.oListGrid));
		try {
			// Iterates the row value
			for (int row = 1; row < oListGrid.size(); row++) {
				if (row == 1) {
					row1 = Constants.osearchDealinGridView2;
				} else {
					otherrow = Constants.osearchDealinGridView2 + row
							+ Constants.osearchDealinGridView4;
				}
				for (int col = 1; col < 4; col++) {
					if (row == 1 && col == 1) {
						sortList = driver.findElement(By
								.xpath(Constants.osearchDealinGridView5 + value
										+ Constants.osearchDealinGridView6
										+ row1
										+ Constants.osearchDealinGridView7));
					} else if (row == 1 && col > 1) {
						sortList = driver.findElement(By
								.xpath(Constants.osearchDealinGridView5 + value
										+ Constants.osearchDealinGridView9
										+ col
										+ Constants.osearchDealinGridView10));
					} else if (row > 1 && col == 1) {
						sortList = driver.findElement(By
								.xpath(Constants.osearchDealinGridView5 + value
										+ Constants.osearchDealinGridView9
										+ otherrow
										+ Constants.osearchDealinGridView7));
					} else if (row > 1 && col > 1) {
						sortList = driver.findElement(By
								.xpath(Constants.osearchDealinGridView5 + value
										+ Constants.osearchDealinGridView9
										+ otherrow
										+ Constants.osearchDealinGridView3
										+ col
										+ Constants.osearchDealinGridView10));
					}
					stringSortList.add(sortList.getText().toLowerCase());
					System.out.println(sortList.getText());
				}

			}
			System.out.println(stringSortList);
			// checks the list of values is ascending
			checkAscending(stringSortList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to get the values from the grid and check the sorting
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean gridViewSortforAssetClass(WebDriver driver,
			String screenName, String ObjectName, String value) {
		// variable declaration
		String row1 = null;
		String sortString = null;
		String otherrow = null;
		List<String> stringSortList = new ArrayList<String>();
		WebElement oGrid = driver.findElement(By.id(value));
		List<WebElement> oListGrid = oGrid.findElements(By
				.xpath(Constants.oListGrid));
		try {
			// Iterates the row value
			for (int row = 1; row < oListGrid.size(); row++) {
				if (row == 1) {
					row1 = Constants.osearchDealinGridView2;
				} else {
					otherrow = Constants.osearchDealinGridView3 + row
							+ Constants.osearchDealinGridView4;
				}
				for (int col = 1; col < 4; col++) {
					if (row == 1 && col == 1) {
						sortString = driver.findElement(
								By.xpath(Constants.osearchDealinGridView5
										+ value
										+ Constants.osearchDealinGridView6
										+ row1 + Constants.oGridView))
								.getText();

					} else if (row == 1 && col > 1) {
						sortString = driver.findElement(
								By.xpath(Constants.osearchDealinGridView5
										+ value
										+ Constants.osearchDealinGridView9
										+ col + Constants.oGridView1))
								.getText();
					} else if (row > 1 && col == 1) {
						sortString = driver.findElement(
								By.xpath(Constants.osearchDealinGridView5
										+ value
										+ Constants.osearchDealinGridView6
										+ otherrow + Constants.oGridView))
								.getText();
					} else if (row > 1 && col > 1) {
						sortString = driver.findElement(
								By.xpath(Constants.osearchDealinGridView5
										+ value
										+ Constants.osearchDealinGridView6
										+ otherrow
										+ Constants.osearchDealinGridView3
										+ col + Constants.oGridView1))
								.getText();
					}
					String[] strSplit = sortString.split("\n");
					String[] strSplit2 = strSplit[1].split(":");
					stringSortList.add(strSplit2[1].trim().toLowerCase());
				}

			}
			System.out.println(stringSortList);
			// checks the list of values is ascending
			checkAscending(stringSortList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to check the String values are in ascending
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkAscending(List<String> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).toLowerCase()
					.compareTo(list.get(i + 1).toLowerCase()) > 0) {
				System.out.println(list.get(i).toString());
				System.out.println("the List is not in ascending order");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "the List is not in ascending order");

			}
		}
		System.out.println("the List is in ascending order");
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
				"the List is in ascending order");
		return true;
	}

	/**
	 * Function to check the view is grid
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkDealView(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screenName,
					ObjectName);
			String oView = oElement.getAttribute("class");
			// Checks the value is active
			if (oView.equals(Constants.iActive)) {
				System.out.println("The default view is list view");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "The default view is list view");
			} else {
				System.out.println("The default view is Grid view");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "The default view is Grid view");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the default view is List
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkListView(WebDriver driver, String screenName,
			String ObjectName, String value) {
		// variable declaration
		WebElement oListElements = null;
		List<String> oStringElement = new ArrayList<String>();
		try {
			WebElement oElement = itr2.getWebElement(driver, screenName,
					ObjectName);
			List<WebElement> oListTr = oElement
					.findElements((By.tagName("tr")));
			// Iterates the list values
			for (int i = 2; i < oListTr.size(); i++) {
				oListElements = driver
						.findElement(By.xpath(Constants.oListView1 + i
								+ Constants.oListView2));
				if (oListElements.getText().equals("")) {
					continue;
				}
				oStringElement.add(oListElements.getText().toLowerCase());
			}
			// checks the order of given value
			checkAscending(oStringElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to validate the status of the control
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean validateControlStatus(WebDriver driver,
			String screenName, String ObjectName, String value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		String valueArray[] = value.split(";", 5);
		String label = valueArray[0];
		String control = valueArray[1];
		String defaultvalue = valueArray[2];
		String enable = valueArray[3];
		String dropdown = valueArray[4];

		System.out.println(control + "," + defaultvalue + "," + enable + ","
				+ dropdown);
		String econtrol = "";
		String elabel = "";
		String evalue = "";
		String eenabled = "";
		WebElement element = itr2.getWebElement(driver, screenName, ObjectName);

		switch (control) {
		// if the controltype is text in inputsheet,the text case will get
		// executed
		case stextbox:
		case spassword:
		case scheckbox:
			try {
				// Checks the label of the control
				elabel = element.getAttribute("id");
				String elabelStatus = containsComparatorMethod(label, elabel);
				// Checks the type of the control
				econtrol = element.getAttribute("type");
				String econtrolStatus = equalsComparatorMethod(control,
						econtrol);
				// Checks the default value of the control
				if (control.equals("checkbox")) {
					evalue = "";
				} else {
					evalue = element.getAttribute("value");
				}
				String evalueStatus = equalsComparatorMethod(defaultvalue,
						evalue);
				// Checks the enable/disable status of the control
				eenabled = String.valueOf(element.isEnabled());
				String eenabledStatus = equalsComparatorMethod(enable, eenabled);
				// Verifies and prints the status in Report
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].scrollIntoView();", element);
				if (eenabled.equals("true") && evalue.equals("")) {
					element.sendKeys(elabel);
				}

				if ((elabelStatus).equals("PASS")
						&& (econtrolStatus).equals("PASS")
						&& (evalueStatus).equals("PASS")
						&& (eenabledStatus).equals("PASS")) {
					System.out.println("Verified For " + ":" + "Label Name : "
							+ label + "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue + ","
							+ "Enable Status : " + eenabled);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Enable Status : " + eenabled);
				} else {
					System.out.println("--------------------------FAIL" + " "
							+ "Verified For " + ":" + "Label Name : " + label
							+ "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue + ","
							+ "Enable Status : " + eenabled);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Enable Status : " + eenabled);
				}
				if (eenabled.equals("true") && evalue.equals("")
						&& !control.equals("checkbox")) {
					element.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		// if the controltype is select/dropdown in inputsheet,the below case
		// will get executed
		case sselect:
		case sdropdown:
		case sddown:
		case scombo:
			try {
				String econtrolStatus = "";
				// Checks the label of the control
				elabel = element.getAttribute("id");
				// Checks the type of the controls
				String elabelStatus = containsComparatorMethod(label, elabel);
				// Checks the default value of the control
				if (control.equals(sselect)) {
					econtrol = element.getTagName();
					econtrolStatus = equalsComparatorMethod(control, econtrol);
					if (!dropdown.equals("")) {
						VerifydropdownElements(dropdown, element);
					}
					if ((element.getText().contains("--Select--"))) {
						evalue = "--Select--";
					} else {
						evalue = element.findElements(By.tagName("option"))
								.get(0).getText();
					}
				} else {
					econtrol = element.getAttribute("class");
					econtrolStatus = containsComparatorMethod(control, econtrol);
				}
				if ((element.getText().contains("--Select--"))) {
					evalue = "--Select--";
				}
				String evalueStatus = equalsComparatorMethod(defaultvalue,
						evalue);
				// Checks the enable/disable status of the control
				eenabled = String.valueOf(element.isEnabled());
				String eenabledStatus = equalsComparatorMethod(enable, eenabled);
				// Verifies and prints the status in Report
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].scrollIntoView();", element);
				if (eenabled.equals("true")) {
					element.click();
					Thread.sleep(3000);
				}
				if ((elabelStatus).equals("PASS")
						&& (econtrolStatus).equals("PASS")
						&& (evalueStatus).equals("PASS")
						&& (eenabledStatus).equals("PASS")) {
					System.out.println("----------------------------PASS" + " "
							+ "Verified For " + ":" + "Label Name : " + label
							+ "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue + ","
							+ "Enable Status : " + eenabled);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Enable Status : " + eenabled);
				} else {
					System.out.println("----------------------------FAIL" + " "
							+ "Verified For " + ":" + "Label Name : " + label
							+ "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue + ","
							+ "Enable Status : " + eenabled);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Enable Status : " + eenabled);
				}
				if (eenabled.equals("true")) {
					element.sendKeys(Keys.ESCAPE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		// if the controltype is link in inputsheet,the below case will get
		// executed
		case slink:
			try {
				// Checks the label of the control
				try {
					if (label.equals("CaptchaImage") || label.equals("Basic")
							|| label.equals("Asset") || label.equals("Deal")
							|| label.equals("DealList")) {
						elabel = element.getAttribute("href");
						System.out.println(elabel);
					} else {
						elabel = element.getAttribute("id");
						System.out.println(elabel);
					}
					if (elabel.equals("")) {
						elabel = element.getAttribute("class");
						System.out.println(elabel);
					}
				} catch (Exception e) {
					elabel = element.getAttribute("class");
					System.out.println(elabel);
				}

				String elabelStatus = containsComparatorMethod(label, elabel);
				// Checks the type of the control

				econtrol = element.getTagName();
				String econtrolStatus = equalsComparatorMethod(control,
						econtrol);
				// Checks the default value of the control
				evalue = element.getText();
				String evalueStatus = equalsComparatorMethod(defaultvalue,
						evalue);
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].scrollIntoView();", element);
				// Verifies and prints the status in Report
				if ((elabelStatus).equals("PASS")
						&& (econtrolStatus).equals("PASS")
						&& (evalueStatus).equals("PASS")) {
					System.out.println("--------------------------PASS" + " "
							+ "Verified For " + ":" + "Label Name : " + label
							+ "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Enable Status : " + eenabled);
				} else {
					System.out.println("--------------------------FAIL" + " "
							+ "Verified For " + ":" + "Label Name : " + label
							+ "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Enable Status : " + eenabled);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		// if the controltype is link in inputsheet,the below case will get
		// executed
		case sbutton:
		case ssubmit:
		case sradio:
			try {
				String evalueStatus = "";
				// Checks the label of the control
				try {
					elabel = element.getAttribute("id");
					if (elabel.equals("")) {
						elabel = element.getAttribute("class");
					}
				} catch (Exception e) {
					elabel = element.getAttribute("class");
				}

				String elabelStatus = containsComparatorMethod(label, elabel);
				// Checks the type of the control

				econtrol = element.getAttribute("type");
				System.out.println(econtrol);

				String econtrolStatus = equalsComparatorMethod(control,
						econtrol);

				// Checks the default value of the control
				if (ObjectName.equals("Internal")
						|| ObjectName.equals("External")) {
					evalue = element.getAttribute("value");
					evalueStatus = equalsComparatorMethod(defaultvalue, evalue);
				} else {
					evalue = element.getText();
					evalueStatus = equalsComparatorMethod(defaultvalue, evalue);
				}
				// Checks the enable/disable status of the control
				eenabled = String.valueOf(element.isEnabled());
				String eenabledStatus = equalsComparatorMethod(enable, eenabled);
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].scrollIntoView();", element);
				// Verifies and prints the status in Report
				if ((elabelStatus).equals("PASS")
						&& (econtrolStatus).equals("PASS")
						&& (evalueStatus).equals("PASS")
						&& (eenabledStatus).equals("PASS")) {
					System.out.println("-------------------------PASS" + " "
							+ "Verified For " + ":" + "Label Name : " + label
							+ "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue + ","
							+ "Enable Status : " + eenabled);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Enable Status : " + eenabled);
				} else {
					System.out.println("-------------------------FAIL" + " "
							+ "Verified For " + ":" + "Label Name : " + label
							+ "," + "Control Type : " + econtrol + ","
							+ "Default Value : " + evalue + ","
							+ "Enable Status : " + eenabled);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Verified For " + ":"
									+ "Label Name : " + label);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Control Type : " + econtrol);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Default Value : " + evalue);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, "Enable Status : " + eenabled);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		return true;
	}

	/**
	 * Function the checks for equals value from the input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static String equalsComparatorMethod(String inputValue,
			String elementValue) {
		String status = "";
		try {
			if (elementValue.equals(inputValue)) {
				status = "PASS";
			} else {
				status = "FAIL";
			}
		} catch (Exception e) {
			System.out.println("Invalid Reporting");
		}
		return status;
	}

	/**
	 * Function the checks for contains value from the input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static String containsComparatorMethod(String inputValue,
			String elementValue) {
		String status = "";
		try {
			if (elementValue.contains(inputValue)) {
				status = "PASS";
			} else {
				status = "FAIL";
			}
		} catch (Exception e) {
			System.out.println("Invalid Reporting");
		}
		return status;
	}

	/**
	 * This function will scroll until element appears.
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean scroll(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			WebElement element = itr2.getWebElement(driver, screenName,
					ObjectName);
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to edit the deal group
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean editDealGroup(WebDriver driver, String screenName,
			String ObjectName, String value) {
		boolean flag = false;
		try {

			List<WebElement> listele = itr2.getWebElements(driver, screenName,
					ObjectName);
			// Iterates the list values
			for (int i = 1; i < listele.size(); i++) {
				String sEle = listele.get(i).getText().trim();
				if (sEle.equals(value.toUpperCase().trim())) {
					System.out.println(sEle);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, sEle);
					WebElement oDealList = driver.findElement(By
							.xpath(Constants.oDeal1 + (i + 1)
									+ Constants.oDeal2));
					List<WebElement> oDealLists = oDealList.findElements(By
							.tagName("li"));
					int dealSize = oDealLists.size();
					System.out.println(dealSize);
					driver.findElement(
							By.xpath(Constants.oDeal1 + (i + 1)
									+ Constants.oDeal3)).click();
					flag = true;

				} else {
					System.out.println(sEle);
				}

			}
			if (flag == true) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, " Value found : " + value);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, " value not found : " + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to sort the deal permission
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean permissionSort(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			int j = 0;
			String[] objSplit = ObjectName.split(";");
			String object0 = objSplit[0];
			String object1 = objSplit[1];
			String object2 = objSplit[2];
			String object3 = objSplit[3];
			String object4 = objSplit[4];
			String object5 = objSplit[5];
			List<String> sElement = new ArrayList<String>();
			boolean flag = false;
			WebElement oPageLink = itr2.getWebElement(driver, screenName,
					object1);
			List<WebElement> oLinks = oPageLink.findElements(By.tagName("a"));
			// Iterates the list
			for (WebElement oNo : oLinks) {
				oNo.click();
				int i = 0;
				List<WebElement> trList = itr2.getWebElements(driver,
						screenName, object0);
				for (WebElement trEle : trList) {
					if (i == 0) {
						i++;
						continue;
					}
					List<WebElement> tdListElements = trEle.findElements(By
							.tagName("td"));
					for (WebElement tdEle : tdListElements) {
						if (tdEle.getText().equals(value)) {
							tdEle.findElement(By.tagName("a")).click();
							flag = true;
						}
					}
					if (flag == true) {
						break;
					}
				}
			}
			List<WebElement> elements = itr2.getWebElements(driver, screenName,
					object2);
			// Iterates the list values
			for (int k = 1; k < elements.size(); k++) {
				elements.get(k).click();
				do {
					List<WebElement> thList = itr2.getWebElements(driver,
							screenName, object3);
					for (WebElement th : thList) {
						th.click();
						Thread.sleep(2000);
						break;
					}
					WebElement oTable = itr2.getWebElement(driver, screenName,
							object4);
					List<WebElement> tableList = oTable.findElements(By
							.tagName("tr"));
					// Iterates the list values
					for (int i = 2; i <= tableList.size(); i++) {
						WebElement oEle = driver.findElement(By.xpath(object5
								+ i + "]/td"));
						sElement.add(oEle.getText().toLowerCase());
					}
					// checks the list values is ascending or descending
					if (j == 0) {
						checkDescending(sElement);
					} else {
						checkAscending(sElement);
					}
					j++;
				} while (j < 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to check the String values are in descending
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static void checkDescending(List<String> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).toLowerCase()
					.compareTo(list.get(i + 1).toLowerCase()) < 0) {
				System.out.println("the List is not in descending order");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "the List is not in descending order");
			}
		}
		System.out.println("the List is in descending order");
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
				"the List is in descending order");
	}

	/**
	 * Method to check the Permission of given user
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public boolean selectPermissionType(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			List<WebElement> elements = driver.findElements(By
					.xpath("//*[@role='presentation']"));
			// Iterates the list values
			for (int k = 1; k < elements.size(); k++) {
				if (elements.get(k).getText().equals(value)) {
					// clicks the respective element
					elements.get(k).click();
					break;

				} else {
					continue;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function for verifying the Select status of the control
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifySelectStatus(WebElement element, String value) {
		try {

			if (value.equals("Select")) {
				if (element.isSelected() == true) {
					System.out.println("The Control " + " is selected");

				} else {
					element.click();
					System.out.println("The Control " + " is now selected");
				}
			} else if (value.equals("UnSelect")) {
				if (element.isSelected() == true) {
					element.click();
					System.out.println("The Control " + " is now unselected");

				} else {

					System.out.println("The Control " + " is  unselected");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * Function for verifying the link in the table
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyTableText(WebDriver driver, String screen,
			String object, String value) throws InterruptedException {
		List<WebElement> trList = itr2.getWebElements(driver, screen, object);
		int j = 0;
		// iterating the table rows
		for (int a = 0; a < trList.size(); a++) {
			if (a == 0 || a == 1) {
				continue;
			}
			List<WebElement> tdList = driver.findElements(By
					.xpath("//table[@class='table']/tbody/tr[" + a + "]/td"));

			String user = "";
			boolean flag = false;
			boolean nonadminflag = false;
			// iterating the table data Elements
			for (int i = 0; i <= tdList.size(); i++) {
				if (i == 0) {
					continue;
				}
				WebElement tdEle = driver.findElement(By
						.xpath("//table[@class='table']/tbody/tr[" + a
								+ "]/td[" + i + "]"));
				// to get the username from the grid
				if (i == 2) {
					user = tdEle.getText();
				}
				// to check the admin checkbox is selected or not in the table
				// row
				if (i == 7) {
					if (!tdEle.findElement(By.tagName("input")).isSelected()) {
						flag = true;
					} else {
						nonadminflag = true;
					}
				}
				// checking for the case when the admin check box is selected
				if (i == 8 && flag == true) {
					// if the td element is a link
					if (tdEle.findElement(By.tagName("a")).isDisplayed()) {
						// the link is clicked
						tdEle.findElement(By.tagName("a")).click();
						Thread.sleep(5000);

						boolean flag2 = itr2
								.verifyExists(driver, "id", "fname");
						// if the Detail link is clicked the page is redirected
						// to the itemized permissioning
						if (flag2 == true) {
							System.out
									.println("Expected : Redirected to itemized permissioning when Details link is clicked for user "
											+ user);
							TestCase.takeScreenShot(
									com.qfor.driverscript.DriverScriptRef.Report,
									new ScreenShotUtils(), driver,
									Environment.Path_Screenshots, "", "", true);
							com.qfor.driverscript.DriverScriptRef.Report
									.log(LogStatus.PASS,
											"Expected : Redirected to itemized permissioning when Details link is clicked for user "
													+ user + "");
							driver.navigate().back();
							Thread.sleep(5000);
							j++;
							break;
						}

						else {
							System.out
									.println("Not Redirected to itemized permissioning when Details link is present for user "
											+ user);
							com.qfor.driverscript.DriverScriptRef.Report
									.log(LogStatus.FAIL,
											"Not Redirected to itemized permissioning when Details link is present for user "
													+ user);
							driver.navigate().back();
							Thread.sleep(5000);
							j++;
							break;
						}
					}
				}
				// checking for the case when the admin check box is not
				// selected
				if (i == 8 && nonadminflag == true) {
					try {
						// if the Detail link is clicked the page is redirected
						// to the itemized permissioning page
						if (tdEle.findElement(By.tagName("a")).isDisplayed()) {
							System.out
									.println("Details link is present for admin user "
											+ user);
							j++;
							break;
						}
					} catch (Exception e) {
						// if the Detail link is clicked the page should not
						// redirected to the itemized permissioning page
						System.out
								.println("Expected : No Details link is present for admin user "
										+ user);
						j++;
						break;
					}
				}

			}
			if (j == 2) {
				break;
			}

		}
		return true;
	}

	/**
	 * Function to handle Alert if anything occurs
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean handleAlertPresent(WebDriver driver, String screen,
			String object, String value) throws InterruptedException {
		try {

			// if value is Accept
			if (value.equals(Constants.iAccept)) {
				Thread.sleep(5000);
				TestCase.takeScreenShotRobot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);
				// the alert is accepted
				Alert alert = driver.switchTo().alert();
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, alert.getText());
				TestCase.takeScreenShotRobot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);

				alert.accept();
				System.out.println("Accepted alert");
			} else if (value.equals("Dismiss")) {
				// the alert is not accepted
				Alert alert = driver.switchTo().alert();
				alert.dismiss();
				System.out.println("Accepted Dismissed");
			}
		} catch (NoAlertPresentException ex) {
			System.out.println("No alert present");
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

	public static boolean checkColor(WebDriver driver, String screen,
			String object, String value) {
		WebElement ele = itr2.getWebElement(driver, screen, object);
		// to get the color
		String color = ele.getAttribute("class");
		// if color matches print the success message
		if (color.equals(value)) {
			System.out.println("Element color " + color + " matches ");
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Element color " + color + " matches ");
		} else {
			System.out.println("Element color " + color + " does not matches ");
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"Element color " + color + " does not matches ");
		}
		return true;
	}

	/**
	 * Function to check the default value in Edit Due Dates
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean editDueDateValidations(WebDriver driver,
			String screen, String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			oElement.click();
			oElement.sendKeys(Keys.BACK_SPACE);
			oElement.clear();
			oElement.clear();
			oElement.click();
			oElement.sendKeys(value);
			TestCase.takeScreenShot(
					com.qfor.driverscript.DriverScriptRef.Report,
					new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Value changes to : " + value);
			oElement.sendKeys(Keys.TAB);
			System.out.println(value);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Value changes to 1");
			TestCase.takeScreenShot(
					com.qfor.driverscript.DriverScriptRef.Report,
					new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			Thread.sleep(3000);
			return true;
		} catch (Exception e) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"Value changes to 1");
			return false;
		}
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

	public static List<String> dueDates(WebDriver driver, String screen,
			String object) {
		int k = 0;
		WebElement oList = null;
		List<String> sList = new ArrayList<String>();
		try {
			for (int i = 2; i <= 4; i++) {
				if (k == 0) {
					oList = driver.findElement(By.xpath(Constants.oDueDates1
							+ i + Constants.oDueDates2));
					k++;
				} else {
					oList = driver.findElement(By.xpath(Constants.oDueDates1
							+ i + Constants.oDueDates2));
				}
				sList.add(oList.getAttribute("value"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sList;
	}

	/**
	 * Function to check the Holiday Calendar
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkHolidayCalendar(WebDriver driver, String screen,
			String object, String value) throws InterruptedException,
			IOException {
		WebElement element = null;
		String[] valsplit = value.split(";");
		String value0 = valsplit[0];
		String value1 = valsplit[1];
		String value2 = valsplit[2];
		try {
			element = itr2.getWebElement(driver, screen, object);
			// if the update Holiday calendar is present then delete the Holiday
			// Calendar from DB
			if (element.getText().trim().equals(value0)) {
				// method to execute the truncate query
				createDB(driver, screen, value1, value2);
				Thread.sleep(10000);
				webPageRefresh(driver, screen, object, value);

			} else {
				System.out.println("Upload the Holiday Calendar");
			}
		} catch (Exception e) {
			// else upload the Holiday Calendar file
			System.out.println("Upload a Holiday Calendar is dispalyed");
			itr2.getWebElement(driver, screen, "UploadHolidayCalendar").click();
			Thread.sleep(10000);
			String path = new File("TestData/" + "SampleHolidayCalendar.csv")
					.getAbsolutePath();
			//String[] cmdArray = { "TestData/AutoItFileUpload.exe", path }; 
			String[] cmdArray = { "TestData/FileuploadChrome.exe", path };
			// to execute the AutoIt exe file
			Runtime.getRuntime().exec(cmdArray);
		}
		return true;
	}

	/**
	 * Function to check the Element present or Not
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkElement(WebDriver driver, String screen,
			String object, String value) {
		try {
			// finding the element in the page
			WebElement element = itr2.getWebElement(driver, screen, object);
			System.out.println(element.getText());
			// if present
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					value + " is Displayed");
			System.out.println(value + " is Displayed");
		} catch (Exception e) {
			// if not present
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Expected : " + value + " is not Displayed");
			System.out.println("Expected : " + value + " is not Displayed");
		}

		return true;

	}

	/**
	 * Function to click the header for sorting
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean headerOrder(WebDriver driver, String screen,
			String object, String value) {
		try {
			if (value.equals("0")) {
				driver.findElement(By.xpath(Constants.oMUHeader)).click();
			} else {
				driver.findElement(
						By.xpath(Constants.oMUHeader1 + value
								+ Constants.oMUHeader2)).click();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the sorting order of Manage Users
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean manageUserSort(WebDriver driver, String screen,
			String object, String value) {
		List<String> headerList = new ArrayList<String>();
		List<String> sStringList = new ArrayList<String>();
		WebElement oElement = null;
		try {
			oElement = itr2.getWebElement(driver, screen, object);
			List<WebElement> oListEle1 = oElement
					.findElements(By.tagName("tr"));
			for (int k = 1; k < oListEle1.size(); k++) {
				if (oListEle1.get(k).findElement(By.tagName("td")).getText()
						.equals("")) {
					continue;
				}
				headerList.add(oListEle1.get(k).findElement(By.tagName("td"))
						.getText().toLowerCase());
				Set<String> set = new HashSet<String>(headerList);
				sStringList = new ArrayList<String>(set);
			}
			if (value.equals("0")) {
				checkAscending(sStringList);
			} else {
				checkDescending(sStringList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to get the values from the grid and check the sorting
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean gridViewSortforYourTasks(WebDriver driver,
			String screenName, String ObjectName, String value) {
		// variable declaration
		String sortValue = null;
		String sortString = null;
		List<Date> stringDate = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.Date2, Locale.US);
		WebElement oGrid = driver.findElement(By.id(Constants.otableViewList));
		List<WebElement> oListGrid = oGrid.findElements(By
				.xpath(Constants.oYourTaskListElements));
		try {
			// Iterates the row value
			for (int row = 1; row < oListGrid.size(); row++) {
				if (row == 1) {
					sortValue = driver.findElement(
							By.xpath(Constants.oYourTaskList1)).getText();
					if (sortValue.contains(value)) {
						sortString = driver.findElement(
								By.xpath(Constants.oYourTaskList2)).getText();
						stringDate.add(sdf.parse(sortString));
					} else {
						continue;
					}

				} else
					sortValue = driver.findElement(
							By.xpath(Constants.oYourTaskList3 + row
									+ Constants.oGridView1)).getText();
				if (sortValue.contains(value)) {
					sortString = driver.findElement(
							By.xpath(Constants.oYourTaskList3 + row
									+ Constants.oYourTaskList4)).getText();
					stringDate.add(sdf.parse(sortString));
				} else {
					continue;
				}
			}

			checkAscendingDate(stringDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the Holiday Calendar Table
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyHolidayCalendarTable(WebDriver driver,
			String screen, String object, String value) {

		List<WebElement> tableList = itr2
				.getWebElements(driver, screen, object);
		@SuppressWarnings("unused")
		int i = 0;
		// iterating the table row
		for (WebElement table : tableList) {
			List<WebElement> divList = table.findElements(By.tagName("div"));
			// iterating the table data
			for (WebElement ele : divList) {
				String str = null;
				str = ele.getText();
				// if the value matches with expected
				if (value.contains(str)) {
					com.qfor.driverscript.DriverScriptRef.Report
							.log(LogStatus.PASS, str
									+ " Header is present in Table");
				} else {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL, str
									+ " Header is not present in Table");
				}

			}
			i++;
		}
		return true;
	}

	/**
	 * Method to get the values from the grid and check the sorting
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean gridViewSort(WebDriver driver, String screenName,
			String ObjectName, String value) {
		String row1 = null;
		String otherrow = null;
		WebElement sortList = null;
		List<String> stringSortList = new ArrayList<String>();
		WebElement oGrid = driver.findElement(By.id("tableViewList"));
		List<WebElement> oListGrid = oGrid.findElements(By
				.xpath("//div[@id='tableViewList']/div/div"));
		try {
			// Iterates the row value
			for (int row = 1; row < oListGrid.size(); row++) {
				if (row == 1) {
					row1 = "/div";
				} else {
					otherrow = "[" + row + "]";
				}
				if (row == 1) {
					sortList = driver.findElement(By
							.xpath("//div[@id='tableViewList']/div" + row1
									+ value));
				} else {
					sortList = driver.findElement(By
							.xpath("//div[@id='tableViewList']/div/div"
									+ otherrow + value));
				}
				stringSortList.add(sortList.getText().toLowerCase());
				System.out.println(sortList.getText());

			}
			System.out.println(stringSortList);
			// checks the list of values is ascending
			checkAscending(stringSortList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to check the alignment of text
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean textAlign(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screenName,
					ObjectName);
			// Read text-align property and print It In console.
			String fonttxtAlign = oElement.getCssValue("text-align");
			System.out.println("Font Text Alignment -> " + fonttxtAlign);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Font Text Alignment -> " + fonttxtAlign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to select the Deal Groups in the input sheet
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public boolean selectDealGroups(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			// Variable Declaration
			String[] valueArray = value.split(";");
			int vArray = valueArray.length;

			List<WebElement> elements = driver.findElements(By
					.xpath("//*[@role='presentation']"));
			// Iterates the list values
			for (int k = 1; k < elements.size(); k++) {
				if (elements.get(k).getText().equals("Deal Groups")) {
					// clicks the respective element
					elements.get(k).click();
					List<WebElement> trList = itr2.getWebElements(driver,
							screenName, ObjectName);
					// Iterates the tr list
					for (WebElement tr : trList) {
						if (tr.getText().equals(newPassword)) {
							com.qfor.driverscript.DriverScriptRef.Report.log(
									LogStatus.PASS,
									"Added New Group has found in grid"
											+ newPassword);
						} else {
							com.qfor.driverscript.DriverScriptRef.Report.log(
									LogStatus.FAIL,
									"Added New Group has not found in grid"
											+ newPassword);
						}
					}
					WebElement oTable = driver.findElement(By
							.xpath(Constants.oPermissionHeader));
					List<WebElement> tableList = oTable.findElements(By
							.tagName("tr"));
					// Iterates the list values
					for (int j = 2; j <= tableList.size() - 1; j++) {
						WebElement oEle = driver.findElement(By
								.xpath("//tr[5]/td"));
						String sType = oEle.getText();
						for (int i = 0; i < vArray; i++) {
							String sValue = valueArray[i];
							if (sType.equals(sValue)) {
								driver.findElement(
										By.xpath("//tr[5]/td[" + j + "]/input"))
										.click();
							}

						}

					}
				} else {
					continue;
				}
				WebElement oElement = itr2.getWebElement(driver, screenName,
						ObjectName);
				oElement.click();
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to search the given text from the input sheet
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean searchText(WebDriver driver, String screen,
			String object, String value) {
		String searchTxt = "";
		List<WebElement> serachList = itr2.getWebElements(driver, screen,
				object);
		for (WebElement searchEle : serachList) {
			searchTxt = searchEle.getText();
			if (searchTxt.toLowerCase().contains(value.toLowerCase())) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, searchTxt + " contains " + value);
				System.out.println(searchTxt + " contains " + value);
				break;
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, searchTxt + " does not contains "
								+ value);
				System.out.println(searchTxt + " does not contains " + value);
			}

		}
		return true;
	}

	/**
	 * Function to search and return the required tr element
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static WebElement returnTrElement(WebDriver driver, String screen,
			String object, String value) {
		WebElement trElement = null;
		boolean flag = false;
		List<WebElement> trListElements = itr2.getWebElements(driver, screen,
				object);
		@SuppressWarnings("unused")
		int i = 0;
		for (WebElement trEle : trListElements) {
			List<WebElement> tdListElements = trEle.findElements(By
					.tagName("td"));
			for (WebElement tdEle : tdListElements) {
				if (tdEle.getText().equals(value)) {
					trElement = trEle;
					flag = true;
					break;
				}
			}
			if (flag == true) {
				break;
			}
			i++;
		}

		return trElement;
	}

	/**
	 * Function to click the required user in Manage Users page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean manageUsersClick(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			boolean flag = false;
			WebElement oPageLink = driver.findElement(By
					.xpath(Constants.oPaginationContainer));
			List<WebElement> oLinks = oPageLink.findElements(By.tagName("a"));
			// Iterates the list
			for (WebElement oNo : oLinks) {
				if (flag == true) {
					break;
				}
				oNo.click();
				int i = 0;
				List<WebElement> trList = itr2.getWebElements(driver,
						screenName, ObjectName);
				for (WebElement trEle : trList) {
					if (i == 0) {
						i++;
						continue;
					}
					List<WebElement> tdListElements = trEle.findElements(By
							.tagName("td"));
					for (WebElement tdEle : tdListElements) {
						if (tdEle.getText().equals(value)) {							
							tdEle.findElement(By.tagName("a")).click();							
							flag = true;
							break;
						}
					}
					if (flag == true) {
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
	 * Reusable function to find the text in grid
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean findTextInGridforPagination(WebDriver driver,
			String screenName, String ObjectName, String value, String no)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		boolean flag = false;
		List<WebElement> trList = itr2.getWebElements(driver, screenName,
				ObjectName);
		int i = 0;
		for (WebElement trEle : trList) {

			if (i == 0) {
				i++;
				continue;
			}
			List<WebElement> tdListElements = trEle.findElements(By
					.tagName("td"));
			for (WebElement tdEle : tdListElements) {
				if (tdEle.getText().equals(value)) {
					// tdEle.findElement(By.tagName("a")).click();
					flag = true;
					break;
				}
			}
			if (flag == true) {
				break;
			}
		}
		// condition to check the expected value is found or not
		if (flag == true) {
			System.out.println("The expected value  found in the grid : "
					+ value);
		} else {
			System.out.println("The expected value not found in the grid"
					+ value);
		}
		return flag;
	}

	/**
	 * Function to click the required deal given in the Input sheet
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean clickDealValue(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebElement oElement = null;
			oElement = returnTrElement(driver, screen, object, value);
			oElement.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to cancel the uploaded file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean fileUploadCancel(WebDriver driver, String screen,
			String object, String value) {
		try {
			String[] valueArray = value.split(";");
			String value1 = valueArray[0];
			String value3 = valueArray[2];
			String DealLevel = "";
			String path = new File("TestData/SubmitFiles/" + value3)
					.getAbsolutePath();
			readProperty(driver);
			if (value3.contains("Deal")) {
				DealLevel = path;
				//String[] cmdArray = { "TestData/AutoItFileUpload.exe", DealLevel };
				String[] cmdArray = { "TestData/FileuploadChrome.exe", DealLevel };
				// to execute the AutoIt exe file
				Runtime.getRuntime().exec(cmdArray);
				WebDriverWait wait = new WebDriverWait(driver,
						Integer.parseInt(value1));
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.className(Constants.oSuccessMessage)));
				WebElement oSuccessMsg = driver.findElement(By
						.className(Constants.oSuccessMessage));
				String sSuccessMsg = oSuccessMsg.getText();
				TestCase.takeScreenShot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, sSuccessMsg);
			} else if (value3.contains("Asset")) {
				DealLevel = path;
				String[] cmdArray = { "TestData/FileUploadCancelExe.exe",
						DealLevel };
				// to execute the AutoIt exe file
				Runtime.getRuntime().exec(cmdArray);
			}

		} catch (Exception e) {

		}
		return true;
	}

	/**
	 * Function to upload the file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean fileUploadSubmit(WebDriver driver, String screen,
			String object, String value) {

		String[] valueArray = value.split(";");
		String value1 = valueArray[0];
		String value2 = valueArray[1];
		String value3 = valueArray[2];
		String DealLevel = "";
		String path = new File("TestData/SubmitFiles/" + value3)
				.getAbsolutePath();
		readProperty(driver);
		if (value3.contains("Deal")) {
			DealLevel = path;
		} else if (value3.contains("Asset")) {
			DealLevel = path;
		}
		try {
			Thread.sleep(2000);
			//String[] cmdArray = { "TestData/AutoItFileUpload.exe", DealLevel };  
			String[] cmdArray = { "TestData/FileuploadChrome.exe", DealLevel };
			// to execute the AutoIt exe file
			Runtime.getRuntime().exec(cmdArray);
			
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(value1));
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.className(Constants.oSuccessMessage)));
			WebElement oSuccessMsg = driver.findElement(By
					.className(Constants.oSuccessMessage));
			String sSuccessMsg = oSuccessMsg.getText();
			TestCase.takeScreenShot(
					com.qfor.driverscript.DriverScriptRef.Report,
					new ScreenShotUtils(), driver,
					Environment.Path_Screenshots, "", "", true);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					sSuccessMsg);
			// Storing the header value
			WebElement oElement = driver.findElement(By
					.xpath(Constants.oSubmitFilesHeader));
			sDealValue = oElement.getText();
			WebElement ele = itr2.getWebElement(driver, "Deals",
					"closeSuccessMsg");
			ele.click();
			if (value3.contains("Deal")) {
				// Verification of Deal level file upload
				WebElement oDealElement = driver.findElement(By
						.xpath("//div[3]/div"));
				String sDealElement = oDealElement.getText();
				System.out.println(sDealElement);
				TestCase.takeScreenShot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);
				com.qfor.driverscript.DriverScriptRef.Report
						.log(LogStatus.PASS, "Deal Level File Upload"
								+ sDealElement);
			} else {
				// Verification of Asset level file upload
				WebElement oDealElement = driver.findElement(By
						.xpath("//div[2]/div/div[3]/div"));
				String sDealElement = oDealElement.getText();
				System.out.println(sDealElement);
				TestCase.takeScreenShot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Asset Level File Upload"
								+ sDealElement);
			}
			// Verifies the success Message
			WebElement oSuccess = driver
					.findElement(By.className("successMsg"));
			String sSuccess = oSuccess.getText();
			if (sSuccess.contains(value2)) {
				System.out.println("Validation message is displayed");
				TestCase.takeScreenShot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Validation message is displayed",
						sSuccess);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the validation for Invalid file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean submitFileInvalid(WebDriver driver, String screen,
			String object, String value) {
		try {
			String DealLevel = "";
			String path = new File("TestData/SubmitFiles/" + value)
					.getAbsolutePath();
			if (value.contains("Deal")) {
				DealLevel = path;
			} else if (value.contains("Asset")) {
				DealLevel = path;
			} else {
				DealLevel = DealLevelInvalid;
			}
			//String[] cmdArray = { "TestData/AutoItFileUploadExe.exe", DealLevel };
			String[] cmdArray = { "TestData/FileuploadChrome.exe", DealLevel };
			// to execute the AutoIt exe file
			Runtime.getRuntime().exec(cmdArray);
			Thread.sleep(10000);
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			String sElement = oElement.getText();
			if (sElement.contains("Please upload csv file")) {
				System.out.println("Only csv is supported file" + " & "
						+ sElement);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Only csv is supported file : "
								+ sElement);
			} else if (sElement
					.contains("Invalid format - Please review and re-submit")) {
				System.out.println(sElement);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "File is in Invalid format : "
								+ sElement);
			} else {
				System.out.println(sElement);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, sElement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the values in Deal Level data
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyDealLevelData(WebDriver driver, String screen,
			String object, String value) {
		try {
			// Verification of Deal Level Data
			WebElement oDealLevel = itr2.getWebElement(driver, screen, object);
			if (oDealLevel.getAttribute("class").contains(value)) {
				System.out.println("Deal Level Data is successfully uploaded");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"Deal Level Data is successfully uploaded");
			} else {
				System.out.println("Please upload Deal Level Data");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "Please upload Deal Level Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the values in Asset Level data
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyAssetLevelData(WebDriver driver, String screen,
			String object, String value) {
		try {
			// Verification of Asset Level Data
			WebElement oDealLevel = itr2.getWebElement(driver, screen, object);
			if (oDealLevel.getAttribute("class").contains(value)) {
				System.out.println("Asset Level Data is uploaded");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Asset Level Data is uploaded");
			} else {
				System.out.println("Please upload Asset Level Data");
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "Please upload Asset Level Data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to verify the validations in Submit files page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean submitFilesVerify(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(value));
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.className(Constants.oSuccessMessage)));
			WebElement oElement = driver.findElement(By
					.xpath(Constants.oSubmitFiles1));
			@SuppressWarnings("unused")
			List<WebElement> oHeader = oElement.findElements(By.tagName("h3"));
			WebElement oElement1 = driver.findElement(By
					.xpath(Constants.oSubmitFiles2));
			String sElement = oElement1.getText();
			System.out.println(sElement);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					sElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to give the different roles for given user in Input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean manageUsersAdminRole(WebDriver driver, String screen,
			String object, String value) {
		String strArray[] = value.split(",");
		WebElement trElement = returnTrElement(driver, screen, object,
				strArray[0]);
		List<WebElement> tdListElements = trElement.findElements(By
				.tagName("td"));
		for (int j = 0; j < tdListElements.size(); j++) {
			if (j == 1 || j == 7) {
				if (tdListElements.get(j).findElement(By.tagName("a"))
						.getTagName().equals("a")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, tdListElements.get(j).getText()
									+ " is a link");
				}

			}
			if (j == 2) {
				verifySelectStatus(
						tdListElements.get(j).findElement(By.tagName("input")),
						strArray[1]);
				if (tdListElements.get(j).findElement(By.tagName("input"))
						.getAttribute("type").equals("checkbox")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS,
							"The control Submitter is : "
									+ tdListElements.get(j)
											.findElement(By.tagName("input"))
											.getAttribute("type"));
				}
			}
			if (j == 3) {
				verifySelectStatus(
						tdListElements.get(j).findElement(By.tagName("input")),
						strArray[1]);
				if (tdListElements.get(j).findElement(By.tagName("input"))
						.getAttribute("type").equals("checkbox")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS,
							"The control Reviewer is : "
									+ tdListElements.get(j)
											.findElement(By.tagName("input"))
											.getAttribute("type"));
				}
			}
			if (j == 4) {
				verifySelectStatus(
						tdListElements.get(j).findElement(By.tagName("input")),
						strArray[1]);
				if (tdListElements.get(j).findElement(By.tagName("input"))
						.getAttribute("type").equals("checkbox")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS,
							"The control Approver is : "
									+ tdListElements.get(j)
											.findElement(By.tagName("input"))
											.getAttribute("type"));
				}
			}
			if (j == 5) {
				String control = tdListElements.get(j)
						.findElement(By.xpath(Constants.oAdminRole1))
						.getAttribute("type");
				if (control.equals("radio")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "The control Internal is : "
									+ control);
				}
				String control1 = tdListElements.get(j)
						.findElement(By.xpath(Constants.oAdminRole2))
						.getAttribute("type");
				if (control1.equals("radio")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "The control External is : "
									+ control1);
				}
			}
			if (j == 6) {
				verifySelectStatus(
						tdListElements.get(j).findElement(By.tagName("input")),
						strArray[2]);
				if (tdListElements.get(j).findElement(By.tagName("input"))
						.getAttribute("type").equals("checkbox")) {
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS,
							"The control Administrator is: "
									+ tdListElements.get(j)
											.findElement(By.tagName("input"))
											.getAttribute("type"));
				}
			}

		}
		return true;
	}

	/**
	 * Method to check the web Elements list ascending
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkAscendingForList(WebDriver driver,
			String screen, String object, String value) {
		List<String> list = new ArrayList<>();
		List<WebElement> listEle = itr2.getWebElements(driver, screen, object);
		int i = 0;
		for (WebElement element : listEle) {
			if (object.equals("SelectedFieldsList")) {
				if (i == 0) {
					continue;
				}
				list.add(element.getText());
			} else {
				list.add(element.getText());
			}
			i++;
		}
		checkAscending(list);
		return true;
	}

	/**
	 * Method to check the active status for the control
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkActiveStatusForControl(WebDriver driver,
			String screen, String object, String value) {
		WebElement ele = itr2.getWebElement(driver, screen, object);
		String active = ele.getAttribute("class");
		if (active.contains("active")) {

			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The " + object + " is active and the Default Value is : "
							+ ele.getText());
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"The " + object
							+ " is not active and the Default Value is : "
							+ ele.getText());

		}

		return true;
	}

	/**
	 * Method to get Default Value of the control
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getDefaultvalue(WebDriver driver, String screen,
			String object, String value) {
		WebElement ele = itr2.getWebElement(driver, screen, object);
		String defaultValue = new Select(ele).getFirstSelectedOption()
				.getText();
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS, "The "
				+ object + " has " + defaultValue + " as Defalut Value");

		return true;
	}

	/**
	 * Method to check the given element is clickable or not
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkClickableArea(WebDriver driver, String screen,
			String object, String value) {
		WebElement trElement = returnTrElement(driver, screen, object, value);
		trElement.findElements(By.tagName("td")).get(0).click();

		return true;

	}

	/**
	 * Function to get the list of Date values
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static List<String> getDateList(WebDriver driver, String screen,
			String object, String value) throws ParseException {
		List<String> dateList = new ArrayList<>();
		List<WebElement> dateEleList = itr2.getWebElements(driver, screen,
				object);
		// SimpleDateFormat sdf = new SimpleDateFormat(value, Locale.US);
		for (WebElement dateEle : dateEleList) {
			try {
				if ((dateEle.getText().equals("")) || (dateEle.getText().equals("n/a"))) {
					continue;
				}
				dateList.add(dateEle.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return dateList;

	}

	/**
	 * Function to check the given list of date values are descending
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkDescendingInAudit(WebDriver driver,
			String screen, String object, String value) throws ParseException {
		List<String> dateList = getDateList(driver, screen, object, value);
		checkDescending(dateList);
		return true;

	}

	/**
	 * Function to check the given list of date values are descending
	 * 
	 * @param list
	 *            of date values
	 */

	public static boolean checkDescendingDate(List<Date> list) {

		try {
			for (int i = 0; i < list.size() - 1; i++) {
				if (list.get(i).compareTo(list.get(i + 1)) < 0) {

					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.FAIL,
							"the Date List is not in descending order "
									+ list.get(i));
				}
			}
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"the Date List is in descending order" + "");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check ascending and descending for string values
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkAscendingAndDescendingForString(
			WebDriver driver, String screen, String object, String value) {
		List<WebElement> list = itr2.getWebElements(driver, screen, object);

		List<String> stringList = new ArrayList<>();
		for (WebElement ele : list) {
			if (ele.getText().equals("")) {
				continue;
			}
			stringList.add(ele.getText().toLowerCase());
		}

		if (value.equals("Ascending")) {
			checkAscending(stringList);
		} else if (value.equals("Descending")) {
			checkDescending(stringList);
		} else {
			System.out
					.println("Please mention Ascending or Descending Keyword in Input");
		}

		return true;

	}

	/**
	 * Function to scroll until the element is present
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean scrollToViewElement(WebDriver driver, String screen,
			String object, String value) {
		WebElement element = itr2.getWebElement(driver, screen, object);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", element);
		return true;
	}

	/**
	 * Function to call the AutoIT code for sending the email
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean mailSend(WebDriver driver, String screen,
			String object, String value) {
		try {
			readProperty(driver);
			@SuppressWarnings("unused")
			Process p;
			String cmdArray = "TestData/MailTo.exe";
			p = Runtime.getRuntime().exec(cmdArray);
			System.out.println("Mail Sent Successfully");
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Mail Sent Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * Function to execute the autoit code and save the exported file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean exportToExcel(WebDriver driver, String screen,
			String object, String value) {
		String eBrowser = Environment.Execution_Browser;
		if(eBrowser.equalsIgnoreCase("Firefox")) {
			try {
				Thread.sleep(5000);
				readProperty(driver);
				Process p;
				if (value.equals("Excel")) {
					String[] cmdArray = { "TestData/ExportToExcel.exe",
							openwithWindow, DesktopPath, DownloadPath };
					p = Runtime.getRuntime().exec(cmdArray);
				} else {
					openwithWindow = "true";				
					String[] cmdArray = { "TestData/XMLOpen.exe", openwithWindow,
						DesktopPath, DownloadPath };				
					p = Runtime.getRuntime().exec(cmdArray);
				}
				Scanner scan = new Scanner(p.getInputStream());			
				openwithWindow = "false";
				new BufferedWriter(new OutputStreamWriter(p.getOutputStream())); 			
				Thread.sleep(5000);
				while (scan.hasNextLine()) {
					text_autoit = scan.nextLine();				
				}
				if (text_autoit.contains("' Save  As 'window does not exist")) {
					System.out.println("Save  As window does not exist");
				} else {
	
					fileArr_Autoit = text_autoit.split(";");
					image_saveasWindow = fileArr_Autoit[0];
					String[] splitSaveas = image_saveasWindow.split("\\\\");
					String saveasArray = splitSaveas[5];
					exportExcelFile = fileArr_Autoit[1];
					excelScreenshot = fileArr_Autoit[2];				
					String[] splitExcelss = excelScreenshot.split("\\\\");
					String excelArray = splitExcelss[5];
	
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Exported Excel File Name : "
									+ exportExcelFile);
					File moveExportedfiletoOutputfolder = FileUtils
							.getFile("C:\\Users\\"
									+ System.getProperty("user.name")
									+ "\\Downloads\\" + exportExcelFile);			
					/*File moveExportedfiletoOutputfolder = FileUtils
							.getFile("C:\\Users\\somiseta"
									+ "\\Downloads\\" + exportExcelFile);	*/
					File moveScreenshotstoOutputfolder = FileUtils
							.getFile("C:\\Users\\"
									+ System.getProperty("user.name")
									+ "\\Downloads\\" + excelArray);				
					/*File moveScreenshotstoOutputfolder = FileUtils
							.getFile("C:\\Users\\somiseta"
								   + "\\Downloads\\" + excelArray);*/
					File moveSaveDialogtoOutputfolder = FileUtils
							.getFile(image_saveasWindow);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Export to Excel Sheet Path : "
									+ moveExportedfiletoOutputfolder.getPath());
					NewFileNamePathExportedExcel = (Environment.Path_Screenshots + exportExcelFile);
					ExportToExcelScreenshots = (Environment.Path_Screenshots + excelArray);
					image_ExportedExcelWindow = (Environment.Path_Screenshots + saveasArray);
					try {
						FileUtils.moveFile(moveScreenshotstoOutputfolder, new File(
								ExportToExcelScreenshots));
						FileUtils.moveFile(moveExportedfiletoOutputfolder,
								new File(NewFileNamePathExportedExcel));
						FileUtils.moveFile(moveSaveDialogtoOutputfolder, new File(
								image_ExportedExcelWindow));
	
						com.qfor.driverscript.DriverScriptRef.Report.log(
								LogStatus.PASS, "The Exported Excel file Path : "
										+ Environment.Path_Screenshots);
						AssetRelFilePath = Environment.Path_Screenshots;
					} catch (FileNotFoundException f) {
						com.qfor.driverscript.DriverScriptRef.Report.log(
								LogStatus.FAIL, "Expected sreenshots not found");
					} catch (IOException e) {
						e.printStackTrace();
					}
	
				}
	
				scan.close();
	
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		if(eBrowser.equalsIgnoreCase("Chrome")){
			try {
				File theNewestFile = null; 
				Thread.sleep(5000);
				readProperty(driver);			
			    File dir = new File(DownloadPath);
			    FileFilter fileFilter = new WildcardFileFilter("*.*");
			    File[] files = dir.listFiles(fileFilter);

			    if (files.length > 0) {
			        /** The newest file comes first **/
			        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			        theNewestFile = files[0];
			    }
			    String[] downloadedFile = theNewestFile.toString().split("\\\\");
			    String downloadFile = downloadedFile[4];
			    image_saveasWindow = DesktopPath+"\\Test.png";			   
			    String[] splitSaveas = image_saveasWindow.split("\\\\");			    
			    String saveasArray = splitSaveas[4];				
					
					exportExcelFile = downloadFile;
					excelScreenshot = theNewestFile.toString();						
					String[] splitExcelss = excelScreenshot.split("\\\\");
					String excelArray = splitExcelss[4];					
					
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Exported Excel File Name : "
									+ exportExcelFile);
					File moveExportedfiletoOutputfolder = FileUtils
							.getFile("C:\\Users\\"
									+ System.getProperty("user.name")
									+ "\\Downloads\\" + exportExcelFile);			
					/*File moveExportedfiletoOutputfolder = FileUtils
							.getFile("C:\\Users\\somiseta"
									+ "\\Downloads\\" + exportExcelFile);*/	
					File moveScreenshotstoOutputfolder = FileUtils
							.getFile("C:\\Users\\"
									+ System.getProperty("user.name")
									+ "\\Downloads\\" + excelArray);				
					/*File moveScreenshotstoOutputfolder = FileUtils
							.getFile("C:\\Users\\somiseta"
								   + "\\Downloads\\" + excelArray);*/
					File moveSaveDialogtoOutputfolder = FileUtils
							.getFile(image_saveasWindow);
					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "Export to Excel Sheet Path : "
									+ moveExportedfiletoOutputfolder.getPath());
					NewFileNamePathExportedExcel = (Environment.Path_Screenshots + exportExcelFile);
					ExportToExcelScreenshots = (Environment.Path_Screenshots + excelArray);
					image_ExportedExcelWindow = (Environment.Path_Screenshots + saveasArray);
					try {
						FileUtils.moveFile(moveScreenshotstoOutputfolder, new File(
								ExportToExcelScreenshots));
						FileUtils.moveFile(moveExportedfiletoOutputfolder,
								new File(NewFileNamePathExportedExcel));
						FileUtils.moveFile(moveSaveDialogtoOutputfolder, new File(
								image_ExportedExcelWindow));

						com.qfor.driverscript.DriverScriptRef.Report.log(
								LogStatus.PASS, "The Exported Excel file Path : "
										+ Environment.Path_Screenshots);
						AssetRelFilePath = Environment.Path_Screenshots;				
					} catch (IOException e) {
						e.printStackTrace();
					}	
					files[0].delete();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	/**
	 * Function to download file on chrome and save the exported file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */
	public static boolean exportToExcelChrome(WebDriver driver, String screen,
			String object, String value) {
		try {
			File theNewestFile = null; 
			Thread.sleep(5000);
			readProperty(driver);			
		    File dir = new File(DownloadPath);
		    FileFilter fileFilter = new WildcardFileFilter("*.*");
		    File[] files = dir.listFiles(fileFilter);

		    if (files.length > 0) {
		        /** The newest file comes first **/
		        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		        theNewestFile = files[0];
		    }
		    String[] downloadedFile = theNewestFile.toString().split("\\\\");
		    String downloadFile = downloadedFile[4];
		    image_saveasWindow = DesktopPath+"\\Test.png";
		    System.out.println(" image_saveasWindow ==== "+image_saveasWindow);
		    String[] splitSaveas = image_saveasWindow.split("\\\\");
		    System.out.println(" splitSaveas ==== "+splitSaveas);
		    String saveasArray = splitSaveas[4];				
				
				exportExcelFile = downloadFile;
				excelScreenshot = theNewestFile.toString();		
				System.out.println(" exportExcelFile ==== "+exportExcelFile);
				System.out.println(" excelScreenshot ==== "+excelScreenshot);
				System.out.println(" Environment.Path_Screenshots ==== "+Environment.Path_Screenshots);
				String[] splitExcelss = excelScreenshot.split("\\\\");
				String excelArray = splitExcelss[4];
				System.out.println(" excelArray ==== "+excelArray);
				
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Exported Excel File Name : "
								+ exportExcelFile);
				/*File moveExportedfiletoOutputfolder = FileUtils
						.getFile("C:\\Users\\"
								+ System.getProperty("user.name")
								+ "\\Downloads\\" + exportExcelFile);	*/		
				File moveExportedfiletoOutputfolder = FileUtils
						.getFile("C:\\Users\\somiseta"
								+ "\\Downloads\\" + exportExcelFile);	
				/*File moveScreenshotstoOutputfolder = FileUtils
						.getFile("C:\\Users\\"
								+ System.getProperty("user.name")
								+ "\\Downloads\\" + excelArray);	*/			
				File moveScreenshotstoOutputfolder = FileUtils
						.getFile("C:\\Users\\somiseta"
							   + "\\Downloads\\" + excelArray);
				File moveSaveDialogtoOutputfolder = FileUtils
						.getFile(image_saveasWindow);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Export to Excel Sheet Path : "
								+ moveExportedfiletoOutputfolder.getPath());
				NewFileNamePathExportedExcel = (Environment.Path_Screenshots + exportExcelFile);
				ExportToExcelScreenshots = (Environment.Path_Screenshots + excelArray);
				image_ExportedExcelWindow = (Environment.Path_Screenshots + saveasArray);
				try {
					FileUtils.moveFile(moveScreenshotstoOutputfolder, new File(
							ExportToExcelScreenshots));
					FileUtils.moveFile(moveExportedfiletoOutputfolder,
							new File(NewFileNamePathExportedExcel));
					FileUtils.moveFile(moveSaveDialogtoOutputfolder, new File(
							image_ExportedExcelWindow));

					com.qfor.driverscript.DriverScriptRef.Report.log(
							LogStatus.PASS, "The Exported Excel file Path : "
									+ Environment.Path_Screenshots);
					AssetRelFilePath = Environment.Path_Screenshots;				
				} catch (IOException e) {
					e.printStackTrace();
				}	
				files[0].delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}
	/**
	 * Function to select the Random Available list elements
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean selectRandomAvailableList(WebDriver driver,
			String screen, String object, String value)
			throws InterruptedException {
		String[] objSplit = object.split(";");
		String object0 = objSplit[0];
		String object1 = objSplit[1];
		moveditemList = new ArrayList<>();
		List<WebElement> availableList = itr2.getWebElements(driver, screen,
				object0);
		WebElement source = availableList.get(0);
		itr2.scrollTo(driver, source);
		WebElement oElement = itr2.getWebElement(driver, screen, object1);
		String sElement = oElement.getText();
		moveditemList.add(sElement);
		moveditemList.add(source.getText());
		System.out.println(source.getText());
		source.click();
		Thread.sleep(5000);

		return true;

	}

	/**
	 * Function to verify the String is present in the List
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkSelectedList(WebDriver driver, String screen,
			String object, String value) {
		verifyTextContainsInList(driver, screen, object, moveditemList);
		return true;
	}

	/**
	 * Function to verify the String is present in the List
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyTextContainsInList(WebDriver driver,
			String screen, String object, List<String> list) {
		boolean flag = false;
		boolean stepflag = false;
		List<String> sourceList = new ArrayList<>();
		List<WebElement> sourceEleList = itr2.getWebElements(driver, screen,
				object);
		for (WebElement ele : sourceEleList) {
			if (ele.getText().equals("")) {
				continue;
			}
			if (object.equals(Constants.sGridAssetClassList)) {

				sourceList.add(ele.getText().split("\n")[1].split(":")[1]
						.trim());
				System.out.println(ele.getText().split("\n")[1].split(":")[1]
						.trim());
			} else {
				sourceList.add(ele.getText());
				System.out.println(ele.getText());
			}
		}

		for (int i = 0; i < sourceList.size(); i++) {

			for (int j = 0; j < list.size(); j++) {
				if (sourceList.get(i).equals(list.get(j))) {
					flag = true;
					break;
				} else {
					flag = false;

				}
			}
			if (flag == true) {
				stepflag = true;
			} else {
				stepflag = false;
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL,
						sourceList.get(i)
								+ " is not found in the expected List "
								+ list.toString());
			}

		}
		if (stepflag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(
					LogStatus.PASS,
					"The filtered values is found in the expected List "
							+ list.toString());

		}

		return true;
	}

	/**
	 * Function to get list of date values and check the sorting order
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkAscendingInAudit(WebDriver driver,
			String screen, String object, String value) throws ParseException {
		try {
			List<String> dateList = getDateList(driver, screen, object, value);
			checkAscending(dateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * Function to check the list of dates are in Ascending
	 * 
	 * @param List
	 *            of Dates
	 */

	public static void checkAscendingDate(List<Date> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).compareTo(list.get(i + 1)) > 0) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "the List is not in ascending order "
								+ list.get(i));
			}
		}
		com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
				"the List is in ascending order" + "");
	}

	/**
	 * Function to check the status in Submission History
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean submissionStatus(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebElement oStatus = itr2.getWebElement(driver, screen, object);
			List<WebElement> oListElement = oStatus.findElement(
					By.tagName("div")).findElements(By.tagName("a"));
			for (int i = 1; i < oListElement.size(); i++) {
				if (oListElement.get(i).getText().equals(value)) {
					oListElement.get(i).click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to click the required value given in the input
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean clickTdElement(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebElement oElement = null;

			if (object.equals(Constants.sSubmissionTable)) {
				driver.findElement(By.id(Constants.oTableView)).click();
				Thread.sleep(4000);
				List<WebElement> gridList = itr2.getWebElements(driver, screen,
						object);
				for (WebElement ele : gridList) {
					if (ele.getText().equals(value)) {
						ele.click();
						Thread.sleep(5000);
						com.qfor.driverscript.DriverScriptRef.Report.log(
								LogStatus.PASS, object + " is clicked");
						break;
					}
				}
			} else if (object.equals(Constants.sSubmissionTableDeal1)) {
				driver.findElement(By.id(Constants.oTableView)).click();
				Thread.sleep(4000);
				WebElement element = itr2.getWebElement(driver, screen, object);
				element.click();
				Thread.sleep(5000);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, object + " is clicked");
			} else {
				oElement = returnTrElement(driver, screen, object, value);
				List<WebElement> tdList = oElement.findElements(By
						.tagName("td"));
				tdList.get(0).findElement(By.tagName("a")).click();
				Thread.sleep(5000);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, object + " is clicked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the String is present in the Column List
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyTextInList(WebDriver driver, String screen,
			String object, String value) {
		List<WebElement> list = itr2.getWebElements(driver, screen, object);
		boolean flag = false;
		String webEleText = null;
		for (WebElement ele : list) {
			if (object.equals("GridAssetClass")) {
				webEleText = ele.getText().split("\n")[1].split(":")[1].trim()
						.toLowerCase();
			} else {
				webEleText = ele.getText().trim().toLowerCase();
			}
			if (webEleText.equals(value.toLowerCase())) {
				// if the value is present in the list the flag is set to true
				flag = true;
				break;
			} else {
				// else set as false
				flag = false;
			}
		}
		if (flag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"All the values in the Column " + object + " has " + value);
			if (object.equals("DealGroupNames")) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"The Deal Group div does not have Edit option ");
			}
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"All the values in the Column " + object + " does not has "
							+ value);
		}
		return true;

	}

	/**
	 * Function to update the holiday calendar
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkUpdateHolidayCalendar(WebDriver driver,
			String screen, String object, String value)
			throws InterruptedException, IOException {
		WebElement element = null;
		try {
			element = itr2.getWebElement(driver, screen, object);
			// if the update Holiday calendar is present then delete the Holiday
			// Calendar from DB
			if (element.getText().trim().equals(value)) {
				// method to execute the truncate query
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"Update the Holiday Calendar link is Present");
				itr2.getWebElement(driver, screen, object).click();
				Thread.sleep(10000);
				String path = new File("TestData/"
						+ "UpdatedSampleHolidayCalendar.csv").getAbsolutePath();
				//String[] cmdArray = { "TestData/AutoItFileUpload.exe", path };
				String[] cmdArray = { "TestData/FileuploadChrome.exe", path };
				Runtime.getRuntime().exec(cmdArray);
				Thread.sleep(5000);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"Updated the Holiday Calendar successfully");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to update the invalid type of holiday calendar
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean checkInvalidHolidayFile(WebDriver driver,
			String screen, String object, String value)
			throws InterruptedException {
		try {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Update the Holiday Calendar link is Present");
			itr2.getWebElement(driver, screen, object).click();
			Thread.sleep(10000);
			String path = new File("TestData/" + value).getAbsolutePath();
			//String[] cmdArray = { "TestData/AutoItFileUpload.exe", path };
			String[] cmdArray = { "TestData/FileuploadChrome.exe", path };
			Runtime.getRuntime().exec(cmdArray);
			Thread.sleep(5000);
			handleAlertPresent(driver, screen, object, Constants.iAccept);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to get the number of rows in the table
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getSize(WebDriver driver, String screen,
			String object, String value) {
		List<WebElement> elements = itr2.getWebElements(driver, screen, object);
		int size = elements.size() - 1;
		if (size == Integer.parseInt(value)) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Manage Users Row size : " + size);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"Manage Users Row size : " + size);
		}
		return true;
	}

	/**
	 * Function to verify the status of Check Box control
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyChkBox(WebDriver driver, String screen,
			String object, String value) {
		WebElement element = itr2.getWebElement(driver, screen, object);
		verifySelectStatus(element, value);
		return true;
	}

	/**
	 * Function to click Export to Excel link
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean clickExport(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			oElement.click();
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to trim the first character of stored variable
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean enterStored(WebDriver driver, String screen,
			String object, String value) {
		try {
			Random rand = new Random();
			String valueArray[] = value.split(",");
			int min = Integer.parseInt(valueArray[0]);
			int max = Integer.parseInt(valueArray[1]);
			int randomNum = rand.nextInt((max - min) + 1) + min;
			System.out.println(randomNum);
			driver.findElement(By.id("edit_1")).click();
			driver.findElement(By.id("edit_1")).click();
			Thread.sleep(2000);
			WebElement oEle = driver.findElement(By
					.xpath("//input[@id='txtInvestorReportValue_1']"));
			oEle.clear();
			oEle.sendKeys(String.valueOf(randomNum));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to check the button is disabled or not
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean buttonDisable(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			if (oElement.isEnabled()) {
				TestCase.takeScreenShot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "Clear changes button is enabled");
			} else {
				TestCase.takeScreenShot(
						com.qfor.driverscript.DriverScriptRef.Report,
						new ScreenShotUtils(), driver,
						Environment.Path_Screenshots, "", "", true);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Clear changes button is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to handle browser alert
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean handleBrowserAlert(WebDriver driver, String screen,
			String object, String value) throws InterruptedException {
		try {

			// if value is Accept
			if (value.equals(Constants.iAccept)) {
				Thread.sleep(5000);
				// the alert is accepted
				Alert alert = driver.switchTo().alert();
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, alert.getText());
				alert.accept();
				System.out.println("Accepted alert");
			} else if (value.equals(Constants.iDismiss)) {
				// the alert is not accepted
				Alert alert = driver.switchTo().alert();
				alert.dismiss();
				System.out.println("Accepted Dismissed");
			}
		} catch (NoAlertPresentException ex) {
			System.out.println("No alert present");
		}
		return true;
	}

	/**
	 * Function to verify the filter value in the grid
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyFilter(WebDriver driver, String screen,
			String object, String value) {
		List<WebElement> list = itr2.getWebElements(driver, screen, object);
		boolean flag = false;
		for (WebElement ele : list) {
			if (ele.getText().equals("")) {
				continue;
			}
			if (ele.getText().contains(value)) {
				flag = true;
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, ele.getText()
								+ " does not contains the " + value);
				flag = false;
			}
		}
		if (flag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"All the filtered values contains the value " + value);
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"All the filtered values does not contains the value "
							+ value);
		}

		return true;

	}

	/**
	 * Function to clear text using Keys
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean clearTextUsingKeys(WebDriver driver, String screen,
			String object, String value) {
		try {
			driver.findElement(By.id(Constants.oTxtFilter)).click();
			driver.findElement(By.id(Constants.oTxtFilter)).sendKeys(
					Keys.CONTROL + "a");
			driver.findElement(By.id(Constants.oTxtFilter)).sendKeys(
					Keys.BACK_SPACE);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The filtered values " + value + " got cleared");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to close the popup if appears
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean closePopup(WebDriver driver, String screen,
			String object, String value) {
		String[] objSplit = object.split(";");
		String object0 = objSplit[0];
		String object1 = objSplit[1];
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object0);
			if (oElement.isDisplayed()) {
				oElement.click();
			}
		} catch (Exception e) {
			WebElement oElement = itr2.getWebElement(driver, screen, object1);
			if (oElement.isDisplayed()) {
				oElement.click();
			}
		}
		return true;
	}

	/**
	 * Function to open the csv file and update the given value
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean updateRRCSV(WebDriver driver, String screen,
			String object, String value) throws IOException {
		try {
			File dealFile = new File(NewFileNamePathExportedExcel);
			CSVReader reader = new CSVReader(new FileReader(dealFile), ',');
			List<String[]> csvBody = reader.readAll();
			reader.close();
			int randomPIN = (int) (Math.random() * 9000) + 1000;
			sRandom = String.valueOf(randomPIN);
			System.out.println(sRandom);
			csvBody.get(1)[5] = sRandom;
			CSVWriter writer = new CSVWriter(new FileWriter(dealFile), ',');
			writer.writeAll(csvBody, false);
			writer.flush();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to upload the edited revised file
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean uploadRevisedFile(WebDriver driver, String screen,
			String object, String value) {
		try {
			String sReplace = NewFileNamePathExportedExcel.replace("/", "\\");
			//String[] cmdArray = { "TestData/AutoItFileUpload.exe", sReplace };
			String[] cmdArray = { "TestData/FileuploadChrome.exe", sReplace };
			// to execute the AutoIt exe file
			Runtime.getRuntime().exec(cmdArray);
			Thread.sleep(15000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to compare the two String values
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean compareValue(WebDriver driver, String screen,
			String object, String value) {
		try {
			if (value.equals(sRandom)) {
				System.out.println("Edited and uploaded values are equal");
			} else {
				System.out.println("Edited and uploaded values are not equal");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to get the Comment List and check the Sorting order of the list
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getCommentsList(WebDriver driver, String screen,
			String object, String value) throws ParseException {
		List<WebElement> commentList = itr2.getWebElements(driver, screen,
				object);
		List<Date> dateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.Date2, Locale.US);

		for (WebElement ele : commentList) {
			com.qfor.driverscript.DriverScriptRef.Report.log(
					LogStatus.PASS,
					"Comment UserName : "
							+ ele.getText().split("\n")[0].split(" ")[0]);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Comment Date : "
							+ ele.getText().split("\n")[0].split(" ")[1]);
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"Comments : " + ele.getText().split("\n")[1]);
			dateList.add(sdf.parse(ele.getText().split("\n")[0].split(" ")[1]));

		}
		boolean flag = checkDescendingDate(dateList);
		if (flag == true) {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.PASS,
					"The Latest Comments is on the Top");
		} else {
			com.qfor.driverscript.DriverScriptRef.Report.log(LogStatus.FAIL,
					"The Latest Comments is not on the Top");
		}
		return true;

	}

	/**
	 * Function to verify text present after filtering the value
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyTextSubmissionFilter(WebDriver driver,
			String screen, String object, String value) {
		List<String> list = new ArrayList<>();
		String[] valueArray = value.split(",");
		for (String inputvalue : valueArray) {
			list.add(inputvalue);
		}
		verifyTextContainsInList(driver, screen, object, list);
		return true;
	}

	/**
	 * Function to get the value from the row
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean getValuefromRow(WebDriver driver, String screen,
			String object, String value) {
		try {
			String[] valArray = value.split(";");
			int i = 0;
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			List<WebElement> oListElements = oElement.findElements(By
					.tagName("td"));
			for (WebElement oList : oListElements) {
				String sList = oList.getText();
				System.out.println(sList);
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, valArray[i] + " : " + sList);
				i++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the values in Your Tasks page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyYourTasks(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			List<WebElement> oListElements = oElement.findElements(By
					.tagName("tr"));
			int trSize = oListElements.size() - 1;
			if (trSize == Integer.parseInt(value)) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"Open Task values are equal both in Home and Your Tasks Page : "
								+ trSize);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL,
						"Open Task values are not equal both in Home and Your Tasks Page : "
								+ trSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the Status of Your Tasks page
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean statusYT(WebDriver driver, String screen,
			String object, String value) {
		try {
			List<String> sList = new ArrayList<String>();
			List<String> iList = new ArrayList<String>();
			List<String> sNewList = new ArrayList<String>();
			List<String> iNewList = new ArrayList<String>();
			String[] valArray = value.split(";");
			// Iterating the input value
			for (int i = 0; i < valArray.length; i++) {
				iList.add(valArray[i]);
				Set<String> iSet = new HashSet<String>(iList);
				iNewList = new ArrayList<String>(iSet);
			}
			// Getting the status values from the application to list
			List<WebElement> oElement = driver.findElements(By
					.xpath("//div[@id='listViewList']/table/tbody/tr/td[1]"));
			for (WebElement oList : oElement) {
				sList.add(oList.getText());
				Set<String> oSet = new HashSet<String>(sList);
				sNewList = new ArrayList<String>(oSet);
			}
			if (sNewList.equals(iNewList)) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"Expected status are displayed in Your Tasks page : "
								+ sNewList);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL,
						"Expected status are not displayed in Your Tasks page : "
								+ sNewList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to navigate the back in browser
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean jseClick(WebDriver driver, String screen,
			String object, String value) {
		try {
			WebElement oElement = itr2.getWebElement(driver, screen, object);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", oElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to find the sorting order for Submission History
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean submissionSort(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yy",
					Locale.US);
			List<Date> sList = new ArrayList<Date>();
			WebElement oGrid = driver.findElement(By.id("tableViewList"));
			List<WebElement> oListGrid = oGrid.findElements(By
					.xpath("//div[@id='tableViewList']/div/div"));
			for (WebElement oList : oListGrid) {
				String[] sElement = oList.getText().split("\n");
				String[] sEleTrim = sElement[2].split(":");
				String sTrim = sEleTrim[1].trim();
				sList.add(format.parse(sTrim));
			}
			checkAscendingDate(sList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Function to verify Carousel/Flipper slide section
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifyFlipper(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			int i = 0;
			List<WebElement> oEle = driver.findElements(By
					.xpath("//div[@class='slick-track']/div/a"));
			for (WebElement oList : oEle) {
				if (oList.getAttribute("aria-hidden").equals("false")) {
					i = i + 1;
				}
			}
			if (i == Integer.parseInt(value)) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"The published submissions per slide is : " + i);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL,
						"The published submissions per slide is : " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the count of submissions
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean verifySubmissions(WebDriver driver,
			String screenName, String ObjectName, String value) {
		try {
			String[] objArray = ObjectName.split(";");
			String object1 = objArray[0];
			String object2 = objArray[1];
			String object3 = objArray[2];
			String object4 = objArray[3];
			String object5 = objArray[4];
			String object6 = objArray[5];
			String object7 = objArray[6];
			WebElement oElement1 = itr2.getWebElement(driver, screenName,
					object1);
			String sElement1 = oElement1.getText();
			int iElement1 = Integer.parseInt(sElement1);
			WebElement oElement2 = itr2.getWebElement(driver, screenName,
					object2);
			int sElement2 = Integer.parseInt(oElement2.getText());
			WebElement oElement3 = itr2.getWebElement(driver, screenName,
					object3);
			int sElement3 = Integer.parseInt(oElement3.getText());
			WebElement oElement4 = itr2.getWebElement(driver, screenName,
					object4);
			int sElement4 = Integer.parseInt(oElement4.getText());
			WebElement oElement5 = itr2.getWebElement(driver, screenName,
					object5);
			int sElement5 = Integer.parseInt(oElement5.getText());
			WebElement oElement6 = itr2.getWebElement(driver, screenName,
					object6);
			int sElement6 = Integer.parseInt(oElement6.getText());
			WebElement oElement7 = itr2.getWebElement(driver, screenName,
					object7);
			int sElement7 = Integer.parseInt(oElement7.getText());
			int sCount = sElement2 + sElement3 + sElement4 + sElement5
					+ sElement6 + sElement7;
			if (iElement1 == sCount) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS, "Submissions are equal : " + iElement1);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL, "Submissions are not equal : "
								+ iElement1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Function to verify the background color of the element
	 * 
	 * @param driver
	 * @param screenName
	 *            from the input sheet
	 * @param ObjectName
	 *            for the respective object from the input sheet
	 */

	public static boolean backgroundColor(WebDriver driver, String screenName,
			String ObjectName, String value) {
		try {
			String[] valArray = value.split(";");
			String value0 = valArray[0];
			String value1 = valArray[1];
			WebElement oElement = itr2.getWebElement(driver, screenName,
					ObjectName);
			String sElement = oElement.getCssValue("background-color");
			if (sElement.equals(value0)) {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.PASS,
						"The expected color matches with application : "
								+ value1);
			} else {
				com.qfor.driverscript.DriverScriptRef.Report.log(
						LogStatus.FAIL,
						"The expected color not matches with application : "
								+ value1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Select dropdown value
		public boolean selectValueDD(WebDriver driver, String screenName,
				String ObjectName, String value) {

			//WebElement we = webElement(driver, identifyBy, locator);
			WebElement we = itr2.getWebElement(driver, screenName,
					ObjectName);
			
			Select selectDate = new Select(itr2.getWebElement(driver, screenName,ObjectName));
			selectDate.selectByValue(value);
			if (we == null) {
				return false;
			} else {
				return true;
			}
		}
		//Get the comment value form asset related file
		public boolean getCommentValue(WebDriver driver, String screenName,
				String ObjectName, String value) throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
			try{
				String xmlPath = AssetRelFilePath+"Exhibit.xml";
				XMLStreamReader xr = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(xmlPath)); 
				while (xr.hasNext()) {
				    if (xr.next() == XMLStreamConstants.COMMENT) {
				        String xmlComment = xr.getText();	        
				        com.qfor.driverscript.DriverScriptRef.Report.log(
								LogStatus.PASS,
								"Verified comment   : "
										+ xmlComment);
				    } 
				}					
			}
			catch(Exception e){
				
			}
			 return true;
		}


}