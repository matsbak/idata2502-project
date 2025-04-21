import type { Todo } from './Todo.ts'

export interface List {
  id: number,
  title: string
  todos?: Array<Todo>
}
