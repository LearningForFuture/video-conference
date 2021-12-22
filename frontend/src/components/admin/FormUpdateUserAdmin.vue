<template>
  <form class="validate-form" @submit.prevent="updateUser">
    <div class="input-form">
      <label
        for="validation-form-1"
        class="form-label w-full flex flex-col sm:flex-row"
      >
        Họ và tên
      </label>
      <input
        id="validation-form-1"
        v-model="user.fullName"
        type="text"
        name="name"
        class="form-control"
        placeholder="John Legend"
        minlength="2"
      />
    </div>
    <div class="input-form mt-3">
      <label
        for="validation-form-2"
        class="form-label w-full flex flex-col sm:flex-row"
      >
        Tên đăng nhập
      </label>
      <input
        id="validation-form-2"
        v-model="user.username"
        type="text"
        name="username"
        class="form-control"
        placeholder="username"
        required
      />
    </div>
    <div class="input-form mt-3">
      <label
        for="validation-form-3"
        class="form-label w-full flex flex-col sm:flex-row"
      >
        Email
      </label>
      <input
        id="validation-form-3"
        v-model="user.email"
        type="email"
        name="email"
        class="form-control"
        placeholder="example@gmail.com"
        required
      />
    </div>
    <div class="input-form mt-3">
      <label
        for="validation-form-4"
        class="form-label w-full flex flex-col sm:flex-row"
      >
        Mật khẩu
      </label>
      <input
        id="validation-form-4"
        v-model="user.password"
        type="password"
        name="password"
        class="form-control"
        placeholder="secret"
        minlength="6"
      />
    </div>
    <div class="input-form mt-3">
      <label
        for="validation-form-5"
        class="form-label w-full flex flex-col sm:flex-row"
      >
        Phân quyền
      </label>
      <span v-for="role in roles" :key="role.roleId" class="mr-4">
        <input
          :id="role.roleName"
          v-model="user.roleId"
          type="checkbox"
          :value="role.roleId"
        />
        <label :for="role.roleName">{{ role.roleName }}</label>
      </span>
    </div>
    <button type="submit" class="btn btn-primary mt-5">Cập nhật</button>
  </form>
</template>

<script>
import UserService from "../../services/UserService.js";
import RoleService from "../../services/RoleService.js";
const userService = new UserService();
const roleService = new RoleService();
export default {
  name: "FormUpdateUserAdmin",
  inject: ["appData"],
  data() {
    return {
      user: {
        userId: null,
        fullName: null,
        username: null,
        password: null,
        email: null,
        roleId: [],
      },
      roles: null,
    };
  },
  created() {
    this.getUserId(this.appData.userUpdateId);
    this.getAll();
  },
  methods: {
    async updateUser() {
      console.log(this.user);
      const response = await userService.updateUser(this.user);
      console.log(response.data);
    },
    async getUserId(userId) {
      const response = await userService.findById(userId);
      console.log(response.data);
      this.user.userId = response.data.userId;
      this.user.fullName = response.data.fullName;
      this.user.username = response.data.username;
      this.user.email = response.data.email;
      this.user.roleId = [...response.data.roles.map((x) => x.roleId)];
    },
    async getAll() {
      const response = await roleService.getRoles();
      this.roles = [...response.data];
    },
  },
};
</script>
<style src="../../assets/css/app.css" scoped>
</style>