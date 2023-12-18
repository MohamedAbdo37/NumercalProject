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
        
       
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required/>
        <h3>Enter absolute relative error</h3>
        <input type="text" v-model="εa" name="εa" placeholder="Enter εa" required/>
    </div>
    <div class="J" v-if="this.methodChosen=='J'">
        <h3>Enter initial guess</h3>
        <input type="text" v-model="initSGuess" name="initialGuess" placeholder="Enter initial guess" required/>
        
        <h3>Enter number of iterations</h3>
        <input type="text" v-model="noItr" name="noIterations" placeholder="Enter # of iter." required/>
        <h3>Enter absolute relative error</h3>
        <input type="text" name="εa" v-model="εa" placeholder="Enter εa" required/>
    </div>
    <input type="button" @click="solve" value="Solve"/>
    <h4 v-show="excutionTime!=0"> {{ excutionTime }} ms</h4>
    </template>
    <script>
    import axios from 'axios';

    export default {
      name: 'Parameters',
      props: ['methodChosen', 'equations'],
      data() {
        return{
            noItr: 0,
            εa: 0.0,
            LUFormat: "",
            initSGuess: 0.0,
            answers: null,
            excutionTime: 0
        }
      },
      methods:{
        async solve(){
            await axios.get("http://localhost:8081/equations", {
                params: {
                    equs:this.equations.replaceAll('\n', '&')

                }
            });
            switch(this.methodChosen){
                case 'G':
                    const startTime=performance.now();
                    await axios.get("http://localhost:8081/G").then(r =>{
                        this.answers = r.data;
                        this.answers=this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    const endTime=performance.now();
                    this.excutionTime = endTime - startTime;
                    break;
                case 'GJ':
                    startTime=performance.now();
                    await axios.get("http://localhost:8081/GJ",{
                        params:{
                                    
                                }
                        }).then(r =>{
                        this.answers = r.data;
                        this.answers=this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    endTime=performance.now();
                    this.excutionTime = endTime - startTime;
                    break;
                case 'LU':
                    switch(this.LUFormat){
                        case 'Dolittle':
                            startTime=performance.now();
                            await axios.get("http://localhost:8081/LUDo",{
                                params:{
                                    
                                }
                                }).then(r =>{
                        this.answers = r.data;
                        this.answers=this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    endTime=performance.now();
                    this.excutionTime = endTime - startTime;
                            break;
                        case 'Crout': 
                            startTime=performance.now();
                            await axios.get("http://localhost:8081/LUCr",{
                                params:{
                                    
                                }
                                }).then(r =>{
                        this.answers = r.data;
                        this.answers=this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    endTime=performance.now();
                    this.excutionTime = endTime - startTime;
                            break;
                        case 'Cholesky':
                            startTime=performance.now();
                            await axios.get("http://localhost:8081/LUChol", {
                                params:{

                                }
                                }).then(r =>{
                        this.answers = r.data;
                        this.answers=this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    endTime=performance.now();
                    this.excutionTime = endTime - startTime;
                    
                            break;
                        default: break;
                    }
                    break;
                case 'GS':
                    startTime=performance.now();
                    await axios.get("http://localhost:8081/GS", {
                        params:{
                            initGuess: this.initSGuess,
                            noIter: this.noItr,
                            εa: this.εa
                        }
                    }).then(r =>{
                        this.answers = r.data;
                        this.answers=this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    endTime=performance.now();
                    this.excutionTime = endTime - startTime;
                    break;
                case 'J':
                    startTime=performance.now();
                    await axios.get("http://localhost:8081/J", {
                        params:{
                            initGuess: this.initSGuess,
                            noIter: this.noItr,
                            εa: this.εa
                        }
                    }).then(r =>{
                        
                        this.answers = r.data;
                        this.answers=this.answers.replaceAll(',', '\n');
                        this.$emit('answers', this.answers);
                        console.log(this.answers);
                    });
                    endTime=performance.now();
                    this.excutionTime = endTime - startTime;
                    break;
                default: break;
                }
        },
        async equations(){
            console.log("form equations");
            
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
    padding: 2px;
    font-weight: bold;
    }
    </style>