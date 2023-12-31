package com.CSED26.Numercal.Project;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CSED26.Numercal.Project.Factory.Numeric;
import com.CSED26.Numercal.Project.Factory.Methods.GauseSedil;
import com.CSED26.Numercal.Project.Factory.Methods.Jacobi;
import com.CSED26.Numercal.Project.Factory.Methods.LUDeomp;
import com.CSED26.Numercal.Project.Factory.Methods.Iterations.Secant;

@RestController
@CrossOrigin
public class Controller {
    private long time = 0;
    private Solver solver;

    @GetMapping("/time")
    public long time() {
        return this.time;
    }

    @GetMapping("/convJ")
    public int convJ() {
        return Jacobi.convergedAfter;
    }

    @GetMapping("/convGS")
    public int convGS() {
        return GauseSedil.convergedAfter;
    }

    @GetMapping("/linearEquations")
    public void equations(@RequestParam String equs) {
        solver = new Solver();
        this.solver.parseLinearEquation(equs);
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
        try {
            Numeric method = solver.getMethod("GJ");
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
        try {
            Numeric method = solver.getMethod("LU");
            solver.solveLU(method, "do");
        } catch (Exception e) {
            return e.getMessage();
        }
        long end = System.currentTimeMillis();

        this.time = end - begin;
        return solver.getAnswer();
    }

    @GetMapping("/LUCr")
    public String LUCrElSolver() {
        long begin = System.currentTimeMillis();
        try {
            Numeric method = solver.getMethod("LU");
            solver.solveLU(method, "Court");
        } catch (Exception e) {
            return e.getMessage();
        }
        long end = System.currentTimeMillis();
        this.time = end - begin;
        return solver.getAnswer();
    }

    @GetMapping("/LUChol")
    public String LUCholElSolver() {
        long begin = System.currentTimeMillis();
        Numeric method = solver.getMethod("LU");
        try {
            solver.solveLU(method, "Chelsky");
        } catch (Exception e) {
            return e.getMessage();
        }
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

    @GetMapping("/nonLinearEquations")
    public void equations(@RequestParam String[] equs) {
        solver = new Solver();
        this.solver.parseNonLinearEquation(equs);
    }

    @GetMapping("/S")
    public double[] secantSolver(@RequestParam double x0, @RequestParam double x1, @RequestParam int noIter,
            @RequestParam double Ea, @RequestParam int significantFigures) {
        double[] answer = { solver.solveBySecant(x0, x1, Ea, noIter, significantFigures),
                solver.getSecantTimeOfExecution(), solver.getSecantNoOfIterations(),
                solver.getSecantTimeOfConvergence() };
        return answer;
    }

    @GetMapping("/FX")
    public double[] fixedPointSolver(@RequestParam double point, @RequestParam int maxIterations,
            @RequestParam int significantFigures, @RequestParam double tol) {
        return solver.solveByFixedPoint(point, maxIterations, significantFigures, tol);
    }

    @GetMapping("/MN1")
    public double[] originalNewtonSolver(@RequestParam int MAX_ITERATIONS, @RequestParam double ea,
            @RequestParam double initialguess, @RequestParam int significantfigures, @RequestParam int m) {
        double[] answer = solver.solveByONewton(1, m, MAX_ITERATIONS, ea, initialguess, significantfigures);
        return answer;
    }

    @GetMapping("/MN2")
    public double[] modifiedNewtonSolver(@RequestParam int MAX_ITERATIONS, @RequestParam double ea,
            @RequestParam double initialguess, @RequestParam int significantfigures) {
        double[] answer = solver.solveByONewton(2, 0, MAX_ITERATIONS, ea, initialguess, significantfigures);
        return answer;
    }

    @GetMapping("/B")
    public double[] BiSolver(@RequestParam double xl, @RequestParam double ea,
            @RequestParam double xu, @RequestParam int significantfigures) {
        double[] answer = solver.BiSolver(xl, xu, ea, 50);
        return answer;
    }

    @GetMapping("/FL")
    public double[] falsePosition(@RequestParam double xl, @RequestParam double ea,
            @RequestParam double xu, @RequestParam int significantfigures) {
        double[] answer = solver.falseSolver(xl, xu, ea, 50);
        return answer;
    }
}