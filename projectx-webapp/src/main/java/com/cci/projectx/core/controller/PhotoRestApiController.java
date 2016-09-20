package com.cci.projectx.core.controller;

import com.cci.projectx.core.RandomUtil;
import com.cci.projectx.core.annotation.IgnoreAuth;
import com.cci.projectx.core.model.PhotoModel;
import com.cci.projectx.core.service.PhotoService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.cci.projectx.core.ResponseEnvelope;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class PhotoRestApiController {

    private final Logger logger = LoggerFactory.getLogger(PhotoRestApiController.class);

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private PhotoService photoService;

    @Value("${photo.base.url}")
    private String baseDirectory;

    @IgnoreAuth
    @GetMapping(value = "/photo/{id}")
    public void getPhotoById(@PathVariable Long id,
                             @RequestParam(value = "width", required = false) Integer width,
                             @RequestParam(value = "height", required = false) Integer height,
                             HttpServletResponse response) {

        PhotoModel photoModel = photoService.findByPrimaryKey(id);
        response.setContentType(photoModel.getContentType());
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = FileUtils.openInputStream(new File(baseDirectory + "/" + photoModel.getPath() + "." + photoModel.getExtension()));
            if (null == width && null == height) {
                IOUtils.copy(inputStream, outputStream);
            } else {
                Thumbnails.of(inputStream)
                        .size(width, height)
                        .toOutputStream(outputStream);
            }

        } catch (IOException e) {
            logger.error("expected error ", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

    }

    @IgnoreAuth
    @GetMapping(value = "/photo/jpg/{path:.+}")
    public void getPhotoExtensionById(@PathVariable String path,
                                      @RequestParam(value = "width", required = false) Integer width,
                                      @RequestParam(value = "height", required = false) Integer height,
                                      HttpServletResponse response) {

        path = path.substring(0, path.indexOf("."));
        PhotoModel photoModel = photoService.findByPrimaryKey(Long.parseLong(path));
        response.setContentType(photoModel.getContentType());
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = FileUtils.openInputStream(new File(baseDirectory + "/" + photoModel.getPath() + "." + photoModel.getExtension()));
            if (null == width && null == height) {
                IOUtils.copy(inputStream, outputStream);
            } else {
                Thumbnails.of(inputStream)
                        .size(width, height)
                        .toOutputStream(outputStream);
            }

        } catch (IOException e) {
            logger.error("expected error ", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

    }


    @PostMapping(value = "/{photoType}/photo")
    public ResponseEnvelope<List<Long>> uploadPhoto(@RequestParam MultipartFile[] files,
                                              @PathVariable Long photoType) {
        List<Long> listId = new ArrayList<>();
        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    PhotoModel photoModel = new PhotoModel();
                    photoModel.setCreateTime(new Date());
                    photoModel.setName(file.getName());
                    photoModel.setPhotoType(photoType);
                    photoModel.setContentType(file.getContentType());
                    String filename = RandomUtil.createRandom(true, 12);
                    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                    String path = baseDirectory + "/" + filename + "." + extension;
                    photoModel.setPath(filename);
                    photoModel.setExtension(extension);
                    OutputStream outputStream = null;
                    InputStream inputStream = null;
                    try {
                        inputStream = file.getInputStream();
                        outputStream = FileUtils.openOutputStream(new File(path));
                        IOUtils.copy(inputStream, outputStream);

                    } catch (IOException e) {
                        logger.error("expected error ", e);
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly(outputStream);
                    }
                    photoService.createSelective(photoModel);
                    listId.add(photoModel.getId());
                }
            }

        }
        ResponseEnvelope<List<Long>> responseEnv = new ResponseEnvelope<>(listId, true);
        return responseEnv;
    }

    @DeleteMapping(value = "/photo/{id}")
    public ResponseEnvelope<String> deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        ResponseEnvelope<String> responseEnv = new ResponseEnvelope<>("ok", true);
        return responseEnv;
    }

}
