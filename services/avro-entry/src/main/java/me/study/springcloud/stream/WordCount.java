/*
 * Copyright (c) 2019
 * @Author:chandler song, email:chandler605@outlook.com
 * @LastModified:2019-08-04T22:02:52.933+08:00
 * LGPL licence
 *
 */

package me.study.springcloud.stream;

import java.util.Date;

public class WordCount {

    private String word;

    private long count;

    private Date start;

    private Date end;

    @Override
    public String toString() {
        return "WordCount{" + "word='" + word + '\'' +
                ", count=" + count +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public WordCount() {

    }

    public WordCount(String word, long count, Date start, Date end) {
        this.word = word;
        this.count = count;
        this.start = start;
        this.end = end;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
