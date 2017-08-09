package com.epam.testgmail;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

import bussinessobj.HomePageBO;
import bussinessobj.LoginBO;
import driver.Driver;
import models.UserModel;
import testdata.Data;
import testdata.DataXL;


@Listeners({MyListener.class, HTMLReporter.class})
public class TestGmailNegative{
	
	@DataProvider(parallel = false)
	public Object[] getData() throws Exception {
		DataXL usersData = new DataXL();
		Object[] data = usersData.getUsersObj().toArray();
		return data;
		
	}

	@Test(dataProvider = "getData")
	public void testLogin(UserModel user) {
		LoginBO login = new LoginBO();
		login.login(user);
		Data emailData = new Data();
		HomePageBO home = new HomePageBO();
		home.clickCompose();
		home.sendEmail(emailData.getEmails().get(0));
		home.closeErrorAndClearEmailInput();
		home.sendEmail(emailData.getEmails().get(1));
		assertFalse(home.verifyMessageisSent(emailData.getEmails().get(1)));
	}
	@AfterMethod
	public void closeBrowser() {
		Driver.close();
	}
}
