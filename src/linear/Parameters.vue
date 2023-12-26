<template>
    <hr v-if="this.methodChosen != 'G' && this.methodChosen != 'GJ' && this.methodChosen != null">
    <h1 v-if="this.methodChosen != 'G' && this.methodChosen != 'GJ' && this.methodChosen != null">
        Parameters
    </h1>
    <div class="LU" v-if="this.methodChosen == 'LU'">
        <select name="format" v-model="LUFormat" required>
            <option value="" disabled selected hidden>Choose the format of LU</option>
            <option value="Dolittle">Dolittle Form</option>
            <option value="Crout">Crout Form</option>
            <option value="Cholesky">Cholesky Form</option>
        </select>
    </div>
    <div class="GS" v-if="this.methodChosen == 'GS'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />


        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required />
    </div>
    <div class="J" v-if="this.methodChosen == 'J'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required />

        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required />
        <h3>Enter absolute relative error</h3>
        <input type="text" name="εa" v-model="εa" placeholder="Enter εa" required />
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
            await axios.get("http://localhost:8081/equations", {
                params: {
                    equs: this.equations.replaceAll('\n', '&').replaceAll(" ",'').replaceAll("−","-")

                }
            });

            switch (this.methodChosen) {
                case 'G':
                    this.converge = false;
                    await axios.get("http://localhost:8081/G").then(r => {
                        this.answers = r.data;
                        this.answers = this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });

                    await axios.get("http://localhost:8081/time").then(r => this.excutionTime = r.data);
                    break;
                case 'GJ':
                    this.converge = false;
                    await axios.get("http://localhost:8081/GJ", {
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
                case 'LU':
                    this.converge = false;
                    switch (this.LUFormat) {
                        case 'Dolittle':

                            await axios.get("http://localhost:8081/LUDo", {
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
                        case 'Crout':
                            await axios.get("http://localhost:8081/LUCr", {
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
                        case 'Cholesky':
                            await axios.get("http://localhost:8081/LUChol", {
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
                        default: break;
                    }
                    break;
                case 'GS':
                    await axios.get("http://localhost:8081/GS", {
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
                case 'J':
                
                    await axios.get("http://localhost:8081/J", {
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
input[type="button"] {
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