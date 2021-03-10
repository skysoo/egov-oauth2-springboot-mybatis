package com.study.egovspringbootmybatis.controller;

import com.study.egovspringbootmybatis.manager.QueueProducer;
import com.study.egovspringbootmybatis.utility.UT;
import com.study.egovspringbootmybatis.vo.QueueEntry;
import com.study.egovspringbootmybatis.worker.WorkerProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class DataController {

    private final WorkerProcess workerProcess;
    private final QueueProducer queueProducer;

    /**
     * Spring Scheduling 을 이용하여 지정 시간마다 Queue 에 값이 있는지 확인한 후, 값이 있다면 작업을 처리하는 로직
     **/
    @GetMapping("/img-list")
    public void getImgList(@RequestParam String priority) throws IOException {
        // TODO: 2020-12-02 입력 파라미터 검증
        if (!UT.priorityPattern.matcher(priority).matches())
            throw new ValidationException("##### priority value is wrong. value = " + priority);

        // TODO: 2020-12-02 지정 경로에서 이미지 리스트 업로드
        List<File> imgList = workerProcess.getImgList();

        // TODO: 2020-12-02 업로드 데이터 Queue에 담기
        for (File file : imgList){
            if (!UT.imageFilePattern.matcher(file.getName()).matches())
                log.warn("##### Not Support this file format that {}", file.getName());
            else {
                QueueEntry queueEntry = QueueEntry.builder()
                        .queueType("IMG")
                        .file(file)
                        .priority(Integer.valueOf(priority))
                        .build();
                queueProducer.putQueue(queueEntry);
            }
        }
    }

    // TODO: 2020-12-02 .csv 파일을 읽어서 Spring Batch를 적용하여 DB에 데이터를 넣는 기능
    @GetMapping("/csv-list")
    public void getCsvList(@RequestParam String priority){
        if (!UT.priorityPattern.matcher(priority).matches())
            throw new ValidationException("##### priority value is wrong. value = " + priority);


    }


}
