<script setup lang="ts">
import type { Todo } from '../types/Todo.ts'

const props = defineProps<{
  title: string
  todos: Todo[]
}>()
</script>

<template>
  <main class="flex flex-col grow">
    <h2 class="flex justify-center items-center p-8 text-3xl">{{ title }}</h2>
    <ul class="flex flex-col grow items-center overflow-y-auto">
      <li v-for="todo in todos" class="flex mb-2 p-2 w-3xl rounded-md bg-white">
        <input
          v-if="todo.completed"
          type="checkbox"
          class="mr-2"
          checked
        />
        <input
          v-else
          type="checkbox"
          class="mr-2"
        />
        <p class="overflow-x-hidden text-nowrap hover:line-through cursor-default">{{ todo.description }}</p>
      </li>
    </ul>
  </main>
</template>
