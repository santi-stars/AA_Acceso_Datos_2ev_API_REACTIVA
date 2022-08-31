package com.svalero.gestitaller2.controller;

import com.svalero.gestitaller2.domain.WorkOrder;
import com.svalero.gestitaller2.domain.dto.WorkOrderDTO;
import com.svalero.gestitaller2.exception.*;
import com.svalero.gestitaller2.service.WorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    private final Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

    @GetMapping("/orders")
    public ResponseEntity<Flux<WorkOrder>> getOrders(@Valid @RequestParam(name = "name_surname", required = false) String nameSurname,
                                                     @Valid @RequestParam(name = "brand_model", required = false) String brandModel,
                                                     @Valid @RequestParam(name = "license_plate", required = false) String licensePlate,
                                                     @Valid @RequestParam(name = "all", defaultValue = "false") boolean all) {
        Flux<WorkOrder> orders;
        logger.info("Inicio getOrders");
        if (all) {
            logger.info("Mostrado de todas las órdenes");
            orders = workOrderService.findAllOrders();
        } else {
            logger.info("Filtrado por parámetro: name_surname=" + nameSurname + "// brand_model=" + brandModel + "// license_plate=" + licensePlate);
            orders = workOrderService.findAllOrders(nameSurname, brandModel, licensePlate);
        }
        logger.info("Fin getOrders");
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Mono<WorkOrder>> getById(@Valid @PathVariable long id) throws WorkOrderNotFoundException {
        logger.info("Inicio getById " + id);
        Mono<WorkOrder> order = workOrderService.findById(id);
        logger.info("Fin getById " + id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Mono<WorkOrder>> deleteOrder(@Valid @PathVariable long id) throws WorkOrderNotFoundException {
        logger.info("Inicio deleteOrder " + id);
        Mono<WorkOrder> order = workOrderService.deleteOrder(id);
        logger.info("Fin deleteOrder " + id);
        return ResponseEntity.ok(order);
    }

    // DTO
    @PostMapping("/order")
    public ResponseEntity<Mono<WorkOrder>> addOrder(@Valid @RequestBody WorkOrderDTO newWorkOrderDTO) throws
            BikeNotFoundException, ClientNotFoundException {
        logger.info("Inicio addOrder");
        Mono<WorkOrder> newOrder = workOrderService.addOrder(newWorkOrderDTO);
        logger.info("Fin addOrder");
        return ResponseEntity.ok(newOrder);
    }

    // DTO
    @PutMapping("/order/{id}")
    public ResponseEntity<Mono<WorkOrder>> modifyOrder(@Valid @RequestBody WorkOrderDTO workOrderDTO, @Valid @PathVariable long id) throws WorkOrderNotFoundException,
            BikeNotFoundException, ClientNotFoundException {
        logger.info("Inicio modifyOrder " + id);
        Mono<WorkOrder> newOrder = workOrderService.modifyOrder(id, workOrderDTO);
        logger.info("Fin modifyOrder " + id);
        return ResponseEntity.ok(newOrder);
    }

    @ExceptionHandler(WorkOrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(WorkOrderNotFoundException onfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, onfe.getMessage());
        logger.info(onfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.generalError(6, "Internal server error");
        logger.error(exception.getMessage(), exception);
        logger.error(Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.error(Arrays.toString(ex.getStackTrace()));
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }
}
