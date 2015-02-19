package com.yahoo.tweets.utils;

/**
 * Created by rakeshch on 2/14/15.
 */
public class PaginationParams {

    private int sinceId;
    private long minIdSoFar;

    public PaginationParams(int sinceId, int minIdSoFar) {
        this.sinceId = sinceId;
        this.minIdSoFar = minIdSoFar;
    }

    public int getSinceId() {
        return sinceId;
    }

    public void setSinceId(int sinceId) {
        this.sinceId = sinceId;
    }

    public long getMinIdSoFar() {
        return minIdSoFar;
    }

    public void setMinIdSoFar(long minIdSoFar) {
        this.minIdSoFar = minIdSoFar;
    }
}
