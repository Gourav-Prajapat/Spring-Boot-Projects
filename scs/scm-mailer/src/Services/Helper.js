import axios from 'axios'

const baseURL = "http://localhost:8080/api"

// axios.defaults.baseURL = '/api';

export const customAxios = axios.create({
    baseURL : baseURL,
    timeout: 10000,
})