package com.triple.mileage.controller;

import com.triple.mileage.entity.*;
import com.triple.mileage.error.CustomException;
import com.triple.mileage.model.*;
import com.triple.mileage.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;


@RestController
@RequestMapping(value = "/events", produces = "application/text; charset=utf8")
@RequiredArgsConstructor
public class EventController{

    private final EventService eventService;

    @PostMapping("/post")
    public ResponseEntity<ResponseVO> event(@RequestBody EventRequest params) {
        ResponseVO response = new ResponseVO();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            if (params.getType() != null && params.getType().equals("REVIEW")) {
                if (params.getAction() == EventAction.ADD) {
                    this.eventService.createEntity(params);
                } else if (params.getAction() == EventAction.MOD) {
                    this.eventService.modifyEntity(params);
                } else if (params.getAction() == EventAction.DELETE) {
                    this.eventService.deleteEntity(params);
                }

                response.setCode(Values.SUCCESS.getCode());
                response.setMessage(Values.SUCCESS.getMessage());

            }
        } catch ( CustomException c) {
            System.out.println("Error Code: " + c.getErrorCode().getMessage());
            response.setCode(c.getErrorCode().getCode());
            response.setMessage(c.getErrorCode().getMessage());

        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
            response.setCode(Values.EXCEPTION.getCode());
            response.setMessage(Values.EXCEPTION.getMessage());
        }

        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }

}
