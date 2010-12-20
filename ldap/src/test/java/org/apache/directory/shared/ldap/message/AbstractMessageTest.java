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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.directory.junit.tools.Concurrent;
import org.apache.directory.junit.tools.ConcurrentJunitRunner;
import org.apache.directory.shared.ldap.codec.controls.ControlImpl;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test cases for the AbstractMessage class' methods.
 * 
 * @author <a href="mailto:dev@directory.apache.org"> Apache Directory Project</a>
 *         $Rev: 910150 $
 */
@RunWith(ConcurrentJunitRunner.class)
@Concurrent()
public class AbstractMessageTest
{
    /**
     * Tests to see the same object returns true.
     */
    @Test
    public void testEqualsSameObj()
    {
        AbstractMessage msg;
        msg = new AbstractMessage( 5, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        assertTrue( msg.equals( msg ) );
    }


    /**
     * Tests to see the same exact copy returns true.
     */
    @Test
    public void testEqualsExactCopy()
    {
        AbstractMessage msg0;
        AbstractMessage msg1;
        msg0 = new AbstractMessage( 5, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        msg1 = new AbstractMessage( 5, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        assertTrue( msg0.equals( msg1 ) );
        assertTrue( msg1.equals( msg0 ) );
    }


    /**
     * Tests to make sure changes in the id result in inequality.
     */
    @Test
    public void testNotEqualsDiffId()
    {
        AbstractMessage msg0;
        AbstractMessage msg1;
        msg0 = new AbstractMessage( 5, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        msg1 = new AbstractMessage( 6, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        assertFalse( msg0.equals( msg1 ) );
        assertFalse( msg1.equals( msg0 ) );
    }


    /**
     * Tests to make sure changes in the type result in inequality.
     */
    @Test
    public void testNotEqualsDiffType()
    {
        AbstractMessage msg0;
        AbstractMessage msg1;
        msg0 = new AbstractMessage( 5, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        msg1 = new AbstractMessage( 5, MessageTypeEnum.UNBIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        assertFalse( msg0.equals( msg1 ) );
        assertFalse( msg1.equals( msg0 ) );
    }


    /**
     * Tests to make sure changes in the controls result in inequality.
     */
    @Test
    public void testNotEqualsDiffControls()
    {
        AbstractMessage msg0;
        AbstractMessage msg1;

        msg0 = new AbstractMessage( 5, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };

        msg0.addControl( new ControlImpl( "0.0" )
        {
            private static final long serialVersionUID = 1L;


            public boolean isCritical()
            {
                return false;
            }


            public void setCritical( boolean isCritical )
            {
            }


            public byte[] getValue()
            {
                return null;
            }


            public String getOid()
            {
                return null;
            }
        } );

        msg1 = new AbstractMessage( 5, MessageTypeEnum.BIND_REQUEST )
        {
            private static final long serialVersionUID = 1L;
        };
        assertFalse( msg0.equals( msg1 ) );
        assertFalse( msg1.equals( msg0 ) );
    }

}
