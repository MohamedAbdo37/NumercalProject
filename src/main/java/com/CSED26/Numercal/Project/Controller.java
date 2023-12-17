package com.CSED26.Numercal.Project;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CSED26.Numercal.Project.Factory.Numeric;
import com.CSED26.Numercal.Project.Factory.Methods.LUDeomp;

@RestController
@CrossOrigin
public class Controller {
    Numeric method;

    @GetMapping("/inputs")
    public void Inputs(@RequestParam String method,@RequestParam String equs, @RequestParam int pres){
        Solver solver = new Solver();
        solver.parseEquation(equs);
        this.method = solver.getMethod(method);
    }

    @GetMapping("/inputs")
    public ArrayList<Double> Inputs(@RequestParam String method,@RequestParam String equs, @RequestParam int pres, @RequestParam String format){
        Solver solver = new Solver();
        solver.parseEquation(equs);
        Matrix.significantFigures = pres;
        this.method = solver.getMethod(method);

        switch (format) {
            case "Dolittle":
                return ((LUDeomp) this.method).doLittle();
            case "Crout":
                return ((LUDeomp) this.method).crout();
            case "Cholesky":
                return ((LUDeomp) this.method).cholesky();
            default:
                return null;
        }

    }
}
