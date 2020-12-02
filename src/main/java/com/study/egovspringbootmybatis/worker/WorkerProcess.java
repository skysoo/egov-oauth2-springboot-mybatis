package com.study.egovspringbootmybatis.worker;

import com.study.egovspringbootmybatis.config.CommonConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class WorkerProcess {

    private final CommonConfiguration commonConfiguration;


    public List<File> getImgList() throws NotDirectoryException {
        String filePath = commonConfiguration.getFilePath();
        File file = new File(filePath);

        if (!file.isDirectory())
            throw new NotDirectoryException(filePath);

        return Arrays.stream(file.listFiles()).collect(Collectors.toList());
    }

    public void saveImgList(File file) throws IOException {
        String saveFilePath = commonConfiguration.getFileSavePath();
        String saveFileName = "COPY_"+file.getName();
        byte[] bytes = FileUtils.readFileToByteArray(file);
        Files.write(Paths.get(saveFilePath+File.separator+saveFileName), bytes);
        log.info("##### Saved the Img - {}", saveFilePath+File.separator+saveFileName);
    }
}
