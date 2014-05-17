package transentia.osb.xquery.unittest

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class XQBeaFunctionTest {
  def xqt

  @Before
   void setup() {
    xqt = new XQueryTransformer("xqueries/fnbea.xq", str: "hello")
  }

  @Test
  void testTransform() {
    xqt.withString { str ->
      println str
    }
  }

  @Test
  void testSlurping() {
    xqt.withString { str ->
      def data = new XmlSlurper().parseText(str)
      assert data.text() == '2005-07-15'
    }
  }

 
}