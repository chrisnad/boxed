package com.pubstack.homework.ws.boxed.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Data source.
 */
public class DataSourceUnit {

    private String target_path;

    private String time;

    private long count;

    @JsonIgnore
    private long hierarchy;

    public DataSourceUnit computeHierarchy() {
        hierarchy = target_path.chars().filter(ch -> ch == '/').count();
        return this;
    }

    public DataSourceUnit addCount(long count) {
        this.count += count;
        return this;
    }

    /**
     * Gets hierarchy.
     *
     * @return the hierarchy
     */
    public long getHierarchy() {
        return hierarchy;
    }

    /**
     * Sets hierarchy.
     *
     * @param hierarchy the hierarchy
     */
    public void setHierarchy(long hierarchy) {
        this.hierarchy = hierarchy;
    }

    /**
     * Gets target path.
     *
     * @return the target path
     */
    public String getTarget_path() {
        return target_path;
    }

    /**
     * Sets target path.
     *
     * @param targetPath the target path
     */
    public void setTarget_path(String targetPath) {
        this.target_path = targetPath;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(long count) {
        this.count = count;
    }
}
