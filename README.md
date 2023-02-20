# OWASP UI Automated test
#### <span style="color:red">**_Java_**</span> 17.0.6 
#### <span style="color:red">**_Junit_**</span> 4.13.2
#### <span style="color:red">**_Selenium (java, chrome-driver, remote-driver, api)_**</span> 4.7.2
#### <span style="color:red">**_log4j2 (log4j-core, log4j-api)_**</span> 2.19.0
#### <span style="color:red">**_ChromeDriver_**</span> 4.7.2
#### <span style="color:red">**_Maven_**</span>

### [Press here for the user story](https://docs.google.com/document/d/15AtmaEBx13mbZ20gWvyGI3bD-8X_y5TKu176OXcMesE/edit?usp=sharing)

The code rn assumes that your webdriver calls localhost:3000 automatically, 
You can change this inside "SignupTests.java"

The code looks like a mix of unit test and UI test. So both junit, selenium are used in conjunction.

Since log4j(apache.log4j) is already depricated, this project uses log4j2(apache.logging.log4j-core, apache.logging.log4j-api)
This can cause confusion, so be careful on that.

Since it can be hard to track actions happening in the maven image, I suggest using the command "docker build . &> build.log" and you can look at the log from there.
