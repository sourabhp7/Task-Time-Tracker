package task.time.tracker.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import task.time.tracker.model.TaskEntity;
import task.time.tracker.model.TaskFile;
import task.time.tracker.repository.TaskFileRepository;

@Service
public class TaskFileService {

	@Autowired
	private TaskFileRepository taskFileRepository;

	public void upoldTaskFile(List<MultipartFile> files, TaskEntity taskEntity) throws IOException {
		for (MultipartFile file : files) {
			TaskFile fileEntity = new TaskFile();
			fileEntity.setFileName(file.getOriginalFilename());
			fileEntity.setFileType(file.getContentType());
			fileEntity.setData(file.getBytes());
			fileEntity.setFileSize(file.getSize());
			fileEntity.setTaskEntity(taskEntity);
			taskFileRepository.save(fileEntity);
		}
	}

}
