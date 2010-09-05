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
package org.apache.directory.shared.asn1.ber.grammar;


import org.apache.directory.shared.asn1.util.Asn1StringUtils;


/**
 * Define a transition between two states of a grammar. It stores the next
 * state, and the action to execute while executing the transition.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class GrammarTransition
{
    /** The action associated to the transition */
    private Action action;

    /** The previous state */
    private int previousState;
    
    /** The current state */
    private int currentState;

    /** The current tag */
    private int currentTag;

    /**
     * Creates a new GrammarTransition object.
     * 
     * @param previousState the previous state
     * @param currentState The current state
     * @param currentTag the current TLV's tag
     * @param action The action to execute. It could be null.
     */
    public GrammarTransition( int previousState, int currentState, int currentTag, Action action )
    {
        this.previousState = previousState;
        this.currentState = currentState;
        this.action = action;
        this.currentTag = currentTag;
    }

    
    /**
     * Tells if the transition has an associated action.
     * 
     * @return <code>true</code> if an action has been associated to the transition
     */
    public boolean hasAction()
    {
        return action != null;
    }

    
    /**
     * @return Returns the action associated with the transition
     */
    public Action getAction()
    {
        return action;
    }

    
    /**
     * @return The current state
     */
    public int getCurrentState()
    {
        return currentState;
    }

    
    /**
     * @return The previous state
     */
    public int getPreviousState()
    {
        return previousState;
    }

    
    /**
     * @param statesEnum Starting state.
     * @return A representation of the transition as a string.
     */
    public String toString( States statesEnum )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "Transition from state <" ).append( statesEnum.getState( previousState ) ).append( "> " );
        sb.append( "to state <" ).append( statesEnum.getState( currentState ) ).append( ">, " );
        sb.append( "tag <" ).append( Asn1StringUtils.dumpByte( (byte)currentTag ) ).append( ">, " );
        sb.append( "action : " );
        
        if ( action == null )
        {
            sb.append( "no action" );
        }
        else
        {
            sb.append( action );
        }
        
        return sb.toString();
    }
}
