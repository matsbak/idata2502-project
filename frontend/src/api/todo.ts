import instance from '../axios-config'

export const postTodo = async (listId: number, description: string): Promise<number> => {
  let id: number = -1
  try {
    const res = await instance.post('/api/todos/' + listId, {
      'description': description
    })
    const status = res.status
    if (status === 201) {
      console.log('Added todo to list', status)
      id = res.data
    } else if (status === 400) {
      console.error('Could not add todo to list (invalid description)', status)
    } else if (status === 404) {
      console.error('Could not add todo to list (list not found)', status)
    } else {
      console.error('Could not add todo', status)
    }
  } catch (err: any) {
    console.error('Error adding todo', err)
  }
  return id
}

export const updateTodo = async (id: number, complete: boolean): Promise<void> => {
  try {
    const res = await instance.put('/api/todos/' + id, {
      'complete': complete
    })
    const status = res.status
    if (status === 200) {
      console.log('Updated todo', status)
    } else if (status === 404) {
      console.error('Could not update todo (not found)', status)
    } else {
      console.error('Could not update todo', status)
    }
  } catch (err: any) {
    console.error('Error updating todo', err)
  }
}

export const deleteTodo = async (id: number): Promise<void> => {
  try {
    const res = await instance.delete('/api/todos/' + id)
    const status = res.status
    if (status === 200) {
      console.log('Deleted todo', status)
    } else if (status === 404) {
      console.error('Could not delete todo (not found)', status)
    } else {
      console.error('Could not delete todo', status)
    }
  } catch (err: any) {
    console.error('Error deleting todo', err)
  }
}
