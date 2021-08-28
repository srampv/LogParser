package com.seo;

import java.util.Objects;

public class JsonData {
    private String ts;
    private String pt;
    private String si;
    private String uu;
    private String bg;
    private String sha;
    private String nm;
    private String ph;
    private DP dp;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getUu() {
        return uu;
    }

    public void setUu(String uu) {
        this.uu = uu;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public DP getDp() {
        return dp;
    }

    public void setDp(DP dp) {
        this.dp = dp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonData)) return false;
        JsonData jsonData = (JsonData) o;
        return Objects.equals(getPh().trim(), jsonData.getPh().trim()) && Objects.equals(getNm().trim(), jsonData.getNm().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNm().trim(), getPh().trim());
    }
}
