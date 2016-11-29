package org.springboot.quick.commons.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.Writer;

/**
 * Created by chababa on 7/14/16.
 */
public class XStreamInitializer {

    public static XStream getInstance() {
        XStream xstream = new XStream(new XppDriver() {

            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, getNameCoder()) {
                    protected String PREFIX_CDATA = "<![CDATA[";
                    protected String SUFFIX_CDATA = "]]>";

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (text.startsWith(PREFIX_CDATA) && text.endsWith(SUFFIX_CDATA)) {
                            writer.write(text);
                        } else {
                            super.writeText(writer, text);
                        }

                    }
                };
            }
        });
        xstream.ignoreUnknownElements();
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        return xstream;
    }

}
