<script setup lang="ts">
import type { List } from '../types/List.ts'
import HeaderSection from './HeaderSection.vue'
import FooterSection from './FooterSection.vue'
import InfoDisplay from '../components/InfoDisplay.vue'
import { onUpdated, ref, useTemplateRef } from 'vue'
import { CornerDownLeft, Plus, Trash2 } from 'lucide-vue-next'

const props = defineProps<{
  lists: List[]
  selected: number
}>()

const emit = defineEmits<{
  // Emits `select` and `removeList` are only used in template
  select: [id: number]
  addList: [title: string]
  removeList: [id: number]
}>()

const input = useTemplateRef('input')
const inputMode = ref<boolean>(false)
const displayInfo = ref<boolean>(false)

const addList = () => {
  if (input.value.value.trim()) {
    emit('addList', input.value.value.trim())
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
            class="group flex justify-between items-center pb-4 px-2 text-lg"
          >
            <p
              @click="$emit('select', list.id)"
              :class="[
                'cursor-pointer',
                selected === list.id ? 'font-semibold' : 'hover:font-semibold'
              ]"
            >{{ list.title }}</p>
            <Trash2
              @click="$emit('removeList', list.id)"
              :size="18"
              class="cursor-pointer hidden group-hover:block"
            />
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
              name="list"
              placeholder="List title"
              class="mr-2 w-full focus:outline-none"
            />
            <CornerDownLeft :size="18" />
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
      <InfoDisplay @sidebar="displayInfo = false" />
      <FooterSection @info="displayInfo = false" />
    </div>
  </aside>
</template>
