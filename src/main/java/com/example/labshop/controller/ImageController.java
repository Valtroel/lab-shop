package com.example.labshop.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

//    @GetMapping("/uploads/saw-photo-1.jpg")
//    public void sawSliderFirst(HttpServletResponse servletResponse) throws IOException {
//       // ServletContext servletContext = new ServletContext(servletResponse);
//        //InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
//        servletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        IOUtils.copy(in, servletResponse.getOutputStream());
//    }
}
