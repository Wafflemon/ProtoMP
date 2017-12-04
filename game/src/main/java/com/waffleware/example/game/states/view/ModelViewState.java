/*
 * $Id$
 * 
 * Copyright (c) 2016, Simsilica, LLC
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the 
 *    distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.waffleware.example.game.states.view;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.waffleware.example.communication.es.BodyPosition;
import com.waffleware.example.communication.es.ObjectType;
import com.waffleware.example.communication.es.SphereShape;
import com.waffleware.example.game.states.TimeState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *  Displays the models for the various physics objects.
 *
 *  @author    Paul Speed
 */
public class ModelViewState extends BaseAppState
{

    static Logger log = LoggerFactory.getLogger(ModelViewState.class);

    private final EntityData entityData;
    private TimeState  timeState;
    
    private Node modelRoot;
 
    private Map<EntityId, Spatial> modelIndex = new HashMap<>();

    // TODO Add a means for retrieving a list of the models in the current zone.
    public ModelViewState(final EntityData entityData)
    {
        this.entityData = entityData;
    }

    public Spatial getModel(EntityId id ) {
        return modelIndex.get(id);
    }

    @Override
    protected void initialize( Application app ) {
        modelRoot = new Node();
        
        // Retrieve the time source from the network connection
        // The time source will give us a time in recent history that we should be
        // viewing.  This currently defaults to -100 ms but could vary (someday) depending
        // on network connectivity.
        // For more information on this interpolation approach, see the Valve networking
        // articles at:
        // https://developer.valvesoftware.com/wiki/Source_Multiplayer_Networking
        // https://developer.valvesoftware.com/wiki/Latency_Compensating_Methods_in_Client/Server_In-game_Protocol_Design_and_Optimization
        //this.timeSource = getState(ConnectionState.class).getRemoteTimeSource();
        // 
        // We now grab time from the TimeState which wraps the TimeSource to give
        // consistent timings over the whole frame
        this.timeState = getState(TimeState.class);
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    @Override
    public void update( float tpf ) {
    }
}

