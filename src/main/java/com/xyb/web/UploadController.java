package com.xyb.web;

import ch.qos.logback.core.util.FileUtil;
import com.xyb.domain.model.FileUploadModel;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.utils.DateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.math.BigInteger;
import java.util.UUID;

@RestController
@RequestMapping("/attachment")
public class UploadController {

    String uploadPath="/Users/fanxinqi/Desktop/洗衣帮项目/jiukuaijiu/yixibang/xi/target/classes/static/";
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
//            String rootpath = request.getSession().getServletContext().getRealPath("/static");
//            String filepath = "/upload/"+pathInfo+"/" + DateUtils.longToString(System.currentTimeMillis()) + "/";
            String fileOriginalName = file.getOriginalFilename();
//            String newfilename = DateUtils.longToString(System.currentTimeMillis()) + fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newfilename =System.currentTimeMillis()+ fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            File saveFile = new File(uploadPath+newfilename);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            BufferedOutputStream out=null;
            try {
                 out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                FileUploadModel fileUploadModel=new FileUploadModel();
                fileUploadModel.setMessage(saveFile.getName() + " 上传成功");
                fileUploadModel.setUrl("/api/sh/static/"+newfilename);
                return  new RestInfo(fileUploadModel);
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
    public RestInfo uploadFiles(HttpServletRequest request) throws IOException {
        File savePath = new File(request.getSession().getServletContext().getRealPath("/upload/"));
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
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
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    return new RestInfo("第 " + i + " 个文件上传有错误" + e.getMessage());
                }
            } else {
                return new RestInfo("第 " + i + " 个文件为空");
            }
        }
        return new RestInfo("所有文件上传成功");
    }
}
