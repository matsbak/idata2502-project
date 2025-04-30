<script setup lang="ts">
import type { List } from '../types/List'
import type { Todo } from '../types/Todo'
import SidebarSection from './sections/SidebarSection.vue'
import MainSection from './sections/MainSection.vue'
import { onMounted, ref } from 'vue'
import { getAll } from './api/list.ts'

const lists = ref<List[]>([])

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

const fetchLists = async () => {
  lists.value = await getAll()
}

onMounted(() => {
  fetchLists()
})
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
