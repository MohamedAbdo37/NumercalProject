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
    private String type;
    private Solver solver;

    @GetMapping("/method")
    public void setMethod(@RequestParam String type) {
        this.type = type;
    }

    @GetMapping("/equations")
    public void equations(@RequestParam String equs) {
        this.solver = new Solver();
        this.solver.parseEquation(equs);

    }

    @GetMapping("/SF")
    public void significantFigures(@RequestParam int SF) {
        Matrix.significantFigures = SF;
    }

    @GetMapping("/solve")
    public String solve() {
        ArrayList<Double> results = new ArrayList<>();
        Numeric method = solver.getMethod(type);

        return "null";
    }

}
