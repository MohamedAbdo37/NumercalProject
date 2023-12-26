<template>
    <h1>
        Methods
    </h1>
    
    <select name="method" required v-model="method" @change="$emit('methodChosen', method)">
        <option value="" disabled selected hidden>Choose a method</option>
        <option value="G" >Gauss Elimination</option>
        <option value="GJ" >Gauss-Jordan Elimination</option>
        <option value="LU" >LU Decomposition</option>
        <option value="GS" >Gauss-Seidel</option>
        <option value="J" >Jacobi Iteration</option>
    </select>
</template>
<script>
export default {
  name: 'Method',
  data(){
    return{
        method: ""
    }
  },
  components: {
    
  },
  watch:{
    // async method(){
    //     console.log("form methods");
    //     await axios.get("http://localhost:8081/method", {
    //     params: {
    //       type: this.method
    //     }
    //   });
    //   console.log(this.method);
    // }
  },
  methods:{
  }
}
</script>
<style>
select{
    cursor: pointer;
    border-radius: 5px;
    color: black;
    background-color: blanchedalmond;
    height: 25px;
    width: 300px;
}
option{
    color: black;
}
select:hover{
    background-color: rgb(219, 207, 188);
}
</style>