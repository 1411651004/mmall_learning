package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService{

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        //拿到上传文件的一个原始文件名
        String fileName = file.getOriginalFilename();
        //获取文件的扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        //获取上传文件的文件名（这里注意两个人可能上传文件名一样,所以用随机的名来代替）
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        //上传文件的路径
        File fileDir = new File(path);
        //判断文件路径是否存在
        if (!fileDir.exists()){
            //获取一个写的权限
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //创建文件
        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功
            //todo 将targetFile上传到我们的FTP服务器上
            FTPUtil.uploadFile(Lists.<File>newArrayList(targetFile));
            //todo 上传完之后，删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }
}
