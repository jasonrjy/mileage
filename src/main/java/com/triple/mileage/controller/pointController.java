package com.triple.mileage.controller;

import com.triple.mileage.error.CustomException;
import com.triple.mileage.model.ResponseVO;
import com.triple.mileage.model.Values;
import com.triple.mileage.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/point", produces = "application/text; charset=utf8")
@RequiredArgsConstructor
public class pointController {

    private final PointService pointService;

    @GetMapping("")
    public ResponseEntity<ResponseVO> getUserPoint(@RequestParam(value="id") String userId) {
        ResponseVO response = new ResponseVO();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Integer> data = new HashMap<String, Integer>();

        try {
            int point = this.pointService.getUserPoint(userId);
            data.put("point", point);

            response.setData(data);
            response.setCode(Values.SUCCESS.getCode());
            response.setMessage(Values.SUCCESS.getMessage());
        } catch( CustomException c) {
            response.setCode(c.getErrorCode().getCode());
            response.setMessage(c.getErrorCode().getMessage());
        } catch ( Exception e) {
            response.setCode(Values.EXCEPTION.getCode());
            response.setMessage(Values.EXCEPTION.getMessage());
        }



        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }
}
