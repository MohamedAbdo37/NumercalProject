package com.CSED26.Numercal.Project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CSED26.Numercal.Project.Factory.Numeric;

@RestController
@CrossOrigin
public class Controller {
    Numeric method;

    @GetMapping("/inputs")
    public void Inputs(@RequestParam String method,@RequestParam String equs, @RequestParam int pres){
        Solver solver = new Solver();
        Matrix matrix = solver.parseEquation(equs);
        this.method = solver.getMethod(method);
    }

    @GetMapping("/inputs")
    public void Inputs(@RequestParam String method,@RequestParam String equs, @RequestParam int pres, @RequestParam String format){
        Solver solver = new Solver();
        Matrix matrix = solver.parseEquation(equs);
        this.method = solver.getMethod(method);
    }
}
