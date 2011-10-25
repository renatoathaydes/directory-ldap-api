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
package org.apache.directory.shared.ldap.model.schema.syntaxCheckers;

import org.apache.directory.shared.ldap.model.constants.SchemaConstants;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A SyntaxChecker which verifies that a value is a certificateList according to RFC 4523 :
 * 
 * "Due to changes made to the definition of a CertificateList through time,
 *  no LDAP-specific encoding is defined for this syntax.  Values of this
 *  syntax SHOULD be encoded using Distinguished Encoding Rules (DER)
 *  [X.690] and MUST only be transferred using the ;binary transfer
 *  option"
 * 
 * It has been removed in RFC 4517
 *  
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@SuppressWarnings("serial")
@Component
@Provides
public class CertificateListSyntaxChecker extends BinarySyntaxChecker
{
    /** A logger for this class */
    private static final Logger LOG = LoggerFactory.getLogger( CertificateListSyntaxChecker.class );

    /**
     * Creates a new instance of CertificateListSyntaxChecker.
     */
    public CertificateListSyntaxChecker()
    {
        super();
        setOid( SchemaConstants.CERTIFICATE_LIST_SYNTAX );
    }


    /**
     * {@inheritDoc}
     */
    public boolean isValidSyntax( Object value )
    {
        LOG.debug( "Syntax valid for '{}'", value );
        return true;
    }
}
