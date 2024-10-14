package com.uber.choreographer.pokedex.lib.choreographer

import com.uber.choreographer.core.api.MutableAppState
import com.uber.choreographer.core.impl.DefaultAppState

class PokedexAppState : MutableAppState by DefaultAppState()