package io.github.talelin.latticy.common.enumeration;

public enum StandardOrNotEnum {
    /**
     * 标准
     */
    STANDARD(1),
    /**
     * 非标准
     */
    NOT_STANDARD(0);

    private int value;

    StandardOrNotEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
