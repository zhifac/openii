/*
 *  Copyright 2009 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.mitre.schemastore.mapfunctions;

import java.net.MalformedURLException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import java.util.*;


/**
 * This class uses the SAX parser to parse a simple XML file representing the list of available 
 * mapping functions.  
 *
 * @author jchoyt
 */
public class FunctionListParser extends DefaultHandler
{
    /** A List of functions that have been processed so far */
    private List<String> targets = new ArrayList<String>(  );

    boolean expectingClass = false; 
    
    
    /** Buffer for collecting data from the "characters" SAX event. */
//    private CharArrayWriter contents = new CharArrayWriter(  );

    /**
     * Override methods of the DefaultHandler class to gain notification of SAX Events. See
     * org.xml.sax.ContentHandler for all available events.
     *
     * @param namespaceURI the Namespace URI, or the empty string if the element has no Namespace URI or if Namespace
     *        processing is not being performed
     * @param localName the local name (without prefix), or the empty string if Namespace processing is not being
     *        performed
     * @param qName the qualified name (with prefix), or the empty string if qualified names are not available
     * @param attr the attributes attached to the element. If there are no attributes, it shall be an empty Attributes
     *        object. The value of this object after startElement returns is undefined
     *
     * @exception SAXException any SAX exception, possibly wrapping another exception
     */
    public void startElement( String namespaceURI, String localName, String qName, Attributes attr ) throws SAXException
    {
        if ( localName.equals( "class" ) )
        {
            expectingClass = true;
        }
    }

    
    /**
     *  adds a new class to the list if it encounters character data at the correct time
     */
    public void characters(char[] ch, int start, int length)
    {
        String s = new String(ch, start, length);
        // System.out.println("characters [" + s.trim() + "]");

        if( expectingClass )
        {
            // System.out.println("adding " + s.trim() );
            targets.add( s.trim() );
            expectingClass = false;
        }
    }
    
    
    /**
     * Description of the Method
     *
     * @param namespaceURI Description of Parameter
     * @param localName Description of Parameter
     * @param qName Description of Parameter
     *
     * @exception SAXException Description of Exception
     */
    public void endElement( String namespaceURI, String localName, String qName ) throws SAXException
    {
        expectingClass = false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public List<String> getFunctions(  )
    {
        return targets;
    }
}

