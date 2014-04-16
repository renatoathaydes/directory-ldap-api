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

import java.nio.ByteBuffer;

import org.apache.directory.api.asn1.Asn1Object;
import org.apache.directory.api.asn1.DecoderException;
import org.apache.directory.api.asn1.EncoderException;
import org.apache.directory.api.asn1.ber.Asn1Decoder;
import org.apache.directory.api.asn1.ber.tlv.BerValue;
import org.apache.directory.api.asn1.ber.tlv.TLV;
import org.apache.directory.api.asn1.ber.tlv.UniversalTag;
import org.apache.directory.api.i18n.I18n;
import org.apache.directory.api.ldap.codec.api.ControlDecorator;
import org.apache.directory.api.ldap.codec.api.LdapApiService;
import org.apache.directory.api.util.Strings;

/**
 * A decorator around AdDirSyncResponse control. It will encode and decode this control.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class AdDirSyncResponseDecorator extends ControlDecorator<AdDirSyncResponse> implements AdDirSyncResponse
{
    /** The global length for this control */
    private int adDirSyncResponseLength;

    /** An instance of this decoder */
    private static final Asn1Decoder decoder = new Asn1Decoder();


    /**
     * Creates a new instance of AdDirSyncResponseControlCodec.
     */
    public AdDirSyncResponseDecorator( LdapApiService codec )
    {
        super( codec, new AdDirSyncResponseImpl() );
    }


    /**
     * Creates a new instance of AdDirSyncResponseDecorator.
     *
     * @param codec The LDAP codec
     * @param control The control to be decorated
     */
    public AdDirSyncResponseDecorator( LdapApiService codec, AdDirSyncResponse control )
    {
        super( codec, control );
    }


    /**
     * Compute the AdDirSyncResponse length.
     * 0x30 L1
     * |
     * +--> 0x02 0x0(1-4) nnn  (Flag)
     * +--> 0x02 0x0(1-4) nnn  (maxReturnLength)
     * +--> 0x04 L2 xkcd!!!... (cookie)
     */
    @Override
    public int computeLength()
    {
        // the parentFirst flag length
        int flagValue = getFlag().getValue();
        adDirSyncResponseLength = 1 + TLV.getNbBytes( flagValue ) + BerValue.getNbBytes( flagValue );

        // the maxReturnLength length
        adDirSyncResponseLength += 1 + TLV.getNbBytes( getMaxReturnLength() ) + BerValue.getNbBytes( getMaxReturnLength() );

        // cookie's length
        byte[] cookie = getCookie();
        
        if ( cookie == null )
        {
            adDirSyncResponseLength += 1 + 1;
        }
        else
        {
            adDirSyncResponseLength += 1 + TLV.getNbBytes( cookie.length ) + cookie.length;
        }

        valueLength = 1 + TLV.getNbBytes( adDirSyncResponseLength ) + adDirSyncResponseLength;

        // Call the super class to compute the global control length
        return valueLength;
    }


    /**
     * Encode the AdDirSyncResponse control
     *
     * @param buffer The encoded sink
     * @return A ByteBuffer that contains the encoded PDU
     * @throws EncoderException If anything goes wrong while encoding.
     */
    @Override
    public ByteBuffer encode( ByteBuffer buffer ) throws EncoderException
    {
        if ( buffer == null )
        {
            throw new EncoderException( I18n.err( I18n.ERR_04023 ) );
        }

        // Encode the SEQ
        buffer.put( UniversalTag.SEQUENCE.getValue() );
        buffer.put( TLV.getBytes( adDirSyncResponseLength ) );

        // Encode the ParentFirst flag
        BerValue.encode( buffer, getFlag().getValue() );

        // Encode the MaxReturnLength
        BerValue.encode( buffer, getMaxReturnLength() );
        
        // Encode the cookie
        BerValue.encode( buffer, getCookie() );

        return buffer;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getValue()
    {
        if ( value == null )
        {
            try
            {
                computeLength();
                ByteBuffer buffer = ByteBuffer.allocate( valueLength );

                // Encode the SEQ
                buffer.put( UniversalTag.SEQUENCE.getValue() );
                buffer.put( TLV.getBytes( adDirSyncResponseLength ) );

                // Encode the ParentFirst flag
                BerValue.encode( buffer, getFlag().getValue() );

                // Encode the MaxReturnLength
                BerValue.encode( buffer, getMaxReturnLength() );
                
                // Encode the cookie
                BerValue.encode( buffer, getCookie() );

                value = buffer.array();
            }
            catch ( Exception e )
            {
                return null;
            }
        }

        return value;
    }
    
    
    /**
     * {@inheritDoc}
     */
    public AdDirSyncFlag getFlag()
    {
        return getDecorated().getFlag();
    }

    
    /**
     * {@inheritDoc}
     */
    public void setFlag( AdDirSyncFlag flag )
    {
        getDecorated().setFlag( flag );
    }
    
    
    /**
     * {@inheritDoc}
     */
    public int getMaxReturnLength()
    {
        return getDecorated().getMaxReturnLength();
    }

    
    /**
     * {@inheritDoc}
     */
    public void setMaxReturnLength( int maxReturnLength )
    {
        getDecorated().setMaxReturnLength( maxReturnLength );
    }
    

    /**
     * {@inheritDoc}
     */
    public byte[] getCookie()
    {
        return getDecorated().getCookie();
    }


    /**
     * {@inheritDoc}
     */
    public void setCookie( byte[] cookie )
    {
        // Copy the bytes
        if ( !Strings.isEmpty( cookie ) )
        {
            byte[] copy = new byte[cookie.length];
            System.arraycopy( cookie, 0, copy, 0, cookie.length );
            getDecorated().setCookie( copy );
        }
        else
        {
            getDecorated().setCookie( null );
        }
    }


    /**
     * {@inheritDoc}
     */
    public Asn1Object decode( byte[] controlBytes ) throws DecoderException
    {
        ByteBuffer bb = ByteBuffer.wrap( controlBytes );
        AdDirSyncResponseContainer container = new AdDirSyncResponseContainer( getCodecService(), this );
        decoder.decode( bb, container );
        return this;
    }
}
