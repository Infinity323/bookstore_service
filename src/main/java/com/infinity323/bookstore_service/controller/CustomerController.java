package com.infinity323.bookstore_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinity323.bookstore_service.domain.ResponseDto;
import com.infinity323.bookstore_service.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/customer")
@Api(tags = "Customer API")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final OrderService orderService;

    /**
     * Endpoint to retrieve a customer's orders.
     * 
     * @param id customer party ID
     * @return data
     */
    @GetMapping("/{id}/orders")
    @ApiOperation(value = "Orders", notes = "Endpoint to retrieve a customer's orders")
    public ResponseEntity<ResponseDto> findOrdersByCustomerPartyId(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(orderService.findOrdersByCustomerPartyId(id));
        responseDto.setStatusCode(HttpStatus.OK);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
