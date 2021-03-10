package com.study.egovspringbootmybatis.manager;

import com.study.egovspringbootmybatis.vo.QueueEntry;
import com.study.egovspringbootmybatis.worker.WorkerProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class QueueConsumer extends QueueManager  {
    private final WorkerProcess workerProcess;

    @Scheduled(cron = "*/1 * * * * *")
    @Override
    public QueueEntry takeQueue() throws InterruptedException, IOException {
        QueueEntry queueEntry = super.takeQueue();
        if (queueEntry==null)
            log.info("##### Empty Priority Blocking Queue");
        else
            workerProcess.saveImgList(queueEntry.getFile());
        return queueEntry;
    }
}
