package org.springboot.quick.commons.xml;

import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * Created by chababa on 7/14/16.
 */
public class XStreamCDataConverter extends StringConverter {

    @Override
    public String toString(Object obj) {
        return "<![CDATA[" + super.toString(obj) + "]]>";
    }

}