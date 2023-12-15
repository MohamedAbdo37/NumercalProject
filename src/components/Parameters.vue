<template>
    <hr v-if="this.methodChosen!='G' && this.methodChosen!='GJ' && this.methodChosen!=null">
    <h1 v-if="this.methodChosen!='G' && this.methodChosen!='GJ' && this.methodChosen!=null">
        Parameters
    </h1>
    <div class="LU" v-if="this.methodChosen=='LU'">
        <select name="formats" required>
            <option value="" disabled selected hidden>Choose the format of LU</option>
            <option value="G">Doolittle Form</option>
            <option value="GJ">Crout Form</option>
            <option value="LU">Cholesky Form</option>
        </select>
    </div>
    <div class="GS" v-if="this.methodChosen=='GS'" >
        <h3>Enter initial guess</h3>
        <input type="text" placeholder="Enter initial guess" required/>
        <h3>Enter stopping condition</h3>
        <select name="stopCondition" required>
            <option value="" disabled selected hidden>Choose a stopping condition</option>
            <option value="NI">Number of Iterations</option>
            <option value="ARE">Absolute Relative Error</option>
        </select>
    </div>
    <div class="J" v-if="this.methodChosen=='J'">
        <h3>Enter initial guess</h3>
        <input type="text" placeholder="Enter initial guess" required/>
        <h3>Enter stopping condition</h3>
        <select name="stopCondition" required>
            <option value="" disabled selected hidden>Choose a stopping condition</option>
            <option value="NI">Number of Iterations</option>
            <option value="ARE">Absolute Relative Error</option>
        </select>
    </div>

    </template>
    <script>
    export default {
      name: 'Parameters',
      props: ['methodChosen'],
      data() {
        return{

        }
      },
      methods:{

      }

    }
    </script>
    
    <style>
    
    </style>