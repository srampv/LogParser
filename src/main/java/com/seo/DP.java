package com.seo;

public enum DP {
    MALICIOUS, CLEAN, UNKNOWN;

    public int getVal() {
        if (this.MALICIOUS == DP.MALICIOUS) {
            return 1;
        } else if (this.CLEAN == DP.CLEAN) {
            return 2;
        } else {
            return 3;
        }
    }
}
