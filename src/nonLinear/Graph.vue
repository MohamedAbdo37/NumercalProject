<template>
<div class="graphCanvas">

</div>
</template>
<script>
export default{
    name: "Graph",
}
</script>
<style>
.graphCanvas{
    width: 500px;
    height: 500px;
    background-color:bisque;
    position: absolute;
    transform: translate(-120%, -70%);
    border-radius: 30px;
    animation-duration: 1s;
    animation-name: slideIn;
}
@keyframes slideIn {
    from {
        transform: translate(-300%, -70%);
    }
    to{
        transform: translate(-120%, -70%); 
    }
}
</style>