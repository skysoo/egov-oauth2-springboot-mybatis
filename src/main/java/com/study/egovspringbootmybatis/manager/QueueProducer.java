package com.study.egovspringbootmybatis.manager;

import com.study.egovspringbootmybatis.vo.QueueEntry;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class QueueProducer extends QueueManager{
    @Override
    public void putQueue(QueueEntry queueEntry) {
        super.putQueue(queueEntry);
    }
}
