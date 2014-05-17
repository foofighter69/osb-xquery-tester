package transentia.osb.xquery.unittest

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EmptyParameterTest {
  def xqt

  @Before
   void setup() {
    xqt = new XQueryTransformer("xqueries/empty_parameter.xq")
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