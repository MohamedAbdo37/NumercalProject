<template>
  <form>
    <Method @methodChosen="this.methodChosen = $event"></Method>
    <hr>
    <Inputs @equations="this.equations = $event"></Inputs>
    <Parameters :methodChosen=methodChosen :equations=equations @answers="this.answers = $event"></Parameters>
    <Answer :answers=answers v-if="answers != null"></Answer>
  </form>
</template>

<script>
import Method from './Method.vue'
import Inputs from './Inputs.vue'
import Parameters from './Parameters.vue'
import Answer from './Answer.vue'


export default {
  name: 'LinearSolver',
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
