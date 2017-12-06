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

package com.waffleware.example.game.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.input.InputMapper;
import com.waffleware.example.game.states.player.PlayerMovementState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 *
 *  @author    Paul Speed
 */
public class InGameMenuState
  extends BaseAppState
{
    static Logger log = LoggerFactory.getLogger(InGameMenuState.class);

    private final Consumer<Boolean> setGameEnabled;

    private Container   mainWindow;
    private TabbedPanel tabs;

    private List<Action> sessionActions = new ArrayList<>();
    private Container sessionButtons;

    /**
     * @param setGameEnabled Enables and disables the game while the help state is enabled.
     */
    public InGameMenuState(final boolean enabled, final Consumer<Boolean> setGameEnabled) {
        setEnabled(enabled);
        this.setGameEnabled = setGameEnabled;
        sessionActions.add(new CallMethodAction("Resume", this, "resume"));
        sessionActions.add(new CallMethodAction("Exit", this, "exitGame"));
    }

    /**
     *  Returns the tabbed panel used in the in-game menu.  This lets other
     *  states potentially add their own in-game menu tabs.
     */ 
    public TabbedPanel getTabs() {
        return tabs;
    }

    protected void resume() {
        setEnabled(false);
    }

    protected void exitGame() {
        log.info("exitGame()");
        getState(OptionPanelState.class).show("Exit Game?", "Really exit the whole game?",
                                              new CallMethodAction("Yes",
                                                                   getApplication(),
                                                                   "stop"),
                                              new EmptyAction("No"),
                                              new EmptyAction("Cancel"));
    }
 
    public List<Action> getSessionActions() {
        return sessionActions;
    }

    public void toggleEnabled() {
        setEnabled(!isEnabled());
    }
    
    @Override   
    protected void initialize( Application app ) {
        mainWindow = new Container();
         
        tabs = mainWindow.addChild(new TabbedPanel());
        
        sessionButtons = tabs.addTab("Game Session", new Container());
        for( Action a : getSessionActions() ) {
            sessionButtons.addChild(new ActionButton(a));
        }
           
        // Calculate a standard scale and position from the app's camera
        // height
        int      height = app.getCamera().getHeight();
        Vector3f pref   = mainWindow.getPreferredSize().clone();

        // With a slight bias toward the top        
        float y = height * 0.6f + pref.y * 0.5f;
                
        InputMapper inputMapper = GuiGlobals.getInstance().getInputMapper();
        inputMapper.addDelegate(MainGameFunctions.F_IN_GAME_MENU, this, "toggleEnabled");
    }
 
    @Override   
    protected void cleanup( Application app ) {
    
        InputMapper inputMapper = GuiGlobals.getInstance().getInputMapper();
        inputMapper.removeDelegate(MainGameFunctions.F_IN_GAME_MENU, this, "toggleEnabled");
    }
    
    @Override   
    protected void onEnable() {
        Node gui = ((SimpleApplication)getApplication()).getGuiNode();
        gui.attachChild(mainWindow);
        GuiGlobals.getInstance().requestFocus(mainWindow);

        this.setGameEnabled.accept(false);
    }
    
    @Override   
    protected void onDisable() {
        mainWindow.removeFromParent();

        this.setGameEnabled.accept(true);
    }
}
