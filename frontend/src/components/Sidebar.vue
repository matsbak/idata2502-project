<script setup lang="ts">
import type { List } from '../types/List'
import HeaderSection from './HeaderSection.vue'
import FooterSection from './FooterSection.vue'
import { onUpdated, ref, useTemplateRef } from 'vue'
import { Plus } from 'lucide-vue-next'

const lists = ref<Array<List>>([
  { "id": 1, "title": "My first list" },
  { "id": 2, "title": "My second list" },
  { "id": 3, "title": "My third list" },
])

const selected = ref<number>(lists.value[0].id)

const inputMode = ref<boolean>(false)
const input = useTemplateRef('input')

let tmpSelected: number = 0
let tmpId: number = -1

onUpdated(() => {
  if (inputMode.value) {
    input.value!.focus()
  }
  if (tmpId > -1) {
    selected.value = tmpId
    tmpId = -1
  }
})

const select = (id: number) => {
  selected.value = id
  inputMode.value = false
}

const enableInputMode = () => {
  tmpSelected = selected.value
  selected.value = -1
  inputMode.value = true
}

const disableInputMode = () => {
  selected.value = tmpSelected
  input.value!.value = ""
  inputMode.value = false
}

const addList = () => {
  const list: List = { "id": lists.value[lists.value.length - 1].id + 1, "title": input.value!.value }
  lists.value.push(list)
  tmpId = list.id
  input.value!.value = ""
  inputMode.value = false
}
</script>

<template>
  <aside class="flex flex-col justify-between h-full w-sm bg-white">
    <div class="h-39/40 w-full">
      <HeaderSection />
      <ul class="px-1 h-7/8 w-full overflow-y-auto">
        <li
          v-for="list in lists"
          :key="list.id"
          @click="select(list.id)"
          :class="[
            'p-4 border-l-3 text-lg cursor-pointer',
            selected === list.id ? 'border-black' : 'border-stone-300 hover:border-black'
          ]"
        >
          {{ list.title }}
        </li>
        <li
          v-if="inputMode"
          @keyup.escape="disableInputMode()"
          @keyup.enter="addList()"
          class="flex items-center justify-between p-4 w-full border-l-3 text-lg"
        >
          <input
            ref="input"
            placeholder="List title"
            class="w-full placeholder:text-stone-300 focus:outline-none"
          />
        </li>
        <li
          v-else
          @click="enableInputMode()"
          class="p-4 w-min cursor-pointer"
        >
          <Plus />
        </li>
      </ul>
    </div>
    <FooterSection />
  </aside>
</template>
