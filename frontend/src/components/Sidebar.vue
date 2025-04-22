<script setup lang="ts">
import type { List } from '../types/List'
import HeaderSection from './HeaderSection.vue'
import FooterSection from './FooterSection.vue'
import Info from './Info.vue'
import { onUpdated, ref, useTemplateRef } from 'vue'
import { CornerDownLeft, Plus } from 'lucide-vue-next'

const props = defineProps<{
  lists: List[]
  selected: number
}>()

const emit = defineEmits<{
  // Define `select` as `defineEmits` is defined in the component
  select: [id: number]
  addList: [list: List]
}>()

const input = useTemplateRef('input')
const inputMode = ref<boolean>(false)
const displayInfo = ref<boolean>(false)

const addList = () => {
  const list: List = {
    "id": props.lists[props.lists.length - 1].id + 1,
    "title": input.value.value,
    "todos": []
  }
  if (list.title) {
    emit('addList', list)
    emit('select', list.id)
    inputMode.value = false
  }
}

onUpdated(() => {
  if (inputMode.value && !displayInfo.value) {
    input.value.focus()
  }
})
</script>

<template>
  <aside class="flex flex-col justify-between h-full w-sm bg-white">
    <div
      v-if="!displayInfo"
      class="h-full"
    >
      <div class="h-39/40 w-full">
        <HeaderSection />
        <ul class="px-1 h-7/8 w-full overflow-y-auto">
          <li
            v-for="list in lists"
            :key="list.id"
            @click="$emit('select', list.id)"
            :class="[
              'pb-4 px-2 w-fit text-lg cursor-pointer',
              selected === list.id ? 'font-semibold' : 'hover:font-semibold'
            ]"
          >
            {{ list.title }}
          </li>
          <li
            v-if="inputMode"
            @keyup.escape="inputMode = false"
            @keyup.enter="addList()"
            class="flex items-center justify-between px-2 w-full text-lg"
          >
            <input
              @focusout="inputMode = false"
              ref="input"
              placeholder="List title"
              class="mr-2 w-full focus:outline-none"
            />
            <CornerDownLeft
              :size="18"
              @click="addList()"
            />
          </li>
          <li
            v-else
            @click="inputMode = true"
            class="flex items-center px-2 w-fit text-lg italic hover:font-semibold cursor-pointer"
          >
            <Plus
              :size="18"
              class="mr-2"
            />
            Add list
          </li>
        </ul>
      </div>
      <FooterSection @info="displayInfo = true" />
    </div>
    <div
      v-else
      class="h-full"
    >
      <Info @sidebar="displayInfo = false" />
      <FooterSection @info="displayInfo = false" />
    </div>
  </aside>
</template>
