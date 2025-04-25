<script setup lang="ts">
import type { List } from '../types/List.ts'
import type { Todo } from '../types/Todo.ts'
import { onUpdated, ref, useTemplateRef } from 'vue'
import { CornerDownLeft, Plus, Trash2 } from 'lucide-vue-next'

const props = defineProps<{
  list: List | undefined
}>()

const emit = defineEmits<{
  // Emits `select` and `remove` are only used in template
  select: [ids: number[]]
  add: [todo: (number | Todo)[]]
  remove: [ids: number[]]
}>()

const input = useTemplateRef('input')
const inputMode = ref<boolean>(false)

const add = () => {
  const todo: Todo = {
    "id": props.list.todos[props.list.todos.length - 1].id + 1,
    "description": input.value.value,
    "completed": false
  }
  if (todo.description) {
    emit('add', [props.list.id, todo])
    inputMode.value = false
  }
}

onUpdated(() => inputMode.value ? input.value.focus() : null)
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
      <li
        v-if="inputMode"
        @keyup.escape="inputMode = false"
        @keyup.enter="add()"
        class="flex justify-between items-center p-2 w-3xl rounded-md bg-white"
      >
        <input
          @focusout="inputMode = false"
          ref="input"
          name="todo"
          class="mr-1 w-full focus:outline-none"
        />
        <CornerDownLeft :size="18" />
      </li>
      <li
        v-else
        class="p-2 w-3xl"
      >
        <div
          @click="inputMode = true"
          class="flex items-center w-fit hover:font-semibold cursor-pointer"
        >
          <Plus
            :size="18"
            class="mr-1 relative right-0.5 bottom-0.25"
          />
          <p class="italic">Add todo</p>
        </div>
      </li>
    </ul>
  </main>
</template>
