<template>
  <transition name="fade">
    <div
      v-if="show"
      class="modal"
    >
      <div
        class="modal__backdrop"
        @click="closeModal()"
      />

      <div class="modal__dialog">
        <form
          action=""
          @submit.prevent="handleSubmit"
        >
          <div class="modal__header">
            <slot name="header" />
            <button
              type="button"
              class="modal__close"
              @click="closeModal()"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-x-circle"
                viewBox="0 0 16 16"
              >
                <path
                  color="#FF0000"
                  d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"
                />
                <path
                  color="#FF0000"
                  d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
                />
              </svg>
            </button>
          </div>

          <div class="modal__body">
            <slot name="body" />
          </div>

          <div class="modal__footer">
            <slot name="footer" />
          </div>
        </form>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'Modalform',

  data() {
    return {
      show: false
    };
  },

  mounted() {

  },

  methods: {
    closeModal() {
      this.show = false;
    },
    openModal() {
      this.show = true;
    },
    handleSubmit() {
      this.$emit('handleSubmit');
    }
  },
};
</script>

<style lang="css" scoped>
.modal {
    overflow-x: hidden;
    overflow-y: auto;
    display: block;
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 9;
    visibility: visible;
    opacity: 1;
}

.modal__backdrop {
    background-color: rgba(0, 0, 0, 0.3);
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1;
}

.modal__dialog {
    background-color: #fff;
    position: absolute;
    top: 10%;left: 50%;
    transform: translateX(-50%);
    width: 600px;
    margin: 50px auto;
    display: flex;
    flex-direction: column;
    border-radius: 5px;
    z-index: 2;
}

@media screen and (max-width: 992px) {
    .modal__dialog {
        width: 90%;
    }
}

.modal__close svg {
    widows: 1.5rem;
}

.modal__close {
    outline: none;
    border: none;
    background: none;
}

.modal__header {
    padding: 20px 20px 10px;
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
}

.modal__body {
    padding: 10px 20px 10px;
    overflow: auto;
    display: flex;
    flex-direction: column;
    align-items: stretch;
}

.modal__footer {
    padding: 10px 20px 20px;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s;
}

.fade-enter,
.fade-leave-to {
    opacity: 0;
}
</style>
