<script setup lang="ts">
import type { List } from '../types/List'
import type { Todo } from '../types/Todo'
import SidebarSection from './sections/SidebarSection.vue'
import MainSection from './sections/MainSection.vue'
import { onMounted, ref } from 'vue'
import { deleteList, postList, getLists } from './api/list.ts'

const lists = ref<List[]>([])

const selected = ref<number>(-1)

const addList = async (title: string) => {
  const id: number = await postList(title)
  if (id > 0) {
    const list: List = {
      "id": id,
      "title": title,
      "todos": []
    }
    lists.value.push(list)
  }
  selected.value = id
}

const removeList = async (id: number) => {
  await deleteList(id)
  lists.value.splice(lists.value.indexOf(lists.value.find(list => list.id === id)), 1)
}

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
  lists.value = await getLists()
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
    @add-list="(title) => addList(title)"
    @remove-list="(id) => removeList(id)"
  />
  <MainSection
    @toggle="(ids) => toggle(ids)"
    @add="(todo) => lists.find(list => list.id === todo[0]).todos.push(todo[1])"
    @remove="(ids) => remove(ids)"
    :list="lists.find(list => list.id === selected)"
  />
</template>
