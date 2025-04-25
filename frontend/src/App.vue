<script setup lang="ts">
import type { List } from '../types/List'
import type { Todo } from '../types/Todo'
import SidebarSection from './sections/SidebarSection.vue'
import MainSection from './sections/MainSection.vue'
import { ref } from 'vue'

const lists = ref<List[]>([
  {
    "id": 1,
    "title": "My first list",
    "todos": [
      { "id": 101, "description": "My first todo", "completed": false },
      { "id": 102, "description": "My second todo", "completed": false },
      { "id": 103, "description": "My third todo", "completed": false }
    ]
  },
  {
    "id": 2,
    "title": "My second list",
    "todos": [
      { "id": 201, "description": "My fourth todo", "completed": false },
      { "id": 202, "description": "My fifth todo", "completed": false },
      { "id": 203, "description": "My sixth todo", "completed": false }
    ]
  },
  {
    "id": 3,
    "title": "My third list",
    "todos": [
      { "id": 301, "description": "My seventh todo", "completed": false },
      { "id": 302, "description": "My eighth todo", "completed": false },
      { "id": 303, "description": "My ninth todo", "completed": false }
    ]
  }
])

const selected = ref<number>(-1)

const toggle = (ids: number[]) => {
  const completed: boolean = lists.value
    .find(list => list.id === ids[0]).todos
    .find(todo => todo.id === ids[1]).completed
  lists.value
    .find(list => list.id === ids[0]).todos
    .find(todo => todo.id === ids[1]).completed = !completed
}

const remove = (ids: number[]) => {
  const todos: Todo[] = lists.value.find(list => list.id === ids[0]).todos
  const index: number = todos.indexOf(todos.find(todo => todo.id === ids[1]))
  // Remove todo at defined index
  lists.value.find(list => list.id === ids[0]).todos.splice(index, 1)
}
</script>

<template>
  <SidebarSection
    :lists="lists"
    :selected="selected"
    @select="(id) => selected === id ? selected = -1 : selected = id"
    @add-list="(list) => lists.push(list)"
    @remove-list="(id) => lists.splice(lists.indexOf(lists.find(list => list.id === id)), 1)"
  />
  <MainSection
    @toggle="(ids) => toggle(ids)"
    @add="(todo) => lists.find(list => list.id === todo[0]).todos.push(todo[1])"
    @remove="(ids) => remove(ids)"
    :list="lists.find(list => list.id === selected)"
  />
</template>
