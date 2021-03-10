package com.study.egovspringbootmybatis.manager;

import com.study.egovspringbootmybatis.vo.QueueEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

public class QueueManager {
    @Autowired
    private PriorityBlockingQueue<QueueEntry> priorityBlockingQueue;

    public void putQueue(QueueEntry queueEntry){
        priorityBlockingQueue.put(queueEntry);
    }

    public QueueEntry takeQueue() throws InterruptedException, IOException {
        if (!priorityBlockingQueue.isEmpty())
            return priorityBlockingQueue.take();
        else
            return null;
    }
}
