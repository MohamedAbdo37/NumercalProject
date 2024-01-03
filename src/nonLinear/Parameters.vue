<template>
    <hr>
    <h1>
        Parameters
    </h1>
    <div class="B" v-if="this.methodChosen == 'B'">
        <h3>Enter first initial guess</h3>
        <input type="text" v-model="initSGuess1" name="initialGuess1" placeholder="Enter first initial guess" required />
        <h3>Enter second initial guess</h3>
        <input type="text" v-model="initSGuess2" name="initialGuess2" placeholder="Enter second initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>

    <div class="FL" v-if="this.methodChosen == 'FL'">
        <h3>Enter first initial guess</h3>
        <input type="text" v-model="initSGuess1" name="initialGuess1" placeholder="Enter first initial guess" required />
        <h3>Enter second initial guess</h3>
        <input type="text" v-model="initSGuess2" name="initialGuess2" placeholder="Enter second initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>

    <div class="FX" v-if="this.methodChosen == 'FX'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>

    <!-- <div class="ON" v-if="this.methodChosen == 'ON'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div> -->

    <div class="MN" v-if="this.methodChosen == 'MN1'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
        <h3>Enter the multiplicity</h3>
        <input type="text" v-model="mul" name="mul" placeholder="Enter multiplicity" required />
    </div>

    <div class="MN" v-if="this.methodChosen == 'MN2'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>

    <div class="S" v-if="this.methodChosen == 'S'">
        <h3>Enter first initial guess</h3>
        <input type="text" v-model="initGuess1" name="initialGuess1" placeholder="Enter first initial guess" required />
        <h3>Enter second initial guess</h3>
        <input type="text" v-model="initGuess2" name="initialGuess2" placeholder="Enter second initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>
    
    <input type="button" @click="solve" value="Solve" />
    <h4 v-show="excutionTime != 0"> excution time = {{ excutionTime }} ms</h4>
    <h4>{{ this.message }}</h4>
</template>
<script>
import axios from 'axios';

export default {
    name: 'Parameters',
    props: ['methodChosen', 'equations', 'precision'],
    data() {
        return {
            noItr: 0,
            εa: 0.0,
            LUFormat: "",
            initSGuess: 0.0,
            initGuess1: 0.0,
            initGuess2: 0.0,
            answers: null,
            excutionTime: 0,
            converge: false,
            message: null,
            mul: 0
        }
    },
    methods: {
        async reportCovergence() {
            let conv = 0;
            if (this.methodChosen === 'J') {
                await axios.get("http://localhost:8081/convJ").then((r) => conv = r.data);
                console.log(conv)
            }

            if (this.methodChosen === 'GS')
                await axios.get("http://localhost:8081/convGS").then((r) => conv = r.data);
                console.log(conv)

            if (conv !== 0)
                this.message = `Converg After ${conv} iteration(s)`;
            else
                this.message =  `the system failed to converge in the spicified iterations`;
        },
        checkInputs(){
            if (this.initGuess1 == this.initGuess2)
                return false
        },
        async solve() {
            // if (!this.checkInputs) return;
            await axios.get("http://localhost:8081/nonLinearEquations", {
                params: {
                    equs: this.equations

                }
            }).then(r =>{
                console.log("equations transfered successfully")
            });

            switch (this.methodChosen) {
                case 'B':
                    await axios.get("http://localhost:8081/B", {
                            params: {
                                xl: this.initSGuess1,
                                ea: this.εa,
                                xu: this.initSGuess2,
                                significantfigures: this.precision
                            }
                        }).then(r => {
                            console.log("solved Bisection successfully")
                            this.answers = r.data[0];
                            this.excutionTime = -1* r.data[1];
                            this.testConvergence(r.data[2], 0);
                            this.$emit('answers', this.answers);
                            console.log(r.data[1]);
                        });
                        break;
                case 'FL':
                    await axios.get("http://localhost:8081/FL", {
                            params: {
                                xl: this.initSGuess1,
                                ea: this.εa,
                                xu: this.initSGuess2,
                                significantfigures: this.precision
                            }
                        }).then(r => {
                            console.log("solved False position successfully")
                            this.answers = r.data[0];
                            this.excutionTime = -1* r.data[1];
                            this.testConvergence(r.data[2], 0);
                            this.$emit('answers', this.answers);
                            console.log(r.data[1]);
                        });
                        break;
                case 'FX':
                    await axios.get("http://localhost:8081/S", {
                            params: {
                                x0: this.initGuess1,
                                x1: this.initGuess2,
                                noIter: this.noItr,
                                Ea: this.εa,
                                significantFigures: this.precision
                            }
                        }).then(r => {
                            console.log("solved secant successfully")
                            this.answers = r.data[0];
                            this.excutionTime = r.data[1];
                            this.testConvergence(r.data[2], r.data[3]);
                            this.$emit('answers', this.answers);
                            console.log(r.data[1]);
                        });
                        break;
                case 'MN1':
                    await axios.get("http://localhost:8081/MN1", {
                        params: {
                            MAX_ITERATIONS: this.noItr,
                            ea: this.εa,
                            initialguess: this.initSGuess,
                            significantfigures: this.precision,
                            m: this.mul
                        }
                    }).then(r => {
                        console.log("solved secant successfully")
                        this.answers = r.data[0];
                        this.excutionTime = r.data[1];
                        this.testConvergence(r.data[2], 0);
                        this.$emit('answers', this.answers);
                        console.log(r.data[1]);
                    });
                    
                    break;
                case 'MN2':
                    await axios.get("http://localhost:8081/S", {
                        params: {
                            x0: this.initGuess1,
                            x1: this.initGuess2,
                            noIter: this.noItr,
                            Ea: this.εa,
                            significantFigures: this.precision
                        }
                    }).then(r => {
                        console.log("solved secant successfully")
                        this.answers = r.data[0];
                        this.excutionTime = r.data[1];
                        this.testConvergence(r.data[2], r.data[3]);
                        this.$emit('answers', this.answers);
                        console.log(r.data[1]);
                    });
                    break;
                case 'S':
                    await axios.get("http://localhost:8081/S", {
                        params: {
                            x0: this.initGuess1,
                            x1: this.initGuess2,
                            noIter: this.noItr,
                            Ea: this.εa,
                            significantFigures: this.precision
                        }
                    }).then(r => {
                        console.log("solved secant successfully")
                        this.answers = r.data[0];
                        this.excutionTime = r.data[1];
                        this.testConvergence(r.data[2], r.data[3]);
                        this.$emit('answers', this.answers);
                        console.log(r.data[1]);
                    });
                    break;
                default: break;
            }
        },
        testConvergence(reachedIterations, convTime){
            if (reachedIterations != 0){
                if (convTime != 0)
                    this.message = `Converged after ${reachedIterations} iterations (${convTime} ms)`
                else 
                    this.message = `Converged after ${reachedIterations} iterations`
            }else {
                this.message = `Failed to converge`
                this.answers = null
            }
        },
        async equations() {
            console.log("form equations");

        }
    }

}
</script>
    
<style>
input[type="submit"] {
    width: 80px;
    height: 30px;
    border-radius: 15px;
    color: black;
    margin: 10px;
    cursor: pointer;
    border: 1px solid rgb(159, 156, 149);
    padding: 2px;
    font-weight: bold;
}
</style>