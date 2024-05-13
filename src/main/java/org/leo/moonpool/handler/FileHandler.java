package org.leo.moonpool.handler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.leo.moonpool.entity.AnswerImage;
import org.leo.moonpool.entity.QuizImage;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FileHandler {
    @Value("${com.moonpool.upload.path}")
    private String uploadPath;

    public void addQuizImageString(String fileName){
        QuizImage quizImage = QuizImage.builder()
                .quizName(fileName)
                .build();
    }
    public void addAnswerImageString(String fileName){
        AnswerImage answerImage = AnswerImage.builder()
                .answerName(fileName)
                .build();
    }
    @PostConstruct
    public void folderInit(){
        File saveFolder = new File(uploadPath);
        if(!saveFolder.exists()){
            saveFolder.mkdir();
        }
        uploadPath = saveFolder.getAbsolutePath();
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{
        List<String> imgNames = new ArrayList<>(); //이미지 이름을 넣을 빈 배열 생성
        if(files == null || files.isEmpty()){
            return imgNames; // 이미지가 널이나 비어있으면 그냥 빈 리스트를 반환
        }
        for(MultipartFile file : files){
            String savedName = UUID.randomUUID().toString()+"_MP_"+file.getOriginalFilename();
            Path savePath = Paths.get(uploadPath,savedName);
            try {
                Files.copy(file.getInputStream(),savePath);
                file.transferTo(savePath);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return imgNames;
    }

    public ResponseEntity<Resource> getFile(String fileName){
        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
        if (!resource.isReadable()){
             resource = (Resource) new FileSystemResource(uploadPath+File.separator+"_Error.jpeg");
        }
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type",Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().headers(headers).body(resource); // 200
    }

    public void deleteFiles(List<String> fileNames){
        if (fileNames == null || fileNames.isEmpty()){
            return ;
        }
        fileNames.forEach(fileName -> {
            String imgFileName = "_MP_" + fileName;
            Path savePath = Paths.get(uploadPath, imgFileName);
        });
    }





}
