package com.stack.medex.enums;

public enum GenderType {
    MALE(1),FEMALE(2);
    final int state;

    GenderType(int state) {
        this.state = state;
    }
}
