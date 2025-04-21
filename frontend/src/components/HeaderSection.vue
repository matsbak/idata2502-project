<script setup lang="ts">
import { SquareCheck } from 'lucide-vue-next'
</script>

<template>
  <header class="flex justify-center items-center h-1/8">
    <SquareCheck
      class="m-1"
      :size="48"
    />
    <h1 class="text-6xl">Todo</h1>
  </header>
</template>
