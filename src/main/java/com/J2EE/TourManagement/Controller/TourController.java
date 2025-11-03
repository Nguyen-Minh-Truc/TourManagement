package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Model.DTO.UploadFileDTO;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Service.FileService;
import com.J2EE.TourManagement.Service.TourService;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.error.InvalidException;
import com.J2EE.TourManagement.Util.error.StorageException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/tours")
public class TourController {
  private final TourService tourService;

  private final FileService fileService;

  @Value("${mt.upload-file.base-uri}") private String basePath;

  public TourController(TourService tourService, FileService fileService) {
    this.tourService = tourService;
    this.fileService = fileService;
  }

  @PostMapping("/file")
  @ApiMessage("uploadFile")
  public ResponseEntity<UploadFileDTO>
  postMethodName(@RequestParam(name = "file", required = false)
                 MultipartFile file, @RequestParam("folder") String folder)
      throws URISyntaxException, IOException, StorageException {
    // valid
    if (file == null || file.isEmpty()) {
      throw new StorageException("file is empty.");
    }
    String fileName = file.getOriginalFilename();
    List<String> allowedExtensions =
        Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");

    boolean idValid = allowedExtensions.stream().anyMatch(
        item -> fileName.toLowerCase().endsWith(item));

    if (!idValid) {
      throw new StorageException("file không hợp lệ. chỉ những file: " +
                                 allowedExtensions.toString());
    }
    // create folder if not exits
    this.fileService.createDirectory(basePath + folder);
    // store file
    String uploadFile = "http://localhost:8080/storage/" + folder + "/" +
                        this.fileService.store(file, folder);
    UploadFileDTO uploadFileDTO = new UploadFileDTO(uploadFile, Instant.now());
    return ResponseEntity.ok().body(uploadFileDTO);
  }

  // Create
  @PostMapping
  @ApiMessage("thêm tour thành công.")
  public ResponseEntity<Tour> postNewTour(@Valid @RequestBody Tour newTour) {
    Tour tour = tourService.handleSave(newTour);
    return ResponseEntity.status(HttpStatus.CREATED).body(tour);
  }

  // Read
  @GetMapping
  public ResponseEntity<ResultPaginationDTO>
  fetchAllTour(@Filter Specification<Tour> spec, Pageable pageable) {
    return ResponseEntity.ok(tourService.handleGetAll(spec, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTourById(@PathVariable("id") long id)
      throws InvalidException {
    boolean isId = this.tourService.checkIdExists(id);
    if (!isId) {
      throw new InvalidException("không tìm thấy id này.");
    }

    return ResponseEntity.ok().body(this.tourService.handleGetById(id));
  }

  @PutMapping("/{id}")
  @ApiMessage("cập nhật tour thành công.")
  public ResponseEntity<Tour> updateTour(@PathVariable Long id,
                                         @RequestBody Tour tour)
      throws InvalidException {
    return ResponseEntity.ok(tourService.handleUpdate(id, tour));
  }


  @PatchMapping("/{id}/status")
  @ApiMessage("cập nhật tour thành công.")
  public ResponseEntity<Tour> updateStatusTour(@PathVariable Long id,
                                               @RequestBody Tour tour)
      throws InvalidException {
    return ResponseEntity.ok(tourService.handleUpdateStatus(id, tour));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
    tourService.handleDelete(id);
    return ResponseEntity.noContent().build();
  }
}
