<script setup lang="ts">
</script>

<template>
  <main class="flex flex-col grow">
    <h2 class="flex justify-center items-center p-8 text-3xl">My first list</h2>
    <ul class="flex flex-col grow items-center overflow-y-auto">
      <li class="flex mb-2 p-2 w-2xl rounded-md bg-white">
        <input
          type="checkbox"
          class="mr-2"
        />
        <p class="overflow-x-hidden text-nowrap hover:line-through cursor-default">My first todo</p>
      </li>
      <li class="flex mb-2 p-2 w-2xl rounded-md bg-white">
        <input
          type="checkbox"
          class="mr-2"
        />
        <p class="overflow-x-hidden text-nowrap hover:line-through cursor-default">My second todo</p>
      </li>
      <li class="flex mb-2 p-2 w-2xl rounded-md bg-white">
        <input
          type="checkbox"
          class="mr-2"
        />
        <p class="overflow-x-hidden text-nowrap hover:line-through cursor-default">My third todo</p>
      </li>
    </ul>
  </main>
</template>
