package transentia.osb.xquery.unittest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 *
 * @author foo
 *
 */
public class Main {

    private static void assertXml(String expectedOutput, String generatedXml) throws Exception {

        System.out.println("[=============[ Expected ]=============]");
        System.out.println(expectedOutput);
        System.out.println("[=============[ Generated ]============]");
        System.out.println(generatedXml);
        System.out.println("[======================================]");

        try {
            Diff diff = new Diff(expectedOutput, generatedXml);

            System.out.println("Similar? " + diff.similar());
            System.out.println("Identical? " + diff.identical());

            DetailedDiff detDiff = new DetailedDiff(diff);
            List differences = detDiff.getAllDifferences();
            for (Object object : differences) {
                Difference difference = (Difference) object;
                System.out.println("[======================================]");
                System.out.println(difference);
                System.out.println("[======================================]");
            }
            assertEquals(detDiff.toString(), 0, differences.size());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String filename) throws FileNotFoundException {
        return new Scanner(new File(filename)).useDelimiter("\\Z").next();
    }

    public static void main(String... param) throws FileNotFoundException {

        if (param.length < 3) {
            
            callFrontLogo();
            
            System.out.println("");
            System.out.println(" Usage: java -jar XQueryTester.jar <xquery_file> <input_xml> <expected_xml>");
            System.out.println("");
            System.out.println(" Example: java -jar XQueryTester.jar strip_namespace.xq strip_namespace_01_input.xml strip_namespace_01_expected.xml");
            System.out.println("");
        } else {

            String xqueryFile = param[0];
            String inputXml = param[1];
            String expectedXml = param[2];

            try {
                XqueryTestWithOneExternalVariable(xqueryFile, inputXml, expectedXml);
            } catch (XqueryTestException ex) {
                System.out.println(" !!! One external file variable supported !!! ");
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

      
        }
    }

    public static void XqueryTestWithOneExternalVariable(String xqueryFile, String inputXml, String expectedXml) throws  FileNotFoundException, XqueryTestException, Exception {
        //xqueryFile = "xqueries/strip_namespace.xq";

        Map<String, String> map = new HashMap<String,String>();
        map = readExternalParametersFromXquery(xqueryFile);
        Map<String, Object> xmlOptions = new HashMap<String, Object>();
        if (map.size() != 1) {
            throw new XqueryTestException("More_Than_One_External_Variable_Defined_In_XQuery");
        }
        for (String elem : map.keySet()) {
            xmlOptions.put(elem, new File(inputXml));
        }


        XQueryTransformer xqt = new XQueryTransformer(xmlOptions, xqueryFile);
        String generated = xqt.getResult();
        String expected = readFile(expectedXml);
        
        XMLUnit.setIgnoreWhitespace(true);
        assertXml(expected, generated);
    }

    private static Map<String, String> readExternalParametersFromXquery(String xqueryfile) throws FileNotFoundException {
        String text = readFile(xqueryfile);
        //String regexp = "^declare\\s+variable\\s+$([^ ]+)\\s+as\\s+([^ ]+)\\s+external;$";
        String regexp = "declare[ ]+variable.*";
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(text);
        String[] tokenlist = null;
        while (m.find()){
            tokenlist = m.group().split("\\s+");
            for(String s : tokenlist){
            }
        }
        Map<String, String> map = new HashMap<String,String>();
        map.put(removeDollarToken(tokenlist[2]), tokenlist[4]);
        return map;
    }

    private static String removeDollarToken(String string) {
        return string.replace(String.valueOf('$'), "");
    }

    private static void callFrontLogo() {
        


System.out.println("__   _______                         _   _       _ _     _____         _   ");
System.out.println("\\ \\ / /  _  |                       | | | |     (_) |   |_   _|       | |  ");
System.out.println(" \\ V /| | | |_   _  ___ _ __ _   _  | | | |_ __  _| |_    | | ___  ___| |_ ");
System.out.println(" /   \\| | | | | | |/ _ \\ '__| | | | | | | | '_ \\| | __|   | |/ _ \\/ __| __|");
System.out.println("/ /^\\ \\ \\/' / |_| |  __/ |  | |_| | | |_| | | | | | |_    | |  __/\\__ \\ |_ ");
System.out.println("\\/   \\/\\_/\\_\\\\__,_|\\___|_|   \\__, |  \\___/|_| |_|_|\\__|   \\_/\\___||___/\\__|");
System.out.println("                              __/ |                                        ");
System.out.println("                             |___/                                         ");


    }

}
