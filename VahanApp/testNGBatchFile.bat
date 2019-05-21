set projectLocation=D:\PortalsBatchAutomation\workspace\ReligarePortal
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\Libs\*
java org.testng.TestNG %projectLocation%\testng.xml
pause