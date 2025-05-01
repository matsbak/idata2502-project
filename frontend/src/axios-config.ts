import axios from 'axios'

const url = import.meta.env.VITE_APP_BASE_URL || 'http://localhost:8081'

const instance = axios.create({
  baseURL: url
})

export default instance
