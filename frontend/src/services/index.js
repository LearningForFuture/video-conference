import axios from 'axios';

export default axios.create({
  baseURL: process.env.VUE_APP_SERVICE_ENDPOINT || 'https://192.168.1.3:3000/',
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  },
  timeout: 1500,
  withCredentials: true
})