<template>
  <div class="signup-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <h2>회원가입</h2>
        </div>
      </template>
      <el-form :model="signupForm" :rules="rules" ref="signupFormRef">
        <el-form-item prop="userId">
          <el-input v-model="signupForm.userId" placeholder="ID"></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input v-model="signupForm.name" placeholder="이름"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="signupForm.password" type="password" placeholder="비밀번호"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="signupForm.confirmPassword" type="password" placeholder="비밀번호 재입력"></el-input>
        </el-form-item>
          <p style="font-size: 0.8rem;">참고: 해당 페이지에서는 USER권한의 회원가입만 가능합니다.</p>
          <p style="font-size: 0.8rem;">Admin 권한의 계정은 Admin 로그인 후 회원관리 페이지에서 생성해 주세요.</p>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">가입</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import {ref, reactive} from 'vue'
import {ElMessage} from 'element-plus'
import {useRouter} from 'vue-router'
import {api} from '../boot/axios'

const router = useRouter()

const signupFormRef = ref(null)
const signupForm = reactive({
  userId: '',
  name: '',
  password: '',
  confirmPassword: ''
})

const rules = {
  userId: [{required: true, message: 'ID를 입력새 주세요', trigger: 'blur'}],
  name: [{required: true, message: '값을 입력해 주세요', trigger: 'blur'}],
  password: [
    {required: true, message: '비밀번호를 입력하세요', trigger: 'blur'},
    {min: 8, message: '8자리 이상 입력해 주세요', trigger: 'blur'}
  ],
  confirmPassword: [
    {required: true, message: '비밀번호를 재입력하세요', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        if (value !== signupForm.password) {
          callback(new Error('비밀번호가 일지하지 않습니다'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const onSubmit = async () => {
  if (!signupFormRef.value) return

  await signupFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const response = await api.post('/auth/signup', {
          userId: signupForm.userId,
          name: signupForm.name,
          password: signupForm.password
        })
        if (response.data) {
          ElMessage.success('회원가입이 완료되었습니다.')
          await router.push('/login')
        }
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message) {
          ElMessage.error(error.response.data.message)
        } else {
          ElMessage.error('회원가입 중 오류가 발생했습니다.')
        }
      }
    } else {
      return false
    }
  })
}
</script>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.box-card {
  width: 400px;
}
</style>