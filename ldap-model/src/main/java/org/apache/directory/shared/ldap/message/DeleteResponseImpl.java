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
package org.apache.directory.shared.ldap.message;


/**
 * DeleteResponse implementation
 * 
 * @author <a href="mailto:dev@directory.apache.org"> Apache Directory Project</a>
 */
public class DeleteResponseImpl extends AbstractResultResponse implements DeleteResponse
{
    /** The encoded deleteResponse length */
    private int deleteResponseLength;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    static final long serialVersionUID = -6830004960050713586L;


    /**
     * Creates a DeleteResponse as a reply to an DeleteRequest.
     */
    public DeleteResponseImpl()
    {
        super( -1, TYPE );
    }


    /**
     * Creates a DeleteResponse as a reply to an DeleteRequest.
     * 
     * @param id the session unique message id
     */
    public DeleteResponseImpl( final int id )
    {
        super( id, TYPE );
    }


    /**
     * Stores the encoded length for the DeleteResponse
     * @param deleteResponseLength The encoded length
     */
    /* No qualifier*/void setDeleteResponseLength( int deleteResponseLength )
    {
        this.deleteResponseLength = deleteResponseLength;
    }


    /**
     * @return The encoded DeleteResponse's length
     */
    /* No qualifier*/int getDeleteResponseLength()
    {
        return deleteResponseLength;
    }


    /**
     * Get a String representation of a DelResponse
     * 
     * @return A DelResponse String
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "    Compare Response\n" );
        sb.append( super.toString() );

        return sb.toString();
    }
}
