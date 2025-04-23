<script setup lang="ts">
import { ArrowLeft } from 'lucide-vue-next'
</script>

<template>
  <article class="p-2 h-39/40">
    <ArrowLeft
      @click="$emit('sidebar')"
      :size="18"
      class="cursor-pointer"
    />
    <img
      src="../assets/ntnu.svg"
      alt="NTNU logo"
      class="my-8"
    />
    <p>
      This project is part of the exam portfolio in the course
      <a
        href="https://www.ntnu.edu/studies/courses/IDATA2502#tab=omEmnet"
        target="_blank"
        class="hover:underline"
      >
      IDATA2502 Cloud services administration</a>
      at
      <a
        href="https://www.ntnu.edu/"
        target="_blank"
        class="hover:underline"
      >
      NTNU</a>.
      The project is done by <span class="font-semibold">candidate 10006</span>.
    </p>
    <p class="my-4">The technologies used in the project include:</p>
    <ul>
      <li>
        <a
          href="https://en.wikipedia.org/wiki/Java_(programming_language)"
          target="_blank"
          class="flex w-fit hover:underline"
        >
          <img
            src="../assets/java.svg"
            alt="Java logo"
            width="20"
            height="20"
            class="mr-1"
          />
          Java
        </a>
      </li>
      <li>
        <a
          href="https://en.wikipedia.org/wiki/JavaScript"
          target="_blank"
          class="flex w-fit hover:underline"
        >
          <img
            src="../assets/javascript.svg"
            alt="JavaScript logo"
            width="20"
            height="20"
            class="mr-1"
          />
          JavaScript
        </a>
      </li>
      <li>
        <a
          href="https://spring.io/projects/spring-boot"
          target="_blank"
          class="flex w-fit hover:underline"
        >
          <img
            src="../assets/springboot.svg"
            alt="Spring Boot logo"
            width="20"
            height="20"
            class="mr-1"
          />
          Spring Boot
        </a>
      </li>
      <li>
        <a
          href="https://vuejs.org/"
          target="_blank"
          class="flex w-fit hover:underline"
        >
          <img
            src="../assets/vue.svg"
            alt="Vue logo"
            width="20"
            height="20"
            class="mr-1"
          />
          Vue
        </a>
      </li>
      <li>
        <a
          href="https://tailwindcss.com/"
          target="_blank"
          class="flex w-fit hover:underline"
        >
          <img
            src="../assets/tailwindcss.svg"
            alt="Tailwind CSS logo"
            width="20"
            height="20"
            class="mr-1"
          />
          Tailwind CSS
        </a>
      </li>
    </ul>
  </article>
</template>
