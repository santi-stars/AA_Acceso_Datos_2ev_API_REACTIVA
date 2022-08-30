package com.svalero.gestitaller2.controller;

import com.svalero.gestitaller2.domain.Bike;
import com.svalero.gestitaller2.domain.dto.BikeDTO;
import com.svalero.gestitaller2.exception.BikeNotFoundException;
import com.svalero.gestitaller2.exception.ClientNotFoundException;
import com.svalero.gestitaller2.exception.ErrorResponse;
import com.svalero.gestitaller2.service.BikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BikeController {

    @Autowired
    private BikeService bikeService;

    private final Logger logger = LoggerFactory.getLogger(BikeController.class);

    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getBikes(@Valid @RequestParam(name = "brand", required = false) String brand,
                                               @Valid @RequestParam(name = "model", required = false) String model,
                                               @Valid @RequestParam(name = "license", required = false) String license,
                                               @Valid @RequestParam(name = "all", defaultValue = "false") boolean all) {
        List<Bike> bikes;
        logger.info("Inicio getBikes");
        if (all) {
            logger.info("Mostrado de todas las bikes");
            bikes = bikeService.findAll();
        } else {
            logger.info("Filtrado por brand, model, license");
            bikes = bikeService.findAll(brand, model, license);
        }
        logger.info("Fin getBikes");
        return new ResponseEntity<>(bikes, HttpStatus.OK);
    }

    @GetMapping("/bike/{id}")
    public ResponseEntity<Bike> getById(@PathVariable long id) throws BikeNotFoundException {
        logger.info("Inicio getById " + id);
        Bike bike = bikeService.findById(id);
        logger.info("Fin getById " + id);
        return new ResponseEntity<>(bike, HttpStatus.OK);
    }

    @GetMapping("/client/{id}/bikes")
    public ResponseEntity<List<Bike>> getBikesByClient(@PathVariable long id) throws ClientNotFoundException, BikeNotFoundException {
        logger.info("Inicio getBikesByClient " + id);
        List<Bike> bikes = bikeService.findBikesByClient(id);
        logger.info("Fin getBikesByClient " + id);
        return new ResponseEntity<>(bikes, HttpStatus.OK);
    }

    @PostMapping("/bike")
    public ResponseEntity<Bike> addBike(@Valid @RequestBody BikeDTO bikeDTO) throws ClientNotFoundException {
        logger.info("Inicio addBike");
        Bike newBike = bikeService.addBike(bikeDTO);
        logger.info("Fin addBike");
        return new ResponseEntity<>(newBike, HttpStatus.CREATED);
    }

    @DeleteMapping("/bike/{id}")
    public ResponseEntity<Bike> deleteBike(@PathVariable long id) throws BikeNotFoundException {
        logger.info("Inicio deleteBike " + id);
        Bike bike = bikeService.deleteBike(id);
        logger.info("Fin deleteBike " + id);
        return new ResponseEntity<>(bike, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/bike/{id}")
    public ResponseEntity<Bike> modifyBike(@Valid @RequestBody BikeDTO bikeDTO, @PathVariable long id) throws BikeNotFoundException, ClientNotFoundException {
        logger.info("Inicio modifyBike " + id);
        Bike newBike = bikeService.modifyBike(id, bikeDTO);
        logger.info("Fin modifyBike " + id);
        return new ResponseEntity<>(newBike, HttpStatus.OK);
    }

    @ExceptionHandler(BikeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBikeNotFoundException(BikeNotFoundException bnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, bnfe.getMessage());
        logger.error(Arrays.toString(bnfe.getStackTrace()));
        logger.error(bnfe.getMessage(), bnfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, cnfe.getMessage());
        logger.error(Arrays.toString(cnfe.getStackTrace()));
        logger.error(cnfe.getMessage(), cnfe);
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
