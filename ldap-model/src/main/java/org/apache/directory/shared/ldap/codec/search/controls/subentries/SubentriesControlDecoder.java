/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.shared.ldap.codec.search.controls.subentries;


import java.nio.ByteBuffer;

import org.apache.directory.shared.asn1.Asn1Object;
import org.apache.directory.shared.asn1.DecoderException;
import org.apache.directory.shared.asn1.ber.Asn1Decoder;
import org.apache.directory.shared.ldap.codec.controls.ControlDecoder;
import org.apache.directory.shared.ldap.message.control.Control;


/**
 * A decoder for SubEntryControls.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class SubentriesControlDecoder extends Asn1Decoder implements ControlDecoder
{
    /** The sub entry decoder */
    private static final Asn1Decoder decoder = new Asn1Decoder();

    /**
     * Decode the sub entry control
     * 
     * @param controlBytes The bytes array which contains the encoded sub entry
     * 
     * @return A valid SubEntry object
     * 
     * @throws org.apache.directory.shared.asn1.DecoderException If the decoding found an error
     * @throws NamingException It will never be throw by this method
     */
    public Asn1Object decode( byte[] controlBytes, Control control ) throws DecoderException
    {
        ByteBuffer bb = ByteBuffer.wrap( controlBytes );
        SubentriesControlContainer container = new SubentriesControlContainer();
        container.setSubEntryControl( (SubentriesControl)control );

        decoder.decode( bb, container );
        return container.getSubEntryControl();
    }
}
