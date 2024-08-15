package com.infinity323.bookstore_service.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infinity323.bookstore_service.domain.Order;
import com.infinity323.bookstore_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Queries and returns orders by customer ID from the database.
     * 
     * @param customerId customer ID
     * @return list of orders
     */
    public List<Order> findOrdersByCustomerId(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        log.info("Retrieved {} orders by customer ID {}", customerId);
        return orders;
    }

    /**
     * Reads orders from an Excel spreadsheet and saves them into the database.
     * e.g.:
     * |ORDER_NUMBER|CUSTOMER_ID|FULFILLED |PAYMENT_TIMESTAMP
     * |123---------|123--------|1---------|1723661157
     * 
     * @param file orders spreadsheet
     * @return error map
     */
    @Transactional
    public Map<String, String> importOrders(MultipartFile file) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            int totalCoumns = sheet.getRow(0).getLastCellNum();
            Iterator<Row> rowsIterator = sheet.iterator();
            int rowNumber = 0;
            while (rowsIterator.hasNext()) {
                Row currentRow = rowsIterator.next();
                try {
                    if (rowNumber != 0) {
                        Order order = new Order();
                        for (int cellCounter = 0; cellCounter < totalCoumns; cellCounter++) {
                            Cell currentCell;
                            if (currentRow.getCell(cellCounter) == null) {
                                currentCell = currentRow.createCell(cellCounter);
                            } else {
                                currentCell = currentRow.getCell(cellCounter);
                            }
                            switch (cellCounter) {
                                case 0: // order_number
                                    order.setOrderNumber(currentCell.getStringCellValue());
                                    break;
                                case 1: // customer_id
                                    order.setCustomerId((long) currentCell.getNumericCellValue());
                                    break;
                                case 2: // fulfilled
                                    if (currentCell.getNumericCellValue() == 1) {
                                        order.setIsFulfilled(true);
                                    } else {
                                        order.setIsFulfilled(false);
                                    }
                                    break;
                                case 3: // payment_timestamp
                                    ZonedDateTime paymentTimestamp = currentCell.getLocalDateTimeCellValue()
                                            .atZone(ZoneId.of("CST"));
                                    order.setPaymentTimestamp(paymentTimestamp);
                                    break;
                                default:
                                    break;
                            }
                        }
                        orderRepository.save(order);
                    }
                    rowNumber++;
                } catch (Exception e) {
                    log.error("Exception occurred while saving order", e);
                    errorMap.put(currentRow.getCell(0).getStringCellValue(), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Exception occurred", e);
            errorMap.put("N/A", e.getMessage());
        }
        return errorMap;
    }

}
