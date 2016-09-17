package com.cci.projectx.core.controller;

import com.cci.projectx.core.RandomUtil;
import com.cci.projectx.core.annotation.IgnoreAuth;
import com.cci.projectx.core.model.AudioModel;
import com.cci.projectx.core.service.AudioService;
import com.wlw.pylon.web.rest.ResponseEnvelope;
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
import java.util.Date;

@RestController
@RequestMapping("/")
public class AudioRestApiController {
	private final Logger logger = LoggerFactory.getLogger(AudioRestApiController.class);

	@Autowired
	private AudioService audioService;

	@Value("${audio.base.url}")
	private String baseDirectory;

	@IgnoreAuth
	@GetMapping(value = "/audio/{id}")
	public void getAudioById(@PathVariable Long id,
							 HttpServletResponse response) {

		AudioModel audioModel = audioService.findByPrimaryKey(id);
		response.setContentType(audioModel.getContentType());
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = response.getOutputStream();
			inputStream = FileUtils.openInputStream(new File(baseDirectory + "/" + audioModel.getPath() + "." + audioModel.getExtension()));
			IOUtils.copy(inputStream, outputStream);

		} catch (IOException e) {
			logger.error("expected error ", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}

	@IgnoreAuth
	@GetMapping(value = "/audio/extension/{path:.+}")
	public void getAudioByExtensionId(@PathVariable String path,
									  HttpServletResponse response) {

		path = path.substring(0, path.indexOf("."));
		AudioModel audioModel = audioService.findByPrimaryKey(Long.parseLong(path));
		response.setContentType(audioModel.getContentType());
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = response.getOutputStream();
			inputStream = FileUtils.openInputStream(new File(baseDirectory + "/" + audioModel.getPath() + "." + audioModel.getExtension()));
			IOUtils.copy(inputStream, outputStream);

		} catch (IOException e) {
			logger.error("expected error ", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}


	@PostMapping(value = "/audio/{filename}")
	public ResponseEnvelope<Long> uploadAudio(@RequestParam MultipartFile file,
											  @PathVariable String filename
											 ) {
		AudioModel audioModel = new AudioModel();
		audioModel.setCreateTime(new Date());
		audioModel.setContentType(file.getContentType());

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fn = RandomUtil.createRandom(true, 12);
		String path = baseDirectory + "/" + fn + "." + extension;
		audioModel.setPath(fn);
		audioModel.setExtension(extension);
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			filename = new String(filename.getBytes("ISO-8859-1"), "utf-8");
			audioModel.setName(filename);
			inputStream = file.getInputStream();
			outputStream = FileUtils.openOutputStream(new File(path));
			IOUtils.copy(inputStream, outputStream);

		} catch (IOException e) {
			logger.error("expected error ", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}

		audioService.createSelective(audioModel);
		ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(audioModel.getId(), true);
		return responseEnv;
	}

	@DeleteMapping(value = "/audio/{id}")
	public ResponseEnvelope<String> deleteAudio(@PathVariable Long id) {
		audioService.deleteAudio(id);
		ResponseEnvelope<String> responseEnv = new ResponseEnvelope<>("ok", true);
		return responseEnv;
	}


}
