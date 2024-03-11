package com.assignment.inncircles.model

enum class CallType(val value: Int) {
    INCOMING(1),
    OUTGOING(2),
    MISSED(3),
    VOICEMAIL(4),
    REJECTED(5),
    BLOCKED(6),
    ANSWERED_EXTERNALLY(7),
    UNKNOWN(-1);

    companion object {
        fun fromInt(value: Int): CallType {
            return entries.firstOrNull { it.value == value } ?: UNKNOWN
        }
    }
}
