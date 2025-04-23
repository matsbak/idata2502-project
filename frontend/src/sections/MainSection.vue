<script setup lang="ts">
import type { Todo } from '../types/Todo.ts'

const props = defineProps<{
  list: List
}>()
</script>

<template>
  <main class="flex flex-col grow">
    <h2 class="flex justify-center items-center p-8 text-3xl">{{ list.title }}</h2>
    <ul class="flex flex-col grow items-center overflow-y-auto">
      <li
        v-for="todo in list.todos"
        :key="todo.id"
        class="flex mb-2 p-2 w-3xl rounded-md bg-white"
      >
        <input
          v-if="todo.completed"
          @click="$emit('toggle', [list.id, todo.id])"
          type="checkbox"
          name="todo"
          class="mr-2"
          checked
        />
        <input
          v-else
          @click="$emit('toggle', [list.id, todo.id])"
          type="checkbox"
          name="todo"
          class="mr-2"
        />
        <p
          @click="$emit('toggle', [list.id, todo.id])"
          :class="[
            'overflow-x-hidden text-nowrap cursor-default',
            todo.completed ? 'line-through' : 'hover:line-through'
          ]"
        >{{ todo.description }}</p>
      </li>
    </ul>
  </main>
</template>
