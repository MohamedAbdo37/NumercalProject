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

    <div class="ON" v-if="this.methodChosen == 'ON'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>

    <div class="MN" v-if="this.methodChosen == 'MN1'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
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
        <input type="text" v-model="initSGuess1" name="initialGuess1" placeholder="Enter first initial guess" required />
        <h3>Enter second initial guess</h3>
        <input type="text" v-model="initSGuess2" name="initialGuess2" placeholder="Enter second initial guess" required />
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>
    
    <input type="button" @click="solve" value="Solve" />
    <h4 v-show="excutionTime != 0"> excution time = {{ excutionTime }} ms</h4>
    <h4 v-if="converge">{{ this.message }}</h4>
</template>
<script>
import axios from 'axios';

export default {
    name: 'Parameters',
    props: ['methodChosen', 'equations'],
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
            message: null
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
        async solve() {
            
            await axios.get("http://localhost:8081/nonLinearEquations", {
                params: {
                    equs: this.equations.join(",")

                }
            }).then(r =>{
                console.log("equations transfered successfully")
            });

            switch (this.methodChosen) {
                case 'B':
                    this.converge = false;
                    await axios.get("http://localhost:8081/B").then(r => {
                        this.answers = r.data;
                        this.answers = this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });

                    await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                    break;
                case 'FL':
                    this.converge = false;
                    await axios.get("http://localhost:8081/FL", {
                        params: {

                        }
                    }).then(r => {
                        this.answers = r.data;
                        this.answers = this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                    break;
                case 'FX':
                    this.converge = false;
                        await axios.get("http://localhost:8081/FX", {
                            params: {

                            }
                        }).then(r => {
                            this.answers = r.data;
                            this.answers = this.answers.replaceAll(',', '\n');
                            this.$emit('answers', this.answers);
                            console.log(this.answers);
                        });
                        await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                        break;
                case 'ON':
                    await axios.get("http://localhost:8081/ON", {
                        params: {
                            initGuess: this.initSGuess,
                            noIter: this.noItr,
                            εa: this.εa
                        }
                    }).then(r => {
                        this.answers = r.data;
                        this.answers = this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                    this.reportCovergence();
                    this.converge = true;
                    break;
                case 'MN1':
                    await axios.get("http://localhost:8081/MN1", {
                        params: {
                            initGuess: this.initSGuess,
                            noIter: this.noItr,
                            εa: this.εa
                        }
                    }).then(r => {

                        this.answers = r.data;
                        this.answers = this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                    this.reportCovergence();
                    this.converge = true;
                    break;
                case 'MN2':
                    await axios.get("http://localhost:8081/MN2", {
                        params: {
                            initGuess: this.initSGuess,
                            noIter: this.noItr,
                            εa: this.εa
                        }
                    }).then(r => {

                        this.answers = r.data;
                        this.answers = this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                    this.reportCovergence();
                    this.converge = true;
                    break;
                case 'S':
                    await axios.get("http://localhost:8081/S", {
                        params: {
                            x0: this.initGuess1,
                            x1: this.initGuess2,
                            noIter: this.noItr,
                            Ea: this.εa
                        }
                    }).then(r => {
                        console.log("solved secant successfully")
                        this.answers = r.data;
                        // this.answers = this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                    this.reportCovergence();
                    this.converge = true;
                    break;
                default: break;
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