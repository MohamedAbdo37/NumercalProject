<template>
    <h1>
        Inputs
    </h1>
    <h3>Enter the Non-Linear Equation </h3>
    <textarea v-model="equations" id ="eq" type="multiliner" placeholder="Enter System"  @input="sendInput" required/>
    <br>
    <input type="button" @click="graph" class="graphBtn" id="graphBtn" value="Graph">
    <h3>Enter the Precision (number of significant figures)</h3>
    <input id="prec" v-model="precision"  type="number" placeholder="Enter precision" required/>
    </template>
    
    <script>
    import axios  from 'axios'
    export default {
      name: 'Inputs',
      data(){
        return{
            precision: null,
            equations: null,
            graphShow: false,
            graphStatus: "Graph"
        }
      },
      watch:{
        async precision(){
            console.log("from precision");
          await axios.get("http://localhost:8081/SF", {
            params: {
              SF: this.precision
            }
          });
          console.log(this.precision);
        },
        
      },
      methods:{
        insertAtIndex(substring, index) {
            this.equations = this.equations.slice(0, index) + substring + this.equations.slice(index)
        },
        putPlusesToEqu(){
            let temp = this.equations.indexOf('-', 0)
            for (let i=temp; i != -1; i = this.equations.indexOf("-", i+2)){
              this.insertAtIndex("+", i)
            }
        },
        sendInput(){
          this.putPlusesToEqu()
          console.log(this.equations)
          let ex = this.equations.split("+")
          if(ex[0] == '') ex.shift()
          console.log(ex)
          this.$emit('equations', ex)
        },
        graph(){
            this.graphShow = !this.graphShow;
            if (!this.graphShow) this.graphStatus = "Graph";
            else this.graphStatus = "Hide graph";
            document.getElementById('graphBtn').value = this.graphStatus;
            this.$emit('graphShow', this.graphShow);
            //code for graphing goes here

        }
      }
    }
    </script>
    
    <style>
    textarea[type="multiliner"]{
        width: 100%;
        height: 40px;
        border-radius: 5px;   
        background-color: blanchedalmond ;
        color: black;
    }
    textarea:hover{
        background-color: rgb(219, 207, 188);
    }
    input{
        border-radius: 5px;
        border: 1px solid rgb(109, 107, 107);
        background-color: blanchedalmond;
        color: black;
        height: 22px;
        margin-left: 10px;
        width: 250px;
        /* width: 200px; */
    }
    input:hover{
        background-color: rgb(219, 207, 188);
    }
    .graphBtn{
      background-color: rgba(255, 235, 205, 0.575);
      border: none;
      color: blanchedalmond;
      border-radius: 30px;
      outline: none;
      cursor: pointer;
      font-size: large;
      font-weight: bold;
      margin: 5px;
    }
    .graphBtn:hover{
      color:rgb(13, 13, 12);
      background-color: aliceblue;
    }
    #graphBtn{
      width: 130px;
    }
    </style>