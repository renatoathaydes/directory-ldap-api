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
package org.apache.directory.api.ldap.extras.controls.ad;

import org.apache.directory.api.ldap.model.message.controls.AbstractControl;
import org.apache.directory.api.util.Strings;

/**
 * The class implementing the AdDirSyncResponse
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class AdDirSyncResponseImpl extends AbstractControl implements AdDirSyncResponse
{
    /** A flag used to indicate that there are more data to return */
    AdDirSyncFlag flag = AdDirSyncFlag.DEFAULT;
    
    /** The maximum number of attributes to return */
    int maxReturnLength = 0;
    
    /** The DirSync cookie */
    private byte[] cookie;

    /**
     * Default constructor for this control
     */
    public AdDirSyncResponseImpl()
    {
        super( OID, Boolean.TRUE );
    }
    

    /**
     * {@inheritDoc}
     */
    public AdDirSyncFlag getFlag()
    {
        return flag;
    }
    

    /**
     * {@inheritDoc}
     */
    public void setFlag( AdDirSyncFlag flag )
    {
        this.flag = flag;
    }
    

    /**
     * {@inheritDoc}
     */
    public int getMaxReturnLength()
    {
        return maxReturnLength;
    }


    /**
     * {@inheritDoc}
     */
    public void setMaxReturnLength( int maxReturnLength )
    {
        this.maxReturnLength = maxReturnLength;
    }


    /**
     * {@inheritDoc}
     */
    public byte[] getCookie()
    {
        return cookie;
    }


    /**
     * {@inheritDoc}
     */
    public void setCookie( byte[] cookie )
    {
        if ( cookie != null )
        {
            this.cookie = new byte[cookie.length];
            System.arraycopy( cookie, 0, this.cookie, 0,cookie.length );
        }
        else
        {
            this.cookie = Strings.EMPTY_BYTES;
        }
    }
}
