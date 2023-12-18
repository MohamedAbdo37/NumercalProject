<template>
    <hr v-if="this.methodChosen!='G' && this.methodChosen!='GJ' && this.methodChosen!=null">
    <h1 v-if="this.methodChosen!='G' && this.methodChosen!='GJ' && this.methodChosen!=null">
        Parameters
    </h1>
    <div class="LU" v-if="this.methodChosen=='LU'">
        <select name="format" v-model="LUFormat" required>
            <option value="" disabled selected hidden>Choose the format of LU</option>
            <option value="Dolittle">Dolittle Form</option>
            <option value="Crout">Crout Form</option>
            <option value="Cholesky">Cholesky Form</option>
        </select>
    </div>
    <div class="GS" v-if="this.methodChosen=='GS'" >
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required/>
        
        <!-- <select name="stopCondition" v-model="stpCondGS" required>
            <option value="" disabled selected hidden>Choose a stopping condition</option>
            <option value="NI">Number of Iterations</option>
            <option value="ARE">Absolute Relative Error</option>
        </select> -->
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required/>
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required/>
    </div>
    <div class="J" v-if="this.methodChosen=='J'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required/>
        <!-- <select name="stopCondition" v-model="stpCondJ" required>
            <option value="" disabled selected hidden>Choose a stopping condition</option>
            <option value="NI">Number of Iterations</option>
            <option value="ARE">Absolute Relative Error</option>
        </select> -->
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required/>
        <h3>Enter absolute relative error</h3>
        <input type="text" name="εa" v-model="εa" placeholder="Enter εa" required/>
    </div>
    <input type="button" @click="solve" value="Solve"/>
    </template>
    <script>
    import axios from 'axios';

    export default {
      name: 'Parameters',
      props: ['methodChosen'],
      data() {
        return{
            noItr: 0,
            εa: 0.0,
            LUFormat: "",
            initSGuess: 0.0,
            answers: null
        }
      },
      methods:{
        async solve(){
            switch(this.methodChosen){
                case 'G':
                    await axios.get("http://localhost:8081/G").then(r =>{
                        this.answers = r.data;
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    break;
                case 'GJ':
                    await axios.get("http://localhost:8081/GJ",{
                        params:{
                                    
                                }
                        });
                    break;
                case 'LU':
                    switch(this.LUFormat){
                        case 'Dolittle':
                            await axios.get("http://localhost:8081/LUDo",{
                                params:{
                                    
                                }
                                });
                            break;
                        case 'Crout': 
                            await axios.get("http://localhost:8081/LUCr",{
                                params:{
                                    
                                }
                                });
                            break;
                        case 'Cholesky':
                            await axios.get("http://localhost:8081/LUChol", {
                                params:{

                                }
                                });
                            break;
                        default: break;
                    }
                    break;
                case 'GS':
                    await axios.get("http://localhost:8081/GS", {
                        params:{
                            initGuess: this.initSGuess,
                            noIter: this.noItr,
                            εa: this.εa
                        }
                    }).then(r =>{
                        
                        this.answers = r.data;
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });;
                    break;
                case 'J':
                    console.log("lsdkjf");
                    await axios.get("http://localhost:8081/J", {
                        params:{
                            initGuess: this.initSGuess,
                            noIter: this.noItr,
                            εa: this.εa
                        }
                    }).then(r =>{
                        
                        this.answers = r.data;
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    break;
                default: break;
                }
                // await axios.get("http://localhost:8081/solve").then(r=>{
                //     this.answers = r.data;
                //     this.$emit('answers', this.answers);
                //     console.log(this.answers);
                // });
        }

      }

    }
    </script>
    
    <style>
    input[type="button"]{
    width: 80px;
    height: 30px;
    border-radius: 15px;
    color: black;
    margin: 10px;
    cursor: pointer;
    border: 1px solid rgb(159, 156, 149);
    }
    </style>