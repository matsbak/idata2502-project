import instance from '../axios-config'
import type { List } from '../types/List'

export const getLists = async (): Promise<List[]> => {
  let lists: List[] = []
  try {
    const res = await instance.get('/api/lists')
    const status = res.status
    if (status === 200) {
      lists = res.data
    } else {
      console.error('Could not fetch all lists', status)
    }
  } catch (err: any) {
    console.error('Error fetching all lists:', err)
  }
  return lists
}

export const postList = async (title: string): Promise<number> => {
  let id: number = -1
  try {
    const res = await instance.post('/api/lists', {
      'title': title
    })
    const status = res.status
    if (status === 201) {
      id = res.data
    } else if (status === 400) {
      console.error('Could not add list (invalid title)', status)
    } else {
      console.error('Could not add list', status)
    }
  } catch (err: any) {
    console.error('Error adding list', err)
  }
  return id
}

export const deleteList = async (id: number): Promise<void> => {
  try {
    const res = await instance.delete('/api/lists/' + id)
    const status = res.status
    if (status === 200) {
      // Intentionally left blank
    } else if (status === 404) {
      console.error('Could not delete list (not found)', status)
    } else {
      console.error('Could not delete list')
    }
  } catch (err: any) {
    console.error('Error deleting list', err)
  }
}
