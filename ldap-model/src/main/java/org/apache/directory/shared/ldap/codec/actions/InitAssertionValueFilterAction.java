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
package org.apache.directory.shared.ldap.codec.actions;


import org.apache.directory.shared.asn1.ber.Asn1Container;
import org.apache.directory.shared.asn1.ber.grammar.GrammarAction;
import org.apache.directory.shared.asn1.ber.tlv.TLV;
import org.apache.directory.shared.asn1.DecoderException;
import org.apache.directory.shared.ldap.codec.AttributeValueAssertion;
import org.apache.directory.shared.ldap.codec.LdapMessageContainer;
import org.apache.directory.shared.ldap.codec.search.AttributeValueAssertionFilter;
import org.apache.directory.shared.ldap.entry.BinaryValue;
import org.apache.directory.shared.ldap.entry.StringValue;
import org.apache.directory.shared.ldap.entry.Value;
import org.apache.directory.shared.ldap.message.SearchRequest;
import org.apache.directory.shared.ldap.message.SearchRequestImpl;
import org.apache.directory.shared.util.CharConstants;
import org.apache.directory.shared.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The action used to initialize the Assertion Value filter
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class InitAssertionValueFilterAction extends GrammarAction
{
    /** The logger */
    private static final Logger LOG = LoggerFactory.getLogger( InitAssertionValueFilterAction.class );

    /** Speedup for logs */
    private static final boolean IS_DEBUG = LOG.isDebugEnabled();


    /**
     * Instantiates a new init assertion value filter action.
     */
    public InitAssertionValueFilterAction()
    {
        super( "Initialize Assertion Value filter" );
    }


    /**
     * {@inheritDoc}
     */
    public void action( Asn1Container container ) throws DecoderException
    {
        LdapMessageContainer ldapMessageContainer = ( LdapMessageContainer ) container;
        SearchRequest searchRequest = ldapMessageContainer.getSearchRequest();

        TLV tlv = ldapMessageContainer.getCurrentTLV();

        // The value can be null.
        Value<?> assertionValue = null;

        if ( tlv.getLength() != 0 )
        {
            assertionValue = new BinaryValue( tlv.getValue().getData() );
        }
        else
        {
            assertionValue = new BinaryValue( CharConstants.EMPTY_BYTES );
        }

        AttributeValueAssertionFilter terminalFilter = ( AttributeValueAssertionFilter ) ( ( SearchRequestImpl ) searchRequest )
            .getTerminalFilter();
        AttributeValueAssertion assertion = terminalFilter.getAssertion();

        if ( ldapMessageContainer.isBinary( assertion.getAttributeDesc() ) )
        {
            if ( tlv.getLength() != 0 )
            {
                assertionValue = new BinaryValue( tlv.getValue().getData() );
            }
            else
            {
                assertionValue = new BinaryValue( CharConstants.EMPTY_BYTES );
            }

            assertion.setAssertionValue( assertionValue );
        }
        else
        {
            if ( tlv.getLength() != 0 )
            {
                assertionValue = new StringValue( Strings.utf8ToString(tlv.getValue().getData()) );
            }
            else
            {
                assertionValue = new StringValue( "" );
            }

            assertion.setAssertionValue( assertionValue );
        }

        // We now have to get back to the nearest filter which is
        // not terminal.
        ( ( SearchRequestImpl ) searchRequest ).unstackFilters( container );

        if ( IS_DEBUG )
        {
            LOG.debug( "Initialize Assertion Value filter" );
        }
    }
}
