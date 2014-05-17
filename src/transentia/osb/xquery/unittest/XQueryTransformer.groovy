package transentia.osb.xquery.unittest

import org.apache.xmlbeans.XmlObject
import org.apache.xmlbeans.XmlOptions
import org.apache.xmlbeans.XmlDateTime
import org.apache.xmlbeans.XmlBoolean
import org.apache.xmlbeans.XmlInteger
import org.apache.xmlbeans.XmlDouble
import org.apache.xmlbeans.XmlString

class XQueryTransformer {
    private final resultXML

    public XQueryTransformer(xQueryTransformSource) {
        this(null, xQueryTransformSource)
    }
    
    public XQueryTransformer(map, xQueryTransformSource) {

        XmlObject xmlObject = XmlObject.Factory.newInstance()
        XmlOptions options = new XmlOptions()
        
        if( map!=null) {
            options.setXqueryVariables(createOptions(map))
        }

        XmlObject[] results = xmlObject.execQuery(new File(xQueryTransformSource).text, options)
        


        resultXML = results[0].xmlText(new XmlOptions().setSavePrettyPrint().setSavePrettyPrintIndent(2))
    }

    private createOptions(map) {
        def optionsMap = [:]
        map.each { k, v ->
            switch (v?.class) {
            case Date:
                XmlDateTime dt = XmlDateTime.Factory.newInstance();
                dt.setDateValue(v)
                optionsMap.put(k, dt)
                break
            case Boolean:
                XmlBoolean b = XmlBoolean.Factory.newInstance();
                b.set(v)
                optionsMap.put(k, b)
                break
            case [BigInteger, Integer, Long, Short, Byte, byte, short, int, long]:
                XmlInteger i = XmlInteger.Factory.newInstance();
                i.setBigIntegerValue(v as BigInteger)
                optionsMap.put(k, i)
                break
            case [BigDecimal, Float, Double, float, double]:
                XmlDouble xd = XmlDouble.Factory.newInstance();
                xd.setDoubleValue(v as double)
                optionsMap.put(k, xd)
                break
            case String:
                XmlString string = XmlString.Factory.newInstance();
                string.setStringValue(v);
                optionsMap.put(k, string)
                break
            case File:
                def txt = v.text
                optionsMap.put(k, XmlObject.Factory.parse(txt).selectPath("/*")[0])
                break

            case null:
                XmlObject n = XmlObject.Factory.newInstance()
                n.setNil()
                optionsMap.put(k, n)
                break

            default:
                throw new Exception("Unhandled type: ${v.class}")
                break
            }
        }
        optionsMap
    }
    String getResult() {
        resultXML
    }
    def withString(c) {
        c.call resultXML
    }
}
