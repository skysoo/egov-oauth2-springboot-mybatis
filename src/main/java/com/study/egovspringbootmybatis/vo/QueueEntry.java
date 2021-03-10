package com.study.egovspringbootmybatis.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.File;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class QueueEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String queueType;
    private Integer priority;
    private File file;

    @Builder
    public QueueEntry(String queueType, Integer priority, File file) {
        this.queueType = queueType;
        this.priority = priority;
        this.file = file;
    }
}
