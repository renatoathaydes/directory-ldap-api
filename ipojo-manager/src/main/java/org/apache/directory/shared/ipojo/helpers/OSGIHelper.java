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
package org.apache.directory.shared.ipojo.helpers;


import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


public class OSGIHelper
{
    /** BundleContext reference which will be assigned through BundleActivator */
    public static BundleContext bundleCtx;


    public static boolean isAPIInOSGIContainer()
    {
        if ( bundleCtx == null )
        {
            return false;
        }

        return true;
    }


    public static List<?> getServices( String serviceClassName, String filter )
    {
        if ( !isAPIInOSGIContainer() )
        {
            return null;
        }

        try
        {
            ServiceReference[] serviceReferences = bundleCtx.getServiceReferences( serviceClassName, filter );
            List<Object> services = new ArrayList<Object>();
            for ( ServiceReference ref : serviceReferences )
            {
                services.add( bundleCtx.getService( ref ) );
            }

            return services;
        }
        catch ( InvalidSyntaxException e )
        {
            e.printStackTrace();
            return null;
        }
        catch ( IllegalStateException e )
        {
            e.printStackTrace();
            return null;
        }
    }


    public static Object getService( String serviceClassName )
    {
        if ( !isAPIInOSGIContainer() )
        {
            return null;
        }

        ServiceReference ref = bundleCtx.getServiceReference( serviceClassName );
        if ( ref == null )
        {
            return null;
        }

        try
        {
            return bundleCtx.getService( ref );
        }
        catch ( IllegalStateException e )
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Object getServiceObject(ServiceReference ref)
    {
        try
        {
            return bundleCtx.getService( ref );
        }
        catch ( IllegalStateException e )
        {
            e.printStackTrace();
            return null;
        }
    }
}
