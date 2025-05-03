import axios from 'axios'

let baseUrl = ''

if (window.location.host === 'localhost:5173') {
  baseUrl = '/dev'
} else {
  baseUrl = '/prod'
}

const instance = axios.create({
  baseURL: baseUrl
})

export default instance
