package com.xyb.web;

import com.xyb.domain.entity.FileEntity;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.FileSrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/attachment")
public class UploadController {
    @Autowired
    private FileSrcService fileSrcService;

    @Value("${upload.uri}")
    private String uploadPath="/Users/rrd/Documents/xyb_server/target/classes/static/";
    String preSuffix="/api/sh/static/";
    /**
     * 单文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public RestInfo upload(@RequestParam("file") MultipartFile file, String pathInfo,HttpServletRequest request) {
        if (!file.isEmpty()) {
            String fileOriginalName = file.getOriginalFilename();
            String newfilename =System.currentTimeMillis()+ fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            File saveFile = new File(uploadPath+newfilename);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            BufferedOutputStream out=null;
            try {
                 out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                FileEntity fileEntity=new FileEntity();
                fileEntity.setUrl(preSuffix+newfilename);
                return  new RestInfo(fileSrcService.save(fileEntity));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw  new MyException("上传失败," + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                throw  new MyException("上传失败," + e.getMessage());
            }finally {
                if(out!=null)
                {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return new RestInfo("上传失败，因为文件为空.");
        }
    }
    /**
     * 多文件上传
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadFiles")
    @ResponseBody
    public RestInfo uploadFiles(@RequestParam("file") ArrayList<MultipartFile> files, HttpServletRequest request) throws IOException {
        if(files==null || files.size()<=0)
        {
            return new RestInfo("上传失败，因为文件为空.");
        }
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String fileOriginalName = file.getOriginalFilename();
                    String newFileName =System.currentTimeMillis()+ fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
                    File saveFile = new File(uploadPath+ newFileName);
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    FileEntity fileEntity=new FileEntity();
                    fileEntity.setUrl(preSuffix+ newFileName);
                    fileSrcService.save(fileEntity);
                } catch (Exception e) {
                    return new RestInfo("第 " + i + " 个文件上传有错误" + e.getMessage());
                }finally {
                    if(stream!=null)
                    {
                        try {
                            stream.flush();
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                return new RestInfo("第 " + i + " 个文件为空");
            }
        }
        return new RestInfo("所有文件上传成功");
    }
}
