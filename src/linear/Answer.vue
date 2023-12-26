<template>
    <hr>
    <h1 class="answer">Answer is : {{ this.answers }}</h1>
    <!-- <input type="button" value="Show Steps" @click="this.stepsShow = !this.stepsShow"/>
    <div class="steps" v-if="this.stepsShow"></div> -->
</template>
<script>
export default {
  name: 'Answer',
  data(){
    return{
        stepsShow: false
    }
  },
  props: ['answers'],
  components: {
    
  },
  watch:{
    
  },
  methods:{
  }
}
</script>
<style>
.answer{
    width: 400px;
}

</style>