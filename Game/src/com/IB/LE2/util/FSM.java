package com.IB.LE2.util;

import java.util.EnumSet;
import java.util.Set;

@Deprecated
public class FSM {
	public enum State {

	    Wander {
	        @Override
	        public Set<State> possibleFollowUps() {
	            return EnumSet.of(Attack, Evade);
	        }
	    },

	    Attack {
	        @Override
	        public Set<State> possibleFollowUps() {
	            return EnumSet.of(Evade);
	        }
	    },

	    Evade // final state 

	    ;
	    public Set<State> possibleFollowUps() {
	        //return EnumSet.noneOf(State.class);
	    	return EnumSet.of(Wander);
	    }

	}
}