<template>
  <div class="admin-container">
    <h2>사용자 관리</h2>
    <el-table :data="users" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="userId" label="User ID" width="120" />
      <el-table-column prop="name" label="User NM" width="120" />
      <el-table-column prop="authority" label="User AUTH" width="120" />
      <el-table-column label="버튼" width="200">
        <template #default="scope">
          <el-button size="small" @click="editUser(scope.row)">수정</el-button>
          <el-button size="small" type="danger" @click="deleteUser(scope.row)">삭제</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="editDialogVisible" title="Edit User">
      <el-form :model="editedUser" label-width="120px">
        <el-form-item label="Name">
          <el-input v-model="editedUser.name" />
        </el-form-item>
        <el-form-item label="Authority">
          <el-select v-model="editedUser.authority">
            <el-option label="User" value="ROLE_USER" />
            <el-option label="Admin" value="ROLE_SYSTEM_ADMIN" />
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
import {ref, onMounted} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {api} from '../boot/axios'

const users = ref([])
const editDialogVisible = ref(false)
const editedUser = ref({})

onMounted(async () => {
  try {
    const response = await api.get('/admin/users')
    users.value = response.data
  } catch (error) {
    console.error('user조회 실패', error)
    ElMessage.error('user조회 실패')
  }
})

const editUser = (user) => {
  editedUser.value = {...user}
  editDialogVisible.value = true
}

const saveUser = async () => {
  try {
    await api.put(`/admin/users/${editedUser.value.id}`, editedUser.value)
    const index = users.value.findIndex(u => u.id === editedUser.value.id)
    users.value[index] = {...editedUser.value}
    editDialogVisible.value = false
    ElMessage.success('user 업데이트 완료')
  } catch (error) {
    console.error('user 업데이트 실패', error)
    ElMessage.error('user 업데이트 실패')
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
    users.value = users.value.filter(u => u.id !== user.id)
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

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>