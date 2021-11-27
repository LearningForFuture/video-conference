<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">
          <slot name="header">
            Cập nhật thành viên
          </slot>
        </div>

        <div class="modal-body">
          <slot name="body">
            <form-update-user-admin />
            <button
              type="button"
              class="btn btn-primary mt-5"
              @click="$emit('close')"
            >
              Close
            </button>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormUpdateUserAdmin from "./FormUpdateUserAdmin.vue";
export default {
  name: "ModalUpdateUserAdmin",
  components: { FormUpdateUserAdmin },
};
</script>

<style src="@/assets/css/modal.css">
</style>