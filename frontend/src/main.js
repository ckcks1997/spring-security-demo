import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { useAuthStore } from './stores/auth'
import './style.css'

const app = createApp(App)

app.use(ElementPlus)
app.use(createPinia())
app.use(router)

const authStore = useAuthStore()
if (authStore.token) {
    authStore.fetchUser()
}

app.mount('#app')