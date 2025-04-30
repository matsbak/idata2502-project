import instance from '../axios-config'
import type { List } from '../types/List'

export const getAll = async (): Promise<List[]> => {
  let lists: List[] = []
  try {
    const res = await instance.get('/api/lists')
    const status = res.status
    if (res.status === 200) {
      console.log('Fetched all lists', status)
      lists = res.data
    } else {
      console.error('Could not fetch all lists', status)
    }
  } catch (err: any) {
    console.error('Error fetching all lists:', err)
  }
  return lists
}
