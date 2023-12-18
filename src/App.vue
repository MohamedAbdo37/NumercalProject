<template>
  
  <form class="canvas">
    <Method @methodChosen="this.methodChosen = $event"></Method>
    <hr>
    <Inputs @equations="this.equations = $event"></Inputs>
    <Parameters :methodChosen=methodChosen :equations=equations @answers="this.answers = $event"></Parameters>
  </form>
  <Answer :answers=answers v-if="answers != null"></Answer>
</template>

<script>
import Method from './components/Method.vue'
import Inputs from './components/Inputs.vue'
import Parameters from './components/Parameters.vue'
import Answer from './components/Answer.vue'


export default {
  name: 'App',
  components: {
    Method,
    Inputs,
    Parameters,
    Answer
},
  data(){
    return{
      methodChosen: null,
      answers: null,
      equations: ""
    }
  },
 
  methods:{
      
    },
    
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
  border-radius: 10%;
  border: 1px solid wheat;
}
.canvas{
  text-align: start;
  display: block;
}

/* input[type="button"]{
  width: 80px;
  height: 30px;
  border-radius: 15px;
  color: black;
  margin: 10px;
  cursor: pointer;
  border: 1px solid rgb(159, 156, 149);
} */
</style>
