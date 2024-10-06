<template>
  <el-container class="layout-container">
    <el-header>
      <el-menu mode="horizontal" class="flex align-items-center" :router="true">
        <h4 @click="router.push('/')">회원 관리 시스템</h4>
        <el-button type="danger" class="mx-3" v-if="isLoggedIn" @click="logout">로그아웃</el-button>
      </el-menu>
    </el-header>

    <el-container>
      <el-aside width="200px">
        <el-menu
            default-active="/"
            class="el-menu-vertical-demo"
            :router="true"
        >
          <el-menu-item v-for="link in visibleLinks" :key="link.title" :index="link.link">
            <el-icon><component :is="link.icon" /></el-icon>
            <span>{{ link.title }}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from './stores/auth'
import { HomeFilled, UserFilled, Key, Plus, Setting } from '@element-plus/icons-vue'

const router = useRouter()
const authStore = useAuthStore()
const isLoggedIn = computed(() => authStore.isLoggedIn)
const isAdmin = computed(() => authStore.isAdmin)
const menuLinks = [
  {
    title: '메인페이지',
    icon: HomeFilled,
    link: '/'
  },
  {
    title: '로그인',
    icon: Key,
    link: '/login'
  },
  {
    title: '회원가입',
    icon: Plus,
    link: '/signup'
  },
  {
    title: '사용자 관리',
    icon: Setting,
    link: '/admin'
  }
]
const visibleLinks = computed(() => {
  if (!isLoggedIn.value) { //비로그인시 로그인/가입만
    return menuLinks.filter(link => ['메인페이지', '로그인', '회원가입'].includes(link.title))
  } else {//로그인시 로그인/가입 삭제, admin만 admin show
    const links = menuLinks.filter(link => !['로그인', '회원가입'].includes(link.title))
    return isAdmin.value ? links : links.filter(link => link.title !== '사용자 관리')
  }
})

const logout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-header {
  background-color: #fff;
  color: #333;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
}
</style>