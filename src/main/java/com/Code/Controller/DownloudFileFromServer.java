package com.Code.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by sngv on 01/02/15.
 */
@RestController
@RequestMapping("/downloud")
public class DownloudFileFromServer {
    private static final int BUFFER_SIZE = 4096;


//    @RequestMapping(value = "/downloudfile/{filePath}/{type}", method = RequestMethod.GET)
//    public ResponseEntity<String> doDownload(@PathVariable String filePath,
//                                             @PathVariable String type, HttpServletRequest request,
//                                             HttpServletResponse response) throws IOException {
//
//        filePath = "/FilesCodes/" + filePath + "." + type;
//
//        try {
//
//            // get absolute path of the application
//            ServletContext context = request.getServletContext();
//            String appPath = context.getRealPath("");
//            System.out.println("appPath = " + appPath);
//
//            // construct the complete absolute path of the file
//            String fullPath = appPath + filePath;
//            File downloadFile = new File(fullPath);
//            FileInputStream inputStream = new FileInputStream(downloadFile);
//
//            // get MIME type of the file
//            String mimeType = context.getMimeType(fullPath);
//            if (mimeType == null) {
//                // set to binary type if MIME mapping not found
//                mimeType = "application/octet-stream";
//            }
//            System.out.println("MIME type: " + mimeType);
//
//            // set content attributes for the response
//            response.setContentType(mimeType);
//            response.setContentLength((int) downloadFile.length());
//
//            // set headers for the response
//            String headerKey = "Content-Disposition";
//            String headerValue = String.format("attachment; filename=\"%s\"",
//                    downloadFile.getName());
//            response.setHeader(headerKey, headerValue);
//
//            // get output stream of the response
//            OutputStream outStream = response.getOutputStream();
//
//            byte[] buffer = new byte[BUFFER_SIZE];
//            int bytesRead = -1;
//
//            // write bytes read from the input stream into the output stream
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outStream.write(buffer, 0, bytesRead);
//            }
//
//            inputStream.close();
//            outStream.close();
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

}
