<script setup lang="ts">
import type { List } from '../types/List'
import Sidebar from './components/Sidebar.vue'
import MainSection from './components/MainSection.vue'
import { ref } from 'vue'

const lists = ref<List[]>([
  {
    "id": 1,
    "title": "My first list",
    "todos": [
      { "id": 1, "description": "My first todo", "completed": false },
      { "id": 2, "description": "My second todo", "completed": false },
      { "id": 3, "description": "My third todo", "completed": false }
    ]
  },
  {
    "id": 2,
    "title": "My second list",
    "todos": [
      { "id": 4, "description": "My fourth todo", "completed": false },
      { "id": 5, "description": "My fifth todo", "completed": false },
      { "id": 6, "description": "My sixth todo", "completed": false }
    ]
  },
  {
    "id": 3,
    "title": "My third list",
    "todos": [
      { "id": 7, "description": "My seventh todo", "completed": false },
      { "id": 8, "description": "My eighth todo", "completed": false },
      { "id": 9, "description": "My ninth todo", "completed": false }
    ]
  }
])

const selected = ref<number>(lists.value[0].id)
</script>

<template>
  <Sidebar
    :lists="lists"
    :selected="selected"
    @select="(id) => selected = id"
    @add-list="(list) => lists.push(list)"
  />
  <MainSection
    :title="lists.find(list => list.id === selected).title"
    :todos="lists.find(list => list.id === selected).todos"
  />
</template>
