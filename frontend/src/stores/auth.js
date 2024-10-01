import { defineStore } from 'pinia'
import { api } from '../boot/axios'


export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
        token: localStorage.getItem('token') || null,
        tokenPayload: null
    }),
    getters: {
        isLoggedIn: (state) => state.token,
        isAdmin: (state) => state.user?.authority === 'ROLE_SYSTEM_ADMIN',
    },
    actions: {
        async login(username, password) {
            try {
                const response = await api.post('/auth/login', { username, password })
                console.log(response.data.token)
                this.token = response.data.token
                localStorage.setItem('token', response.data.token)
                this.fetchUser()// 성공했으면 me 조회
                return true
            } catch (error) {
                console.error('Login 실패', error)
                return false
            }
        },
        logout() {
            this.user = null
            this.token = null
            localStorage.removeItem('token')
        },
        async fetchUser() {
            try {
                const response = await api.get('/auth/me')
                this.user = response.data
                console.log(this.user)
            } catch (error) {
                console.error('유저정보 조회 실패', error)
                this.logout()
            }
        }
    }
})