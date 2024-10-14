package com.uber.choreographer.core.api

interface Slot {
    val type: SlotType
}

typealias SlotType = String