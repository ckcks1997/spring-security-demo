import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import {useAuthStore} from "../stores/auth.js";

const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomeView
    },
    {
        path: '/login',
        name: '로그인',
        component: () => import('../views/LoginView.vue')
    },
    {
        path: '/signup',
        name: '회원가입',
        component: () => import('../views/SignupView.vue')
    },
    {
        path: '/admin',
        name: 'Admin 회원관리',
        component: () => import('../views/AdminView.vue'),
        meta: { requiresAuth: true, requiresAdmin: true }
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: '/'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore()

    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!authStore.isLoggedIn) {
            next({ name: 'Login', query: { redirect: to.fullPath } })
        } else if (to.matched.some(record => record.meta.requiresAdmin) && !authStore.isAdmin) {
            next({ name: 'Home' })
        } else {
            next()
        }
    } else {
        next()
    }
})

export default router