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
    private long time = 0;
    private Solver solver;

    @GetMapping("/time")
    public long time(@RequestParam String equs) {
        return this.time;
    }
    @GetMapping("/equations")
    public void equations(@RequestParam String equs) {
        solver = new Solver();
        this.solver.parseEquation(equs);
    }

    @GetMapping("/SF")
    public void significantFigures(@RequestParam int SF) {
        Matrix.significantFigures = SF;
    }

    @GetMapping("/G")
    public String GauseElSolver() {
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("G");
        try {
            solver.solve(method);
        } catch (Exception e) {
            return e.getMessage();
        }
        long end = System.currentTimeMillis();

        this.time = end - begin;
        return solver.getAnswer();
    }

    @GetMapping("/GJ")
    public String GauseJourdanElSolver() {
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("GJ");
        try {
            solver.solve(method);
        } catch (Exception e) {
            return e.getMessage();
        }
        long end = System.currentTimeMillis();

        this.time = end - begin;
        return solver.getAnswer();

    }

    @GetMapping("/LUDo")
    public String LUDoElSolver() {
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("LU");
        solver.solveLU(method, "do");
        long end = System.currentTimeMillis();

        this.time = end - begin;
        return solver.getAnswer();
    }

    @GetMapping("/LUCr")
    public String LUCrElSolver() {
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("LU");
        solver.solveLU(method, "Court");
        return solver.getAnswer();
    }

    @GetMapping("/LUChol")
    public String LUCholElSolver() {
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("LU");
        solver.solveLU(method, "Chelsky");
        long end = System.currentTimeMillis();

        this.time = end - begin;
        return solver.getAnswer();
    }

    @GetMapping("/GS")
    public String GSElSolver(@RequestParam double initGuess, @RequestParam int noIter, @RequestParam double εa) {
        solver.setIteration(noIter);
        solver.setTolerance(εa);
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("GS");
        solver.solve(method);
        long end = System.currentTimeMillis();

        this.time = end - begin;
        return solver.getAnswer();
    }

    @GetMapping("/J")
    public String JElSolver(@RequestParam double initGuess, @RequestParam int noIter, @RequestParam double εa) {
        solver.setIteration(noIter);
        solver.setTolerance(εa);
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("J");
        solver.solve(method);
        long end = System.currentTimeMillis();
        this.time = end - begin;
        return solver.getAnswer();
    }

}
