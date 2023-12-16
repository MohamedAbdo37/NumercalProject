<template>
<h1>
    Inputs
</h1>
<h3>Enter the System of Linear Equations </h3>
<textarea id ="eq" type="multiliner" placeholder="Enter System" required/>
<h3>Enter the Precision (number of significant figures)</h3>
<input id="prec" type="number" placeholder="Enter precision" required/>
</template>

<script>
export default {
  name: 'Inputs',
  props: {
  },
  data(){
    return{
    }
  },
  methods:{

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
    /* width: 200px; */
}
input:hover{
    background-color: rgb(219, 207, 188);
}
</style>