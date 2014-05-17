/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package transentia.osb.xquery.unittest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author foo
 */
public class MainTest {
    

    /**
     * Test of XqueryTestWithOneExternalVariable method, of class Main.
     */
    @Test
    public void testXqueryTestWithOneExternalVariable() throws Exception {
        System.out.println("XqueryTestWithOneExternalVariable");
        String xqueryFile = "xqueries/strip_namespace.xq";
        String inputXml = "xml/strip_namespace_01_input.xml";
        String expectedXml = "xml/strip_namespace_01_expected.xml";
        Main.XqueryTestWithOneExternalVariable(xqueryFile, inputXml, expectedXml);
    }
    
}
