<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">
          <slot name="header">
            Thêm mới thành viên
          </slot>
        </div>

        <div class="modal-body">
          <slot name="body">
            <form-create-user-admin />
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
import FormCreateUserAdmin from "./FormCreateUserAdmin.vue";
export default {
  name: "ModalCreateUserAdmin",
  components: { FormCreateUserAdmin },
};
</script>

<style src="@/assets/css/modal.css">
</style>