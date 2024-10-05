<template>
  <div class="home-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <h2>회원 시스템</h2>
        </div>
      </template>
      <div v-if="isLoggedIn">
        <h3 v-if="!isAdmin">{{ user?.name }}님, 환영합니다!</h3>
        <p v-if="isAdmin">관리자 계정입니다.</p>
        <el-button v-if="isAdmin" type="success" @click="$router.push('/admin')">Admin 회원관리</el-button>
      </div>
      <div v-else>
        <p>로그인이 필요합니다.</p>
        <el-button type="primary" @click="$router.push('/login')">로그인</el-button>
        <el-button type="success" @click="$router.push('/signup')">회원가입</el-button>
      </div>
    </el-card>

  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()

const isLoggedIn = computed(() => authStore.isLoggedIn)
const isAdmin = computed(() => authStore.isAdmin)
const user = computed(() => authStore.user)
</script>

<style scoped>
.home-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}
.box-card {
  margin-bottom: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.mt-20 {
  margin-top: 20px;
}
</style>