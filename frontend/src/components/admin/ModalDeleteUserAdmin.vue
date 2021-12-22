<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">
          <slot name="header"> Bạn có chắc chắn muốn xóa người dùng này? </slot>
        </div>

        <div class="modal-body">
          <slot name="body">
            <button
              type="button"
              class="btn btn-danger mt-5"
              @click.prevent="deleteUser"
            >
              Delete
            </button>
            <button
              type="button"
              class="btn btn-primary mt-5"
              @click="$emit('close')"
            >
              Close
            </button>
          </slot>
        </div>

        <!-- <div class="modal-footer">
          <slot name="footer">
            default footer
            <button class="modal-default-button" @click="$emit('close')">
              OK
            </button>
          </slot>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "../../services/UserService.js";
const userService = new UserService();
export default {
  name: "ModalDeleteUserAdmin",
  props: {
    userId: {
      type: Number,
      require: true,
    },
  },
  methods: {
    async deleteUser() {
      const response = await userService.delete(this.userId);
      console.log(response.status);
    },
  },
};
</script>

<style src="@/assets/css/modal.css" scoped>
</style>