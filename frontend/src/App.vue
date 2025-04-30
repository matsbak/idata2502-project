<script setup lang="ts">
import type { List } from '../types/List.ts'
import type { Todo } from '../types/Todo.ts'
import SidebarSection from './sections/SidebarSection.vue'
import MainSection from './sections/MainSection.vue'
import { onMounted, ref } from 'vue'
import { deleteList, getLists, postList } from './api/list.ts'
import { deleteTodo, postTodo, updateTodo } from './api/todo.ts'

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

const toggle = async (ids: number[]) => {
  const complete: boolean = lists.value
    .find(list => list.id === ids[0]).todos
    .find(todo => todo.id === ids[1]).complete
  await updateTodo(ids[1], !complete)
  lists.value
    .find(list => list.id === ids[0]).todos
    .find(todo => todo.id === ids[1]).complete = !complete
}

const add = async (data: (number | string)[]) => {
  const id: number = await postTodo(data[0], data[1])
  if (id > 0) {
    const todo: Todo = {
      "id": id,
      "description": data[1],
      "complete": false
    }
    lists.value.find(list => list.id === data[0]).todos.push(todo)
  }
}

const remove = async (ids: number[]) => {
  await deleteTodo(ids[1])
  const todos: Todo[] = lists.value.find(list => list.id === ids[0]).todos
  const index: number = todos.indexOf(todos.find(todo => todo.id === ids[1]))
  // Remove todo at defined index
  lists.value.find(list => list.id === ids[0]).todos.splice(index, 1)
}

const fetchLists = async () => {
  lists.value = await getLists()
}

onMounted(() => fetchLists())
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
    @add="(data) => add(data)"
    @remove="(ids) => remove(ids)"
    :list="lists.find(list => list.id === selected)"
  />
</template>
