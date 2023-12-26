<template>
<div class="canvas">
    <div class="systemType" v-show="this.systemType == ''">
        <h1>Choose the required type of system you want to solve</h1>
        <select name="systemType" v-model="systemType">
            <option value="" disabled selected hidden>Choose a type</option>
            <option value="linear">Linear System</option>
            <option value="nonLinear">Non-Linear System</option>
        </select>
    </div>
    <LinearSolver v-show="this.systemType == 'linear'"></LinearSolver>
    <NonLinearSolver v-show="this.systemType == 'nonLinear'"></NonLinearSolver>

    <button class="backBtn" v-show="this.systemType != ''" @click="this.systemType=''">&lt back</button>
</div>
</template>
<script>
import LinearSolver from './linear/LinearSolver.vue';
import NonLinearSolver from './nonLinear/NonLinearSolver.vue';
export default {
  name: 'App',
  components:{
    LinearSolver,
    NonLinearSolver
  },
  data(){
    return{
        systemType: ""
    }
  }
}
</script>
<style>
::-webkit-scrollbar {
    width: 5px;
    height: 0px;
  }
  
  /* Track */
  ::-webkit-scrollbar-track {
    background: #170404;
    border-radius: 2.5px;
  }
  
  /* Handle */
  ::-webkit-scrollbar-thumb {
    background: blanchedalmond;
    
  }
  
  /* Handle on hover */
  ::-webkit-scrollbar-thumb:hover {
    background: rgb(219, 207, 188);
    
  }
*{
  font-family: monospace;
  color: whitesmoke;
}
body{
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgb(28, 29, 31);
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  background-color:rgb(40, 49, 49);
  margin-top: 60px;
  padding: 2%;
  width: fit-content;
  border-radius: 30px;
  border: 1px solid wheat;
}
.canvas{
  text-align: start;
  display: block;
}
.backBtn{
    width: 70px;
    height: 60px;
    color: blanchedalmond;
    outline: none;
    border: none;
    background-color: transparent;
    font-weight: bold;
    font-size: large;
    cursor: pointer;
}
.backBtn:hover{
    color: rgb(92, 107, 120);
}
</style>