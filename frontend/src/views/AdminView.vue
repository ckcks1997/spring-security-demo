<template>
  <div class="admin-container">
    <div class="action-bar">
    <h2>사용자 관리</h2>
      <el-button type="primary" @click="showAddUserModal">사용자 추가</el-button>
    </div>
    <el-table :data="users" style="width: 100%">
      <el-table-column prop="id" label="ID" width="100" />
      <el-table-column prop="userId" label="User ID" width="120" />
      <el-table-column prop="name" label="User NM" width="200" />
      <el-table-column prop="authority" label="User AUTH" width="150" />
      <el-table-column label="버튼" width="200">
        <template #default="scope">
          <el-button size="small" @click="editUser(scope.row)">수정</el-button>
          <el-button size="small" type="danger" @click="deleteUser(scope.row)">삭제</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="editDialogVisible" :title="isAdding ? '사용자 추가' : '사용자 수정'">
      <el-form :model="editedUser" label-width="120px">
        <el-form-item label="ID" v-if="isAdding">
          <el-input v-model="editedUser.userId" />
        </el-form-item>
        <el-form-item label="이름">
          <el-input v-model="editedUser.name" />
        </el-form-item>
        <el-form-item label="비밀번호" v-if="isAdding">
          <el-input v-model="editedUser.password" type="password" />
        </el-form-item>
        <el-form-item label="권한">
          <el-select v-model="editedUser.authority">
            <el-option label="User" value="USER" />
            <el-option label="Admin" value="SYSTEM_ADMIN" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">취소</el-button>
          <el-button type="primary" @click="saveUser">저장</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { api } from '../boot/axios'

const users = ref([])
const editDialogVisible = ref(false)
const editedUser = ref({})
const isAdding = ref(false)

onMounted(async () => {
  await fetchUsers()
})

const fetchUsers = async () => {
  try {
    const response = await api.get('/admin/users')
    users.value = response.data
  } catch (error) {
    console.error('user조회 실패', error)
    ElMessage.error('user조회 실패')
  }
}

const showAddUserModal = () => {
  isAdding.value = true
  editedUser.value = { userId: '', name: '', password: '', authority: 'USER' }
  editDialogVisible.value = true
}

const editUser = (user) => {
  isAdding.value = false
  editedUser.value = { ...user }
  editDialogVisible.value = true
}

const saveUser = async () => {
  try {
    if (isAdding.value) {
      await api.post('/admin/users', editedUser.value)
      ElMessage.success('사용자가 추가되었습니다.')
    } else {
      await api.put(`/admin/users/${editedUser.value.id}`, editedUser.value)
      ElMessage.success('사용자 정보가 업데이트되었습니다.')
    }
    editDialogVisible.value = false
    await fetchUsers()
  } catch (error) {
    console.error('사용자 저장 실패', error)
    if (error.response && error.response.data && error.response.data.message) {
      ElMessage.error(error.response.data.message)
    } else {
      ElMessage.error('사용자 저장에 실패했습니다.')
    }
  }
}
const deleteUser = async (user) => {
  try {
    await ElMessageBox.confirm('유저를 삭제하시겠습니까?', 'Warning', {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning'
    })
    await api.delete(`/admin/users/${user.id}`)
    await fetchUsers()
    ElMessage.success('삭제되었습니다.')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('실패')
    }
  }
}
</script>

<style scoped>
.admin-container {
  padding: 20px;
}

.action-bar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>