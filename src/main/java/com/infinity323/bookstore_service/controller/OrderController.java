package com.infinity323.bookstore_service.controller;

import java.util.Map;
import java.util.Objects;

import org.apache.poi.openxml4j.opc.ContentTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infinity323.bookstore_service.domain.ResponseDto;
import com.infinity323.bookstore_service.service.OrderService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * Endpoint to upload and save orders to the database using an Excel
     * spreadsheet.
     * 
     * @param file orders spreadsheet
     * @return data
     */
    @PostMapping("/upload")
    @ApiOperation(value = "Upload Orders", notes = "Endpoint to bulk upload orders using Excel spreadsheet")
    public ResponseEntity<ResponseDto> uploadOrders(@RequestBody MultipartFile file) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (Objects.nonNull(file)) {
                if (Objects.isNull(file.getOriginalFilename())
                        || !file.getContentType().equals(ContentTypes.CUSTOM_XML_PART)) {
                    Map<String, String> errorMap = orderService.importOrders(file);
                    responseDto.setData(errorMap);
                    if (errorMap.isEmpty()) {
                        responseDto.setStatusCode(HttpStatus.OK);
                        return new ResponseEntity<>(responseDto, HttpStatus.OK);
                    } else {
                        responseDto.setStatusCode(HttpStatus.BAD_REQUEST);
                        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    responseDto.setMessage("File extension must be .xlsx");
                    responseDto.setStatusCode(HttpStatus.BAD_REQUEST);
                    return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseDto.setMessage("File is null");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception occurred while uploading orders", e);
            responseDto.setMessage(e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
