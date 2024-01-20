package com.saf.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model){
        model.addAttribute("files",storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,"serveFile",
                        path.getFileName().toString()).build().toUri().toString()).toList());

        return "uploadForm";
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName){

        Resource file = storageService.loadAsResource(fileName);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFilename()).body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){

        storageService.store(file);

        redirectAttributes.addFlashAttribute(
                "message","You successfully uploaded "+ file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFoundException(StorageFileNotFoundException exc){
        return ResponseEntity.notFound().build();
    }
}
