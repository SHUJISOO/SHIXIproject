<template>
  <div>
    <AdminHome v-if="userInfo.role === '管理员'" :userInfo="userInfo"></AdminHome>
    <CompanyHome v-if="userInfo.role === '企业'" :userInfo="userInfo"></CompanyHome>
  </div>
</template>

<script>
import AdminHome from "@/components/back/AdminHome.vue"
import CompanyHome from "@/components/back/CompanyHome.vue"

export default {
  data() {
    return {
      userInfo: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  components: {
    AdminHome,
    CompanyHome
  }
}
</script>
