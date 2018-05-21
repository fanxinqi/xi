package com.xyb.web;

import com.xyb.domain.entity.FileEntity;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.FileSrcService;
import com.xyb.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attachment")
public class UploadController {
    @Autowired
    private FileSrcService fileSrcService;

    @Value("${upload.uri}")
    private String uploadPath;
    @Value("${upload.preSuffix}")
    private String preSuffix;
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
    @PostMapping(value="/uploadBase64")
    public RestInfo base64UpLoad(@RequestParam(value = "base64Data") String base64Data){
        try{
            String dataPrix = "";
            String data = "";
            if(base64Data == null || "".equals(base64Data)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                String [] d = base64Data.split("base64,");
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else{
                    throw new Exception("上传失败，数据不合法");
                }
            }
            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
                throw new Exception("上传图片格式不合法");
            }
            String tempFileName = UUID.randomUUID().toString() + suffix;

            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);
            try{

                if(FileUtils.writeBytesToFile(uploadPath+tempFileName, bs)){
                    //使用apache提供的工具类操作流
                    FileEntity fileEntity=new FileEntity();
                    fileEntity.setUrl(preSuffix+tempFileName);
                    return  new RestInfo(fileSrcService.save(fileEntity));
                }else {
                    throw new MyException("上传失败，写入文件失败，");
                }
            }catch(Exception ee){
                throw new MyException("上传失败，写入文件失败，"+ee.getMessage());
            }
        }catch (Exception e) {
            throw new MyException("上传失败，写入文件失败");
        }
    }
}
