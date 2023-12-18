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
    private String LUType;
    private Solver solver;

    @GetMapping("/equations")
    public void equations(@RequestParam String equs) {
        this.solver = new Solver();
        this.solver.parseEquation(equs);

    }

    @GetMapping("/SF")
    public void significantFigures(@RequestParam int SF) {
        Matrix.significantFigures = SF;
    }

    @GetMapping("/G")
    public String GauseElSolver() {
        Numeric method = solver.getMethod("G");
        solver.solve(method);
        return solver.getAnswer();
    }

    @GetMapping("/GJ")
    public void GauseJourdanElSolver() {

    }

    @GetMapping("/LUDo")
    public String LUDoElSolver() {
        Numeric method = solver.getMethod("LU");
        solver.solveLU(method,"do");
        return solver.getAnswer();
    }

    @GetMapping("/LUCr")
    public String LUCrElSolver() {
        Numeric method = solver.getMethod("LU");
        solver.solveLU(method,"Court");
        return solver.getAnswer();
    }

    @GetMapping("/LUChol")
    public String LUCholElSolver() {
        Numeric method = solver.getMethod("LU");
        solver.solveLU(method,"Chelsky");
        return solver.getAnswer();
    }

    @GetMapping("/GS")
    public String GSElSolver(@RequestParam double initGuess, @RequestParam int noIter, @RequestParam double εa) {
        solver.setIteration(noIter);
        solver.setTolerance(εa);
        Numeric method = solver.getMethod("GS");
        solver.solve(method);
        return solver.getAnswer();
    }

    @GetMapping("/J")
    public String JElSolver(@RequestParam double initGuess, @RequestParam int noIter, @RequestParam double εa) {
        solver.setIteration(noIter);
        solver.setTolerance(εa);
        Numeric method = solver.getMethod("J");
        solver.solve(method);
        return solver.getAnswer();
    }

}
