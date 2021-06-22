package com.moneycounter.counter.controller;

import com.moneycounter.counter.models.Constant;
import com.moneycounter.counter.models.Month;
import com.moneycounter.counter.models.Position;
import com.moneycounter.counter.services.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CounterController {

    @Autowired
    private CounterService counterService;

    @GetMapping("/")
    public Month getMonth(@RequestParam int monthNum,
                          @RequestParam int yearNum) {
        if (monthNum == 0 || yearNum == 0) {
            return counterService.getCurrentMonth();
        }
        return counterService.getMonth(monthNum, yearNum);
    }

    @PostMapping("/add")
    public Month addPosition(@RequestBody Position position) {
        return counterService.addPosition(position);
    }

    @PutMapping("/change")
    public Month changePosition(@RequestBody Position position) {
        return counterService.changePosition(position);
    }

    @DeleteMapping("/delete")
    public Month deletePosition(@RequestParam Long id) {
        return counterService.deletePosition(id);
    }

    @GetMapping("/constant")
    public List<Constant> getConstants() {
        return counterService.getConstants();
    }

    @PostMapping("/constant/add")
    public List<Constant> addConstant(@RequestBody Constant constant, @RequestParam boolean toThisMonth) {
        return counterService.addConstant(constant, toThisMonth);
    }

    @PutMapping("/constant/change")
    public List<Constant> changeConstant(@RequestBody Constant constant) {
        return counterService.changeConstant(constant);
    }

    @DeleteMapping("/constant/delete")
    public List<Constant> deleteConstant(@RequestParam Long id) {
        return counterService.deleteConstant(id);
    }
}
