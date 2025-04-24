<script setup lang="ts">
import type { Todo } from '../types/Todo.ts'
import { Plus, Trash2 } from 'lucide-vue-next'

const props = defineProps<{
  list: List
}>()
</script>

<template>
  <main
    v-if="list === undefined"
    class="flex flex-col grow"
  >
    <h2 class="flex justify-center items-center p-8 text-3xl italic">Please select a list</h2>
  </main>
  <main
    v-else
    class="flex flex-col grow"
  >
    <h2 class="flex justify-center items-center p-8 text-3xl">{{ list.title }}</h2>
    <ul class="flex flex-col grow items-center overflow-y-auto">
      <li
        v-for="todo in list.todos"
        :key="todo.id"
        class="group flex justify-between items-center mb-2 p-2 w-3xl rounded-md bg-white"
      >
        <div class="flex">
          <input
            v-if="todo.completed"
            @click="$emit('toggle', [list.id, todo.id])"
            type="checkbox"
            name="check"
            class="mr-2"
            checked
          />
          <input
            v-else
            @click="$emit('toggle', [list.id, todo.id])"
            type="checkbox"
            name="check"
            class="mr-2"
          />
          <p
            @click="$emit('toggle', [list.id, todo.id])"
            :class="[
              'overflow-x-hidden text-nowrap cursor-default',
              todo.completed ? 'line-through' : 'hover:line-through'
            ]"
          >{{ todo.description }}</p>
        </div>
        <Trash2
          @click="$emit('remove', [list.id, todo.id])"
          :size="18"
          class="cursor-pointer hidden group-hover:block"
        />
      </li>
      <li class="p-1 w-3xl">
        <div class="flex items-center w-fit hover:font-semibold cursor-pointer">
          <Plus
            :size="18"
            class="mr-1"
          />
          <p class="italic relative top-0.25">Add todo</p>
        </div>
      </li>
    </ul>
  </main>
</template>
