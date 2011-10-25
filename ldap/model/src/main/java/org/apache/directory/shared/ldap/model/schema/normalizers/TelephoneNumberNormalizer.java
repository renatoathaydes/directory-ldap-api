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
package org.apache.directory.shared.ldap.model.schema.normalizers;


import java.io.IOException;

import org.apache.directory.shared.i18n.I18n;
import org.apache.directory.shared.ldap.model.constants.SchemaConstants;
import org.apache.directory.shared.ldap.model.entry.StringValue;
import org.apache.directory.shared.ldap.model.entry.Value;
import org.apache.directory.shared.ldap.model.exception.LdapException;
import org.apache.directory.shared.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.shared.ldap.model.schema.Normalizer;
import org.apache.directory.shared.ldap.model.schema.PrepareString;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;


/**
 * Normalize Telephone Number Strings
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@SuppressWarnings("serial")
@Component
@Provides
public class TelephoneNumberNormalizer extends Normalizer
{
    /**
     * Creates a new instance of TelephoneNumberNormalizer.
     */
    public TelephoneNumberNormalizer()
    {
        super( SchemaConstants.TELEPHONE_NUMBER_MATCH_MR_OID );
    }


    /**
     * {@inheritDoc}
     */
    public Value<?> normalize( Value<?> value ) throws LdapException
    {
        try
        {
            String normalized = PrepareString.normalize( value.getString(),
                PrepareString.StringType.TELEPHONE_NUMBER );

            return new StringValue( normalized );
        }
        catch ( IOException ioe )
        {
            throw new LdapInvalidDnException( I18n.err( I18n.ERR_04224, value ), ioe );
        }
    }


    /**
     * {@inheritDoc}
     */
    public String normalize( String value ) throws LdapException
    {
        try
        {
            return PrepareString.normalize( value,
                PrepareString.StringType.TELEPHONE_NUMBER );
        }
        catch ( IOException ioe )
        {
            throw new LdapInvalidDnException( I18n.err( I18n.ERR_04224, value ), ioe );
        }
    }
}