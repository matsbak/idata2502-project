<script setup lang="ts">
import { ref } from 'vue'

const lists: { id: number, title: string }[] = [
  { "id": 1, "title": "My first list" },
  { "id": 2, "title": "My second list" },
  { "id": 3, "title": "My third list" },
]

const selected = ref<number>(lists[0].id)
</script>

<template>
  <aside class="flex flex-col justify-between h-full w-sm px-1 bg-white">
    <div>
      <header class="p-8">
        <h1 class="text-6xl text-center">todo</h1>
      </header>
      <ul>
        <li
          v-for="list in lists"
          :key="list.id"
          @click="selected = list.id"
          :class="[
            'p-4 border-l-3 text-lg cursor-pointer transistion duration-300',
            selected === list.id ? 'border-black' : 'border-stone-300 hover:border-black'
          ]"
        >
          {{ list.title }}
        </li>
      </ul>
    </div>
    <p class="p-1 text-sm text-stone-300 italic">Version 0.1.0-SNAPSHOT</p>
  </aside>
</template>
