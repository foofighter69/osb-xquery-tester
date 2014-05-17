# OSB XQueryTester

OSB XQuery unit test - based on Transentia nice work - http://wordpress.transentia.com.au/wordpress/2010/11/20/unit-testing-xquery-using-osbs-api-2/

This is a mixed Groovy 2.1.7 and Java 1.6 project built in NetBeans 8.0. 
This program helps writing unit tests in Groovy/Java for OSB XQuery scripts 
and it can be used in commandline.

## One fat jar
The build.xml is extended with clean-fat-jar and fat-jar target. 
All dependencies is in the lib directory. Unfortunately running the clean-fat-jar
does not build a working jar. You must manually copy from the groovy-all.jar the 
META-INF folder and then running fat-jar.
   
## Usage

java -jar XQueryTester.jar <xquery_file> <input_xml> <expected_xml>

## Examples

java -jar XQueryTester.jar strip_namespace.xq strip_namespace_01_input.xml strip_namespace_01_expected.xml